#  MSA Project



#### Setting

```
data/UsersRepository
- repository 내 findByEmail()

model/LoginRequestModel
- 사용자 입력 내용 설정

security/AuthenticationFilter
- 클래스 생성

service/UserServiceImpl
- loadUserByUsername() , getUserDetailByEmail() 추가

myapp-api-user , myapp-zuul-gateway
- bootstrap.yml 추가 : gateway.ip , token.secret 정보 포함되어 있음
(※ Consfig Server에 application.yml을 설정하여, 각 프로젝트에서 이를 호출하여 사용하도록 한다.)
```





## 1. Login - Authorization Token

> Login 시 발급되는 Token을 이용하여 접근 권한 설정하기



- #### LoginRequestModel

  ```java
  @Data
  public class LoginRequestModel {
      private String email;
      private String password;
  
  }
  ```

  - 사용자의 로그인 화면. email, password만 입력 받는다.



- #### WebSecurity

  ```java
  @Configuration
  @EnableWebSecurity
  public class WebSecurity extends WebSecurityConfigurerAdapter {
  
      private Environment env;
      private UserService userService;
      private BCryptPasswordEncoder bCryptPasswordEncoder;
  
      @Autowired
      public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
          this.env = env;
          this.userService=userService;
          this.bCryptPasswordEncoder=bCryptPasswordEncoder;
  
      }
  
      //Login - Authorization Token
      private AuthenticationFilter getAuthenticationFilter() throws Exception {
          AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env, authenticationManager());
          authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
          return authenticationFilter;
      }
  }
  ```
  - `env.getProperty("login.url.path")` : `application.yml`에 저장되어 있는 **login.url**  값을 받는다.
  - `UserEntity`와 `Environment`에 저장되어 있는 값은 `AuthenticationFilter` 클래스로 이동된다.



- #### AuthenticationFilter

  ```java
  public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  
      private UserService userService;
      private Environment env;
  
      public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {
  
          this.userService = userService;
          this.env = env;
          super.setAuthenticationManager(authenticationManager);
      }
  
      @Override
      public Authentication attemptAuthentication(HttpServletRequest request,
                                                  HttpServletResponse response) throws AuthenticationException {
          try {
              //Postman을 통해 입력받은 값을 LoninRequestModel 객체와 Mapping
              LoginRequestModel creds = new ObjectMapper()
                      .readValue(request.getInputStream(), LoginRequestModel.class);
  
              //입력받은 email, password를 이용하여 사용자에게 Token을 발급
              return getAuthenticationManager().authenticate(
                      new UsernamePasswordAuthenticationToken(
                              creds.getEmail(),
                              creds.getPassword(),
                              new ArrayList<>()
                      ));
  
          } catch (IOException ex) {
              throw new RuntimeException();
          }
      }
  
      @Override
      protected void successfulAuthentication(HttpServletRequest req,
                                              HttpServletResponse res,
                                              FilterChain chain, Authentication auth) throws IOException, ServletException {
  
          String email = ((User) auth.getPrincipal()).getUsername();
          UserDto userDetail = userService.getUserDetailByEmail(email);
  
          //generate token with userID(email)
          //token expire_date (configuration or application.yml)
          String token = Jwts.builder()
                  .setSubject(userDetail.getUserId())
                  .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                  .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                  .compact();
          res.addHeader("token", token);
          res.addHeader("userId", userDetail.getUserId());
      }
  
  }
  ```

  



- #### UserServiceImpl

  > UserService에 getUserDetailByEmail() 메서드를 UserDto Type으로 추가한다.

  ```java
      @Override
      public UserDto getUserDetailByEmail(String email) {
          UserEntity userEntity = repository.findByEmail(email);
  
          if (userEntity == null) {
              throw new UsernameNotFoundException(email);
          }
          //UserEntity -> UserDto (using ModelMapper)
          return new ModelMapper().map(userEntity, UserDto.class);
  
      }
  ```

  - 사용자에게 입력받은 **email**을 `UserEntity`에서 찾고, 이를 `ModelMapper() `를 이용하여 `UserDto`로 변환한다.

  

## 2. Spring Cloud Bus

> Config Server에서 전달하고 싶은 내용 혹은 변경사항을 Application Server에 자동으로 Push



![xspring_cloud_config-2 png pagespeed ic Tg3ksvkwZk](https://user-images.githubusercontent.com/58682321/81909584-c4dc5b80-9605-11ea-8178-544db5e63a23.png)



### 2.1) Dependency 설정



- #### ConfigServer

  ```xml
          <!--spring cloud bus -->
          <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-bus-amqp</artifactId>
          </dependency>
  
          <!-- actuator -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-actuator</artifactId>
          </dependency>
  ```

  

- #### Zuul, api-users Server

  ```xml
          <!--spring cloud bus -->
          <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-bus-amqp</artifactId>
          </dependency>
  ```

  

### 2.2) RabbitMQ

> Docker를 이용하여 RabbitMQ 실행
>
> [RabbitMQ download](https://www.rabbitmq.com/)



**Docker Run**

```powershell
C:\Users\HPE\Work\git\MyAppConfiguration>docker run -d --name rabbitmq -p 5672:5672 -p 9090:15672 --restart=unless-stopped -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:management
```



- #### application.yml

  > Config, Zuul, API-User 모두 아래와 같이 설정해준다.
	```yaml
	  rabbitmq:
    host: localhost
    port: 5672
    username: admin
    password: admin
	```
	



#### 실행순서

---

> **RabbitMQ Server**  → **Discovery Server** → **Zuul Server** → **User-WS**



![bus_rabbitmq](https://user-images.githubusercontent.com/58682321/81909988-5b108180-9606-11ea-86a9-cfb1b9995c3c.png)

[출처](https://handcoding.tistory.com/186)



**※** **Config server에서 변동 사항이 발생**했을 때, **Spring Cloud Bus** 를 이용하면 각 **Server 마다 Refresh를 해주지 않아도 된다.**  이 때, **Spring Cloud Bus**는 **RabbitMQ**에게 **등록된 서버 마다 환경 파일을 Refresh하여 새롭게 캐싱**하라고 알려준다.



#### Postman - Refresh

회원 등록 → 로그인(**Token 발급**)  →  **<변동 사항 발생>** →  **Refresh** → 로그인(**새로운 Token 발급**) → 변경된 정보 확인(**GET**) 

![refresh](https://user-images.githubusercontent.com/58682321/81910150-85fad580-9606-11ea-9c99-9dee561cf03a.PNG)


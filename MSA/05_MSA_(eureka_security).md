# Eureka 

> 마이크로 서비스의 실행 상태를 파악할 수 있는 Eureka  서버에 접근 권한 설정하기



### 1. Setting

```
[Discovery Server]
- spring-cloud-starter-config
- spring-boot-starter-security

[Config yml file]
- DiscoveryService.yml
```



- #### application.yml

  ```yaml
  server:
    port: 8010
  
  spring:
    application:
      name: DiscoveryService
  #  security:
  #    user:
  #      name: test
  #      password: test
  
  
  eureka:
    client:
      registerWithEureka : false
      fetchRegistry : false
      serviceUrl:
        defaultZone : http://localhost:${server.port}/eureka
  ```

  - 기존에는 `myapp-discovery-service`의 `application.yml`에 **security** 를 설정했다. 외부에 노출될 수 있기 때문에 **Config Server**에서 통합 관리할 수 있도록 한다.



- #### security/WebSecurity

  > security 설정. pom.xml에 spring-boot-starter-security가 설정 되어 있어야 함

  ```java
  @Configuration
  @EnableWebSecurity
  public class WebSecurity extends WebSecurityConfigurerAdapter {
  
      @Override
      public void configure(HttpSecurity http) throws Exception {
          http.csrf().disable()
                  .authorizeRequests()
                  .anyRequest().authenticated()
                  .and()
                  .httpBasic();
      }
  }
  
  ```



- #### DiscoveryService.yml

  > dev/DiscoveryService.yml 
  >
  > Eureka Server를 통합 관리하기 위한 yml 파일

  ```yaml
  spring:
      security:
          user:
              name: test
              password: '{cipher}AQAnDDHtuC0EmwTvpax8miQXYyk92YN+Skja70QZ8Gkta0LC8zQggx0y1srDQysmR7/XkJAvr8MjRFebsbtIPkjBWTQVxulCHJ0GDYYkLR0R0QUlMMVMb5dh2AiZGcjBXyii3p+JlvaBXxZqhKoYncNCahNJsmiBHgy0r54/SyFnfOGJXhHikjTJwZWhKgi8hrjjYEcw0+9puZCkaAZ5I5yIWbn5lN/z4cdVZEPTLLYgrvXOfOZTmqxURQ6BKhKbbQ1DVYZvaS9KO7QI2Dy3kiyxr+6l5rf8AIWptRRcQI65eaeneeiZZiErmIKOB2MIymwfPmGZ0UI/RQluPpx7CP9GSvedf+hsxP2f00qfX58nXj/g+RMymFmdp/PoGRIjQY8='
  ```

  - `localhost:8012/encrypt` 를 통해 **password** 값을 **encoding** 해서 저장한다.



### 2. Eureka Server Start

> myapp-discovery-server : bootstrap.yml

- #### bootstrap.yml

  > Service가 가동될 때, 가장 먼저 실행되는 설정 파일
  >
  > 해당 파일에 Config Server에 대한 정보를 설정해야 한다.

  ```yaml
  spring:
    cloud:
      config:
        uri: http://localhost:8012
        name: DiscoveryService
  ```

  - `myapp-config-sever`는 `spring.cloud.config.server.native.search-locations.<File Path>`(profiles.active.native)를 참조하여 서버를 가동한다. `myapp-discovery-server`는 이러한 **config 설정 파일**과, 동일한 **config.name**을 가진 설정 파일을 읽어온다.

  

- 이후, `myapp-config-server` 와 `myapp-discovery-server`를 순서대로 가동하고, `localhost:8010`으로 접속하면, 로그인 창이 뜨며, yml에 설정한 Id, Password 값을 입력하면 Monitoring 화면이 보이는 것을 확인할 수 있다. 



- #### ※ Eureka Client

  > Eureka Client로 설정한 서비스들은 아래와 같이 설정해야 한다.

  ```yaml
  #Eureka Client
  eureka:
    client:
      serviceUrl:
        defaultZone: http://test:test@localhost:8010/eureka
  ```

  - `defaultZone: http://<Id>:<Password>@localhost:8010/eureka`
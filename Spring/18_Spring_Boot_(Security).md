# Security



## 1.  Spring Security 소개



**인증 방법**

- Basic 인증, Form 인증, OAuth, LDAP



**Spring Boot Security 자동 설정**

: SecurityAutoConfiguration

: UserDetailsServiceAutoConfiguration

: 모든 요청에 인증이 필요함

: 기본 사용자를 자동으로 생성해준다.





## 2. Basic 인증



### 2.1 의존성 추가

- **Spring boot security 의존성 추가**

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>     		
      <artifactId>spring-boot-starter-security</artifactId> </dependency>
  ```

  - 이후, 서버를 가동하면 **Console** 창에 **security password** 값이 나온다.

- `localhost:8085` 접속하면 로그인 화면 생성. **Default ID**  값은 **user**
- **Application/Cookies**에 **JSESSIONID**라는 기록이 남는다. 해당 기록이 있으면 다음 접속 부터는 로그인을 따로 하지 않아도 된다.



### 2.2 로그인 정보 페이지 작성

- **UserController**

  ```java
  	@GetMapping("/mypage")
  	public String mypage() {
  		return "mypage";
  	}
  ```

- **mypage(template)**

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
      <!-- 생략 -->
  <h2>마이페이지</h2>
  	<div>
  		로그인 한 UserName : [[${#httpServletRequest.remoteUser}]]
  		<form th:action="@{/app-logout}" method="post">
  			<input type="submit" value="로그아웃">
  		
  		</form>
  	</div>
  ```

  - **Thymleaf** 사용



### 2.3 SecurityConfig 클래스 작성

> mypage URL 경로에만 인증 요청
>
> 로그아웃 기능 추가



- **Config**

  ```java
  @Configuration
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
  	
  	@Override
  	protected void configure(HttpSecurity http) throws Exception {
  		http.authorizeRequests()
  			.antMatchers("/mypage/**").authenticated() //문자열 패턴
  			.antMatchers("/**").permitAll() // mypage 외에는 인증 처리를 하지 않겠다.
  			.and()
  			.formLogin()
  			.and()
  			.httpBasic()
  			.and()
  			.logout()
  			.logoutUrl("/app-logout")
  			.deleteCookies("JSESSIONID") //cookie 삭제
  			.logoutSuccessUrl("/");
  	}
  
  }
  ```

  - **mypage URL**에 접속했을 시에만 인증 요청.
  - **로그아웃** 이후 **Cookie**를 삭제해, 로그인 정보를 삭제한다.



### 2.4 AccountService 클래스 작성

> AccountService 클래스에 Account를 생성하는 createAccount() 메서드 추가



- **AccountService**

  ```java
  @Service
  public class AccountService {
  	@Autowired
  	private AccountRepository accountRepository;
  	
  	public Account createAccount(String username, String password) {
  		Account account = new Account();
  		account.setUsername(username);
  		account.setPassword(password);
  		return accountRepository.save(account);
  	}
  	//@PostConstruct : AccountService 클래스가 Bean으로 생성된 후에 init() method가 바로 호출됨
  	@PostConstruct
  	public void init() {
  		Optional<Account> findByUsername = accountRepository.findByUsername("test");
  		// 해당 user가 없으면, 등록
  		if (!findByUsername.isPresent()) {
  			Account createAccount = this.createAccount("test", "1234");
  			System.out.println(createAccount);
  		}
  	}
  }
  ```

  - **@PostConstruct** : **AccountService Bean**이 생성된 후에 바로 **createAccount()**메서드가 호출되도록 선언하는 어노테이션

    

- **UserDetailsService** 인터페이스 추가

  ```java
  @Service
  public class AccountService implements UserDetailsService {
  	@Autowired
  	private AccountRepository accountRepository;
  	
  	@Override //login 할 때 사용자가 입력한 정보가 유요한지 체크
  	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  		Optional<Account> findByUsername = accountRepository.findByUsername(username);
  		Account account = findByUsername.orElseThrow(()-> new UsernameNotFoundException(username));
  		return new User(account.getUsername(), account.getPassword(), authorities());
  	}
  	//User 객체의 세 번째 인자 USER라는 ROLE을 가진 사용자라고 설정
  	private Collection<? extends GrantedAuthority> authorities() {
  		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
  	}
  ```



#### ※ 위의 과정을 마치고 `localhost:8085`에서 test/1234 로 로그인

```
java.lang.IllegalArgumentException:   There is no PasswordEncoder mapped for the id "null"
```

- **오류 발생**
  - **Password를 Encording 하지 않았기 때문에 IllegalArgumentException 발생**



### 2.5 PasswordEncording

- `SecurityConfig.java`에 **PasswordEncoder()**를 **Bean**으로 등록한다.

- **SecurityConfig**

  ```java
  	@Bean
  	public PasswordEncoder passwordEncoder() {
  		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  	}
  ```

  

- **AccountService**

  ```java
  @Service 
  public class AccountService implements UserDetailsService {   		@Autowired  
      private PasswordEncoder passwordEncoder;    
      
      public Account createAccount(String username, String password) {   
          Account account = new Account();   
          account.setUsername(username);   
          account.setPassword(passwordEncoder.encode(password));   
          //account.setPassword(password);   
          return accountRepository.save(account);  } }
  ```

  - 등록된 **passwordEncoder Bean**을 주입 받아 **password**를 인코딩한다.





## 3. Account 등록 실습



- **UserController**

  ```java
  	@Autowired
  	private AccountService accountService;	
  
  	//Add Account
  	@GetMapping("/accountForm")
  	public String accountForm(Account account) {
  
  		return "add-account";
  	}
  	
  	@PostMapping("/addAccount")
  	public String addAccount(@ModelAttribute Account account) {
  		Account addAccount = accountService.createAccount(account.getUsername(), account.getPassword());
  		System.out.println(" >>>>>>> 등록된 Account : " + addAccount);
  		return "redirect:/index.html";
  //		index.html 로 표기해야 static/index로 이동한다. html을 입력하지 않으면 template/index로 이동
  ```

  - `localhost:8085`에서 **Add Account** 버튼을 클릭하면, **Account** 객체를 `add-account.html`에 전달한다.
  - `add-account.html`에서 **th:object:"${account}**에 들어있는 **account**는 변수 명이 아닌 **Account** 객체를 의미한다.
  - **@ModelAttribute**를 통해 `add-account.html`에서 입력한 값을 **Entity** 객체로 바인딩 한다.
  - `AccountService.java`에서 등록한 **createAccount()** 메서드를 통해 password를 **Encording** 한 후 **accountRepository**에 저장한다.
  - 위의 과정을 마치고, **MySQL DB**를 확인해보면, password가 **encording**된 Account 객체가 생성된 것을 확인할 수 있다.
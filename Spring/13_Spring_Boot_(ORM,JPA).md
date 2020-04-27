# Spring Boot



**Review**

```
[Spring Boot 특징]

1. stand-alone, production-grade
2. Configuration XML 설정하지 않는다.
3. 많이 사용되는 third-party Library들의 dependency 제공
4. boot-starter-web, boot-starter-test, boot-starter-data
5. Actuator 제공 - Monitoring
6. jar 형태로 배포 가능 (Terminal)
7. Spring initializer 
	https://start.spring.io/ 에서 project 시작 가능
	
8.Spring Boot Application Class
@SpringBootApplication
:@SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan

9.Java Config 프로젝트 기반으로 하고 있다.

1

```



## 1. Sprnig Boot 활용



### 1.1) Type-Safe Property 클래스 작성

> @ConfigurationProperties 사용



- **@Value("${abc.name}")** 와 같이 **기존 properties**를 사용하는 경우 **type-safe 하지 않아(오타와 같은 오류를 잡아내지 못함)** 에러가 나는 경우가 많다.

- **@ConfigurationProperties**를 사용하는 경우, **properties** 를 클래스로 생성하고, **application** 클래스에서 이를 **@Autowired** 같은 어노테이션을 통해 자동 주입한다. 

  

#### 예제)

- **properties.java**

  ```java
  @Component 
  @ConfigurationProperties("vega2k") 
  public class Vega2kProperties {  
      private String name;  
      private int age;  
      private String fullName;            
      getter();         
      setter(); 
  } 
  ```

  - **properties**를 **class 로 설정**하며, **getter/setter 메서드를 이용**한다.
  - **getter**는 **Runner**에서 호출하고, **setter**는 **프레임워크**에서 호출하여 **properties**에 있는 값 들을 대입시켜 준다.

  

- **MyRunner.java**

  ```java
  @Component
  public class MyRunner implements ApplicationRunner {     
  
  @Autowired     
  Vega2kProperties vega2kProperties; 
   
      public void run(ApplicationArguments args) throws Exception {  
          System.out.println(vega2kProperties.getName());  
          System.out.println(vega2kProperties.getFullName());  
          System.out.println(vega2kProperties.getAge());     } 
  } 
  ```

  - **get Method**를 이용하여 **type-safe** 하게 클래스를 작성할 수 있다.



### 1.2) @Profile

> profile을 통해 스프링 부트 애플리케이션의 런타임 환경 관리
>
> 애플리케이션 작동 시 테스트 환경에서 실행할 지, 운영 환경에서 실행할 지를 profile을 통해 관리 할 수 있다.



![profile](https://user-images.githubusercontent.com/58682321/79931585-48cb7980-8486-11ea-9cda-3ba607b9aee5.PNG)



**test 환경**에서 실행하고 싶은 경우는?

- `pplication.properties` 보다 우선 순위가 높은 **커맨드 라인**에서 아규먼트 값을 지정해 주면 된다.

  - target>java -jar ~.jar **--spring.profiles.active=test**

  

※ **Profile** 설정은 `application.properties`에서 한다. 일반적으로는 **test**로 설정해두고, 운영 환경을 실행하고 싶은 경우 **커맨드 라인**에서 **운영 환경**를 입력한다.



#### prod, test properties

- **properties**도  `application-test.properties`, `application-prod.properties` 파일을 **resources**아래에 생성하면, **@Profile**처럼 테스트 환경과 운영 환경을 분리하여 실행할 수 있다.



### 1.3) Logger 설정



- **application-test.properties**

  ```properties
  #logging
  logging.path=logs
  
  #log level
  logging.level.com.whdvlf94.myspringboot=debug
  ```

  - **logging.path**를 지정하면 `logs/spring.log`파일이 생성된다.

- **application-prod.properties**

  ```properties
  #log level
  logging.level.com.whdvlf94.myspringboot=info
  ```

  

- **application.properties**

  ```properties
  spring.profiles.active=prod(or test)
  ```

  - `application.properties`에서 **log level**을 조정한다.



- **MyRunner.java**

  ```java
  import org.slf4j.Logger; 
  import org.slf4j.LoggerFactory;
  
  //(중간 생략)
  
  @Autowired
  private Logger logger = LoggerFactory.getLogger(MyRunner.class)
      
  //(중간 생략)
      
  logger.debug("");
  logger.info("");
  ```

  - `application.properties`에서 **active** 설정에 따라 **log level**이 변경되기 때문에,  **Console**에 출력되는 값이 바뀐다.





## 2. Spring Boot 데이터



### 2.1) In-Memory 데이터 베이스

> H2 데이터베이스 기본 연결 정보 확인



**Setting**

- **pom.xml**

  ```xml
  	<!-- h2 memory DB -->
  	<dependency>
  		<groupId>com.h2database</groupId>
  		<artifactId>h2</artifactId>
  		<scope>runtime</scope>
  	</dependency>
  
  	<!-- boot jdbc -->
  	<dependency>
  		<groupId>org.springframework.boot</groupId>
  		<artifactId>spring-boot-starter-jdbc</artifactId>
  	</dependency>
  ```

  - H2 데이터베이스와 Spring-jdbc 의존성 추가

  - **Spring-boot-starter-jdbc** 의존성을 추가하면 **설정이 필요한 bean을 자동으로 설정** 해준다.

    

- **DatabaseRunner.java**

  ```java
  @Component
  public class DatabaseRunner implements ApplicationRunner {
  	@Autowired
  	DataSource dataSource;
  	
  	@Autowired
  	JdbcTemplate jdbcTemplate;
  	
  	@Override
  	public void run(ApplicationArguments args) throws Exception{
  		try(Connection connection = dataSource.getConnection()) {
  			DatabaseMetaData metaData = connection.getMetaData();
  			System.out.println("URL = " + metaData.getURL());
  			System.out.println("User = " + metaData.getUserName());
  			System.out.println("DataSource Class Name = " + metaData.getclass().getName());
  			
  		}
  	}
  
  }
  
  ----------------------------------------------
  URL = jdbc:h2:mem:testdb
  User = SA
  DataSource Class Name = hikari
  ```

  - `application.properties`에 **spring.h2.console.enabled=true** 추가.
  - http://localhost:8085/h2-console 에 접속하면 H2 Console과 연동된 것을 확인할 수 있다.
  - 이때,  JDBC URL를 jdbc:h2:mem:testdb 로 설정한다. 



- **DatabaseRunner.java**

  ```java
  			//CUSTOMER Table 생성, h2 Console에서 확인 
  			String sql = "CREATE TABLE CUSTOMER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY\r\n" + "KEY (id))"; 
  			Statement statement = connection.createStatement();
  			statement.executeUpdate(sql);
  			
  			//jdbcTemplate 이용해서 Table 값 추가
  			jdbcTemplate.execute("insert into customer values(1,'jdbcTemplate')");
  ```

  - **Table 생성 및 데이터를 추가하여 H2 Console에서 확인해본다.**





### 2.2) Maria 데이터 베이스

> Spring Boot가 지원하는 DBCP



**Setting**

- **pom.xml**

  ```xml
  <!-- mysql connector-->
  <dependency>
  	<groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
  	<version>8.0.13</version>
  </dependency>
  ```

  

- **Maria DB Command Prompt**

  ```mariadb
  use mysql;
  create user spring@localhost identified by 'spring';
  grant all on *.* to spring@localhost;
  flush privileges;
  exit;
  
  mysql -u spring -p
  create database spring_db;
  ```

  

**MySQL DataSource 설정**

- **application.properties**

  ```properties
  spring.datasource.url=jdbc:mysql://127.0.0.1:3306/spring_db?useUnicode=true&charaterEncoding=utf-8&useSSL=false&serverTimezone=UTC
  spring.datasource.username=spring
  spring.datasource.password=spring
  spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
  ```
  
  - **접속 정보를 확인하기 위해 위와 같이 설정**
  
    
  
- 위의 과정을 마치고 **Runner** 클래스를 **restart** 해보면 아래와 같이 출력된다.

  ```java
  ---------------------------------------------
  URL = jdbc:mysql://127.0.0.1:3306/spring_db?useUnicode=true&charaterEncoding=utf-8&useSSL=false&serverTimezone=UTC
  User = spring@localhost
  DataSource Class Name = com.mysql.cj.jdbc.DatabaseMetaData
  ```

  - **Maria 데이터베이스**와 **In-Memory 데이터 베이스**가 연동 된 것을 확인할 수 있다.





### 2.3) ORM과 JPA - Entity 클래스 작성



**ORM**

- 객체와 릴레이션을 맵핑 할 때 발생하는 개념적 불일치를 해결하는 프레임워크

**JPA(Java Persistence API)**

- **ORM**을 위한 자바 (EE) 표준



**Setting**

- **pom.xml**

  ```xml
  <!-- JPA -->
  <dependency>
  	<groupId>org.springframework.boot</groupId>
  	<artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  ```

  - JPA를 쉽게 사용하기 위해 스프링에서 제공하는 프레임워크
    - **Repository Bean** 자동 생성
    - **Query Method** 자동 구현
    - **@EnableJpaRepositories** (스프링 부트가 자동으로 설정 해줌)



#### Entity 클래스 작성

- **Account.java**

  ```java
  @Entity
  public class Account {
  	
  	@Id @GeneratedValue
  	private Long id;
  	
  	@Column(unique = true) // 중복 불허
  	private String username;
  	
  	@Column
  	private String password;
      
      getter(), setter(), equals(), hashcode(), toString()
  }
  ```

  - 위의 클래스 작성 후 **restart**하면, **H2 Console**에 새로운 객체가 생성된 것을 확인할 수 있다.
  - 하지만, **Maria DB**에서는 **Entity** Table이 생성된 것을 확인할 수 없다.



**해결 방안)**

- **application.properties**

  ```properties
  #jpa
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  ```

  - `application.properties` 파일에 JPA에 의한 데이터베이스 자동 초기화 설정을 하면, **Maria DB**에 **Account Table**이 생성 된 것을 확인할 수 있다.

    

#### 즉, MySQL 과 연동하며 사용하기 위해서는 JPA를 이용한 데이터베이스 초기화 설정이 이루어져야 한다.





### 2.4) JPA 테스트



#### Repository 인터페이스 작성

- **AccountRepository.java**

  ```java
  public interface AccountRepository extends JpaRepository<Account, Long> {     
      Account findByUsername(String username); 
  } 
  ```

  - `AccountRepository.java`의 구현체를 따로 작성하지 않아도 **Spring-Data-JPA가 해당 문자열  username에 대한 인수를 받아 자동적으로 DB Table과 매핑한다.**

    

#### JPA 테스트

- **AccountRepositoryTest.java**

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class AccountRepositoryTest {
  
  	@Autowired
  	AccountRepository repository;
  	
  	@Test
  	public void account() throws Exception {
  		Account account = new Account();
  		account.setUsername("spring");
  		account.setPassword("1234");
  		
  		Account saveAct = repository.save(account);
  		System.out.println(saveAct);
  		
  		assertThat(saveAct).isNotNull();
  	}
  }
  -----------------------------------------------
  Account [id=3, username=spring2, password=1234]
  ```

  - **MySQL Client**에서 **select * from account;** 로 확인해보면, **튜플(Row)**이 생성된 것을 확인할 수 있다.

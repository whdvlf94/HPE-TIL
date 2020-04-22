# Spring Boot



**Spring boot Project 작성시 유의사항**

```
1. src/main/java 아래에 있는 base package 와 다른 별도의 package를 작성하면 안된다.
이유: base package 가 componentScan을 시작하는 package 이므로

2.src/test/java 하위에 테스트 클래스를 만들어야 한다.
이유 : boot test Dependency 설정에서 scope이 test로 범위가 정해져 있기 때문
<scope>test</scope>

3.src/main/resources 아래에 application.properties 파일이 위치하고
1) static : html, css, js
2) templates : jsp, html(thymeleaf)
3) resources 하위에 sub 폴더를 생성 한 후에 반드시 Config 클래스에 설정해주어야 한다.

```





## 1. Spring Boot 원리



- 단독 실행이 가능한 수준의 스프링 애플리케이션 제작이 가능하다.
- 최대한 내부적으로 자동화된 설정을 제공
- XML 설정 없이 단순한 설정 방식을 제공



### 1.1) @SpringBootApplication



- **MyspringbootApplication.java**

```java
package com.whdvlf94.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Spring Bean Container 생성

//@SpringBootApplication에는 아래와 같은 어노테이션들이 포함되어 있다.

//@SpringBootConfiguration + @ComponentScan + @EnableAutoConfiguration

//@EnableAutoConfiguration : 미리 설정되어 있는 Configuration 파일들을 활성화 시킨다.
//boot-autoconfigure.jar -> Meta-INF/spring.factories 파일 안에 Configuration 클래스 목록
public class MyspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringbootApplication.class, args);
	}

}
```

- **@SpringBootApplication** 설정은 **Bean을 두 단계로 나눠서 등록**한다.  
  - **1단계** : **@ComponentScan** 
    -  project 생성시 정해준 default 팩키지 부터 scanning을 한다.  
  - **2단계** : **@EnableAutoConfiguration**
    - 스프링 부트에서 **스프링 프레임워크에서 많이 쓰이는 스프링 bean 들을 자동적으로 컨테이너에 등록하는 역할**을 하는 어노테이션



#### 자동 설정 이해

```java
SpringApplication.run(MyspringbootApplication.class, args);

//SpringApplication 다른 방법으로 나타내기
SpringApplication application = new SpringApplication(MyspringbootApplication.class);

//Default WebApplication Type = SERVLET
application.setWebApplicationType(WebApplicationType.SERVLET);

//WebApplication Type 변경 해보기
//웹 애플리케이션 서버가 연결 되지 않는 것을 확인할 수 있다.
application.setWebApplicationType(WebApplicationType.NONE);

application.run(args);
		
```

- **@EnableAutoConfiguration**을 선언했을 때, 스프링 부트 프로젝트를 기본적으로 웹 프로젝트(**SERVLET**)로 만들 수 있는 **Default**로 설정 되어 있다.



## 2. Spring Boot 활용



### 2.1) Spring Boot Banner 변경

- **resources/banner.txt** 파일 생성

  ```
  ===============================================================
  My Spring Boot  ${spring-boot.version} /  ${application.version} 
  ===============================================================
  ```

  -  **${spring-boot.version} , ${application.version}** 등의 변수를 사용할 수 있음. 
  - **Boot Dashboard** 에서 **myspringboot**를 **restart** 해보면  변경된 배너가 출력되는 것을 확인할 수 있다.



### 2.2) Spring Boot 프로젝트 jar 파일로 생성하기

- **Spring Boot 프로젝트를 jar 파일로 생성하기**  

     : Run As -> Maven Build -> Goals : package -> Run 

-  **pom.xml**에 `<packaging>jar</packaging>` 추가 



- target 폴더에서 Terminel 실행 > java -jar jartest-0.0.1-SNAPSHOT.jar
  - **springapplication을 실행하지 않아도 웹 서버가 가동되는 것을 확인할 수 있다.**



### 2.3) 이벤트 리스너(Event Listener)

- **Starting Event**

  ```java
  package com.whdvlf94.myspringboot.listener;
  
  import org.springframework.boot.context.event.ApplicationStartingEvent;
  import org.springframework.context.ApplicationListener;
  
  //@Component 사용 불가
  //이유: 컨테이너 생성 전에 호출되는 클래스이기 때문
  public class MyStartingEventListener implements ApplicationListener<ApplicationStartingEvent>{
  	
  	@Override
  	public void onApplicationEvent(ApplicationStartingEvent event) {
  		System.out.println("Spring Bean 컨테이너 생성에 호출됨 ApplicationStarting Event " + new Date(event.getTimestamp()));
  		
  	}
  
  }
  ```

  - **Starting Event**는 컨테이너 생성 전에 호출되기 때문에 **@Component** 어노테이션을 사용할 수 없다.

    

- **Started Event**

  ```java
  @Component
  public class MystartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
  	
  	@Override
  	public void onApplicationEvent(ApplicationStartedEvent event) {
  		System.out.println("Spring Bean 컨테이너가 생성된 후에 호출됨" + new Date(event.getTimestamp()));
  	}
  
  }
  
  ```

  - **Started Event**는 컨테이너 생성 후에 호출되기 때문에, **@Component**를 설정 해주어야 한다.

  

- **MyspringbootApplication.java**

  ```java
  //Starting Event -> Listener 객체를 등록
  application.addListeners(new MyStartingEventListener());
  application.run(args);
  
  
  ------------------------------------------------
  Spring Bean 컨테이너 생성에 호출됨 ApplicationStarting Event Tue Apr 21 15:51:54 KST 2020
  ================================================================ 
  My Spring Boot  2.1.13.RELEASE /   
  ================================================================
  // (중간 생략)
  Spring Bean 컨테이너가 생성된 후에 호출됨Tue Apr 21 15:51:59 KST 2020
  
  ```

  - **주의)  Starting Event는 컨테이너 생성되기 전에 생성되기 때문에 SpringApplication 객체에 Listener를 추가해야 한다.**

  

- **Spring boot**의 **banner** 가 뜨기 전에 **Starting Event Listener가 동작**하는 것을 확인할 수 있다. 또한, 컨테이너 생성 이후 **started Event Listener**가 동작하는 것을 확인할 수 있다.



### 2.4) 커맨드 Argument 처리

> SpringApplication 실행된 후에 arguments 값을 받거나, 무엇인가를 실행하고 싶을 때  ApplicationRunner 인터페이스를 구현하는 Runner 클래스를 작성한다.



**Argument 값 설정 방법**

-  **Application -> Run Configuration**
  - **Program arguments -> --bar  를 추가한다. **
  - **VM arguments -> -Dfoo 를 추가한다.** 



- **MyRunner.java**

  ```java
  @Component
  @Order(1)
  //@Order(1) : Runner 클래스 중에서 실행 순서가 가장 먼저
  public class MyRunner implements ApplicationRunner {
  
  	@Override
  	public void run(ApplicationArguments args) throws Exception {
  		System.out.println("SourceAgrs : " + args.getOptionNames());
  		System.out.println("Program Arguments : " + args.containsOption("bar"));
  		System.out.println("VM Arguments : " + args.containsOption("foo"));
  	}
  }
  
  -----------------------------------------
  SourceAgrs : [spring.output.ansi.enabled, bar]
  Program Arguments : true
  VM Arguments : false
  ```

  

### 2.5) 외부 설정

> 외부 설정을 통해 Spring Boot Applicaiton 의 환경 설정 혹은 설정 값을 정할 수 있다.
>
> 가능한 외부 설정 : properties, YAML, 환경변수, 커맨드 라인 인수 등



- main>resources>application.properties

- **application.properties**

  ```
  whdvlf94.name=스프링
  whdvlf94.age=${random.int(1,50)}
  whdvlf94.fullName=${whdvlf94.name} 부트
  ```

  - **JAR 안에 있는 application properties는 우선 순위가 15 이다.**

    

- **MyRunner.java**

  ```java
  public class MyRunner implements ApplicationRunner {
  	@Value("${whdvlf94.name}")
  	private String name;
  
  	@Value("${whdvlf94.age}")
  	private int age;
  	
  	@Value("${whdvlf94.fullName}")
  	private String fullName;
  	
  
  	@Override
  	public void run(ApplicationArguments args) throws Exception {
  		
  		System.out.println(">> Propery Name : " + name);
  		System.out.println(">> Propery FullName : " + fullName);
  		System.out.println(">> Propery Age : " + age);
      }
      
  ------------------------------------
  >> Propery Name : 스프링
  >> Propery FullName : 스프링 부트                           
  >> Propery Age : 44
  ```

  

- 이 때, 우선 순위가 더 높은(**우선 순위 4**) **jar Terminal** 에서**Command Line Argument**을 사용하면 Value 값을 변경 할 수 있다.

  - java -jar myspringboot-0.0.1-SNAPSHOT.jar **--whdvlf94.name=javascript**

  ```java
  >> Propery Name : javascript
  >> Propery FullName : javascript 부트                           
  >> Propery Age : 44
  ```





### 2.6) Type-Safe Property 클래스 작성

> @ConfigurationProperties 사용



- **@Value("${abc.name}")** 와 같이 **기존 properties**를 사용하는 경우 **type-safe 하지 않아(오타와 같은 오류를 잡아내지 못함)** 에러가 나는 경우가 많다.

- **@ConfigurationProperties**를 사용하는 경우, **get Method**를 이용하면 되기 때문에 **type-safe** 하다.



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
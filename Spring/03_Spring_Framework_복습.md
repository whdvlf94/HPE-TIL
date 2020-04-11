# Spring Framework 복습



### 1. Framework 란?
- 비기능적인 요구사항들(인증, 로깅, 트랜잭션 처리, 성능 ..)을 구현해서 제공
- 업무 개발자는 기능적인 요구사항(biz logic)
- 프레임워크의 구성요소
	: IoC(제어의 역전)
	: Design Pattern(GoF pattern) - 클래스 구조에 대한 가이드



### 2. Framework와 Library의 차이점
: 제어권 - 객체생성, 소멸(Life cycle), 특정 메서드 호출
: Library는 제어권을 개발자가 가진다.
: Framework는 프레임워크가 제공하는 컨테이너가 제어권을 가진다.





### 3. DI(Dependency Injection)
- 의존성 주입
- 의존하는 객체의 레퍼런스를 프레임워크가 생성해서 주입해 주겠다.
- 개발자는 의존하는 클래스의 정보를 XML이나 Java Config에 설정 해야한다.



#### <DI 종류>

- **Setter injection** : 기본생성자로 객체를 생성하고, setter 메서드의 인자로 의존하는 객체를 1개씩 주입해주는 방식.
- **Constructor injection** : Overloading된 생성자로 객체를 생성하고, 이 생성자의 인자로 의존하는 객체를 여러 개씩 주입 해주는 방식
- 프레임워크가 대신 객체를 생성하면 특정 기능(singleton, 트랙잭션 처리)을 주입시켜준다. => **byte code injection**
- **용어 정의**
 Bean : 스프링이 IoC 방식으로 관리하는 객체
 BeanFactory, ApplicationContext : Spring Bean Container
 Configuration MetaData : Config XML, java Config 클래스



#### <DI 구현하는 전략 3가지>



**전략 (1) **

---

: **configuration(설정)을 XML에 한다.**

```xml
<bean id="" class="package.class이름"/>
<property name="" value=""/>
<property name="" ref=""/>
</bean>
```

- 장점: 전체 의존관계 구조를 파악하기 쉽다.
- 단점: xml 파일 공유의 문제점(충돌)



**전략 (2) **

---

: **Annotation과 XML을 혼용 ** 



**@Component, @Repository, @Service, @Controller**
: Spring Bean 등록(생성)

**@Value**
: Spring Bean의 의존성 주입, 값을 주입

**@Autowired  / @Resource(javax.annotation)** 
\- 의존관계가 있는 Bean을 찾아서 자동으로 주입시켜 주는 기능

\- **@Autowired**는 Type으로 찾음, **@Resource**는 Name(id)으로 찾음

- **@Autowired**는 변수, 메서드, 생성자 위에 선언 가능

- **@Resource**는 변수, 메서드 위에 선언 가능



- **@Qualifier**
  : **@Autowired**와 같이 사용되며 , **특정 Bean을 지정**한다.



**예) **

- **xml : component-scan 설정**

```xml
<!-- Component Auto Scanning 설정 -->
<context:component-scan
	base-package="myspring.di.annot" />
```
**: basepackage에서 지정한 패키지 아래의 모든 클래스에 선언된 @Component 등을 찾아주는(Auto scanning) 기능**



- **Hello.java**

```java
@Component("helloA")
public class Hello {
	
	//<property name="name" value="Annotation"/>
	@Value("${name}")
	String name;
	
//	@Autowired
//	@Qualifier("stringPrinter")
//<property name="printer" ref="stringPrinter" />
	
//	@Qualifier("${stringPrinter}")는 지원하지 않는다. 
//	즉, Bean의 id를 properties에서 가져와서 사용하는 것은 지원하지 않는다.
	
	@Resource(name ="${myprinter}")
	Printer printer;
	List<String> names;
	Map<String,Integer> ages;

	public Hello() {
		System.out.println("Hello Default Constructor called...");
	}
//	@Autowired
//	public Hello(@Value("Annotation") String name, @Qualifier("stringPrinter") Printer printer) {
	
	public Hello(String name, Printer printer) {
		System.out.println("OverLoading Hello Constructor called...");
		this.name = name;
		this.printer = printer;
	}
```

- **Component Auto Scanning**이 어노테이션을 인식하기 위해서는 **클래스 위에 @Component 로 설정**해두어야 한다.



- **setter method - setName, setPrinter  -  생략 가능**

```java
	public void setName(String name) {
		System.out.println("Hello setName() called..." + name);
		this.name = name;
	}

	public void setPrinter(Printer printer) {
		System.out.println("Hello setPrinter() called..." + printer.getClass().getName());
		this.printer = printer;
	}
```

**※ 어노테이션을 이용하면, 위와 같은 setter method를 생략할 수 있다.**



**전략(3)**

---

- **Spring Boot - java Config, XML 사용하지 않는다.**

**@Configuration** : Java Config class

**@Bean** : **\<Bean>** 태그와 동일한 역할

**@ComponentScan** : **\<context:component-scan>** 태그와 동일한 역할





#### \<DI Test class>

- **jUnit**
  - @Test, @Before, @After, @Ignore
- **Spring Test**
  - @Runwith , @ContextConfiguration

**@Runwith**은 jUnit을 확장해서 테스트 케이스를 실행해주는 Runner를 지정할 때. =>  **@Runwith(SpringJUnit4ClassRunner)**





### 4. Open Source

ex) Spring(Context, Test), Maven(pom.xml), jUnit, Tomcat(web container)

: **Web Server와 Web Container는 다르다**
	ex) Apache, Nginx, GWS



- 웹서버에서 JSP, Servlet으로 작성 app을 실행할 수 없다. Apache, nginx에는 JRE(Java Runtime Environment)이 없기 때문
- J2EE API에 있는 interface들은 WAS vendor에서 구현한다.
- **WAS를 제공하는 vendor는 J2EE에 들어있는 모든 인터페이스들의 구현체를 개발해서 제공한다.**
  - **WAS = web container(tomcat) + ejb container**
  - tomcat은 jsp, servlet에 관련된 web과 관련된 인터페이스의 구현체를 제공한다.
- JDBC(java.sql, javax.sql) 에 있는 인터페이스는 DB vendor(JDBC Driver)가 구현한다. 
- 표준 인터페이스를 제공함으로써, 어떤 언어를 사용하더라도 구현이 가능하도록 한다.

**Web Server 와 WAS의 차이점**

: Web server - static contents

: WAS - Dynamic contents

[참조](
# Spring Framework

> 어떤 플랫폼이든 자바 기반 enterprise aplication을 작성하기 위한 프로그래밍, xml 설정(Configuration) 기능을 제공



```
1. Framework 와 Library의 차이점
2. IoC란?
3. DI(Dependency Injection) - setter injection
4. jUnit을 이용한 실습
```



#### Spring Framework vs Spring Boot

- xml 설정을 많이 동반한다. 이를 보다 가볍게 수행하기 위해 나온 것이 Spring Boot

- Spring Framework 의 작동 원리를 이해해야, Spring Boot를 사용하기 쉽다.



#### Overview

- **비기능적인 부분**(infrastructural)을 지원한다. (인증, 트랜잭션 처리, 로깅 등) 

  즉, Spring Framework를 사용하는 기업은 업무 로직만을 담당하면 된다.

#### Features

- **Core technologies** : **dependency injection**(DI, 의존성주입) , AOP(Aspect Oriented Programming, 관점지향프로그래밍),
- **Testing**: TestContext framework
- **Data Access**: DAO(Data Access Object), JDBC(java Database connectivity), ORM(Object Relational Mapping)
  - DB에 연동하는 기능은 Spring Boot에서 제공하는 것이 아닌, MyBatis, JPA(Java Persistence API)을 이용한다.
- **Spring MVC** : Model View Controller, 웹 어플리케이션을 작성하는데 사용하는 프레임워크
  - JSP(Java Server Page)형태로 제공, Restful Web Service는 JSON, xml 형식의 데이터를 제공
- **IoC(Inversion of Control)**: 제어의 역행
  - 인스턴스 생명주기 관리를 개발자가 아닌 컨테이너가 대신 해준다.
  - 프레임워크 코드가 전체 애플리케이션의 처리 흐름을 제어
  - 외부의 이벤트에 대해 애플리케이션이 어떤 메서드를 수행해야할지 결정





## 1. 프레임 워크란



#### 정의

- **비기능적(Non-Functional) 요구사항(성능,보안,확장성,안정성)을 만족하는 구조**와 구현된 기능을 **안정적으로 실행하도록 제어**해주는 잘 만들어진 구조의 라이브러리 덩어리
  - 기능적인(Functional) 요구사항에 집중함으로써 하부 구조를 구현하는데 들어가는 노력을 절감할 수 있게 해준다.



#### Framework vs Library

> 제어의 소유권 차이

- **Library** : 개발자가 주체가 되어 **객체 생성을 직접** 한다. 
  - ex) new ArrayList() 등
- **Framework** : 클래스를 만들기만 하면, **Spring Framework가 제공하는 컨테이너에서 객체를 생성**해 준다.
  - ex) 프레임워크를 사용할 때 개발자는 **xml**(framework와 소통하는 수단)에 설정을 반드시 해야하며, Framework는 이를 파싱하여 객체를 생성을 한다. 
    - **※ xml에는 패키지, 클래스 구조를 설명하는 내용이 담겨있어야 한다.**



- **Framework 예 - Singleton 패턴** 
  - 객체를 한번만 생성(Heap 영역 재사용)함으로써 메모리를 절약할 수 있다. 이를 위해서는 `private static`으로 인스턴스를 생성해야 하며, `getInstance`로 인자 값을 받아와야 한다. **즉, 프레임워크 코드가 애플리케이션의 처리 흐름을 제어하는 것을 확인할 수 있다.**



#### 프레임워크의 구성요소와 종류

**IoC(Inversion of Control)**

- **Spring 컨테이너는 IoC를 지원**하며, **XML 설정(메타데이터)을 통해 beans를 관리**하고 **애플리케이션의 중요부분을 형성**한다.
  - **beans** : Spring이 관리해주는 객체





## 2. Spring 실습



#### 1. Javaconfig.zip , Xmlconfig.zip import

- Import>General>Existing Projects into Workspace>archive file(압축파일)
- Xmlconfig.zip 을 import 하면 **Maven(Java Open source Library Web site)**에 있는 라이브러리 파일들을 웹 에서 로컬 PC로 다운 받아온다. **즉, 일일이 라이브러리를 받아올 필요 없다. 의존성의 문제도 걱정할 필요 없음**
  - **npm(node package manager)** : javascript Open source Library Web site

- pom.xml : Maven 설정 파일



**※ Maven?**

- Maven은 자바 프로젝트의 **빌드(build)를 자동화 해주는 빌드 툴(build tool)**이다.

  즉, 자바 소스를 compile하고 package해서 deploy하는 일을 자동화 해주는 것이다.

  



#### 2. 실습

1.  **Dynamic web project 생성**

   1. 파일명 `web.xml` 설정

2. **pom.xml 생성**

   1.  Configure > Convert to Maven Project

3. **\<dependencies> 추가**

   1.  `pom.xml` 내에 `<dependencies>`태그를 추가한다.
   2.  Maven Repository에서 `spring-context` DI를 복사해 `pom.xml`에 추가한다.

      - **code format 단축키** : `ctrl` + `shift` + `f`

4.   `src` 아래에 `config` 폴더 생성 후, `spring bean.xml`생성

    

![캡처](https://user-images.githubusercontent.com/58682321/78858216-aadbb600-7a66-11ea-99db-e6fd4db1d3a3.PNG)





## 3. Spring Framework 개요

> Java 엔터프라이즈 개발을 편하게 해주는 오픈소스 **경량급 애플리케이션 프레임워크**

- Spring은 OpenSource의 장점을 충분히 취하는 동시에 OpenSource 제품의 단점과 한계를 잘 극복함.



#### Spring Framework 전략

**※ Spring 삼각형**



## 4. IoC와 Spring DI



### 4.1 IoC



#### IoC 컨테이너

- 스프링 프레임워크도 **객체에 대한 생성 및 생명주기를 관리 할 수 있는 기능**을 제공하고 있다. 즉, **IoC 컨테이너 기능**을 제공
- 객체의 생성을 책임지고, 의존성을 관리한다.



#### IoC 구성요소

- **DL(Dependency Lookup)** : EJB , Spring
- **DI(Dependency Injection)** : Spring, Pico Container
  - Setter Injection(getter, setter method), Constructor Injection, Method Injection



#### Spring DI 용어

- **Bean** : 스프링이 IoC 방식으로 관리하는 객체

- **BeanFactory** : 스프링의 IoC를 담당하는 핵심 컨테이너. 

  - `Spring Bean` 컨테이너 역할을 하며, `getBean()`(**조회**) 메서드로 요청한다.
  - `ApplicationContext`도 `Spring Bean` 컨테이너 역할을 한다.

  

  

![4](https://user-images.githubusercontent.com/58682321/78868290-9951d880-7a7d-11ea-83fb-935eaa90ad2a.PNG)

- **BeanFactory**, **ApplicationContext** 가 Spring Bean 컨테이너 역할을 한다.

- 예)

```java
BeanFactory factory = new GenericXmlApplicationContext();
```



### 4.2 DI



#### DI 구현 클래스 작성

**POJO 클래스 다이어그램**

![2](https://user-images.githubusercontent.com/58682321/78861860-e11e3300-7a70-11ea-90bd-1cbf257559b7.PNG)



- **Hello 클래스를 구현하기 위해서는 `Interface`에 의존해야 한다.**
  -  `String Printer` , `Console Printer` 와 같은 구현부에 의존하게 되면, 추후에 메서드를 변경하는 작업이 불가능하게 된다.



#### DI : Bean 의존관계

**※ Setter Injection : \<property> 태그**

![3](https://user-images.githubusercontent.com/58682321/78863829-71f70d80-7a75-11ea-91c0-d7b28dd3cd91.PNG)

- `bean` , `property`로 작성된 객체는 `Spring Bean`컨테이너에 의해 호출된다.
- `<property>`는 메서드를 지칭한다.
  - 참조하는 메서드가 있을 시에 사용한다.
  - 즉, `<property name="printer" ..>`는 `setPrinter()`를 호출한다.
- `setName()`에는 Spring 값이 들어가고, `setPrinter()`는 아래에 작성된 `myspring.di.xml.StringPrinter` 클래스를 참조한다.





## 5. 실습 예제



#### 3.1) jUnit을 사용한 DI 테스트 클래스

- java에서 독립된 단위테스트(Unit Test)를 지원해주는 **프레임워크**이다.

  **※ 단위테스트(Unit Test)?**

  - **모든 함수와 메서드에 대한 테스트 케이스를 작성하는 절차**. 즉, 소스 코드의 특정 모듈이 의도된 대로 정확히 작동하는지 검증하는 절차.

- jUnit은 보이지 않고 숨겨진 단위 테스트를 끌어내어 정형화시켜 단위테스트를 쉽게 해주는 테스즈 지원 프레임워크



- **myspring.di.xml**

```java
package myspring.di.xml;

import java.util.List;

public class Hello {
	String name;
	Printer printer;
	List<String> names;

	public Hello() {
		System.out.println("Hello Default Constructor called...");
	}

	public Hello(String name, Printer printer) {
		System.out.println("OverLoading Hello Constructor called...");
		this.name = name;
		this.printer = printer;
	}

	public List<String> getNames() {
		return this.names;
	}

	public void setNames(List<String> list) {
		this.names = list;
	}

	public void setName(String name) {
		System.out.println("Hello setName() called..." + name);
		this.name = name;
	}

	public void setPrinter(Printer printer) {
		System.out.println("Hello setPrinter() called..." + printer.getClass().getName());
		this.printer = printer;
	}

	public String sayHello() {
		return "Hello " + name;
	}

	public void print() {
		this.printer.print(sayHello());
	}

}

```



- **spirng_beans.xml(설정 파일)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">


	<!-- StringPrinter 클래스를 Bean으로 설정 -->

	<bean id="sPrinter" class="myspring.di.xml.StringPrinter" />

	<!-- ConsolePrinter 클래스를 Bean으로 설정 -->
	<bean id="cPrinter" class="myspring.di.xml.ConsolePrinter" />

	<!-- Hello 클래스를 Bean으로 설정 -->
	<bean id="hello" class="myspring.di.xml.Hello" scope="prototype"> 
    <!-- scope의 default값은 singleton이다. -->
        
	<!-- setter injection -->
		<property name="name" value="Spring"></property>
		<property name="printer" ref="sPrinter"></property>
	</bean>
	
</beans>

```

**※** **\<scope>** : singleton(default), prototype, request, session 타입이 존재한다.





- **HelloBeanJunitTest.java**

```java
package myspring.di.xml.test;

// static import : static method import
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import myspring.di.xml.Hello;
import myspring.di.xml.Printer;
import myspring.di.xml.StringPrinter;

public class HelloBeanJunitTest {
BeanFactory factory;
// factory를 전역 변수로 설정하여 init() 메서드 안에서도 실행 가능하도록 한다.

// @Test 메서드가 실행되기 전에 먼저 실행되는 메서드
// 컨테이너를 먼저 생성해야 객체를 생성할 수 있기 때문에, @Before 어노테이션에 지정해 준다.
	@Before
	public void init() {

// 1. Spring Bean Container 생성
// ResourceLocation - Spring Bean Config xml 정보를 설정해서 , 파싱작업이 이루어질 수 있게 해야한다.
factory = new GenericXmlApplicationContext("config/spring_beans.xml");
	}

	/*
	 * TestCase 메서드를 선언할 때 규칙 1. @Test 어노테이션을 반드시 가장 처음에 선언한다. 2. 테스트 메서드의 접근 제한자는
	 * 반드시 public void 이여야 한다.
	 * 
	 */
	@Test
	public void hello() {

// 2. Container에게 Bean을 요청
Hello hello = (Hello) factory.getBean("hello"); 
// factory.getBean은 Object 이므로 형변환을 해주어야 한다.

Hello hello2 = factory.getBean("hello", Hello.class);
// 위 형태로 getBean()을 더 많이 사용한다.
        
System.out.println(hello == hellow); // true 
//또한, 싱글톤 패턴이므로 Hello.java에 입력해 놓았던 println이 한번만 출력된다.

        
// 2.1 Assert.assertSame() 메서드를 사용해서 주소 비교
// 주소 값을 비교
// import static Assert 를 했기 때문에 Assert.assertSame으로 작성하지 않아도 된다.
		assertSame(hello, hello2);

		// 2.2 Assert.assertEquals() 메서드를 사용해서 값을 비교
		assertEquals("Hello Spring", hello.sayHello());

		hello.print();

		// 3. Container에게 StringPrinter Bean을 요청

		// sPrinter 이지만, 상위 클래스로 입력한다.
		Printer printer = factory.getBean("sPrinter", Printer.class);
		assertEquals("Hello Spring", printer.toString());

	}

}
```







#### 3.2) Spring-Test를 이용한 실습

- jUnit에서는 `@Before` 어노테이션을 이용해서 컨테이너를 일일이 생성해주어야 했다.
- 하지만,  Spring-Test는 **컨테이너를 자동으로 생성**해주는 어노테이션`@RunWith(SpringJUnit4ClassRunner.class)`과 **xml 파일의 경로**를 찾아주는 `@ContextConfiguration(locations = "classpath:config/spring_beans.xml")`를 이용할 수 있다.

- 
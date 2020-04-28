# Spring Boot Actuator



**Review**

```
[Thymeleaf - Server Sie Template Engine, JSP와 같은 기능]
:JSP를 사용하면 war 로만 배포가 가능하다.
:thymeleaf는 jar,war 둘 다 배포가 가능하다.
:ex) java -jar xx.jar

[Expression Variable]
1. ${} : variable expression
2. *{} : selection variable expression

	th:object="${session.user}"
		th:text="*{name}"
	-> th:text="${session.user.name}"

3. @{} : Link URL Expression

	<form th:action="@{/edit/{id}(id="${user.id}")}>
	<a th:href="@{}"></a>
	
4. #{} : Message Expression
	
	message.properties
	my.nationality=Korean
	th:text="#{my.nationality}"
	
[키 값을 이용해 문자열 나타내는 방법]

	<h2 th:text="${name}">Name</h2>
	<h2><span th:text="${name}"></span></h2>
	<h2>[[${name}]]</h2>
	
[Validation(javax.validation.*)]

: NotBlank(name="이름은 필수 입력 항목입니다.")

BindingResult : 검증 오류가 발생했을 때 객체 저장
:@Valid = @ModelAttribute + Validation check

public String createUser(@Valid User user, BindingResult result) {
	if(result.hasErrors()) { // result 객체에 오류 내용 저장
	 return "add-user";
	 }
}

<span th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></span>
#fields = BindingResult : #fields java에서 지정 해놓은 이름
```





## 1. Actuator 소개

> EndPoint와 매트릭스 데이터를 활용하는 모니터링 기능
>
> 즉, 애플리케이션의 각종 정보를 확인할 수 있는 EndPoints



#### Actuator 의존성 추가

- **pom.xml**

  ```xml
  <dependency>     
      <groupId>org.springframework.boot</groupId>     		 
      <artifactId>spring-boot-starter-actuator</artifactId> </dependency>
  ```

  - 사용하고자 하는 프로젝트 `pom.xml`에 **Actuator** 의존성을 추가한다.



**※ health, info 를 제외한 대부분의 Endpoint가 기본적으로 비공개. 공개하는 방법은?**

- **application.properties**

  ```properties
  management.endpoints.web.exposure.include=*
  ```

  

## 2. Actuator Server

> Actuator Admin Server 를 통해 Client 프로젝트의 애플리케이션 정보를 확인할 수 있다.



#### Server

- **AdminApplication**

  ```java
  @SpringBootApplication 
  @EnableAdminServer // 이 어노테이션을 작성해야 한다.
  public class SpringbootAdminApplication { 
  }   
  ```

- **pom.xml**

  ```xml
  <!-- boot admin server -->
  <dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-starter-server</artifactId>     	<version>2.1.4</version> 
  </dependency> 
  ```

  - 의존성 추가

**※** `application.properties`에서 **server.port=8090**으로 설정



#### Client

- **Actuator** 를 통해 관리하고 싶은 프로젝트에 아래와 같이 설정한다.

- **application.properties**

  ```properties
  spring.boot.admin.client.url=http://localhost:8090
  ```

- **pom.xml**

  ```xml
  <dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-client</artifactId>    			<version>2.1.4</version> 
  </dependency> pom
  ```

  
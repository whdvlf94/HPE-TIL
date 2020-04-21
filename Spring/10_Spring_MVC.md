# Spring MVC



**Setting**

- **Maven Repository 에서 Spring Web MVC 받아와 pom.xml에 추가한다.**
- Spring Beans Configuration XML 정보를 Tomcat에 알려줘야 함
- FrontController 역할을 수행하는 DispatcherServlet 클래스를 설정



## 0. Spring MVC 주요 구성요소

```
[Spring MVC] - Model2
Model : Java(DAO, Service, VO)
View : JSP, HTML
Controller : Servlet
```



**DispatcherServlet**

- 클라이언트의 요청을 받아서 Controller에게 클라이언트의 요청을 전달하고, 리턴한 결과값을 View에게 전달하여 알맞은 응답을 생성

**HandlerMapping**

- URL과 요청 정보를 기준으로 어떤 핸들러 객체를 사용할지 결정하는 객체이며, DispatcherServlet은 하나 이상의 핸들러 매핑을 가질 수 있다.

**Controller**

- 클라이언트의 요청을 처리한 뒤, Model을 호출하고, 그 결과를 DispatcherServlet에게 알려줌

**ModelAndView**

- Controller가 처리한 데이터 및 화면에 대한 정보를 보유한 객체

**View**

- Controller의 처리 결과 화면에 대한 정보를 보유한 객체

**ViewResolver**

- Controller가 리턴한 뷰 이름을 기반으로 Controller 처리 결과를 생성할 뷰를 결정





## 1.  Controller Class



- **UserController.java**

  ```java
  package myspring.user.controller;
  
  import java.util.List;
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.servlet.ModelAndView;
  
  import myspring.user.service.UserService;
  import myspring.user.vo.UserVO;
  
  @Controller
  public class UserController {
  	@Autowired
  	private UserService userService;
  	
  	//사용자 목록 조회
  	@RequestMapping("/userList.do")
  	public ModelAndView userList() {
  		List<UserVO> userList = userService.getUserList();
  		
          // request.setAttribute(key, value)와 같은 기능을 한다.
  		// "userList.jsp" : View page 이다.
  		return new ModelAndView("userList.jsp", "userList", userList);
          
  
  //Spring MVC 프레임워크를 사용하지 않을 경우 아래와 같이 진행한다.
         
  		//request.setAttribute("userList", users);
  		//rd = request.getRequestDispatcher("userList.jsp");
  		//rd.forward(request, response);
          
  	}
      
      //사용자 상세정보 조회
  	@RequestMapping("/userDetail.do")
  	
  	//@RequestParam
  	//String userid = request.getParameter("id");와 동일
  	public String userDetail(@RequestParam String userid, Model model) {
  		UserVO user = userService.getUser(userid);
         
  		
  		//ModelAndView 에서 ("userList", userList) 부분
  		model.addAttribute("user", user);
  		
          //ModelAndView 에서 "userList.jsp"(view) 부분
  		return "userDetail.jsp";
  	}
  
  }
  ```

  - **@Controller** : Controller 클래스를 Bean으로 등록
  - **@RequestMapping** : HTTP 요청 URL을 처리할 **Controller 메서드 정의**
    - **@RequestMapping("/userList.do")** 요청이 들어오면 **HandlerMapping** 이 가지고 있는 매핑 정보를 통해 **ModelAndView userList() {..}** 와 매핑 해준다.
  - **@RequestParam** : query String 형태로 보낸 문자열의 값을 추출
  - **ModelAndView(userList)** 또는 **Model(userDetail)** 타입을 이용하여 브라우저 상에서 JSP를 실행할 수 있다.
    - **Model** : **addAttribute(modelName, modelObject)**와 동일





## 2. Spring MVC 실습

> 사용자 등록처리, 사용자 등록 요청



### 2.1) 사용자 등록 Form 조회

```java
	@RequestMapping("/userInsertForm.do")
	public String insetUserForm(HttpSession session) {
		List<String> genderList = new ArrayList<String>();
		genderList.add("남");
		genderList.add("여");
		
		//session 객체에 genderList 객체를 저장
		session.setAttribute("genderList", genderList);

		List<String> cityList = new ArrayList<String>();
		cityList.add("서울");
		cityList.add("경기");
		cityList.add("부산");
		cityList.add("대구");
		cityList.add("제주");
		
		//session 객체에 cityList 객체를 저장
		session.setAttribute("cityList", cityList);
		


		return "userInsert.jsp";
	}
```

※ `userList.jsp`에서 사용자 등록 **href** 태그와 **@RequestMapping**이름을 동일하게 설정해야 한다.

- **userInsert.jsp**

  ```jsp
  <!--  EL 에서 session scope은 session객체를 나타날때 -->
  						<td><c:forEach var="genderName"
  								items="${sessionScope.genderList}">
  								<input type="radio" name="gender" value="${genderName}">${genderName}
                         </c:forEach></td>
  					</tr>
  					<tr>
  						<td>거주지 :</td>
  						<td><select name="city">
  								<c:forEach var="cityName" items="${sessionScope.cityList}">
  									<option value="${cityName}">${cityName}</option>
  								</c:forEach>
  						</select></td>
  					</tr
  ```

  - `userController.java`에서 **session**에 객체를 추가 했기 때문에, `userInsert.jsp`에서도 이를 반영해 주어야 한다. 즉, **map** 메서드 대신 **sessionScope** 로 바꿔 **session** 객체를 나타낼 수 있다.





### 2.2) 사용자 등록 처리



**@ModelAttribute**

: Controller 핵심 어노테이션, HTTP 요청에 포함된 파라미터를 모델 객체로 바인딩

즉, 객체마다 **@ResquestParam**을 일일이 하지 않아도 된다.



**등록 요청 과정**

- `userInsert.jsp`(**사용자 등록**) → Form data 추출해서 **VO 저장** (**@ModelAttribute**) → **DAO 호출**하면서 **인자로 VO 전달** → 사용자 목록 페이지로 포워딩
  - `userInsert.jsp` 에서의 **name**과 `userVO.java` 에서의 **getter/setter** 명을 동일하게 설정해야 한다.



- **userController.java**

  ```java
  	// 사용자 등록 처리
  	// @RequestMapping은 default가 GET 방식이다. 따라서 POST 방식의 경우 method 를 추가해 주어야 한다.
  	@RequestMapping(value = "/userInsert.do", method = RequestMethod.POST)
  	public String userInsert(@ModelAttribute UserVO user) {
  		System.out.println(">> UserVO " + user);
  		userService.insertUser(user);
  
  		// 사용자 목록조회를 처리하는 요청으로 포워딩을 하겠다
  		return "redirect:/userList.do";
  	}
  ```

- **userInsert.jsp**

  ```jsp
  <form method="post" action="userInsert.do">
  ```

  ※ **@RequestMapping value** 와 **`userInsert.jsp`의 action** 이름을 동일하게 설정해야 한다.





### 2.3) 한글 깨짐 현상?

#### Encoding

**요청(request) 데이터 인코딩**

: 요청 마다 인코딩하지 않고, Filter를 설정하여 모든 요청에 대한 인코딩이 이루어 지도록 한다.

- **Servlet으로 개발 하는 경우** : request.setCharacterEncoding("utf-8")

- **Servlet Filter** : 공통적으로 사용되는 기능을 포함한 객체 

  - Spring 에서 CharacterEncodingFilter 클래스를 제공한다.

- `web.xml`에 Filter 설정3

  ```xml
  	<filter>
  		<filter-name>encodingFilter</filter-name>
  		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  		<init-param>
  			<param-name>encoding</param-name>
  			<param-value>utf-8</param-value>
  		</init-param>
  	</filter>
  	<filter-mapping>
  		<filter-name>encodingFilter</filter-name>
  		<url-pattern>*.do</url-pattern>
  	</filter-mapping>
  ```

  

**응답(response) 데이터 인코딩**

- response.setContentType("text/html;charset=utf-8")
- <%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

- **contentType** : contentType에 설정한 **charset은 응답(response) 데이터의 인코딩** 

  **pageEncoding** : jsp에 포함된 코드에 대한 인코딩







### 2.4) 사용자 삭제 처리

**@PathVariable**

: 파라미터를 URL 형식으로 받을 수 있도록 해줌



- **usrList.jsp**

  ```jsp
  <script type="text/javascript">
  	function deleteUser(userId){
  		var result = confirm(userId +" 사용자를 정말로 삭제하시겠습니까?");
  		if(result) {
  			location.href = "userDelete.do/"+userId;
  		}
  	}
  </script>
  ```

- **userController.java**

  ```java
  //사용자 삭제 처리
  	//기존 방식 : userDelete.do?userId=gildong (query string)
  	//@PathVariable : userDelete.do/gildong
  	@RequestMapping("userDelete.do/{id}")
  	public String userDelete(@PathVariable("id") String userid) {
  		userService.deleteUser(userid);
  		
  		return "redirect:/userList.do";
  	}
  ```

  

#### @PathVariable을 사용하기 위한 설정

- **spring_beans_web.xml** 생성

  ```xml
  <mvc:default-servlet-handler/>
  	     
  <!-- XML, JSON Data support -->
  <mvc:annotation-driven/>
  ```

  `<mvc:default-servlet-handler/>`  :**DispatcherServlet의 url-pattern**과 **DefaultServlet의 url-pattern(tomcat)**의 충돌 문제를 해결 해주는 설정. 

  
  
- **web.xml**

  ```xml
  	<!-- Map all requests to the DispatcherServlet for handling -->
  	<servlet-mapping>
  		<servlet-name>springDispatcherServlet</servlet-name>
  		<url-pattern>/</url-pattern> 
          <!-- *.do 패턴에서 / 으로 변경 -->
  	</servlet-mapping>
  ```

  - **DispatcherServlet의 url-pattern 변경**

  - **FrontController 역할을 하는 DispatcherServlet 클래스 정보 설정**

    

#### ※ default-servlet-handler 태그를 설정하는 이유

- **DispatcherServlet**은 **url-pattern**을 "/" 와 같이 설정하게 되면서 **tomcat의 web.xml**에 정의되어 있는 **url-pattern** "/"을 **무시**하게 된다. 
- 결국 **DispatcherServlet url-pattern을 재정의**하게 되어서 **DefaultServlet은 더 이상 동작할 수 없게 된 것**이다. 
- **Spring에서는 이를 해결하기 위해서 <mvc:default-servlet-handler /> 설정을 지원한다.**





### 2.5) Componet Auto Scanning 수정

: Server 를 시작하면 왜 Default Constructor 가 두 번 호출 되는가?

- **@Controller** Bean의 scanning을 기존에는 `spring_beans.xml`에서 담당했지만 exclude 시킴
- **@Controller Bean**의 scanning을 `spring_beans_web.xml`에서 include 시킴



- **web.xml**

  ```xml
  	<!-- needed for ContextLoaderListener -->
  	<context-param>
  		<param-name>contextConfigLocation</param-name>
  		<param-value>classpath:config/spring_beans.xml</param-value>
  	</context-param>
  
  <!--------- 중간 생략 ------------->
  
  
  	<servlet>
  		<servlet-name>springDispatcherServlet</servlet-name>
  		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  		<init-param>
  			<param-name>contextConfigLocation</param-name>
  			<param-value>classpath:config/spring_beans_web.xml</param-value>
  <!-- springDispatcherServlet은 Controller 에서만 진행하기 때문에 spring_beans.xml은 제외 시킨다. 이를 위해 spring_beans.xml에서 @Controller annotation을 exclude 해주어야 한다. -->
  		</init-param>
  ```





- **spring_beans.xml**

  ```xml
  	<!-- Component Auto Scanning 설정 -->
  	<context:component-scan
  		base-package="myspring.user">
  		<!-- @Controller 어노테이션을 선언한 컴포넌트는 제외하겠다. -->
  		<context:exclude-filter type="annotation"
  			expression="org.springframework.stereotype.Controller" />
  	</context:component-scan>
  ```

  

- **spring_beans_web.xml**

  ```xml
  	<!-- Component Auto Scanning 설정 -->
  	<context:component-scan
  		base-package="myspring.user">
  		<!-- @Controller 어노테이션을 선언한 컴포넌트만 포함하겠다. -->
  		<context:include-filter type="annotation"
  			expression="org.springframework.stereotype.Controller" />
  	</context:component-scan>
  ```

  - 위와 같이 수정하면 **Controller** 클래스가 한 번만 출력 되는 것을 확인할 수 있다.


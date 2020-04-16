# JSP, Servlet, JSTL



## 1. Review

```
- MyBatis에서 테이블 간의 의존 관계가 있는 경우, 컬럼명과 VO의 getter/setter 이름이 일치하지 않는 경우

- J2EE(Enterprise Edition) 기술
J2SE, J2EE
: Servlet, JSP(Java Server Page), JSTL(Java Standard Tag Library)
Spring MVC 구조와 핵심 컴포넌트
```



- **StudentMapper.xml**

  ```xml
  <!-- parameterType Student : 등록하려는 입력 데이터를  저장하고 있는 StudentVO 객체, 따라서 getter 방식으로 데이터를 꺼내온다. -->
  	<insert id="insertStudent" parameterType="Student">
  		insert into student
  		(stu_id,stu_name,stu_age,stu_grade,stu_daynight,dept_id)
  		values(
  		<!-- get 방식으로 호출 -->
  		#{id},
  		#{name},
  		#{age},
  		#{grade},#{daynight},
  		#{dept.deptid} 
  		<!-- dept.deptid : StudentVO 에서 getDept() -> DeptVO 에서 getDeptId() -->
  		)
  	</insert>
  ```

  

- **MyBatis.java**

  ```java
  	@Test
  	public void stuMapper() {
  		
  // Test 케이스 : StudentMapper -> SqlSession -> StudentMapper.xml
  		
  		StudentVO student =new StudentVO(123, "Mapper", 24, "4학년", "주간", new DeptVO(10));
  		int cnt = studentMapper.insertStudent(student);
  		System.out.println("등록된 학생 건수 : " + cnt );
  		
  		
  	}
  
  -----------------------------------------
  StudentVO [id=123, name=Mapper, age=24, grade=4학년, daynight=주간, dept=Dept [deptid=10, deptname=경제학과], courseStatus=null]
  ```

  

## 2. Servlet, JSP, JSTL 개념

> JSP : Java Server Page
>
> JSTL : Java Standard Tag Library



- tomcat(web container) 서버에서 동작한다.
- **Servlet은 클래스 , JSP는 스크립트**
- JSP와 비슷한 종류 : PHP, ASP
- html, css, javascript : 정적(Static) 컨텐츠 생산
  - **html 에서 UserDAO(DB 연동) 객체의 method를 호출할 수 없다.**
- **Servlet, JSP : 동적(Dynamic) 컨텐츠 생산**
  - **HTML → Servlet/JSP → DAO 객체**



### MVC 패턴

- **Model / View /  Controller**
- **Seperation of Concerns(=Responsibility 책임, 역할) **
  - 역할을 분리해서 유지보수성을 높여보자



#### MVC 패턴을 기반으로 하는 Web Architecture

- **Model 1 아키텍처(초창기 사용법)**

  - Model - Java(DAO, Service, VO)
  - View - JSP, HTML
  - **Controller - JSP**

  

- **Model 2 아키텍처(요즘 사용하는 모델)**

  - Model - Java(DAO, Service, VO)
  - View - JSP, HTML
  - **Controller - Servlet**





## 3. Dynamic Web Project

> **src** : DAO, VO, Servlet Class
>
> **WebContent** : JSP, HTML



#### JSP 생성

- **Setting**

  - Window>Web Browser>Chrome

  - Window>Preferenece>Web>JSP Files>Encoding : ISO(UTF-8)

  - **WebContent**>new other>Web>JSP File

  - **Tomcat Server에 Add and Remove 를 통해 Web Dynamic Project package를 추가하면 된다.**

    

- **index.jsp**

  ```jsp
  <%@page import="java.util.Date"%>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<h2>사용자 관리 메인</h2>
  	<!-- html 주석 -->
  	<%-- jsp 주석 
  		<% %> : jsp 안에 java code를 자유롭게 기술할 수 있는 영역 - scriptlet tag
  		<%= %> : java의 method 실행 결과, 변수를 출력 - expression tag
  	--%>
  	<%
  	Date date = new Date();
  	out.println(date);
  	%> 
  	<h4>현재 날짜는 <%=date%></h4>
  </body>
  </html>
  ```



#### Servlet 생성

- **URL Mapping** : 사용자가 원하는 이름으로 URL을 지정할 수 있다.

  ```java
  @WebServlet("/hello")
  // Tomcat이 @WebServlet 어노테이션을 통해 객체를 대신 생성해 준다.
  
  
  public class HelloServlet extends HttpServlet {
      
      
      //(생략)
      
      
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		
  		//1. Content type 설정 - Mime type , encoding
  		response.setContentType("text/html;charset=utf-8");
  		
  		//2. 출력 스트림 생성
  		PrintWriter out = response.getWriter();
  		
  		//3. html 생성
  		Date date = new Date(); 
  		out.println("<h2>현재 날짜는 " + date + "</h2>");
  		out.println("Http Method : " + request.getMethod());
          // Http Method : GET 출력
          
  		out.close(); //출력 스트림 반환
  		
  		
  	}
```
  
**※ Servlet 에서도 JSP와 같은 기능을 사용할 수 있다.**
  

  
  

\- **Hello**는 객체를 생성하지 않았는데 어떻게 생성되었는가?

  - **Tomcat(WAS)이 `@WebServlet` 어노테이션을 보고 자동 생성해주었다.**



\- `web.xml`을 이용하여 **Hello** 객체와 매핑하는 방법

​	**: `@WebServlet` 어노테이션 주석 처리 후 이용해야 한다.**

- **web.xml**

  ```xml
    <servlet>
    	<servlet-name>HelloServlet</servlet-name>
    	<servlet-class>controller.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
    	<servlet-name>HelloServlet</servlet-name>
    	<url-pattern>/hello</url-pattern>
    </servlet-mapping>
  </web-app>
  ```



## 4. Servlet 실습



### 4.1) doGet을 이용하여 정보 조회하기



#### 흐름

: `index.jsp(조회)` → `UserServlet` → `DAO(Model)` → `JDBC(DB 데이터 조회)` → `DAO` → `UserServlet`  → `userList.jsp(출력)`



- **index.jsp**

  ```jsp
  <%@page import="java.util.Date"%>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<!-- html 주석 -->
  	<%-- jsp 주석 
  		<% %> : jsp 안에 java code를 자유롭게 기술할 수 있는 영역 - scriptlet tag
  		<%= %> : java의 method 실행 결과, 변수를 출력 - expression tag
  	--%>
      
  	<%
  	Date date = new Date();
  	out.println(date);
  	%> 
  	<h4>현재 날짜는 <%=date%></h4>
  	<h2>사용자 관리 메인</h2>
  	<ol>
  		<li><a href="user.do">사용자 리스트</a></li>
  	</ol>
  </body>
  </html>
  ```

  - **<% %>** : jsp 안에 java code를 자유롭게 기술할 수 있는 영역 - **scriptlet tag**
  - **<%= %>** : java의 method 실행 결과, 변수를 출력 - **expression tag**
  - 링크를 누르면 `UserServlet` 클래스로 이동
    - `UserServlet`클래스는 `@WebServlet(*.do)` 로 어노테이션 해놓았기 때문에, 해당 클래스로 링크가 연결된다.
  - **결과**

  ![index](https://user-images.githubusercontent.com/58682321/79431366-5061cd00-8005-11ea-8458-c7a6eaf1db4e.PNG)



- **UserServlet.java**

  ```java
  @WebServlet("*.do")
  public class UserServlet extends HttpServlet {
  	private static final long serialVersionUID = 1L;
  	private UserDAO dao;
  	private RequestDispatcher rd;
  
  	@Override
  	public void init() throws ServletException {
  		System.out.println("UserServlet init() method called!!");
  		dao = new UserDAO();
  	}
  
  	@Override
  	public void destroy() {
  		System.out.println("UserServlet destroy() method called!");
  	}
  
      
      // 생략
      
      
  	protected void doGet(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		System.out.println("UserServlet doGet() method called!");
  
  		// 1. DAO 호출
  		List<UserVO> users = dao.getUsers();
  		System.out.println("-------------------");
  		System.out.println(users + "\n");
          //DB table에 저장되어 있는 users 정보가 Console에 출력된다.
  
  		// 2. DAO로 받아 온 List 객체를 JSP에서 사용할 수 있도록 Request 객체에 저장
  		request.setAttribute("userList", users);
  
  		// 3. 결과를 출력해 줄 JSP - userList.jsp를 포워딩
  		rd = request.getRequestDispatcher("userList.jsp");
  		rd.forward(request, response);
  	}
  
  ```

  - `DAO(DB 연동)` 클래스가 DB로 부터 얻어온 정보를 **`userList.jsp`**에 넘겨 출력될 수 있게 한다.
    - 이때, `request.setAttribute()`를 이용해 정보를 입력할 수 있다.
    - 또한, `request.getRequestDispatcher()`를 통해 **`userList.jsp`**에 정보를 포워딩 한다.



- **userList.jsp(출력)**

  ```jsp
  <%@page import="java.util.List"%>
  <%@page import="jdbc.user.vo.UserVO" %>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<h2>사용자 리스트</h2>
  	
  	<%
  	 //1. request 객체 가져오기
  	 List<UserVO> list = (List<UserVO>)request.getAttribute("userList");
  	 out.println(list);
  	%>
  </body>
  </html>
  ```

  - `request.getAttribute()`는 **Object** 타입 이기 때문에 `(List<UserVO>)`를 통해 **형변환**을 해주어야 한다.
  - **`UserServlet.java`** 가 포워딩한 정보를 `request.getAttribute()`를 통해 받아올 수 있다.



**※** 위의 과정을 마치고, **`index.jsp`**에서 서버를 재실행 한 뒤, **웹 브라우저를 확인**해 보면, **`userList(~.do)` 페이지에 DB 에 들어있는 User 정보가 출력**되어 있는 것을 확인 할 수 있다.





## 5. JSTL(Java Standard Tag Library)

> 간단한 프로그램 로직의 구사(자바의 변수 언언, if 문, for 문 등에 해당하는 로직)



- **Setting**
  - **jstl-1.2.jar** 파일을 WebContent>WEB-INF>lib 아래에 추가한다.



- **userList.jsp**

  ```jsp
  <%--@page import="java.util.List"--%>
  <%--@page import="jdbc.user.vo.UserVO" --%>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
  	pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<h2>사용자 리스트</h2>
  	<%-- EL(Expression Language) --%>
  	<%-- 	${userList} --%>
  	<table border="1">
  		<tr>
  			<th>아이디</th>
  			<th>이름</th>
  			<th>성별</th>
  			<th>주소</th>
  		</tr>
  		<c:forEach var="user" items="${userList}">
  			<tr>
  				<td>${user.userid}</td>
  				<td>${user.name}</td>
  				<td>${user.gender}</td>
  				<td>${user.city}</td>
  			
  			</tr>
  		</c:forEach>
  
  	</table>
  
  	<%--
  	 //1. request 객체 가져오기
  	 List<UserVO> list = (List<UserVO>)request.getAttribute("userList");
  	 out.println(list);
  	--%>
  </body>
  </html>
  ```

  

  - **결과**

  ![사용자리스트](https://user-images.githubusercontent.com/58682321/79431371-535cbd80-8005-11ea-8fbc-f091e2a35a33.PNG)



### 5.1) 서블릿 분기 만들어 보기

: **cmd**를 이용하여 **userList**,  **userDetail**과 같은 서블릿 분기를 만들어준다.



- **index.jsp  - userList**

  ```jsp
  	<ol>
  		<li><a href="user.do?cmd=userList">사용자 리스트</a></li>
  	</ol>
  ```

  

- **userList.jsp - userDetail**

  ```jsp
  	<table border="1">
  		<tr>
  			<th>아이디</th>
  			<th>이름</th>
  		</tr>
  		<c:forEach var="user" items="${userList}">
  			<tr>
  				<td>${user.userid}</td>
  				<td><a href="user.do?id=${user.userid}&cmd=userDetail">${user.name}</a></td>
  
  			</tr>
  		</c:forEach>
  	</table>
  ```



**jsp에서 설정을 마친 후, 서블릿 클래스에 이를 반영해준다.**



- **UserServlet.java**

  ```java
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  
  		String cmd = request.getParameter("cmd");
      
  		if(cmd.equals("userList")) {
  			userList(request, response);
  		} else if(cmd.equals("userDetail")) {
  			userDetail(request, response);
  		}
  	}
  
  // userDetail 메서드 추가하기
  	private void userDetail(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		String userid = request.getParameter("id");
          //id 값을 이용
  		
  		System.out.println(userid);
  // 웹 브라우저에서 이름을 클릭하면 Console에 해당 아이디가 출력된다.
  
  	}
  	
  	private void userList(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		// 1. DAO 호출
  		List<UserVO> users = dao.getUsers();
  		System.out.println("-------------------");
  		System.out.println(users + "\n");
  
  		// 2. DAO로 받아 온 List 객체를 JSP에서 사용할 수 있도록 Request 객체에 저장
  		request.setAttribute("userList", users);
  	
  		// 3. 결과를 출력해 줄 JSP - userList.jsp를 포워딩
  		rd = request.getRequestDispatcher("userList.jsp");
  		rd.forward(request, response);
  	}
  ```

  - **if 문**을 추가하여 **userList** , **userDetail** 중 어떤 정보를 요청했는지 파악한다.
    - **userList**의 경우 **user List** 정보를 넘겨 주고, 화면에 **user id, user name** 이 출력된다.
    - **userDetail**의 경우 **user name**을 클릭 할 경우, 해당되는 **user id** 값을 **Console** 에 출력해준다.
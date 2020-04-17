# Servlet, JSP



- **Review**

```
[Servlet과 JSP]
Servlet : Java Code 내에 html을 포함시킬 수 있다. (컴파일)
JSP : html 내에 java code를 포함시킬 수 있다.더 최신에 나옴 (스크립트)

html → servlet/JSP → DAO

[Spring MVC] - Model2
Model : Java(DAO, Service, VO)
View : JSP, HTML
Controller : Servlet

Batch Program : 기업 내의 데이터를 모아 두었다가 정기적으로 한 번에 처리하는 방식
```



## 1. userDetail 페이지 만들기(GET 방식)



- **userDetail.jsp**

  ```jsp
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
  	<h2>사용자 상세 정보</h2>
  <%-- 	${user} --%>
  
  <ul>
  	<li>아이디 : ${user.userid}</li>
  	<li>이름 : ${user.name}</li>
  	<li>성별 : ${user.gender}</li>
  	<li>지역 : ${user.city}</li>
  
  </ul>
  
  </body>
  </html>
  ```

  **contentType** : contentType에 설정한 charset은 응답(response) 데이터의 인코딩 

  **pageEncoding** : jsp에 포함된 코드에 대한 인코딩


- **UserServlet.java**

  ```java
  //분기 로직 지정
  else if (cmd.equals("userForm")) {
  userForm(request, response) };
  
  	// userDetail
  	private void userDetail(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  
  		String userid = request.getParameter("id");
  		System.out.println(userid);
  // 웹 브라우저에서 이름을 클릭하면 Console에 해당 아이디가 출력된다.
  
  		// 1. DAO 호출
  		UserVO userVO = dao.getUser(userid);
  		System.out.println("-------------------");
  		System.out.println(userVO);
  
  		// 2. DAO로 받아 온 userVO 객체를 JSP에서 사용할 수 있도록 Request 객체에 저장
  		request.setAttribute("user", userVO); // (key, value)
  
  		// 3. 결과를 출력해 줄 JSP - userDetail.jsp를 포워딩
  		rd = request.getRequestDispatcher("userDetail.jsp");
  		rd.forward(request, response);
  	}
  ```

  





## 2. userForm 페이지 만들기(POST 방식)



#### 1. index.jsp 에서 링크를 통해 userForm 페이지로 이동한다.

- **index.jsp**

  ```jsp
  <li><a href="user.do?cmd=userForm">사용자 등록</a></li>
  ```



#### 2. userForm.html 생성(view)

- **userForm.html**

  ```html
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="utf-8">
  <title>Insert title here</title>
  </head>
  <body>
  	<h2>사용자 등록</h2>
  	<form action="user.do" method="post">
  		<input type="hidden" name="cmd" value="userInsert"> 
  		<!-- 화면 상에 보이지 않지만 cmd에 userInsert value를 부여한다.-->
  	
  		<label>아이디 :</label> <input type="text" name="userid"> <br>
  		<label>이름 :</label> <input type="text" name="name"> <br>
  	
  		<label>성별 :</label> 
  		<input type="radio" name="gender" value="남">남
  		<input type="radio" name="gender" value="여">여<br>
  		
  		<label>주소 :</label>
  		<select name="city">
  			<option value="">지역 선택</option>
  			<option value="서울">서울</option>
  			<option value="경기">경기</option>
  			<option value="부산">부산</option>
  			<option value="제주">제주</option>
  		</select> <br><br>
  		
  		<input type="submit" value="등록">
  		
  	</form>
  </body>
  </html>
  ```

  - **hidden type** : 화면 상에 보이지 않지만 **cmd에 userInsert value를 부여**한다.

    

#### 3. POST 방식으로 Servlet에 정보 전달

- **UserServlet.java**

  ```java
  //1. 요청(request) 데이터의 인코딩
  request.setCharacterEncoding("utf-8");
  //이때, 인코딩 방식은 userForm.html 인코딩 방식과 동일해야 한다.
  
  //(중간 생략)
  
  
  // userForm
  	private void userForm(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		response.sendRedirect("userForm.html");
  		// 객체를 넘기지 않고, 해당 페이지를 포워딩만 해준다.
  	}
  ```

  - `doGet()` 으로 받은 객체들은 모두 `doPost()` 를 거치기 때문에, `doPost()`에는 따로 로직을 추가하지 않아도 된다.



## 3. 입력한 Form 을 userList 에 등록하기



#### 1. UserServlet 설정하기

- **UserServlet.java**

  ```java
  	// userInsert
  	private void userInsert(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  
  		// 1. userFrom 데이터를 추출해서 UserVO에 저장한다.
  		UserVO user = new UserVO(request.getParameter("userid"), request.getParameter("name"),
  				request.getParameter("gender").charAt(0), request.getParameter("city"));
  
  		System.out.println(user);
  		
  		int cnt = dao.insertUser(user);
  		if(cnt ==1) { //정상 실행
  			userList(request, response);
  			
  		}
  
  	}
  ```

  - **userForm.html** 에서 **등록**을 하게 되면 **cmd**에 **userInsert** 값을 넣어 전송하게 된다. 
  - 이후, **분기 로직**에 의해 위와 같은 `userInsert()`  실행
  - **userForm 페이지**에서 등록한 정보를 **UserVO** 객체에 담고,  **UserDAO(DB 연동)**에 추가하여 **실제 DB에 해당 데이터를 삽입**한다.
  - 위의 과정이 정상적으로 실행 되었을 때, **userList(request, response)** 를 통해 화면 단에 **userList** 가 출력되도록 설정한다.
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
		<li><a href="user.do?cmd=userList">사용자 리스트</a></li>
		<!-- cmd를 통해 userList or userDetail 인지에 대해서 서블릿을 분기 처리 하기 위함이다. -->
		<li><a href="user.do?cmd=userForm">사용자 등록</a></li>
	</ol>
</body>
</html>
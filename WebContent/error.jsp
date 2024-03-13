<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="error_style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page here</title>
</head>
<body >
<div align="center">
<h1>You have entered wrong ID or Password.</h1>
<a href="login.html">Click here to go back.</a>
	
	<h2>You have <%out.print( request.getParameter("att")) ;%> attempts left.</h2>
	<%
	String i = request.getParameter("att"); 
	int j = Integer.parseInt(i);
	if(j==1){
		%>
	<h2>Your Account will be blocked after this attempt.</h2>
	<% 
	}
	 %>
	</div>
</body>
</html>
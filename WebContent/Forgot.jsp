<%@page import="com.project.Dao.DaoOperation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
</head>
<body>
<%
DaoOperation dao = new DaoOperation();

%>
 <form action="./CheckOtp">
 <input type="text" placeholder="Enter OTP" name="otp"/><br>
 Enter Login ID <input type="text" name="loginid"/><br>
 Enter new password <input type="password" name="pwd"><br>
 Re-enter new Password <input type="password" name="repwd"/><br>
 <input type="submit" value="Submit">
 
 </form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

	<%
	String result = (String) request.getParameter("status");
	if(result.equalsIgnoreCase("SUCCESFULL")){
		%>
		<body onload="myFunction()"></body>
		<script type="text/javascript">
			function myFunction(){
				alert("Transaction Succesfull");
				window.location = "./FundTransfer";
			}
		</script>
		<% 
	}
	%>
	
	<%
	String result1 = (String) request.getParameter("status");
	if(result1.equalsIgnoreCase("UNSUCCESFULL")){
		%>
		<body onload="myFunction()"></body>
		<script type="text/javascript">
			function myFunction(){
				alert("Transaction Failed Due to internal DB ERROR.");
				window.location = "logout.jsp";
			}
		</script>
		<% 
	}
	%>
	
	<%
	String result2 = (String) request.getParameter("status");
	if(result2.equalsIgnoreCase("insufficientbalance")){
		%>
		<body onload="myFunction()"></body>
		<script type="text/javascript">
			function myFunction(){
				alert("Transaction Failed : Insufficient Balance in your Account");
				window.location = "logout.jsp";
			}
		</script>
		<% 
	}
	%>
	
	<%
	String result3 = (String) request.getParameter("status");
	if(result3.equalsIgnoreCase("wrongpassword")){
		%>
		<body onload="myFunction()"></body>
		<script type="text/javascript">
			function myFunction(){
				alert("Transaction Failed : Wrong Transaction Password");
				window.location = "logout.jsp";
			}
		</script>
		<% 
	}
	%>
	<%
	String result4 = (String) request.getParameter("status");
	if(result4.equalsIgnoreCase("aidnotfound")){
		%>
		<body onload="myFunction()"></body>
		<script type="text/javascript">
			function myFunction(){
				alert("Transaction Failed : Account ID not Found in DB");
				window.location = "logout.jsp";
				
			}
		</script>
		<% 
	}
	%>
	
	<br><br>

</body>
</html>
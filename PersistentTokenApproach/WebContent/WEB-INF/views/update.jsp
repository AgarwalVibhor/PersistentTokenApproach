<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Page</title>
</head>
<body>
	<h1>Title : Spring Security Remember Me Example - Update Form</h1>
	<h1>Message : This page is for ROLE_ADMIN and fully authenticated only
            (Remember me cookie is not allowed!)</h1>

	<h2>Update Account Information...</h2>
	
	<br />
	
	<form id="logoutForm" action='<c:url value="/j_spring_security_logout" />' method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<script type="text/javascript">
		function formSubmit()
		{
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<h3>Click <a href="javascript:formSubmit()">Here</a> to Logout.</h3>
</body>
</html>
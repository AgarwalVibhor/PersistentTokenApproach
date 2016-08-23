<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
</head>
<body>
	<h1>${title}</h1>
	<br />
	<h1>${message}</h1>
	
	<form id="logoutForm" action='<c:url value="/j_spring_security_logout" />' method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<script type="text/javascript">
		function formSubmit()
		{
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>Admin : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">Logout</a></h2>
	</c:if>
	
	<sec:authorize access="isRememberMe()">
		<h2># This user is login by "Remember Me Cookies"</h2>
	</sec:authorize>
	
	<sec:authorize access="isFullyAuthenticated()">
		<h2># This user is login by username / password.</h2>
	</sec:authorize>
	
	<br />
	<br />
	
	
	<h3>Click <a href='<c:url value="/admin/update" />'>Here</a> to go to the Update Page.</h3>
	
</body>
</html>
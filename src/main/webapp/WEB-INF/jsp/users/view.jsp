<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="lbl.user.details"/></title>
</head>
<body>

<h1><fmt:message key="lbl.user.informationUpdate"/></h1>

<c:if test="${not empty mensagem}">

<h3><fmt:message key="word.message"/>: <span style="color: red;"> ${mensagem}</span></h3>

</c:if>

<form action="<c:url value="/users" />" name="registerForm" method="post">
	<fieldset>
		<legend><fmt:message key="word.user"/></legend>
		<label for="name"><fmt:message key="word.name"/></label>
		<input type="text" name="user.name" value="${user.name}" />
		
		<input type="hidden" name="_method" value="put" />
		
		<input type="submit" value="<fmt:message key="word.update"/>" />
	</fieldset>
</form>

</body>
</html>
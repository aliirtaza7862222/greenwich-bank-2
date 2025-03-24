<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose>
	<c:when test="${not empty cookie.user.value}">
	<h2>Welcome, ${cookie.user.value}. You are now logged in.</h2>
	<button onclick="logout()">Log Out</button>
	</c:when>
	<c:otherwise>
		<h2>Welcome to Greenwich Bank. Please log in.</h2>
		<button id="loginButton">Log In</button>
	</c:otherwise>
</c:choose>
<script></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLDecoder" %>
<c:choose>
	<c:when test="${not empty cookie.user.value}">
		<%
			String decodedUser = "";
			if (request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
					if ("user".equals(cookie.getName())) {
						decodedUser = URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			}
			request.setAttribute("decodedUser", decodedUser);
		%>
	<h2>Welcome, ${decodedUser}. You are now logged in.</h2>
	<button onclick="logout()">Log Out</button>
	</c:when>
	<c:otherwise>
		<h2>Welcome to Greenwich Bank. Please log in.</h2>
		<button id="loginButton">Log In</button>
	</c:otherwise>
</c:choose>
<script></script>
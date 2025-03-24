<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLDecoder" %>
<div class="container">
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
            <h2>List of ${decodedUser}'s Accounts</h2>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Alias</th>
                    <th>Account Balance</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="account" items="${Accounts}">
                    <tr>
                        <td>${account.accountID}</td>
                        <td>${account.accountAlias}</td>
                        <td>£<fmt:formatNumber value="${account.accountBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                        </td>
                        <td>
    						<button class="view-account-btn" data-id="${account.accountID}">View</button>
   							<button class="delete-account-btn" data-id="${account.accountID}">Delete</button>
						</td>
                    </tr>
                </c:forEach>
            </table>
            <button type="submit" class="action-btn" data-action="addAccount">Create Account</button>
        </c:when>
        <c:otherwise>
            <h2>List of All Accounts</h2>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Alias</th>
                    <th>Account Balance</th>
                    <th>Associated Student</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="account" items="${Accounts}">
                    <tr>
                        <td>${account.accountID}</td>
                        <td>${account.accountAlias}</td>
                        <td>£<fmt:formatNumber value="${account.accountBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" />
</td>
                        <td>${account.student.studentName}, ${account.student.studentID}</td>
                        <td>
                            <button class="view-account-btn" data-id="${account.accountID}">View</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script>
    document.addEventListener("click", (e) => {
        if (e.target.classList.contains("view-account-btn")) {
            const id = e.target.dataset.id;
            loadPage(`viewAccount&id=${id}`);
        }
    });
    document.addEventListener("click", (e) => {
        if (e.target.classList.contains("delete-account-btn")) {
            const id = e.target.dataset.id;
            loadPage(`deleteAccountConfirmation&id=${id}`);
        }
    });
</script>

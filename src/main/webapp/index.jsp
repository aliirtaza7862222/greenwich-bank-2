<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to Greenwich Bank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
    <header>
        <h1>Greenwich Bank Co.</h1>
        <nav>
            <ul id="menu">
                <li><a href="#" data-action="home">Home</a></li>
                <li><a href="#" data-action="listStudents">Students</a></li>
                <li><a href="#" data-action="listAccounts">Accounts</a></li>
                <c:if test="${not empty cookie.user.value}">
                <li><a href="#" data-action="transfer">Transfer</a></li>
                <li><a href="#" data-action="depositOrWithdraw">Deposit/Withdraw</a></li>
                </c:if>
            </ul>
        </nav>
    </header>
    
    <div id="content">
        <!-- Dynamic content loads here -->
    </div> 
    <footer>
        <p>&copy; 2025 Greenwich Bank Co. Your money is probably not safe with us.</p>
    </footer>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>

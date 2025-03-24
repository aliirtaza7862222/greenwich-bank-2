<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">
    <c:choose>
        <c:when test="${not empty cookie.user.value}">
            <h2>List of ${cookie.user.value}'s Accounts</h2>
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
                        <td>${account.accountBalance}</td>
                        <td>
                            <button class="view-account-btn" data-id="${account.accountID}">View</button>
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
                        <td>${account.accountBalance}</td>
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
</script>

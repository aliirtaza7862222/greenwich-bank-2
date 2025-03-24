<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="account-detail">
    <h2>Account Detail</h2>
    <p><strong>ID:</strong> ${account.accountID}</p>
    <p><strong>Alias:</strong> ${account.accountAlias}</p>
    <p><strong>Balance:</strong> £${account.accountBalance}</p>
    <p><strong>Student:</strong> ${account.student.studentName} (ID: ${account.student.studentID})</p>

    <form action="Controller" method="post" style="display:inline;">
        <input type="hidden" name="action" value="deleteAccount" />
        <input type="hidden" name="accountID" value="${account.accountID}" />
        <button id="deleteAccountBtn" data-account-id="${account.accountID}">Delete Account</button>
    </form>

    <button onclick="loadPage('listAccounts')">Back</button>
</div>

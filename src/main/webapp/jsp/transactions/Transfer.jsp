<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="form-container">
    <h2>Transfer Funds</h2>
    <form action="Controller" method="post">
        <input type="hidden" name="action" value="transfer">

        <div class="form-group">
            <label for="fromAccountID">From Account ID:</label>
             <select name="fromAccountID" id="fromAccountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £${account.accountBalance})
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="toAccountID">To Account ID:</label>
            <select name="toAccountID" id="toAccountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £${account.accountBalance})
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="amount">Amount (£):</label>
            <input type="number" name="amount" id="amount" step="0.01" required>
        </div>

        <button type="submit" class="submit-btn">Transfer</button>
    </form>
</div>

<script>
    document.getElementById('transferForm').addEventListener('submit', function(event) {
        const fromAccountID = document.getElementById('fromAccountID').value;
        const toAccountID = document.getElementById('toAccountID').value;
        const amount = document.getElementById('amount').value;

        // for debugging
        console.log("From Account ID: " + fromAccountID);
        console.log("To Account ID: " + toAccountID);
        console.log("Amount: " + amount);
    });
</script>
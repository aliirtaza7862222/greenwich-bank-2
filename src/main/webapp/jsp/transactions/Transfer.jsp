<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="form-container">
    <h2>Transfer Funds</h2>
    <form action="Controller" method="post">
        <input type="hidden" name="action" value="transfer">

        <div class="form-group">
            <label for="fromAccountID">From Account:</label>
            <select name="fromAccountID" id="fromAccountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £<fmt:formatNumber value="${account.accountBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" />
)
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="toAccountID">To Account:</label>
            <select name="toAccountID" id="toAccountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £<fmt:formatNumber value="${account.accountBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" />
)
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

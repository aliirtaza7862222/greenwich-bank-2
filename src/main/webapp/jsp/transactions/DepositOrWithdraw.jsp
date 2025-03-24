<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="form-container">
    <h2>Account Transaction</h2>

    <form action="Controller?action=depositOrWithdraw" method="post">
        <input type="hidden" name="action" value="processTransaction">

        <label>
			<input type="radio" name="transaction" value="deposit" checked/> Deposit 
		</label>

		<label>
			<input type="radio" name="transaction" value="withdraw" /> Withdraw 
		</label>

        <div class="form-group">
            <label for="accountID">Select Account:</label>
            <select name="accountID" id="accountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £<fmt:formatNumber value="${account.accountBalance}" type="number" minFractionDigits="2" maxFractionDigits="2" />)
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="amount">Transaction Amount (£):</label>
            <input type="number" name="amount" id="amount" step="0.01" required>
        </div>

        <button type="submit" class="submit-btn">Submit</button>
    </form>
</div>

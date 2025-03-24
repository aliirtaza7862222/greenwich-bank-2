<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<div class="form-container">
    <h2>Account Transaction</h2>

    <form id="transactionForm" action="Controller" method="post">
        <!-- Hidden input to set the action dynamically -->
        <input type="hidden" name="action" id="transactionAction" value="deposit">

        <!-- Radio buttons for deposit or withdraw -->
        <label>
            <input type="radio" name="transaction" value="deposit" checked/> Deposit 
        </label>
        <label>
            <input type="radio" name="transaction" value="withdraw" /> Withdraw 
        </label>

        <!-- Dropdown to select account -->
        <div class="form-group">
            <label for="accountID">Select Account:</label>
            <select name="accountID" id="accountID" required>
                <c:forEach var="account" items="${Accounts}">
                    <option value="${account.accountID}">
                        ${account.accountAlias} (ID: ${account.accountID}, £${account.accountBalance})
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Input for transaction amount -->
        <div class="form-group">
            <label for="amount">Transaction Amount (£):</label>
            <input type="number" name="amount" id="amount" step="0.01" required>
        </div>

        <!-- Submit button -->
        <button type="submit" class="submit-btn">Submit</button>
    </form>
</div>

<script>
    document.getElementById('transactionForm').addEventListener('submit', function(event) {
        //to not submit immediately
        event.preventDefault();

        // first we get the data
        const selectedTransaction = document.querySelector('input[name="transaction"]:checked').value;
        const accountID = document.getElementById('accountID').value;
        const amount = document.getElementById('amount').value;

        // for debugging
        console.log("Selected Transaction: " + selectedTransaction);
        console.log("Account ID: " + accountID);
        console.log("Amount: " + amount);

        // based on what we selected we will select the action for post form either deposit or withdraw
        document.getElementById('transactionAction').value = selectedTransaction;

        // Submit the form programmatically
        event.target.submit();
    });
</script>

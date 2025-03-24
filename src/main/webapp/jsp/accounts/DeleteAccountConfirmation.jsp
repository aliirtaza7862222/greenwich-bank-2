<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="confirmation-dialog">
    <h2>Confirm Account Deletion</h2>
    <p>Are you sure you want to delete account ${account.accountAlias} (ID: ${account.accountID})?</p>
    <p>Balance: £${account.accountBalance}</p>
    
    <form action="Controller" method="post">
        <input type="hidden" name="action" value="deleteAccount" />
        <input type="hidden" name="accountID" value="${account.accountID}" />
        <button type="submit" class="confirm-btn">Confirm Delete</button>
        <button type="button" class="cancel-btn" onclick="loadPage('listAccounts')">Cancel</button>
    </form>
</div>
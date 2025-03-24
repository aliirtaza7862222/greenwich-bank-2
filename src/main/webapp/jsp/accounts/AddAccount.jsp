<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="form-container">
    <h2>Create New Account</h2>

    <form action="Controller?action=addAccount" method="post" accept-charset="UTF-8">

        <div class="form-group">
            <label for="accountAlias">Account Alias:</label>
            <input type="text" name="accountAlias" id="accountAlias" placeholder="My Savings Account" required>
        </div>

        <button type="submit" class="submit-btn">Create Account</button>
    </form>
</div>

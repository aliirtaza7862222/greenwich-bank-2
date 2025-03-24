
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="form-container">
    <h2>Add New Student</h2>

    <form action="Controller?action=addStudent" method="post" accept-charset="UTF-8">
        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text" name="name" id="name" placeholder="John Doe" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" placeholder="john@example.com" required>
        </div>

        <div class="form-group">
            <label for="phone">Phone Number:</label>
            <input type="text" name="phone" id="phone" placeholder="07123 456789" required>
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" name="address" id="address" placeholder="123 Bank Street" required>
        </div>

        <button type="submit" class="submit-btn">Add Student</button>
    </form>
</div>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Choose a student to log in:</h2>

<form id="loginForm">
    <label for="user">Student:</label>
    <select name="username" id="username" required>
        <c:forEach var="student" items="${students}">
            <option value="${student.studentName}" data-id="${student.studentID}">${student.studentName}</option>
        </c:forEach>
    </select>
    <button type="submit">Log In</button>
</form>
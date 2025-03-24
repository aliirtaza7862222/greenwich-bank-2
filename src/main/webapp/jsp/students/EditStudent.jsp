<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">
    <h2>Edit Student</h2>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <form method="post" action="Controller">
        <input type="hidden" name="action" value="updateStudent" />
        <input type="hidden" name="studentID" value="${student.studentID}" />

        <div class="form-group">
            <label for="studentName">Name:</label>
            <input type="text" id="studentName" name="studentName" 
                   value="${student.studentName}" required class="form-control" />
        </div>

        <div class="form-group">
            <label for="studentEmail">Email:</label>
            <input type="email" id="studentEmail" name="studentEmail" 
                   value="${student.studentEmail}" required class="form-control" />
        </div>

        <div class="form-group">
            <label for="studentAddress">Address:</label>
            <input type="text" id="studentAddress" name="studentAddress" 
                   value="${student.studentAddress}" required class="form-control" />
        </div>

        <div class="form-group">
            <label for="studentPhone">Phone:</label>
            <input type="tel" id="studentPhone" name="studentPhone" 
                   value="${student.studentPhone}" required class="form-control" />
        </div>

        <button type="submit" class="action-btn">Update Student</button>
        <a href="Controller?action=listStudents" class="action-btn cancel">Cancel</a>
    </form>
</div>
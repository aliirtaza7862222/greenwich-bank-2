<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">
    <h2>List of All Students</h2>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Postal Address</th>
            <th>Email</th>
            <th>Telephone</th>
            <th>Actions</th>
        </tr>

        <c:forEach var="student" items="${Students}">
            <tr>
                <td>${student.studentID}</td>
                <td>${student.studentName}</td>
                <td>${student.studentAddress}</td>
                <td>${student.studentEmail}</td>
                <td>${student.studentPhone}</td>
                <td>
                    <form method="post" action="Controller" style="display:inline;">
                        <input type="hidden" name="action" value="deleteStudent" />
                        <input type="hidden" name="studentID" value="${student.studentID}" />
                        <button type="submit" class="action-btn delete"
                                onclick="return confirm('Are you sure you want to delete this student?');">
                            Delete
                        </button>
                    </form>
                    <a href="Controller?action=editStudent&id=${student.studentID}" 
                           class="action-btn edit">
                            Edit
                        </a>
                   
                </td>
                
            </tr>
        </c:forEach>
    </table>

    <div class="button-wrapper">
            <button type="button" class="action-btn" data-action="addStudent">Add Student</button>
    </div>
</div>

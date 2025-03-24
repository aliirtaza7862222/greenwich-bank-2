<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <div class="container">
  <body>
    <h2>List of all Students</h2>
    <table border="1">
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
        	<button class="delete-student-btn" data-id="${student.studentID}">Delete</button>
      	</td>
        </tr>
      </c:forEach>
    </table>
    <c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>
		<button type="submit" class="action-btn" data-action="addStudent">Add Student</button>
		
  </body>
</div>

<script>
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete-student-btn")) {
        if (confirm(`Are you sure you want to delete student ${e.target.dataset.id}?`)) {
            loadPage(`deleteStudent&id=${e.target.dataset.id}`);
        }
    }
});
</script>

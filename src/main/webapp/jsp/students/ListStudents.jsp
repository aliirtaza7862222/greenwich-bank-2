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
      </tr>
      <c:forEach var="student" items="${Students}">
        <tr>
          <td>${student.studentID}</td>
          <td>${student.studentName}</td>
          <td>${student.studentAddress}</td>
          <td>${student.studentEmail}</td>
          <td>${student.studentPhone}</td>
        </tr>
      </c:forEach>
    </table>
    <c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>
		<button type="submit" class="action-btn" data-action="addStudent">Add Student</button>
  </body>
</div>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <title>Edit Student</title>
</head>

<div class="form-container">
    <h2>Edit Student</h2>
    
    <c:if test="${not empty error}">
        <div class="error-message">
            <h3>❌ Error</h3>
            <p>${error}</p>
        </div>
    </c:if>
    
    <c:if test="${not empty success}">
        <div class="success-message">
            <h3>✅ Success</h3>
            <p>${success}</p>
        </div>
    </c:if>

    <form method="post" action="Controller" class="student-form">
        <input type="hidden" name="action" value="updateStudent" />
        <input type="hidden" name="studentID" value="${student.studentID}" />

        <div class="form-group">
            <label for="studentName">Full Name</label>
            <input type="text" id="studentName" name="studentName" 
                   value="${student.studentName}" required 
                   class="form-control" placeholder="Enter full name">
        </div>

        <div class="form-group">
            <label for="studentEmail">Email Address</label>
            <input type="email" id="studentEmail" name="studentEmail" 
                   value="${student.studentEmail}" required 
                   class="form-control" placeholder="Enter email address">
        </div>

        <div class="form-group">
            <label for="studentAddress">Postal Address</label>
            <input type="text" id="studentAddress" name="studentAddress" 
                   value="${student.studentAddress}" required 
                   class="form-control" placeholder="Enter full address">
        </div>

        <div class="form-group">
            <label for="studentPhone">Phone Number</label>
            <input type="tel" id="studentPhone" name="studentPhone" 
                   value="${student.studentPhone}" required 
                   class="form-control" placeholder="Enter phone number">
        </div>

        <div class="form-actions">
            <button type="submit" class="submit-btn">
                <i class="fas fa-save"></i> Update Student
            </button>
            <a href="Controller?action=home" class="cancel-btn">
                <i class="fas fa-times"></i> Cancel
            </a>
        </div>
    </form>
</div>

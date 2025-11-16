<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.CV" %>

<%
    CV cv = (CV) request.getAttribute("cv");
    String error = (String) request.getAttribute("error");

    if (cv == null) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
            <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <title>Edit CV</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4">Edit CV</h2>

    <% if (error != null) { %>
        <div class="alert alert-danger"><%= error %></div>
    <% } %>

<form action="edit-cv" method="post" enctype="multipart/form-data" class="card p-4 shadow-sm">
    <input type="hidden" name="id" value="<%= cv.getCVID() %>"/>

    <!-- Full Name -->
    <div class="mb-3">
        <label class="form-label">Full Name</label>
        <input type="text" class="form-control" name="fullName" 
               value="<%= cv.getFullName() %>" required>
    </div>

    <!-- Email -->
    <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="email" class="form-control" name="email" 
               value="<%= cv.getEmail() %>" required>
    </div>

    <!-- Address -->
    <div class="mb-3">
        <label class="form-label">Address</label>
        <input type="text" class="form-control" name="address" 
               value="<%= cv.getAddress() %>">
    </div>

    <!-- Position -->
    <div class="mb-3">
        <label class="form-label">Position</label>
        <input type="text" class="form-control" name="position" 
               value="<%= cv.getPosition() %>">
    </div>

    <!-- Years of Experience -->
    <div class="mb-3">
        <label class="form-label">Years of Experience</label>
        <input type="number" class="form-control" name="numberExp" min="0"
               value="<%= cv.getNumberExp() %>">
    </div>

    <!-- Education -->
    <div class="mb-3">
        <label class="form-label">Education</label>
        <input type="text" class="form-control" name="education" 
               value="<%= cv.getEducation() %>">
    </div>

    <!-- Field -->
    <div class="mb-3">
        <label class="form-label">Field</label>
        <input type="text" class="form-control" name="field" 
               value="<%= cv.getField() %>">
    </div>

    <!-- Current Salary -->
    <div class="mb-3">
        <label class="form-label">Current Salary</label>
        <input type="number" step="0.01" class="form-control" name="currentSalary"
               value="<%= cv.getCurrentSalary() != null ? cv.getCurrentSalary().toPlainString() : "" %>">
    </div>

    <!-- Birthday -->
    <div class="mb-3">
        <label class="form-label">Birthday</label>
        <input type="date" class="form-control" name="birthday"
               value="<%= cv.getBirthday() != null ? cv.getBirthday().toString() : "" %>">
    </div>

    <!-- Nationality -->
    <div class="mb-3">
        <label class="form-label">Nationality</label>
        <input type="text" class="form-control" name="nationality" 
               value="<%= cv.getNationality() %>">
    </div>

    <!-- Gender -->
    <div class="mb-3">
        <label class="form-label">Gender</label>
        <select class="form-select" name="gender">
            <option value="Male" <%= "Male".equals(cv.getGender()) ? "selected" : "" %>>Male</option>
            <option value="Female" <%= "Female".equals(cv.getGender()) ? "selected" : "" %>>Female</option>
            <option value="Other" <%= "Other".equals(cv.getGender()) ? "selected" : "" %>>Other</option>
        </select>
    </div>

    <!-- CV File -->
    <div class="mb-3">
        <label class="form-label">Upload New CV File (optional)</label>
        <input type="file" class="form-control" name="cvFile">
        <% if (cv.getFileData() != null) { %>
            <p class="mt-2">Current File:
                <a href="<%= request.getContextPath() + "/" + cv.getFileData() %>" target="_blank">Download</a>
            </p>
        <% } %>
    </div>

    <button type="submit" class="btn btn-primary">Update CV</button>
    <a href="<%= request.getContextPath() %>/list-cv" class="btn btn-secondary">Cancel</a>
</form>




</div>

</body>
</html>

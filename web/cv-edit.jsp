<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.CV" %>
<%
    CV cv = (CV) request.getAttribute("cv");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
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
        <input type="hidden" name="CVID" value="<%= cv.getCVID() %>"/>

        <div class="mb-3">
            <label class="form-label">Full Name</label>
            <input type="text" class="form-control" name="fullName" value="<%= cv.getFullName() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" name="email" value="<%= cv.getEmail() %>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Address</label>
            <input type="text" class="form-control" name="address" value="<%= cv.getAddress() %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Position</label>
            <input type="text" class="form-control" name="position" value="<%= cv.getPosition() %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Years of Experience</label>
            <input type="number" class="form-control" name="numberExp" value="<%= cv.getNumberExp() %>" min="0">
        </div>

        <div class="mb-3">
            <label class="form-label">Education</label>
            <input type="text" class="form-control" name="education" value="<%= cv.getEducation() %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Field</label>
            <input type="text" class="form-control" name="field" value="<%= cv.getField() %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Current Salary</label>
            <input type="number" step="0.01" class="form-control" name="currentSalary"
                   value="<%= cv.getCurrentSalary() != null ? cv.getCurrentSalary().toPlainString() : "" %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Birthday</label>
            <input type="date" class="form-control" name="birthday"
                   value="<%= cv.getBirthday() != null ? cv.getBirthday().toString() : "" %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Nationality</label>
            <input type="text" class="form-control" name="nationality" value="<%= cv.getNationality() %>">
        </div>

        <div class="mb-3">
            <label class="form-label">Gender</label>
            <select class="form-select" name="gender">
                <option value="Male" <%= "Male".equals(cv.getGender()) ? "selected" : "" %>>Male</option>
                <option value="Female" <%= "Female".equals(cv.getGender()) ? "selected" : "" %>>Female</option>
                <option value="Other" <%= "Other".equals(cv.getGender()) ? "selected" : "" %>>Other</option>
            </select>
        </div>

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
        <a href="<%= request.getContextPath() %>/cv-list" class="btn btn-secondary">Cancel</a>
    </form>
</div>

</body>
</html>

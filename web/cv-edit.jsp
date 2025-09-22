<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Edit CV</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS here -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-12 my-5">
                <h2>Edit CV</h2>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/edit-cv" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="CVID" value="${cv.CVID}">
                    
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" 
                                       value="${cv.fullName}" required>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${cv.email}" required>
                            </div>

                            <div class="form-group">
                                <label for="address">Address</label>
                                <input type="text" class="form-control" id="address" name="address" 
                                       value="${cv.address}" required>
                            </div>

                            <div class="form-group">
                                <label for="position">Position</label>
                                <input type="text" class="form-control" id="position" name="position" 
                                       value="${cv.position}" required>
                            </div>

                            <div class="form-group">
                                <label for="numberExp">Years of Experience</label>
                                <input type="number" class="form-control" id="numberExp" name="numberExp" 
                                       value="${cv.numberExp}" min="0" required>
                            </div>

                            <div class="form-group">
                                <label for="field">Field</label>
                                <input type="text" class="form-control" id="field" name="field" 
                                       value="${cv.field}" required>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="currentSalary">Current Salary (USD)</label>
                                <input type="number" class="form-control" id="currentSalary" name="currentSalary" 
                                       value="${cv.currentSalary}" min="0" required>
                            </div>

                            <div class="form-group">
                                <label for="birthday">Birthday</label>
                                <input type="date" class="form-control" id="birthday" name="birthday" 
                                       value="${cv.birthday}" required>
                            </div>

                            <div class="form-group">
                                <label for="nationality">Nationality</label>
                                <input type="text" class="form-control" id="nationality" name="nationality" 
                                       value="${cv.nationality}" required>
                            </div>

                            <div class="form-group">
                                <label for="gender">Gender</label>
                                <select class="form-control" id="gender" name="gender" required>
                                    <option value="">Select Gender</option>
                                    <option value="male" ${cv.gender == 'male' ? 'selected' : ''}>Male</option>
                                    <option value="female" ${cv.gender == 'female' ? 'selected' : ''}>Female</option>
                                    <option value="other" ${cv.gender == 'other' ? 'selected' : ''}>Other</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="education">Education</label>
                                <textarea class="form-control" id="education" name="education" 
                                          rows="4" required>${cv.education}</textarea>
                            </div>

                            <div class="form-group">
                                <label for="cvFile">CV File (Optional)</label>
                                <input type="file" class="form-control" id="cvFile" name="cvFile" 
                                       accept=".pdf,.doc,.docx">
                                <small class="text-muted">Current file: ${cv.fileData}</small>
                            </div>
                        </div>
                    </div>

                    <div class="mt-4">
                        <button type="submit" class="boxed-btn3">Update CV</button>
                        <a href="${pageContext.request.contextPath}/cv-list" class="boxed-btn3">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
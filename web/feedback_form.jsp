<%-- 
    Document   : feedback_form
    Created on : Oct 16, 2025, 4:11:13 PM
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String role = (String) session.getAttribute("role");
    Integer employerID = null;
    Integer candidateID = null;

    if (role == null) {
        response.sendRedirect("access-denied.jsp");
        return;
    } else if (role.equalsIgnoreCase("Employer")) {
        employerID = (Integer) session.getAttribute("employerID"); 
    } else if (role.equalsIgnoreCase("Candidate")) {
        candidateID = (Integer) session.getAttribute("candidateID"); 
    }
    
%>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Gửi phản hồi</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .feedback-card {
            max-width: 600px;
            margin: 3rem auto;
            background: #fff;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        .btn-submit {
            background-color: #0d6efd;
            color: white;
            border-radius: 30px;
            border: none;
            padding: 0.6rem 1.5rem;
        }
        .btn-submit:hover {
            background-color: #095bb5;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="feedback-card">
        <h4 class="text-center text-primary mb-3">
            <i class="ti-comment-alt"></i> Gửi phản hồi cho quản trị viên
        </h4>
<c:if test="${role eq 'Employer'}">
    <form action="sendFeedback" method="post">
        <input type="hidden" name="employerID" value="<%= employerID %>">
        <div class="mb-3">
                <label class="form-label fw-bold">Chủ đề</label>
                <input type="text" name="subject" class="form-control" placeholder="Nhập tiêu đề phản hồi..." required>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Loại phản hồi</label>
                <select name="type" class="form-select" required>
                    <option value="">-- Chọn loại phản hồi --</option>
                    <option value="Dịch vụ">Dịch vụ</option>
                    <option value="Khuyến mãi">Khuyến mãi</option>
                    <option value="Hệ thống">Hệ thống</option>
                    <option value="Khác">Khác</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Nội dung</label>
                <textarea name="content" class="form-control" rows="6" placeholder="Nhập nội dung phản hồi..." required></textarea>
            </div>

            <div class="text-center">
                <button type="submit" class="btn-submit">Gửi phản hồi</button>
            </div>
        </form>
    </c:if>
      
           
            <c:if test="${role eq 'Candidate'}">
                 <form action="sendFeedbackCan" method="post">
                <input type="hidden" name="candidateID" value="<%= candidateID %>">
                <div class="mb-3">
                <label class="form-label fw-bold">Chủ đề</label>
                <input type="text" name="subject" class="form-control" placeholder="Nhập tiêu đề phản hồi..." required>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Loại phản hồi</label>
                <select name="type" class="form-select" required>
                    <option value="">-- Chọn loại phản hồi --</option>
                    <option value="Dịch vụ">Dịch vụ</option>
                    <option value="Khuyến mãi">Khuyến mãi</option>
                    <option value="Hệ thống">Hệ thống</option>
                    <option value="Khác">Khác</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Nội dung</label>
                <textarea name="content" class="form-control" rows="6" placeholder="Nhập nội dung phản hồi..." required></textarea>
            </div>

            <div class="text-center">
                <button type="submit" class="btn-submit">Gửi phản hồi</button>
            </div>
        </form>
            </c:if>

            
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>

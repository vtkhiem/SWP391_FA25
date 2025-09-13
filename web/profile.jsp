<%-- 
    Document   : profile
    Created on : Sep 14, 2025, 1:52:11 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            min-height: 100vh;
            margin: 0;
            font-family: 'Arial', sans-serif;
            color: #333;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.95);
            border-radius: 10px;
            box-shadow: 0 14px 28px rgba(0,0,0,0.25);
            width: 600px;
            max-width: 100%;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
        }
        h1 {
            color: #2a5298;
        }
        .profile-info {
            text-align: left;
            margin: 20px 0;
        }
        .profile-info p {
            margin: 10px 0;
            font-size: 16px;
        }
        .profile-info img {
            max-width: 150px;
            border-radius: 50%;
            margin: 10px 0;
        }
        button {
            border-radius: 20px;
            border: 1px solid #2a5298;
            background-color: #2a5298;
            color: #FFFFFF;
            font-size: 12px;
            font-weight: bold;
            padding: 12px 45px;
            cursor: pointer;
        }
        button:hover {
            background-color: #1e3c72;
            transform: scale(0.95);
        }
        .error {
            color: #ff4444;
            font-size: 14px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Thông tin cá nhân</h1>
    <c:choose>
        <c:when test="${sessionScope.role == 'Candidate'}">
            <div class="profile-info">
                <p><strong>Tên:</strong> ${sessionScope.candidate.candidateName}</p>
                <p><strong>Email:</strong> ${sessionScope.candidate.email}</p>
                <p><strong>Địa chỉ:</strong> ${sessionScope.candidate.address}</p>
                <p><strong>Số điện thoại:</strong> ${sessionScope.candidate.phoneNumber}</p>
                <p><strong>Quốc tịch:</strong> ${sessionScope.candidate.nationality}</p>
                <c:if test="${not empty sessionScope.candidate.avatar}">
                    <img src="${sessionScope.candidate.avatar}" alt="Avatar">
                </c:if>
            </div>
            <button onclick="window.location.href='editProfile'">Chỉnh sửa</button>
        </c:when>
        <c:when test="${sessionScope.role == 'Employer'}">
            <div class="profile-info">
                <p><strong>Tên công ty:</strong> ${sessionScope.employer.companyName}</p>
                <p><strong>Email:</strong> ${sessionScope.employer.email}</p>
                <p><strong>Địa chỉ:</strong> ${sessionScope.employer.address}</p>
                <p><strong>Số điện thoại:</strong> ${sessionScope.employer.phoneNumber}</p>
            </div>
            <button onclick="window.location.href='editProfile'">Chỉnh sửa</button>
        </c:when>
        <c:otherwise>
            <p class="error">Bạn cần đăng nhập để xem thông tin cá nhân.</p>
            <button onclick="window.location.href='login.jsp'">Đăng nhập</button>
        </c:otherwise>
    </c:choose>
    <button onclick="window.location.href='index.jsp'">Quay lại trang chủ</button>
</div>
</body>
</html>

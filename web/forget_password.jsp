<%-- 
    Document   : forget_password
    Created on : Sep 15, 2025, 9:59:31 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quên Mật Khẩu</title>
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
            width: 400px;
            max-width: 100%;
            margin: 50px auto;
            padding: 20px;
            text-align: center;
        }
        h1 {
            color: #2a5298;
            font-size: 24px;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        input {
            background-color: #f0f0f0;
            border: none;
            padding: 12px 15px;
            margin: 8px 0;
            width: 100%;
            border-radius: 5px;
            font-size: 14px;
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
            margin: 10px 0;
        }
        button:hover {
            background-color: #1e3c72;
            transform: scale(0.95);
        }
        a {
            color: #2a5298;
            font-size: 14px;
            text-decoration: none;
            margin: 10px 0;
        }
        a:hover {
            text-decoration: underline;
        }
        .notification {
            font-size: 14px;
            margin: 10px 0;
            text-align: center;
            display: none;
        }
        .notification.success {
            color: #28a745;
        }
        .notification.error {
            color: #ff4444;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Quên Mật Khẩu</h1>
    <form id="forgetPasswordForm" action="forgotPassword"  method="post">
        <input type="email" name="email" id="email" placeholder="Nhập email của bạn" required>
        <p id="notification" class="notification"></p>
        <button type="submit">Gửi yêu cầu</button>
        <a href="login.jsp">Quay lại đăng nhập</a>
    </form>
</div>

<script>
    const forgetPasswordForm = document.getElementById('forgetPasswordForm');
    const notification = document.getElementById('notification');

    forgetPasswordForm.addEventListener('submit', function(e) {
        e.preventDefault();
        notification.style.display = 'none';

        const formData = new FormData(forgetPasswordForm);
        fetch('forgetPassword', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            notification.style.display = 'block';
            notification.className = 'notification ' + (data.success ? 'success' : 'error');
            notification.textContent = data.message || data.error;
        })
        .catch(error => {
            notification.style.display = 'block';
            notification.className = 'notification error';
            notification.textContent = 'Lỗi hệ thống: ' + error.message;
        });
    });
</script>
</body>
</html>

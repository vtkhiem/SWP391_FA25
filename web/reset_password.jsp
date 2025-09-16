<%-- 
    Document   : reset_password
    Created on : Sep 16, 2025, 7:34:22 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt lại mật khẩu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .reset-container {
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            width: 350px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input[type="password"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background: #007bff;
            color: white;
            font-weight: bold;
            cursor: pointer;
            border: none;
        }
        input[type="submit"]:hover {
            background: #0056b3;
        }
        .error {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
        .success {
            color: green;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="reset-container">
    <h2>Đặt lại mật khẩu</h2>

    <form action="resetPassword" method="post">
        <!-- token & role được ẩn để gửi lại cho servlet -->
        <input type="hidden" name="token" value="${param.token}">
        <input type="hidden" name="role" value="${param.role}">

        <label for="password">Mật khẩu mới:</label>
        <input type="password" name="password" id="password" required>

        <label for="confirmPassword">Xác nhận mật khẩu:</label>
        <input type="password" name="confirmPassword" id="confirmPassword" required>

        <input type="submit" value="Đổi mật khẩu">

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>
    </form>
</div>
</body>
</html>

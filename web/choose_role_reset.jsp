<%-- 
    Document   : choose_role_reset
    Created on : Sep 17, 2025, 12:13:09 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chọn vai trò</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            width: 400px;
            text-align: center;
        }
        h2 {
            color: #2a5298;
            margin-bottom: 20px;
        }
        .role-btn {
            display: block;
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            font-size: 16px;
            color: #fff;
            background: #2a5298;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .role-btn:hover {
            background: #1e3c72;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Email tồn tại ở nhiều vai trò</h2>
    <p>Vui lòng chọn vai trò để đặt lại mật khẩu:</p>

    <form action="forgotPassword" method="post">
        <input type="hidden" name="email" value="${email}">
        <button type="submit" name="role" value="Candidate" class="role-btn">Candidate</button>
        <button type="submit" name="role" value="Employer" class="role-btn">Employer</button>
    </form>
</div>
</body>
</html>

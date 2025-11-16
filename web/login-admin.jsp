<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
        <title>Đăng nhập</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <style>
            body {
                background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
                min-height: 100vh;
                margin: 0;
                padding: 0;
                font-family: 'Arial', sans-serif;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .container {
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 14px 28px rgba(0,0,0,0.25),
                    0 10px 10px rgba(0,0,0,0.22);
                width: 400px;
                max-width: 100%;
                padding: 40px;
                text-align: center;
            }
            form {
                display: flex;
                flex-direction: column;
            }
            input {
                background-color: #f0f0f0;
                border: none;
                padding: 12px 15px;
                margin: 8px 0;
                width: 92%;
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
                letter-spacing: 1px;
                text-transform: uppercase;
                cursor: pointer;
                margin-top: 15px;
                transition: background-color 0.3s ease;
            }
            button:hover {
                background-color: #1e3c72;
            }
            a {
                color: #2a5298;
                font-size: 14px;
                text-decoration: none;
                margin-top: 15px;
            }
            a:hover {
                text-decoration: underline;
            }
            #notificationTab {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #2a5298;
                color: white;
                padding: 15px 20px;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                z-index: 1000;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty param.success}">
            <div id="notificationTab">${param.success}</div>
        </c:if>
        <c:if test="${not empty status}">
            <div id="notificationTab">${status}</div>
        </c:if>


        <div class="container">
            <h2>Đăng nhập</h2>
            <form action="login-admin" method="post" autocomplete="off">
                <input type="text" name="username" placeholder="Username" required />
                <input type="password" name="password" placeholder="Mật khẩu" required />
                <button type="submit">Đăng nhập</button>
            </form>
        </div>

        <script>
            window.addEventListener('DOMContentLoaded', function () {
                var noti = document.getElementById('notificationTab');
                if (noti) {
                    setTimeout(function () {
                        noti.style.display = "none";
                    }, 4000);
                }
            });
        </script>
    </body>
</html>
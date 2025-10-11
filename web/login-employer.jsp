<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Đăng nhập & Đăng ký cho Nhà Tuyển Dụng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <style>
            body {
                background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
                min-height: 100vh;
                margin: 0;
                padding: 0;
                font-family: 'Arial', sans-serif;
            }

            .container {
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 10px;
                box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
                position: relative;
                overflow: hidden;
                width: 768px;
                max-width: 100%;
                min-height: 480px;
                margin: 50px auto;
            }

            .form-container {
                position: absolute;
                top: 0;
                height: 100%;
                transition: all 0.6s ease-in-out;
            }

            .sign-in-container {
                left: 0;
                width: 50%;
                z-index: 2;
            }

            .container.right-panel-active .sign-in-container {
                transform: translateX(100%);
            }

            .sign-up-container {
                left: 0;
                width: 50%;
                opacity: 0;
                z-index: 1;
            }

            .container.right-panel-active .sign-up-container {
                transform: translateX(100%);
                opacity: 1;
                z-index: 5;
                animation: show 0.6s;
            }

            @keyframes show {
                0%, 49.99% {
                    opacity: 0;
                    z-index: 1;
                }
                50%, 100% {
                    opacity: 1;
                    z-index: 5;
                }
            }

            .overlay-container {
                position: absolute;
                top: 0;
                left: 50%;
                width: 50%;
                height: 100%;
                overflow: hidden;
                transition: transform 0.6s ease-in-out;
                z-index: 100;
            }

            .container.right-panel-active .overlay-container {
                transform: translateX(-100%);
            }

            .overlay {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                background-repeat: no-repeat;
                background-size: cover;
                background-position: 0 0;
                color: #FFFFFF;
                position: relative;
                left: -100%;
                height: 100%;
                width: 200%;
                transform: translateX(0);
                transition: transform 0.6s ease-in-out;
            }

            .container.right-panel-active .overlay {
                transform: translateX(50%);
            }

            .overlay-panel {
                position: absolute;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 40px;
                text-align: center;
                top: 0;
                height: 100%;
                width: 50%;
                transform: translateX(0);
                transition: transform 0.6s ease-in-out;
            }

            .overlay-left {
                margin-left: -50px;
                transform: translateX(-20%);
            }

            .container.right-panel-active .overlay-left {
                transform: translateX(0);
            }

            .overlay-right {
                margin-right: -30px;
                right: 0;
                transform: translateX(0);
            }

            .container.right-panel-active .overlay-right {
                transform: translateX(20%);
            }

            form {
                background-color: #FFFFFF;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                padding: 0 50px;
                height: 100%;
                text-align: center;
            }

            input, select {
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
                letter-spacing: 1px;
                text-transform: uppercase;
                transition: transform 80ms ease-in;
                cursor: pointer;
                margin: 10px 0;
            }

            button:hover {
                background-color: #1e3c72;
                border-color: #1e3c72;
                transform: scale(0.95);
            }

            button.ghost {
                background-color: transparent;
                border-color: #FFFFFF;
            }

            button.ghost:hover {
                background-color: #FFFFFF;
                color: #2a5298;
            }

            a {
                color: #2a5298;
                font-size: 14px;
                text-decoration: none;
                margin: 15px 0;
            }

            a:hover {
                text-decoration: underline;
            }

            #passwordError {
                color: #ff4444;
                font-size: 12px;
                margin: 5px 0;
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

            #phoneContainer {
                width: 100%;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty param.success}">
            <div id="notificationTab">${param.success}</div>  
        </c:if>
        <div class="container" id="container">
            <div class="form-container sign-up-container">
                <form id="signupForm" action="register" method="post" autocomplete="off">
                    <input type="text" name="name" placeholder="Tên" value="${name}" required />
                    <input id="phone"type="text" name="phone" placeholder="Số điện thoại" value="${phone}" required />
                       <p id="phoneResult" style="font-size: 12px; color: red; margin: 0;"></p>
                    <input id="email" type="email" name="email" placeholder="Email" value="${email}" required />
                       <p id="emailResult" style="font-size: 12px; color: red; margin: 0;"></p>
                       <input id="taxcode" type="text" name="taxcode" placeholder="Mã số thuế" value="${taxcode}" required />
                          <p id="taxcodeResult" style="font-size: 12px; color: red; margin: 0;"></p>
                           <p id="taxcodeResult1" style="font-size: 12px; color: red; margin: 0;"></p>
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required />
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Xác nhận mật khẩu" required />
                      <input type="hidden" name="role" value="employer"/>
                    <div id="passwordError" style="display:none;">Mật khẩu không trùng khớp!</div>
                    <button type="submit">Đăng ký</button>
                </form>
            </div>
            <div class="form-container sign-in-container">
                <form action="login" method="post" autocomplete="off">
                    <input type="text" name="email" placeholder="Email" value="${username}" required />
                    <input type="password" name="password" placeholder="Mật khẩu" required />
                    <button type="submit">Đăng nhập</button>
                    <a href="<%= request.getContextPath() %>/forget_password.jsp">Quên mật khẩu?</a>
                </form>
            </div>
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1>Chào mừng trở lại!</h1>

                        <button class="ghost" id="signIn">Đăng nhập</button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1>Xin chào!</h1>

                        <button class="ghost" id="signUp">Đăng ký</button>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${status!=null}">
            <div id="notificationTab">${status}</div>
        </c:if>
        <script>
            window.addEventListener('DOMContentLoaded', function () {
                var noti = document.getElementById('notificationTab');
                if (noti) {
                    setTimeout(function () {
                        noti.style.display = "none";
                    }, 4000);
                }
            });

            const signUpButton = document.getElementById('signUp');
            const signInButton = document.getElementById('signIn');
            const container = document.getElementById('container');
            signUpButton.addEventListener('click', () => {
                container.classList.add('right-panel-active');
            });
            signInButton.addEventListener('click', () => {
                container.classList.remove('right-panel-active');
            });

            const roleSelect = document.getElementById('role');
            const phoneContainer = document.getElementById('phoneContainer');
            const phoneInput = document.getElementById('phoneInput');
            roleSelect.addEventListener('change', function () {
                if (this.value === 'employer') {
                    phoneContainer.style.display = 'block';
                    phoneInput.required = true;
                } else {
                    phoneContainer.style.display = 'none';
                    phoneInput.required = false;
                    phoneInput.value = '';
                }
            });

            const signupForm = document.getElementById('signupForm');
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            const passwordError = document.getElementById('passwordError');
            signupForm.addEventListener('submit', function (e) {
                if (password.value !== confirmPassword.value) {
                    passwordError.style.display = 'block';
                    e.preventDefault();
                    confirmPassword.focus();
                } else {
                    passwordError.style.display = 'none';
                }
            });
        </script>
          <script>
    // --- KIỂM TRA EMAIL ---
    document.getElementById("email").addEventListener("keyup", function () {
        let email = this.value.trim();
        if (email.length === 0) {
            document.getElementById("emailResult").innerText = "";
            return;
        }

        let xhr = new XMLHttpRequest();
        xhr.open("GET", "checkInputEmployer?type=email&value=" + encodeURIComponent(email), true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                document.getElementById("emailResult").innerText = xhr.responseText;
            }
        };
        xhr.send();
    });

    // --- KIỂM TRA SỐ ĐIỆN THOẠI ---
    document.getElementById("phone").addEventListener("keyup", function () {
        let phone = this.value.trim();
        if (phone.length === 0) {
            document.getElementById("phoneResult").innerText = "";
            return;
        }

        let xhr = new XMLHttpRequest();
        xhr.open("GET", "checkInputEmployer?type=phone&value=" + encodeURIComponent(phone), true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                document.getElementById("phoneResult").innerText = xhr.responseText;
            }
        };
        xhr.send();
    });
     document.getElementById("taxcode").addEventListener("keyup", function () {
        let taxcode = this.value.trim();
        if (taxcode.length === 0) {
            document.getElementById("taxcodeResult").innerText = "";
            return;
        }

        let xhr = new XMLHttpRequest();
        xhr.open("GET", "checkInputEmployer?type=taxcode&value=" + encodeURIComponent(taxcode), true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                document.getElementById("taxcodeResult").innerText = xhr.responseText;
            }
        };
        xhr.send();
    });
        document.getElementById("taxcode").addEventListener("keyup", function () {
        let taxcode = this.value.trim();
        if (taxcode.length === 0) {
            document.getElementById("taxcodeResult1").innerText = "";
            return;
        }

        let xhr = new XMLHttpRequest();
        xhr.open("GET", "checkInputEmployer?type=taxcode1&value=" + encodeURIComponent(taxcode), true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                document.getElementById("taxcodeResult1").innerText = xhr.responseText;
            }
        };
        xhr.send();
    });
</script>
            
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Đăng nhập & Đăng ký</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!-- CSS here -->
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <div class="container" id="container">
            <div class="form-container sign-up-container">
                <form id="signupForm" action="register" method="post" autocomplete="off">
                    <input type="text" name="name" placeholder="Tên" value="${name}" required />
                    <input type="text" name="phone" placeholder="Số điện thoại" value="${phone}" required />
                    <input type="email" name="email" placeholder="Email" value="${email}" required />
                    <input type="password" id="password" name="password1" placeholder="Mật khẩu" required />
                    <input type="password" id="confirmPassword" name="password2" placeholder="Xác nhận mật khẩu" required />
                    <select id="role" name="role" required>
                        <option value="" disabled selected>Chọn vai trò</option>
                        <option value="candidate">Ứng viên</option>
                        <option value="employer">Nhà tuyển dụng</option>
                    </select>
                    <div id="passwordError" style="display:none;">Mật khẩu không trùng khớp!</div>
                    <button type="submit">Đăng ký</button>
                </form>
            </div>
            <div class="form-container sign-in-container">
                <form action="login" method="post" autocomplete="off">
                    <input type="text" name="email" placeholder="Email" value="${username}" required />
                    <input type="password" name="password" placeholder="Mật khẩu" required />
                    <button type="submit">Đăng nhập</button>
                    <a href="<%= request.getContextPath() %>/forgetPassword.jsp">Quên mật khẩu?</a>
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
    </body>
</html>
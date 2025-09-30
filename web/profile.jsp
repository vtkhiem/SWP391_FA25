
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
                margin: 5px;
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
            /* Modal Styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.5);
            }
            .modal-content {
                background-color: #fefefe;
                margin: 10% auto;
                padding: 20px;
                border-radius: 10px;
                width: 90%;
                max-width: 400px;
                box-shadow: 0 14px 28px rgba(0,0,0,0.25);
            }
            .modal-content h2 {
                color: #2a5298;
                text-align: center;
                margin-bottom: 20px;
            }
            .modal-content input {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
            }
            .modal-content .error {
                display: none;
                text-align: center;
            }
            .modal-footer {
                text-align: center;
                margin-top: 20px;
            }
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }
            .close:hover {
                color: #000;
            }
            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #28a745;
                color: white;
                padding: 15px 20px;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                z-index: 1000;
                display: none;
            }
            .notification.error {
                background-color: #ff4444;
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
                    <button onclick="window.location.href = 'editProfile'">Chỉnh sửa</button>
                    <button onclick="openChangePasswordModal()">Bảo mật</button>
                </c:when>
                <c:when test="${sessionScope.role == 'Employer'}">
                    <div class="profile-info">
                        <p><strong>Tên công ty:</strong> ${sessionScope.employer.companyName}</p>
                        <p><strong>Email:</strong> ${sessionScope.employer.email}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.employer.phoneNumber}</p>
                    </div>
                    <button onclick="window.location.href = 'editProfile'">Chỉnh sửa</button>
                    <button onclick="openChangePasswordModal()">Bảo mật</button>
                </c:when>
                <c:otherwise>
                    <p class="error">Bạn cần đăng nhập để xem thông tin cá nhân.</p>
                    <button onclick="window.location.href = 'login.jsp'">Đăng nhập</button>
                </c:otherwise>
            </c:choose>
            <button onclick="window.location.href = 'index.jsp'">Quay lại trang chủ</button>
        </div>

        <!-- Modal Đổi Mật Khẩu -->
        <div id="changePasswordModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeChangePasswordModal()">&times;</span>
                <h2>Đổi Mật Khẩu</h2>
                <form id="changePasswordForm" action="changePassword" method="post">
                    <input type="hidden" name="role" value="${sessionScope.role}">
                    <input type="hidden" name="email" value="${sessionScope.email}">
                    <input type="password" name="oldPassword" id="oldPassword" placeholder="Mật khẩu cũ" required>
                    <input type="password" name="newPassword" id="newPassword" placeholder="Mật khẩu mới" required>
                    <input type="password" name="confirmNewPassword" id="confirmNewPassword" placeholder="Xác nhận mật khẩu mới" required>
                    <p id="passwordError" class="error">Mật khẩu mới không khớp!</p>
                    <div class="modal-footer">
                        <button type="submit">Cập nhật</button>
                        <button type="button" onclick="closeChangePasswordModal()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="notification" class="notification"></div>

        <script>
            function openChangePasswordModal() {
                document.getElementById('changePasswordModal').style.display = 'block';
                document.getElementById('passwordError').style.display = 'none';
                document.getElementById('changePasswordForm').reset();
                document.body.style.overflow = 'hidden';
            }

            function closeChangePasswordModal() {
                document.getElementById('changePasswordModal').style.display = 'none';
                document.body.style.overflow = 'auto';
            }

            window.onclick = function (event) {
                if (event.target == document.getElementById('changePasswordModal')) {
                    closeChangePasswordModal();
                }
            }

            const changePasswordForm = document.getElementById('changePasswordForm');
            const newPassword = document.getElementById('newPassword');
            const confirmNewPassword = document.getElementById('confirmNewPassword');
            const passwordError = document.getElementById('passwordError');

            changePasswordForm.addEventListener('submit', function (e) {
                if (newPassword.value !== confirmNewPassword.value) {
                    e.preventDefault();
                    passwordError.style.display = 'block';
                    confirmNewPassword.focus();
                } else {
                    passwordError.style.display = 'none';
                }
            });

            function showNotification(message, isError = false) {
                const noti = document.getElementById('notification');
                noti.textContent = message;
                noti.className = 'notification ' + (isError ? 'error' : '');
                noti.style.display = 'block';
                setTimeout(() => {
                    noti.style.display = 'none';
                }, 4000);
            }



            <c:if test="${not empty error}">
            showNotification('Lỗi: ${error}', true);
            </c:if>
            <c:if test="${not empty success}">
            showNotification('${success}');
            </c:if>
        </script>
    </body>
</html>

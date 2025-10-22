
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Hồ sơ người dùng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            body {
                background: linear-gradient(135deg, #1d2671, #c33764);
                min-height: 100vh;
                margin: 0;
                font-family: 'Segoe UI', sans-serif;
                color: #333;
            }

            .container {
                background-color: rgba(255, 255, 255, 0.95);
                border-radius: 16px;
                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
                max-width: 900px;
                margin: 60px auto;
                padding: 40px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            h1 {
                color: #c33764;
                font-size: 28px;
                margin-bottom: 10px;
            }

            .role-tag {
                background-color: #1d2671;
                color: white;
                padding: 6px 14px;
                border-radius: 8px;
                font-size: 13px;
                margin-bottom: 20px;
            }

            .profile-section {
                width: 100%;
                margin-bottom: 30px;
            }

            .profile-card {
                display: flex;
                flex-wrap: wrap;
                align-items: flex-start;
                gap: 20px;
            }

            .profile-avatar {
                flex: 1;
                text-align: center;
            }

            .profile-avatar img {
                max-width: 160px;
                border-radius: 50%;
                border: 4px solid #c33764;
            }

            .profile-info {
                flex: 2;
                font-size: 16px;
            }

            .profile-info p {
                margin: 10px 0;
                line-height: 1.5;
            }

            .info-label {
                font-weight: 600;
                color: #1d2671;
            }

            .button-group {
                margin-top: 20px;
                text-align: center;
            }

            button {
                background-color: #1d2671;
                color: #fff;
                border: none;
                border-radius: 25px;
                padding: 12px 25px;
                margin: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: 0.3s;
            }

            button:hover {
                background-color: #c33764;
                transform: scale(1.05);
            }

            /* Dịch vụ */
            .service-card {
                border-top: 2px solid #eee;
                padding-top: 20px;
                width: 100%;
            }

            .service-card h2 {
                color: #1d2671;
                margin-bottom: 15px;
                text-align: center;
            }

            .service-details {
                background-color: #f8f8f8;
                border-radius: 10px;
                padding: 15px 25px;
                font-size: 15px;
            }

            .service-details p {
                margin: 8px 0;
            }

            /* Modal */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                inset: 0;
                background-color: rgba(0,0,0,0.5);
            }

            .modal-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 30px;
                border-radius: 10px;
                width: 90%;
                max-width: 400px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            }

            .modal-content input {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            .error {
                color: #ff4444;
                text-align: center;
                display: none;
            }

            .notification {
                position: fixed;
                top: 25px;
                right: 25px;
                padding: 15px 20px;
                background-color: #28a745;
                color: white;
                border-radius: 5px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.2);
                display: none;
            }

            .notification.error {
                background-color: #e53935;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Hồ sơ người dùng</h1>
            <c:choose>

                <c:when test="${sessionScope.role == 'Candidate'}">
                    <span class="role-tag">Ứng viên</span>
                    <div class="profile-section">
                        <div class="profile-card">
                            <div class="profile-avatar">
                                <c:if test="${not empty sessionScope.candidate.avatar}">
                                    <img src="${sessionScope.candidate.avatar}" alt="Avatar">
                                </c:if>
                            </div>
                            <div class="profile-info">
                                <p><span class="info-label">Họ tên:</span> ${sessionScope.candidate.candidateName}</p>
                                <p><span class="info-label">Email:</span> ${sessionScope.candidate.email}</p>
                                <p><span class="info-label">Số điện thoại:</span> ${sessionScope.candidate.phoneNumber}</p>
                                <p><span class="info-label">Địa chỉ:</span> ${sessionScope.candidate.address}</p>
                                <p><span class="info-label">Quốc tịch:</span> ${sessionScope.candidate.nationality}</p>
                            </div>
                        </div>
                    </div>
                </c:when>


                <c:when test="${sessionScope.role == 'Employer'}">
                    <span class="role-tag">Nhà tuyển dụng</span>
                    <div class="profile-section">
                        <div class="profile-card">
                            <div class="profile-avatar">
                                <c:if test="${not empty sessionScope.user.imgLogo}">
                                    <img src="${sessionScope.user.imgLogo}" alt="Logo công ty">
                                </c:if>
                            </div>
                            <div class="profile-info">
                                <p><span class="info-label">Tên công ty:</span> ${sessionScope.employer.companyName}</p>
                                <p><span class="info-label">Email:</span> ${sessionScope.employer.email}</p>
                                <p><span class="info-label">Số điện thoại:</span> ${sessionScope.employer.phoneNumber}</p>
                                <p><span class="info-label">Địa điểm:</span> ${sessionScope.employer.location}</p>
                                <p><span class="info-label">Mã số thuế:</span> ${sessionScope.employer.taxCode}</p>
                                <p><span class="info-label">Website:</span> 
                                    <a href="${sessionScope.employer.urlWebsite}" target="_blank">${sessionScope.employer.urlWebsite}</a>
                                </p>
                                <p><span class="info-label">Mô tả:</span> ${sessionScope.employer.description}</p>
                            </div>
                        </div>
                    </div>


                    <c:if test="${not empty sessionScope.service}">
                        <div class="service-card">
                            <h2>Gói Dịch Vụ Hiện Tại</h2>
                            <div class="service-details">
                                <p><span class="info-label">Tên gói:</span> ${sessionScope.service.serviceName}</p>
                                <p><span class="info-label">Giá:</span> ${sessionScope.service.price} VNĐ</p>
                                <p><span class="info-label">Thời hạn:</span> ${sessionScope.service.duration} ngày</p>
                                <p><span class="info-label">Ngày đăng ký:</span> </p>
                                <p><span class="info-label">Ngày hết hạn:</span> </p>
                                <p><span class="info-label">Trạng thái thanh toán:</span> </p>
                            </div>
                        </div>
                    </c:if>
                </c:when>


                <c:otherwise>
                    <p class="error">Bạn cần đăng nhập để xem hồ sơ.</p>
                    <button onclick="window.location.href = 'login.jsp'">Đăng nhập</button>
                </c:otherwise>
            </c:choose>

            <div class="button-group">
                <c:if test="${not empty sessionScope.role}">
                    <button onclick="openEditCandidateProfileModal()">Chỉnh sửa</button>
                    <button onclick="openChangePasswordModal()">Đổi mật khẩu</button>
                </c:if>
                <button onclick="window.location.href = 'index.jsp'">Trang chủ</button>
            </div>
        </div>

        <!-- Modal đổi mật khẩu -->
        <div id="changePasswordModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeChangePasswordModal()">&times;</span>
                <h2>Đổi mật khẩu</h2>
                <form id="changePasswordForm" action="changePassword" method="post">
                    <input type="hidden" name="role" value="${sessionScope.role}">
                    <input type="hidden" name="email" value="${sessionScope.email}">
                    <input type="password" name="oldPassword" placeholder="Mật khẩu cũ" required>
                    <input type="password" name="newPassword" id="newPassword" placeholder="Mật khẩu mới" required>
                    <input type="password" name="confirmNewPassword" id="confirmNewPassword" placeholder="Xác nhận mật khẩu" required>
                    <p id="passwordError" class="error">Mật khẩu mới không khớp!</p>
                    <div style="text-align:center;margin-top:15px;">
                        <button type="submit">Cập nhật</button>
                        <button type="button" onclick="closeChangePasswordModal()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal thêm thông tin -->
        <div id="editCandidateProfileModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeEditCandidateProfileModal()">&times;</span>
                <h2>Thông tin người dùng</h2>
                <form id="editCandidateProfileform" action="editCandidateProfile" method="post">
                    <input type="text" name="candidateName" value="${candidate.candidateName}" placeholder="Tên người dùng">
                    <input type="text" name="email" value="${candidate.email}" readonly="" placeholder="Email">
                    <input id="phone" type="text" name="phoneNumber" 
                           value="${candidate.phoneNumber}" placeholder="Số điện thoại">
                    <small id="phoneResult" style="color:red;font-size:13px;"></small>

                    <input type="text" name="address" value="${candidate.address}" placeholder="Địa chỉ">
                    <input type="text" name="nationality" value="${candidate.nationality}" placeholder="Quốc tịch">
                    <div style="text-align:center;margin-top:15px;">
                        <button type="submit">Cập nhật</button>
                        <button type="button" onclick="closeEditCandidateProfileModal()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="notification" class="notification"></div>

        <script>
            function openEditCandidateProfileModal() {
                document.getElementById("editCandidateProfileModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }

            function closeEditCandidateProfileModal() {
                document.getElementById("editCandidateProfileModal").style.display = "none";
                document.body.style.overflow = "auto";
            }

            function openChangePasswordModal() {
                document.getElementById("changePasswordModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }
            function closeChangePasswordModal() {
                document.getElementById("changePasswordModal").style.display = "none";
                document.body.style.overflow = "auto";
            }

            window.onclick = function (e) {
                if (e.target === document.getElementById("changePasswordModal"))
                    closeChangePasswordModal();
            };

            const form = document.getElementById("changePasswordForm");
            form.addEventListener("submit", e => {
                const newPass = document.getElementById("newPassword").value;
                const confirm = document.getElementById("confirmNewPassword").value;
                if (newPass !== confirm) {
                    e.preventDefault();
                    document.getElementById("passwordError").style.display = "block";
                }
            });

            function showNotification(msg, isError = false) {
                const noti = document.getElementById("notification");
                noti.textContent = msg;
                noti.className = "notification" + (isError ? " error" : "");
                noti.style.display = "block";
                setTimeout(() => noti.style.display = "none", 4000);
            }

            <c:if test="${not empty success}">
            showNotification('${success}');
            </c:if>
            <c:if test="${not empty error}">
            showNotification('Lỗi: ${error}', true);
            </c:if>

            document.getElementById("phone").addEventListener("keyup", function () {
                const phone = this.value.trim();
                const resultEl = document.getElementById("phoneResult");
                if (phone.length === 0) {
                    resultEl.innerText = "";
                    return;
                }

                const xhr = new XMLHttpRequest();
                xhr.open("GET", "checkInput?type=phone&value=" + encodeURIComponent(phone), true);
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        resultEl.innerText = xhr.responseText;
                    }
                };
                xhr.send();
            });

        </script>
    </body>
</html>
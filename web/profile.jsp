
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Hồ sơ người dùng - Job Board</title>
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
                width: 150px;              /* kích thước avatar */
                height: 150px;
                object-fit: cover;         /* cắt ảnh cho vừa khung, không méo */
                border: 2px solid #ddd;    /* viền nhẹ */
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                margin-top: 10px;
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

        <!-- Toast Notification -->
        <c:if test="${not empty sessionScope.error}">
            <div class="toast-message error">${sessionScope.error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.message}">
            <div class="toast-message success">${sessionScope.message}</div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <div class="container">
            <h1>Hồ sơ người dùng</h1>
            <c:choose>

                <c:when test="${sessionScope.role == 'Candidate'}">
                    <span class="role-tag">Ứng viên</span>
                    <div class="profile-section">
                        <div class="profile-card">
                            <div class="profile-avatar">
                                <c:if test="${not empty candidate.avatar}">
                                    <img src="${candidate.avatar}" alt="Avatar">
                                </c:if>
                            </div>
                            <div class="profile-info">
                                <p><span class="info-label">Họ tên:</span> ${candidate.candidateName}</p>
                                <p><span class="info-label">Email:</span> ${candidate.email}</p>
                                <p><span class="info-label">Số điện thoại:</span> ${candidate.phoneNumber}</p>
                                <p><span class="info-label">Địa chỉ:</span> ${candidate.address}</p>
                                <p><span class="info-label">Quốc tịch:</span> ${candidate.nationality}</p>
                            </div>
                        </div>
                    </div>

                    <div class="button-group">
                        <button onclick="openUploadCandidateImageModal()">Avatar</button>
                        <button onclick="openEditCandidateProfileModal()">Chỉnh sửa</button>
                        <button onclick="openChangePasswordModal()">Đổi mật khẩu</button>
                        <button onclick="window.location.href = 'home'">Trang chủ</button>
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
                                <p><span class="info-label">Tên công ty:</span> ${employer.companyName}</p>
                                <p><span class="info-label">Email:</span> ${employer.email}</p>
                                <p><span class="info-label">Số điện thoại:</span> ${employer.phoneNumber}</p>
                                <p><span class="info-label">Mã số thuế:</span> ${employer.taxCode}</p>
                                <p><span class="info-label">Địa điểm:</span> ${employer.location}</p>
                                <p><span class="info-label">Website:</span> 
                                    <a href="${employer.urlWebsite}" target="_blank">${employer.urlWebsite}</a>
                                </p>
                                <p><span class="info-label">Mô tả:</span> ${employer.description}</p>
                            </div>
                        </div>

                        <div class="button-group">
                            <c:if test="${not empty sessionScope.role}">
                                <button onclick="openUploadEmployerLogoModal()">Avatar</button>
                                <button onclick="openEditEmployerProfileModal()">Chỉnh sửa</button>
                                <button onclick="openChangePasswordModal()">Đổi mật khẩu</button>
                            </c:if>
                            <button onclick="window.location.href = 'viewPromotionPosts'">Trang chủ</button>
                        </div>
                    </div>


                    <c:if test="${not empty service}">
                        <div class="service-card">
                            <h2>Gói Dịch Vụ Hiện Tại</h2>
                            <div class="service-details">
                                <p><span class="info-label">Tên gói: </span> ${service.serviceName}</p>
                                <p><span class="info-label">Giá: </span> ${service.price} VNĐ</p>
                                <p><span class="info-label">Thời hạn: </span> ${service.duration} ngày</p>
                                <p><span class="info-label">Ngày đăng ký: </span>${serviceEmployer.registerDate} </p>
                                <p><span class="info-label">Ngày hết hạn: </span>${serviceEmployer.expirationDate} </p>
                            </div>
                        </div>
                    </c:if>
                </c:when>


                <c:otherwise>
                    <p class="error">Bạn cần đăng nhập để xem hồ sơ.</p>
                    <button onclick="window.location.href = 'login.jsp'">Đăng nhập</button>
                </c:otherwise>
            </c:choose>


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

        <!-- Modal thêm thông tin candidate -->
        <div id="editCandidateProfileModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeEditCandidateProfileModal()">&times;</span>
                <h2>Thông tin người dùng</h2>
                <form id="editCandidateProfileform" action="editCandidateProfile" method="post">
                    <input type="text" name="candidateName" value="${candidate.candidateName}" placeholder="Tên người dùng" required>
                    <input type="text" name="email" value="${candidate.email}" readonly="" placeholder="Email" required>
                    <input id="phone" type="text" name="phoneNumber" 
                           value="${candidate.phoneNumber}" placeholder="Số điện thoại" required>
                    <small id="phoneResult" style="color:red;font-size:13px;"></small>

                    <input type="text" name="address" value="${candidate.address}" placeholder="Địa chỉ" required>
                    <input type="text" name="nationality" value="${candidate.nationality}" placeholder="Quốc tịch" required>
                    <div style="text-align:center;margin-top:15px;">
                        <button type="submit">Cập nhật</button>
                        <button type="button" onclick="closeEditCandidateProfileModal()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!--Modal upload avt candidate-->
        <div id="uploadCandidateImageModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeUploadCandidateImageModal()">&times;</span>
                <h2>Avatar</h2>
                <form id="uploadCandidateImage" action="uploadCandidateImage" method="post"  enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="file" class="form-control" name="candidateImage" accept="image/png, image/jpeg, image/jpg"
                               required>
                        <small class="form-text text-muted">Chỉ chấp nhận file: IMG,PNG,JPEG,JPG</small>
                        <div style="text-align:center;margin-top:15px;">
                            <button type="submit">Cập nhật</button>
                            <button type="button" onclick="closeUploadCandidateImageModal()">Hủy</button>
                        </div>

                    </div>
                </form>
            </div>
        </div>

        <!-- Modal thêm thông tin employer -->
        <div id="editEmployerProfileModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeEditEmployerProfileModal()">&times;</span>
                <h2>Thông tin người dùng</h2>
                <form id="editEmployerProfileform" action="editEmployerProfile" method="post">
                    <input type="text" name="companyName" value="${employer.companyName}" placeholder="Tên công ty" required >
                    <input type="text" name="email" value="${employer.email}" readonly="" placeholder="Email" required>
                    <input id="phone" type="text" name="phoneNumber" 
                           value="${employer.phoneNumber}" placeholder="Số điện thoại" required>
                    <small id="phoneResult" style="color:red;font-size:13px;"></small>
                    <input type="text" name="taxCode" value="${employer.taxCode}" readonly="" placeholder="Mã số thuế" required>
                    <input type="text" name="location" value="${employer.location}" placeholder="Địa chỉ"required>
                    <input type="text" name="urlWebsite" value="${employer.urlWebsite}" placeholder="Website" required>
                    <input type="text" name="description" value="${employer.description}" placeholder="Mô tả" required>
                    <div style="text-align:center;margin-top:15px;">
                        <button type="submit">Cập nhật</button>
                        <button type="button" onclick="closeEditEmployerProfileModal()">Hủy</button>
                    </div>
                </form>
            </div>
        </div>

        <!--Modal upload logoEmployer-->
        <div id="uploadEmployerLogoModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeUploadEmployerLogoModal()">&times;</span>
                <h2>Avatar</h2>
                <form id="uploadEmployerLogo" action="uploadEmployerLogo" method="post"  enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="file" class="form-control" name="employerLogo" accept="image/png, image/jpeg, image/jpg"
                               required>
                        <small class="form-text text-muted">Chỉ chấp nhận file: IMG,PNG,JPEG,JPG</small>
                        <div style="text-align:center;margin-top:15px;">
                            <button type="submit">Cập nhật</button>
                            <button type="button" onclick="closeUploadEmployerLogoModal()">Hủy</button>
                        </div>
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

            function openEditEmployerProfileModal() {
                document.getElementById("editEmployerProfileModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }
            function closeEditEmployerProfileModal() {
                document.getElementById("editEmployerProfileModal").style.display = "none";
                document.body.style.overflow = "auto";
            }

            function openUploadEmployerLogoModal() {
                document.getElementById("uploadEmployerLogoModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }
            function closeUploadEmployerLogoModal() {
                document.getElementById("uploadEmployerLogoModal").style.display = "none";
                document.body.style.overflow = "auto";
            }
            
            function openUploadCandidateImageModal() {
                document.getElementById("uploadCandidateImageModal").style.display = "block";
                document.body.style.overflow = "hidden";
            }
            function closeUploadCandidateImageModal() {
                document.getElementById("uploadCandidateImageModal").style.display = "none";
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

            document.addEventListener("DOMContentLoaded", () => {
                const toasts = document.querySelectorAll(".toast-message");
                toasts.forEach((toast, index) => {
                    Object.assign(toast.style, {
                        position: "fixed",
                        top: `${20 + index * 60}px`,
                        right: "-350px",
                        opacity: "1",
                        transition: "all 0.6s ease",
                        zIndex: "9999",
                        padding: "12px 20px",
                        borderRadius: "6px",
                        color: "#fff",
                        fontWeight: "500",
                        boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
                        minWidth: "250px",
                        textAlign: "center",
                        backgroundColor: toast.classList.contains("success") ? "#28a745" : "#dc3545"
                    });

                    setTimeout(() => (toast.style.right = "20px"), 200 + index * 150);

                    setTimeout(() => {
                        toast.style.right = "-350px";
                        toast.style.opacity = "0";
                    }, 4000 + index * 150);
                });
            });
        </script>
    </body>
</html>
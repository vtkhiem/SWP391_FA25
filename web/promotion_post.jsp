<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Đăng tin khuyến mãi - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                margin: 0;
                font-family: Arial, sans-serif;
                background: #f3f4f6;
                color: #111827;
                min-height: 100vh;
            }

            .container {
                width: 100%;
                max-width: 1000px;
                padding: 0;
            }

            header {
                background: #00366d;
                color: #fff;
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 12px 20px;
                position: sticky;
                top: 0;
                z-index: 10;
            }

            header h1 {
                color: #fff;
                font-size: 20px;
                font-weight: 700;
                margin: 0;
            }

            .user-info {
                display: flex;
                align-items: center;
                gap: 12px;
                color: #fff;
            }

            .welcome-text {
                font-size: 14px;
                color: #fff;
            }

            .welcome-text b {
                font-weight: 700;
                color: #fff;
            }

            .logout {
                background: #fff;
                color: #00366d;
                text-decoration: none;
                padding: 8px 14px;
                border-radius: 10px;
                border: 1px solid #fff;
                transition: all 0.2s ease;
                font-weight: 600;
                font-size: 14px;
            }

            .logout:hover {
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
            }

            .card {
                background: #fff;
                border: 1px solid #e5e7eb;
                border-radius: 14px;
                margin: 16px auto;
                padding: 16px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .card h2 {
                margin: 0;
                font-size: 18px;
                color: #111827;
                font-weight: 700;
            }

            .card p {
                margin: 0;
                color: #6b7280;
                font-size: 14px;
                line-height: 1.5;
            }

            .form-group {
                margin-bottom: 14px;
            }

            .form-group label {
                display: block;
                margin-bottom: 6px;
                color: #111827;
                font-weight: 600;
                font-size: 14px;
            }

            .form-control {
                width: 100%;
                padding: 10px 14px;
                border: 1px solid #e5e7eb;
                border-radius: 10px;
                font-size: 14px;
                transition: all 0.2s ease;
                background: #fff;
                color: #111827;
                font-family: Arial, sans-serif;
            }

            .form-control:focus {
                outline: none;
                border-color: #00366d;
                box-shadow: 0 0 0 3px rgba(0, 54, 109, 0.1);
            }

            .form-control.is-invalid {
                border-color: #ef4444;
            }

            .error-message {
                display: none;
                color: #ef4444;
                font-size: 13px;
                margin-top: 5px;
            }

            .d-flex {
                display: flex;
                gap: 12px;
            }

            .col-6 {
                flex: 1;
            }

            .col-12 {
                width: 100%;
            }

            .form-actions {
                display: flex;
                gap: 10px;
                margin-top: 14px;
                flex-wrap: wrap;
            }

            .btn {
                padding: 10px 14px;
                border-radius: 10px;
                border: 1px solid #e5e7eb;
                background: #fff;
                cursor: pointer;
                font-weight: 600;
                text-decoration: none;
                color: #111827;
                display: inline-block;
                font-size: 14px;
                font-family: Arial, sans-serif;
                transition: all 0.2s ease;
            }

            .btn:hover {
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
            }

            .btn-primary {
                background: #ff7a00;
                color: #fff;
                border-color: #ff7a00;
            }

            .btn-primary:hover {
                box-shadow: 0 2px 6px rgba(255, 122, 0, 0.3);
            }

            .btn-secondary {
                background: #fff;
                color: #111827;
                border-color: #e5e7eb;
            }

            .btn-secondary:hover {
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
            }

            .card-link {
                padding: 10px 14px;
                border-radius: 10px;
                border: 1px solid #e5e7eb;
                background: #ff7a00;
                color: #fff;
                border-color: #ff7a00;
                cursor: pointer;
                font-weight: 600;
                text-decoration: none;
                display: inline-block;
                font-size: 14px;
                transition: all 0.2s ease;
            }

            .card-link:hover {
                box-shadow: 0 2px 6px rgba(255, 122, 0, 0.3);
            }

            .icon {
                font-size: 20px;
                display: inline-block;
            }

            /* Responsive design */
            @media (max-width: 768px) {
                .container {
                    padding: 0 12px;
                }

                header {
                    flex-direction: column;
                    gap: 10px;
                    text-align: center;
                    padding: 12px 16px;
                }

                header h1 {
                    font-size: 18px;
                }

                .user-info {
                    flex-direction: column;
                    gap: 8px;
                }

                .d-flex {
                    flex-direction: column;
                }

                .form-actions {
                    flex-direction: column;
                }

                .btn {
                    width: 100%;
                }
            }

            /* Animation */
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .card {
                animation: fadeInUp 0.5s ease forwards;
            }
        </style>
    </head>

    <body>
        <header>
            <h1>Marketing Staff Dashboard</h1>
            <div class="user-info">
                <span class="welcome-text">Xin chào, <b>${user.username}</b></span>
                <a class="logout" href="logout">Đăng xuất</a>
            </div>
        </header>

        <div class="container">
            <!-- Toast Notification -->
            <c:if test="${not empty message}">
                <div class="toast-message success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="toast-message error">${error}</div>
            </c:if>

            <div class="card">
                <h2>Đăng tin khuyến mãi</h2>
                <form action="post_promotion" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="createBy" value="${sessionScope.user.adminId}"/>

                    <div class="form-group col-12">
                        <label>Tiêu đề <span style="color: red;">*</span></label>
                        <input type="text" class="form-control" name="title" placeholder="Nhập tiêu đề..." required>
                        <span class="error-message"></span>
                    </div>

                    <div class="form-group col-12">
                        <label>Dịch vụ <span style="color: red;">*</span></label>
                        <select class="form-control" name="service" required>
                            <option value="" disabled selected hidden>Chọn dịch vụ...</option>
                            <c:forEach var="s" items="${services}">
                                <option value="${s.serviceID}">${s.serviceName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-12">
                        <label>Nội dung <span style="color: red;">*</span></label>
                        <textarea class="form-control" name="content" rows="5" placeholder="Nhập nội dung..." required></textarea>
                        <span class="error-message"></span>
                    </div>

                    <div class="form-group col-12">
                        <label>Ảnh bìa <span style="color: red;">*</span></label>
                        <input type="file" class="form-control" name="bannerImage" required>
                        <span class="error-message"></span>
                    </div>

                    <div class="form-group d-flex">
                        <div class="col-6">
                            <label>Ngày bắt đầu <span style="color: red;">*</span></label>
                            <input type="date" class="form-control" name="startDate" required>
                            <span class="error-message"></span>
                        </div>
                        <div class="col-6">
                            <label>Ngày kết thúc <span style="color: red;">*</span></label>
                            <input type="date" class="form-control" name="endDate" required>
                            <span class="error-message"></span>
                        </div>
                    </div>

                    <div class="form-actions col-12">
                        <button type="submit" class="btn btn-primary">Đăng tin</button>
                        <button type="button" class="btn btn-secondary" onclick="history.back()">Huỷ</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", () => {
                // --- Toast Notification ---
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

                // --- Validation ---
                const form = document.querySelector("form");

                function showError(input, message) {
                    const msgEl = input.parentElement.querySelector(".error-message");
                    if (msgEl) {
                        msgEl.textContent = message;
                        msgEl.style.display = "block";
                    }
                    input.classList.add("is-invalid");
                }

                function clearError(input) {
                    const msgEl = input.parentElement.querySelector(".error-message");
                    if (msgEl)
                        msgEl.style.display = "none";
                    input.classList.remove("is-invalid");
                }

                function validateForm() {
                    let isValid = true;

                    const title = form.querySelector('[name="title"]');
                    const service = form.querySelector('[name="service"]');
                    const content = form.querySelector('[name="content"]');
                    const bannerImage = form.querySelector('[name="bannerImage"]');
                    const startDate = form.querySelector('[name="startDate"]');
                    const endDate = form.querySelector('[name="endDate"]');

                    const inputs = [title, service, content, bannerImage, startDate, endDate];
                    inputs.forEach(clearError);
                    
                    const hasSpecialChar = (str) => /[!@#$%^&*(),.?":{}|<>]/.test(str);
                    const trim = (s) => s.trim();

                    // --- Validate Title ---
                    if (!title.value.trim()) {
                        showError(title, "Vui lòng nhập tiêu đề.");
                        isValid = false;
                    } else if (hasSpecialChar(title.value)) {
                        showError(title, "Tiêu đề không được chứa ký tự đặc biệt.");
                        isValid = false;
                    }

                    // --- Validate Category ---
                    if (!service.value) {
                        showError(service, "Vui lòng chọn dịch vụ.");
                        isValid = false;
                    }

                    // --- Validate Content ---
                    if (!content.value.trim()) {
                        showError(content, "Vui lòng nhập nội dung khuyến mãi.");
                        isValid = false;
                    }

                    // --- Validate Banner Image ---
                    if (!bannerImage.value) {
                        showError(bannerImage, "Vui lòng chọn ảnh bìa.");
                        isValid = false;
                    } else {
                        const allowedExtensions = ["jpg", "jpeg", "png"];
                        const fileExt = bannerImage.value.split(".").pop().toLowerCase();
                        if (!allowedExtensions.includes(fileExt)) {
                            showError(bannerImage, "Ảnh bìa chỉ được phép là JPG, JPEG hoặc PNG.");
                            isValid = false;
                        }
                    }

                    // --- Validate Dates ---
                    if (!startDate.value) {
                        showError(startDate, "Vui lòng chọn ngày bắt đầu.");
                        isValid = false;
                    }
                    if (!endDate.value) {
                        showError(endDate, "Vui lòng chọn ngày kết thúc.");
                        isValid = false;
                    }

                    if (startDate.value && endDate.value) {
                        const start = new Date(startDate.value);
                        const end = new Date(endDate.value);
                        const today = new Date();
                        today.setHours(0, 0, 0, 0);

                        if (start < today) {
                            showError(startDate, "Ngày bắt đầu phải từ hôm nay trở đi.");
                            isValid = false;
                        }
                        if (end < start) {
                            showError(endDate, "Ngày kết thúc phải sau ngày bắt đầu.");
                            isValid = false;
                        }
                    }

                    return isValid;
                }

                form.addEventListener("submit", (e) => {
                    if (!validateForm())
                        e.preventDefault();
                });
            });
        </script>
    </body>
</html>
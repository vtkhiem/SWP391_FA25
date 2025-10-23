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
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                color: #333;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }

            header {
                background: rgba(255, 255, 255, 0.15);
                backdrop-filter: blur(10px);
                border-radius: 15px;
                padding: 25px 30px;
                margin-bottom: 30px;
                border: 1px solid rgba(255, 255, 255, 0.2);
                box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
                display: flex;
                justify-content: space-between;
                align-items: center;
                flex-wrap: wrap;
            }

            header h1 {
                color: white;
                font-size: 28px;
                font-weight: 300;
                text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
            }

            .user-info {
                display: flex;
                align-items: center;
                gap: 20px;
                color: white;
            }

            .welcome-text {
                font-size: 16px;
                opacity: 0.9;
            }

            .welcome-text b {
                font-weight: 600;
                color: #ffd700;
                text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
            }

            .logout {
                background: rgba(255, 255, 255, 0.2);
                color: white;
                text-decoration: none;
                padding: 10px 20px;
                border-radius: 25px;
                border: 1px solid rgba(255, 255, 255, 0.3);
                transition: all 0.3s ease;
                font-weight: 500;
            }

            .logout:hover {
                background: rgba(255, 255, 255, 0.3);
                transform: translateY(-2px);
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            }

            main {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
                gap: 25px;
            }

            .card {
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(10px);
                padding: 30px;
                border-radius: 20px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
                border: 1px solid rgba(255, 255, 255, 0.2);
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
            }

            .card::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                height: 4px;
                background: linear-gradient(90deg, #4facfe 0%, #00f2fe 100%);
            }

            .card:hover {
                transform: translateY(-10px);
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
            }

            .card h2 {
                color: #2c3e50;
                font-size: 24px;
                font-weight: 600;
                margin-bottom: 15px;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .card p {
                color: #5a6c7d;
                line-height: 1.6;
                margin-bottom: 25px;
                font-size: 16px;
            }

            .card-link {
                display: inline-flex;
                align-items: center;
                gap: 10px;
                padding: 12px 25px;
                background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
                color: white;
                border-radius: 50px;
                text-decoration: none;
                font-weight: 600;
                transition: all 0.3s ease;
                border: none;
                cursor: pointer;
                font-size: 14px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .card-link:hover {
                transform: translateX(5px);
                box-shadow: 0 5px 15px rgba(79, 172, 254, 0.4);
            }

            .icon {
                font-size: 32px;
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            /* Responsive design */
            @media (max-width: 768px) {
                .container {
                    padding: 15px;
                }

                header {
                    flex-direction: column;
                    gap: 15px;
                    text-align: center;
                }

                header h1 {
                    font-size: 24px;
                }

                main {
                    grid-template-columns: 1fr;
                }

                .card {
                    padding: 20px;
                }

                .user-info {
                    flex-direction: column;
                    gap: 10px;
                }
            }

            /* Animation */
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .card {
                animation: fadeInUp 0.6s ease forwards;
            }

            .card:nth-child(2) {
                animation-delay: 0.2s;
            }

            /* Đảm bảo card mới có animation delay */
            .card:nth-child(3) {
                animation-delay: 0.4s;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <header>
                <h1>📊 Marketing Staff Dashboard</h1>
                <div class="user-info">
                    <span class="welcome-text">Xin chào, <b>${user.username}</b></span>
                    <a class="logout" href="logout">🚪 Đăng xuất</a>
                </div>
            </header>

            <!-- Toast Notification -->
            <c:if test="${not empty message}">
                <div class="toast-message success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="toast-message error">${error}</div>
            </c:if>

            <div class="card">
                <h2>📰 Đăng tin khuyến mãi</h2>
                <form action="job_add" method="post">
                    <input type="hidden" name="createBy" value=""/>

                    <div class="form-group col-12">
                        <label>Tiêu đề <span style="color: red;">*</span></label>
                        <input type="text" class="form-control" name="title" placeholder="Nhập tiêu đề..." required>
                        <span class="error-message"></span>
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
                            <input type="date" class="form-control" name="dueDate" required>
                            <span class="error-message"></span>
                        </div>
                        <div class="col-6">
                            <label>Ngày kết thúc <span style="color: red;">*</span></label>
                            <input type="date" class="form-control" name="dueDate" required>
                            <span class="error-message"></span>
                        </div>
                    </div>

                    <div class="form-actions col-12">
                        <button type="submit" class="btn btn-primary">Đăng tin\</button>
                        <button type="button" class="btn btn-secondary" onclick="history.back()">Huỷ</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
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
                const position = form.querySelector('[name="position"]');
                const desc1 = form.querySelector('[name="description-1"]');
                const desc2 = form.querySelector('[name="description-2"]');
                const desc3 = form.querySelector('[name="description-3"]');
                const offerMin = form.querySelector('[name="offerMin"]');
                const offerMax = form.querySelector('[name="offerMax"]');
                const dueDate = form.querySelector('[name="dueDate"]');

                const inputs = [title, position, desc1, desc2, desc3, offerMin, offerMax, dueDate];
                inputs.forEach(clearError);

                const hasSpecialChar = (str) => /[!@#$%^&*(),.?":{}|<>]/.test(str);
                const trim = (s) => s.trim();

                if (!trim(title.value)) {
                    showError(title, "Vui lòng nhập tiêu đề.");
                    isValid = false;
                } else if (hasSpecialChar(title.value)) {
                    showError(title, "Tiêu đề không được chứa ký tự đặc biệt.");
                    isValid = false;
                }

                if (!trim(position.value)) {
                    showError(position, "Vui lòng nhập vị trí công việc.");
                    isValid = false;
                } else if (hasSpecialChar(position.value)) {
                    showError(position, "Vị trí không được chứa ký tự đặc biệt.");
                    isValid = false;
                }

                if (!trim(desc1.value)) {
                    showError(desc1, "Vui lòng nhập mô tả cụ thể.");
                    isValid = false;
                }

                if (!trim(desc2.value)) {
                    showError(desc2, "Vui lòng nhập yêu cầu công việc.");
                    isValid = false;
                }

                if (!trim(desc3.value)) {
                    showError(desc3, "Vui lòng nhập quyền lợi.");
                    isValid = false;
                }

                if (offerMin.value === "" || offerMax.value === "") {
                    showError(offerMin, "Vui lòng nhập mức lương tối thiểu và tối đa.");
                    showError(offerMax, "Vui lòng nhập mức lương tối thiểu và tối đa.");
                    isValid = false;
                } else {
                    const min = parseFloat(offerMin.value);
                    const max = parseFloat(offerMax.value);
                    if (min < 0 || max < 0) {
                        if (min < 0) {
                            showError(offerMin, "Mức lương không được là số âm.");
                            isValid = false;
                        }
                        if (max < 0) {
                            showError(offerMax, "Mức lương không được là số âm.");
                            isValid = false;
                        }
                    } else if (max < min) {
                        showError(offerMax, "Lương tối đa phải lớn hơn hoặc bằng lương tối thiểu.");
                        isValid = false;
                    }
                }

                if (!dueDate.value) {
                    showError(dueDate, "Vui lòng chọn ngày hết hạn.");
                    isValid = false;
                } else {
                    const today = new Date();
                    const due = new Date(dueDate.value);
                    today.setHours(0, 0, 0, 0);
                    if (due < today) {
                        showError(dueDate, "Ngày hết hạn phải từ hôm nay trở đi.");
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
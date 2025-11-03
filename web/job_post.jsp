<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Đăng công việc - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS: Bootstrap first, then libraries, then main style -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/nice-select.css">
        <link rel="stylesheet" href="css/gijgo.css">
        <link rel="stylesheet" href="css/animate.min.css">
        <link rel="stylesheet" href="css/slicknav.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/custom.css"> 
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="header.jsp"/>
        <!-- header_end -->

        <!-- bradcam_area -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Tạo Công Việc Mới</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area -->

        <div class="job_details_area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="white-bg p-4">
                            <h4 class="mb-3">Nội Dung Công Việc Mới</h4>

                            <!-- Toast Notification -->
                            <c:if test="${not empty message}">
                                <div class="toast-message success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="toast-message error">${error}</div>
                            </c:if>

                            <form action="job_add" method="post" onsubmit="removeCommasBeforeSubmit()">
                                <input type="hidden" name="employerId" value="${sessionScope.user.employerId}"/>

                                <div class="form-group">
                                    <label>Tiêu đề <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <input type="text" class="form-control" name="title" placeholder="Nhập tiêu đề công việc..." required>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Mô tả công việc <span style="color: red;">*</span></label>
                                    <small class="form-text text-muted pb-3">
                                        Phần mô tả dành cho công việc cần bao gồm:<br>
                                        - Mô tả cụ thể cho công việc cần đăng tuyển.<br>
                                        - Phần yêu cầu của công việc (yêu cầu về kĩ năng, bằng cấp, kinh nghiệm,...).<br>
                                        - Những quyền lợi mà người lao động được hưởng khi ứng tuyển.
                                    </small>
                                    <div class="col-12">
                                        <textarea class="form-control mb-2" name="description-1" rows="1" placeholder="Nhập mô tả công việc..." required></textarea>
                                        <span class="error-message"></span>
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control mb-2" name="description-2" rows="1" placeholder="Nhập yêu cầu công việc..." required></textarea>
                                        <span class="error-message"></span>
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control" name="description-3" rows="1" placeholder="Nhập quyền lợi công việc..." required></textarea>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Ngành nghề <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="category" required>
                                            <option value="" disabled selected hidden>Chọn ngành nghề...</option>
                                            <option value="Công nghệ thông tin">Công nghệ thông tin</option>
                                            <option value="Kinh doanh & Quản trị">Kinh doanh & Quản trị</option>
                                            <option value="Thiết kế - Nghệ thuật - Truyền thông">Thiết kế - Nghệ thuật - Truyền thông</option>
                                            <option value="Kỹ thuật – Cơ khí – Điện – Xây dựng">Kỹ thuật – Cơ khí – Điện – Xây dựng</option>
                                            <option value="Y tế – Dược – Chăm sóc sức khỏe">Y tế – Dược – Chăm sóc sức khỏe</option>
                                            <option value="Giáo dục – Đào tạo">Giáo dục – Đào tạo</option>
                                            <option value="Du lịch – Nhà hàng – Khách sạn">Du lịch – Nhà hàng – Khách sạn</option>
                                            <option value="Nông – Lâm – Ngư nghiệp">Nông – Lâm – Ngư nghiệp</option>
                                            <option value="Luật – Hành chính – Chính trị">Luật – Hành chính – Chính trị</option>
                                            <option value="Khác / Tự do">Khác / Tự do</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Vị trí công việc <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <input type="text" class="form-control" name="position" placeholder="Nhập vị trí công việc..." required>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Địa điểm <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="location" required>
                                            <option value="" disabled selected hidden>Chọn tỉnh thành...</option>
                                            <option value="Hà Nội">TP. Hà Nội</option>
                                            <option value="Hồ Chí Minh">TP. Hồ Chí Minh</option>
                                            <option value="Đà Nẵng">TP. Đà Nẵng</option>
                                            <option value="Hải Phòng">TP. Hải Phòng</option>
                                            <option value="Huế">TP. Huế</option>
                                            <option value="Cần Thơ">TP. Cần Thơ</option>
                                            <option value="An Giang">An Giang</option>
                                            <option value="Bắc Ninh">Bắc Ninh</option>
                                            <option value="Cà Mau">Cà Mau</option>
                                            <option value="Cao Bằng">Cao Bằng</option>
                                            <option value="Đắk Lắk">Đắk Lắk</option>
                                            <option value="Điện Biên">Điện Biên</option>
                                            <option value="Đồng Nai">Đồng Nai</option>
                                            <option value="Đồng Tháp">Đồng Tháp</option>
                                            <option value="Gia Lai">Gia Lai</option>
                                            <option value="Hà Tĩnh">Hà Tĩnh</option>
                                            <option value="Hưng Yên">Hưng Yên</option>
                                            <option value="Khánh Hòa">Khánh Hòa</option>
                                            <option value="Lai Châu">Lai Châu</option>
                                            <option value="Lâm Đồng">Lâm Đồng</option>
                                            <option value="Lạng Sơn">Lạng Sơn</option>
                                            <option value="Lào Cai">Lào Cai</option>
                                            <option value="Ninh Bình">Ninh Bình</option>
                                            <option value="Nghệ An">Nghệ An</option>
                                            <option value="Phú Thọ">Phú Thọ</option>
                                            <option value="Quảng Ngãi">Quảng Ngãi</option>
                                            <option value="Quảng Ninh">Quảng Ninh</option>
                                            <option value="Quảng Trị">Quảng Trị</option>
                                            <option value="Sơn La">Sơn La</option>
                                            <option value="Tây Ninh">Tây Ninh</option>
                                            <option value="Thái Nguyên">Thái Nguyên</option>
                                            <option value="Thanh Hóa">Thanh Hóa</option>
                                            <option value="Tuyên Quang">Tuyên Quang</option>
                                            <option value="Vĩnh Long">Vĩnh Long</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Mức lương <span style="color: red;">*</span></label>
                                    <div class="d-flex">
                                        <div class="col-6">
                                            <input type="text" id="offerMin" class="form-control mr-2 money-input" name="offerMin" step="1" placeholder="Từ..." required>
                                            <span class="error-message"></span>
                                        </div>
                                        <div class="col-6">
                                            <input type="text" id="offerMax" class="form-control money-input" name="offerMax" step="1" placeholder="Đến..." required>
                                            <span class="error-message"></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Yêu cầu kinh nghiệm <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="numberExp" required>
                                            <option value="" disabled selected hidden>Chọn yêu cầu kinh nghiệm...</option>
                                            <option value="0">Không yêu cầu kinh nghiệm</option>
                                            <option value="1">1 năm</option>
                                            <option value="2">2 năm</option>
                                            <option value="5">5 năm</option>
                                            <option value="10">Trên 10 năm</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Hình thức làm việc <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="typeJob" required>
                                            <option value="" disabled selected hidden>Chọn hình thức làm việc...</option>
                                            <option value="Full-time">Toàn thời gian (Full-time)</option>
                                            <option value="Part-time">Bán thời gian (Part-time)</option>
                                            <option value="Internship">Thực tập (Internship)</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Ngày hết hạn <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <input type="date" class="form-control" name="dueDate" required>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-actions mt-3">
                                    <button type="submit" class="btn btn-primary">Thêm Công Việc</button>
                                    <button type="button" class="btn btn-secondary" onclick="history.back()">Huỷ</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

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
                        const min = parseFloat(offerMin.value.replace(/\./g, ""));
                        const max = parseFloat(offerMax.value.replace(/\./g, ""));
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

                document.querySelectorAll('.money-input').forEach(el => {
                    el.addEventListener('input', () => formatNumber(el));
                });

                form.addEventListener('submit', e => {
                    document.querySelectorAll('.money-input').forEach(el => {
                        el.value = el.value.replace(/\./g, '');
                    });
                    if (!validateForm())
                        e.preventDefault();
                });
            });

            function formatNumber(input) {
                let value = input.value.replace(/\D/g, "");
                input.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
            }
        </script>
    </body>
</html>
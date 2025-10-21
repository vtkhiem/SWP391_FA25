<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Chỉnh sửa công việc - Job Board</title>
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
                            <h3>Chỉnh sửa công việc</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area-->  

        <div class="job_details_area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="white-bg p-4">
                            <h4 class="mb-3">Chỉnh Sửa Thông Tin Công Việc</h4>

                            <!-- Toast Notification -->
                            <c:if test="${not empty message}">
                                <div class="toast-message success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="toast-message error">${error}</div>
                            </c:if>

                            <form action="job_edit" method="post">
                                <input type="hidden" name="id" value="${job.jobPostID}"/>
                                <input type="hidden" name="employerId" value="${sessionScope.employer.employerID}"/>

                                <div class="form-group">
                                    <label>Tiêu đề <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <input type="text" class="form-control" name="title" placeholder="Nhập tiêu đề công việc..." value="${job.title}" required>
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
                                        <textarea class="form-control mb-2" name="description-1" rows="1" placeholder="Nhập mô tả công việc..." required>${desc1}</textarea>
                                        <span class="error-message"></span>
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control mb-2" name="description-2" rows="1" placeholder="Nhập yêu cầu công việc..." required>${desc2}</textarea>
                                        <span class="error-message"></span>
                                    </div>
                                    <div class="col-12">
                                        <textarea class="form-control" name="description-3" rows="1" placeholder="Nhập quyền lợi công việc..." required>${desc3}</textarea>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Ngành nghề <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="category" required>
                                            <option value="" disabled selected hidden>Chọn ngành nghề...</option>
                                            <option value="Công nghệ thông tin" <c:if test="${job.category eq 'Công nghệ thông tin'}">selected</c:if>>Công nghệ thông tin</option>
                                            <option value="Kinh doanh & Quản trị" <c:if test="${job.category eq 'Kinh doanh & Quản trị'}">selected</c:if>>Kinh doanh & Quản trị</option>
                                            <option value="Thiết kế - Nghệ thuật - Truyền thông" <c:if test="${job.category eq 'Thiết kế - Nghệ thuật - Truyền thông'}">selected</c:if>>Thiết kế - Nghệ thuật - Truyền thông</option>
                                            <option value="Kỹ thuật – Cơ khí – Điện – Xây dựng" <c:if test="${job.category eq 'Kỹ thuật – Cơ khí – Điện – Xây dựng'}">selected</c:if>>Kỹ thuật – Cơ khí – Điện – Xây dựng</option>
                                            <option value="Y tế – Dược – Chăm sóc sức khỏe" <c:if test="${job.category eq 'Y tế – Dược – Chăm sóc sức khỏe'}">selected</c:if>>Y tế – Dược – Chăm sóc sức khỏe</option>
                                            <option value="Giáo dục – Đào tạo" <c:if test="${job.category eq 'Giáo dục – Đào tạo'}">selected</c:if>>Giáo dục – Đào tạo</option>
                                            <option value="Du lịch – Nhà hàng – Khách sạn" <c:if test="${job.category eq 'Du lịch – Nhà hàng – Khách sạn'}">selected</c:if>>Du lịch – Nhà hàng – Khách sạn</option>
                                            <option value="Nông – Lâm – Ngư nghiệp" <c:if test="${job.category eq 'Nông – Lâm – Ngư nghiệp'}">selected</c:if>>Nông – Lâm – Ngư nghiệp</option>
                                            <option value="Luật – Hành chính – Chính trị" <c:if test="${job.category eq 'Luật – Hành chính – Chính trị'}">selected</c:if>>Luật – Hành chính – Chính trị</option>
                                            <option value="Khác / Tự do" <c:if test="${job.category eq 'Khác / Tự do'}">selected</c:if>>Khác / Tự do</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Vị trí công việc <span style="color: red;">*</span></label>
                                        <div class="col-12">
                                            <input type="text" class="form-control" name="position" placeholder="Nhập vị trí công việc..." value="${job.position}" required>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Địa điểm <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="location" required>
                                            <option value="" disabled selected hidden>Chọn tỉnh thành...</option>
                                            <option value="Hà Nội" <c:if test="${job.location eq 'Hà Nội'}">selected</c:if>>TP. Hà Nội</option>
                                            <option value="Hồ Chí Minh" <c:if test="${job.location eq 'Hồ Chí Minh'}">selected</c:if>>TP. Hồ Chí Minh</option>
                                            <option value="Đà Nẵng" <c:if test="${job.location eq 'Đà Nẵng'}">selected</c:if>>TP. Đà Nẵng</option>
                                            <option value="Hải Phòng" <c:if test="${job.location eq 'Hải Phòng'}">selected</c:if>>TP. Hải Phòng</option>
                                            <option value="Huế" <c:if test="${job.location eq 'Huế'}">selected</c:if>>TP. Huế</option>
                                            <option value="Cần Thơ" <c:if test="${job.location eq 'Cần Thơ'}">selected</c:if>>TP. Cần Thơ</option>
                                            <option value="An Giang" <c:if test="${job.location eq 'An Giang'}">selected</c:if>>An Giang</option>
                                            <option value="Bắc Ninh" <c:if test="${job.location eq 'Bắc Ninh'}">selected</c:if>>Bắc Ninh</option>
                                            <option value="Cà Mau" <c:if test="${job.location eq 'Cà Mau'}">selected</c:if>>Cà Mau</option>
                                            <option value="Cao Bằng" <c:if test="${job.location eq 'Cao Bằng'}">selected</c:if>>Cao Bằng</option>
                                            <option value="Đắk Lắk" <c:if test="${job.location eq 'Đắk Lắk'}">selected</c:if>>Đắk Lắk</option>
                                            <option value="Điện Biên" <c:if test="${job.location eq 'Điện Biên'}">selected</c:if>>Điện Biên</option>
                                            <option value="Đồng Nai" <c:if test="${job.location eq 'Đồng Nai'}">selected</c:if>>Đồng Nai</option>
                                            <option value="Đồng Tháp" <c:if test="${job.location eq 'Đồng Tháp'}">selected</c:if>>Đồng Tháp</option>
                                            <option value="Gia Lai" <c:if test="${job.location eq 'Gia Lai'}">selected</c:if>>Gia Lai</option>
                                            <option value="Hà Tĩnh" <c:if test="${job.location eq 'Hà Tĩnh'}">selected</c:if>>Hà Tĩnh</option>
                                            <option value="Hưng Yên" <c:if test="${job.location eq 'Hưng Yên'}">selected</c:if>>Hưng Yên</option>
                                            <option value="Khánh Hòa" <c:if test="${job.location eq 'Khánh Hoà'}">selected</c:if>>Khánh Hòa</option>
                                            <option value="Lai Châu" <c:if test="${job.location eq 'Lai Châu'}">selected</c:if>>Lai Châu</option>
                                            <option value="Lâm Đồng" <c:if test="${job.location eq 'Lâm Đồng'}">selected</c:if>>Lâm Đồng</option>
                                            <option value="Lạng Sơn" <c:if test="${job.location eq 'Lạng Sơn'}">selected</c:if>>Lạng Sơn</option>
                                            <option value="Lào Cai" <c:if test="${job.location eq 'Lào Cai'}">selected</c:if>>Lào Cai</option>
                                            <option value="Ninh Bình" <c:if test="${job.location eq 'Ninh Bình'}">selected</c:if>>Ninh Bình</option>
                                            <option value="Nghệ An" <c:if test="${job.location eq 'Nghệ An'}">selected</c:if>>Nghệ An</option>
                                            <option value="Phú Thọ" <c:if test="${job.location eq 'Phú Thọ'}">selected</c:if>>Phú Thọ</option>
                                            <option value="Quảng Ngãi" <c:if test="${job.location eq 'Quảng Ngãi'}">selected</c:if>>Quảng Ngãi</option>
                                            <option value="Quảng Ninh" <c:if test="${job.location eq 'Quảng Ninh'}">selected</c:if>>Quảng Ninh</option>
                                            <option value="Quảng Trị" <c:if test="${job.location eq 'Quảng Trị'}">selected</c:if>>Quảng Trị</option>
                                            <option value="Sơn La" <c:if test="${job.location eq 'Sơn La'}">selected</c:if>>Sơn La</option>
                                            <option value="Tây Ninh" <c:if test="${job.location eq 'Tây Ninh'}">selected</c:if>>Tây Ninh</option>
                                            <option value="Thái Nguyên" <c:if test="${job.location eq 'Thái Nguyên'}">selected</c:if>>Thái Nguyên</option>
                                            <option value="Thanh Hóa" <c:if test="${job.location eq 'Thanh Hoá'}">selected</c:if>>Thanh Hóa</option>
                                            <option value="Tuyên Quang" <c:if test="${job.location eq 'Tuyên Quang'}">selected</c:if>>Tuyên Quang</option>
                                            <option value="Vĩnh Long" <c:if test="${job.location eq 'Vĩnh Long'}">selected</c:if>>Vĩnh Long</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Mức lương <span style="color: red;">*</span></label>
                                        <div class="d-flex">
                                            <div class="col-6">
                                                <input type="number" class="form-control mr-2" name="offerMin" step="1" placeholder="Từ..." value="${job.offerMin}" required>
                                            <span class="error-message"></span>
                                        </div>
                                        <div class="col-6">
                                            <input type="number" class="form-control" name="offerMax" step="1" placeholder="Đến..." value="${job.offerMax}" required>
                                            <span class="error-message"></span>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Yêu cầu kinh nghiệm <span style="color: red;">*</span></label>
                                    <div class="col-12">
                                        <select class="form-control" name="numberExp" required>
                                            <option value="" disabled selected hidden>Chọn yêu cầu kinh nghiệm...</option>
                                            <option value="0" <c:if test="${job.numberExp == 0}">selected</c:if>>Không yêu cầu kinh nghiệm</option>
                                            <option value="1" <c:if test="${job.numberExp == 1}">selected</c:if>>1 năm</option>
                                            <option value="2" <c:if test="${job.numberExp == 2}">selected</c:if>>2 năm</option>
                                            <option value="5" <c:if test="${job.numberExp == 5}">selected</c:if>>5 năm</option>
                                            <option value="10" <c:if test="${job.numberExp == 10}">selected</c:if>>Trên 10 năm</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Hình thức làm việc <span style="color: red;">*</span></label>
                                        <div class="col-12">
                                            <select class="form-control" name="typeJob" required>
                                                <option value="" disabled selected hidden>Chọn hình thức làm việc...</option>
                                                <option value="Full-time" <c:if test="${job.typeJob eq 'Full-time'}">selected</c:if>>Toàn thời gian (Full-time)</option>
                                            <option value="Part-time"<c:if test="${job.typeJob eq 'Part-time'}">selected</c:if>>Bán thời gian (Part-time)</option>
                                            <option value="Internship"<c:if test="${job.typeJob eq 'Intership'}">selected</c:if>>Thực tập (Internship)</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Ngày hết hạn <span style="color: red;">*</span></label>
                                        <div class="col-12">
                                            <input type="date" class="form-control" name="dueDate" value="${dueDateFormatted}" required>
                                        <span class="error-message"></span>
                                    </div>
                                </div>

                                <div class="form-actions mt-3">
                                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                    <button type="button" class="btn btn-secondary" onclick="history.back()">Huỷ</button>
                                </div>
                            </form>
                        </div>

                        <div class="white-bg p-4 mt-3">
                            <h4>Các Chức Năng Khác</h4>
                            <c:choose>
                                <c:when test="${job.visible}">
                                    <form action="hide_job" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn ẩn công việc này?');">
                                        <input type="hidden" name="jobId" value="${job.jobPostID}">
                                        <button type="submit" class="btn btn-sm btn-danger m-1">
                                            <i class="ti-lock"></i>
                                            <b style="color: white"> Ẩn công việc này?</b>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="visible_job" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn hiện công việc này?');">
                                        <input type="hidden" name="jobId" value="${job.jobPostID}">
                                        <button type="submit" class="btn btn-sm btn-success m-1">
                                            <i class="ti-unlock"></i>
                                            <b style="color: white"> Hiện công việc này?</b>
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
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
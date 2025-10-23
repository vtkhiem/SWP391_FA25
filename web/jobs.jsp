<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Việc làm khả dụng - Job Board</title>
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

        <!-- bradcam_area  -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Việc làm khả dụng</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <!-- job_listing_area -->
        <div class="job_listing_area plus_padding">
            <div class="container">
                <div class="row">
                    <!-- Bộ lọc bên trái -->
                    <div class="col-lg-3 col-md-4">
                        <div class="job_filter white-bg p-3 rounded shadow-sm">
                            <h4 class="mb-3">Bộ lọc</h4>
                            <form id="filterForm" action="search" method="get">
                                <div class="form-group mb-3">
                                    <label>Vị trí tuyển dụng</label>
                                    <input type="text" name="keyword" placeholder="Vị trí tuyển dụng..." class="form-control">
                                </div>

                                <div class="form-group mb-3">
                                    <label>Ngành nghề</label>
                                    <select name="category" class="form-control">
                                        <option value="">Tất cả ngành nghề</option>
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

                                <div class="form-group mb-3">
                                    <label>Vị trí</label>
                                    <select name="location" class="form-control">
                                        <option value="">Toàn quốc</option>
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

                                <div class="form-group mb-3">
                                    <label>Mức lương</label>
                                    <div class="d-flex">
                                        <input type="number" name="minSalary" placeholder="Từ..." class="form-control me-2">
                                        <input type="number" name="maxSalary" placeholder="Đến..." class="form-control">
                                    </div>
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">${errorMessage}</div>
                                    </c:if>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Yêu cầu kinh nghiệm</label>
                                    <select name="numberExp" class="form-control">
                                        <option value="">Tất cả</option>
                                        <option value="0">Không yêu cầu kinh nghiệm</option>
                                        <option value="1">1 năm</option>
                                        <option value="2">2 năm</option>
                                        <option value="5">5 năm</option>
                                        <option value="10">Trên 10 năm</option>
                                    </select>
                                </div>

                                <div class="form-group mb-3">
                                    <label>Hình thức làm việc</label>
                                    <select name="jobType" class="form-control">
                                        <option value="">Tất cả</option>
                                        <option value="Full-time">Toàn thời gian (Full-time)</option>
                                        <option value="Part-time">Bán thời gian (Part-time)</option>
                                        <option value="Internship">Thực tập (Internship)</option>
                                    </select>
                                </div>

                                <button type="submit" class="boxed-btn3 w-100 mt-3">Áp Dụng</button>
                            </form>
                        </div>
                    </div>

                    <!-- Danh sách việc làm -->
                    <div class="col-lg-9 col-md-8">
                        <div class="recent_joblist_wrap">
                            <div class="recent_joblist white-bg mb-3">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h4>Danh sách việc làm</h4>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="job_lists m-0">
                            <div class="row">
                                <c:forEach var="job" items="${jobs}">
                                    <div class="col-lg-12 col-md-12 mb-3">
                                        <div class="single_jobs white-bg d-flex justify-content-between p-3 rounded shadow-sm">
                                            <div class="jobs_left d-flex align-items-center">
                                                <div class="thumb me-3">
                                                    <img src="img/svg_icon/1.svg" alt="">
                                                </div>
                                                <div class="jobs_conetent">
                                                    <a href="job_details?id=${job.jobPostID}">
                                                        <h4>${job.title}</h4>
                                                    </a>
                                                    <div class="links_locat d-flex align-items-center">
                                                        <div class="location me-3">
                                                            <p><i class="fa fa-map-marker"></i> ${job.location}</p>
                                                        </div>
                                                        <div class="location">
                                                            <p><i class="fa fa-clock-o"></i> ${job.typeJob}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="jobs_right">
                                                <div class="apply_now justify-content-center">
                                                    <a href="job_details?id=${job.jobPostID}" class="boxed-btn3">Xem Ngay</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="pagination justify-content-center mt-4">
                            <ul class="pagination">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage-1}">
                                        &lt;
                                    </a>
                                </li>

                                <c:forEach var="i" begin="1" end="${noOfPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>

                                <li class="page-item ${currentPage == noOfPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage+1}">
                                        &gt;
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- job_listing_area_end  -->

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("filterForm");
                const minInput = document.querySelector('input[name="minSalary"]');
                const maxInput = document.querySelector('input[name="maxSalary"]');

                form.addEventListener("submit", function (e) {
                    const min = parseFloat(minInput.value);
                    const max = parseFloat(maxInput.value);

                    if (!isNaN(min) && !isNaN(max) && max < min) {
                        e.preventDefault(); // chặn gửi form
                        alert("Mức lương tối đa phải lớn hơn hoặc bằng mức lương tối thiểu!");
                        // (tùy chọn) highlight lỗi cho người dùng thấy
                        minInput.classList.add("is-invalid");
                        maxInput.classList.add("is-invalid");
                    } else {
                        minInput.classList.remove("is-invalid");
                        maxInput.classList.remove("is-invalid");
                    }
                });
            });
        </script>
    </body>
</html>
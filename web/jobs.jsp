<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Jobs - Job Board</title>
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
                            <h3>Jobs Available</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <!-- search-area_start -->
        <div class="search_container">
            <form action="search" method="get" class="search_bar">
                <div class="search_inputs">
                    <!-- Vị trí -->
                    <input type="text" name="keyword" placeholder="Enter your expected position">

                    <!-- Ngành nghề -->
                    <select name="category">
                        <option value="">All occupations</option>
                        <option value="IT">IT</option>
                        <option value="Kinh doanh">Kinh doanh</option>
                        <option value="Marketing">Marketing</option>
                        <option value="Finance">Finance</option>
                        <option value="Design">Design</option>
                        <option value="Education">Education</option>
                        <option value="Human Resources">Human Resources</option>
                    </select>

                    <!-- Địa điểm -->
                    <select name="location">
                        <option value="">Nationwide</option>
                        <option value="Hà Nội">Hà Nội</option>
                        <option value="Hồ Chí Minh">Hồ Chí Minh</option>
                        <option value="Hải Phòng">Hải Phòng</option>
                        <option value="Đà Nẵng">Đà Nẵng</option>
                        <option value="Cần Thơ">Cần Thơ</option>
                        <option value="An Giang">An Giang</option>
                        <option value="Bà Rịa - Vũng Tàu">Bà Rịa - Vũng Tàu</option>
                        <option value="Bắc Giang">Bắc Giang</option>
                        <option value="Bắc Kạn">Bắc Kạn</option>
                        <option value="Bạc Liêu">Bạc Liêu</option>
                        <option value="Bắc Ninh">Bắc Ninh</option>
                        <option value="Bến Tre">Bến Tre</option>
                        <option value="Bình Định">Bình Định</option>
                        <option value="Bình Dương">Bình Dương</option>
                        <option value="Bình Phước">Bình Phước</option>
                        <option value="Bình Thuận">Bình Thuận</option>
                        <option value="Cà Mau">Cà Mau</option>
                        <option value="Cao Bằng">Cao Bằng</option>
                        <option value="Đắk Lắk">Đắk Lắk</option>
                        <option value="Đắk Nông">Đắk Nông</option>
                        <option value="Điện Biên">Điện Biên</option>
                        <option value="Đồng Nai">Đồng Nai</option>
                        <option value="Đồng Tháp">Đồng Tháp</option>
                        <option value="Gia Lai">Gia Lai</option>
                        <option value="Hà Giang">Hà Giang</option>
                        <option value="Hà Nam">Hà Nam</option>
                        <option value="Hà Tĩnh">Hà Tĩnh</option>
                        <option value="Hải Dương">Hải Dương</option>
                        <option value="Hậu Giang">Hậu Giang</option>
                        <option value="Hòa Bình">Hòa Bình</option>
                        <option value="Hưng Yên">Hưng Yên</option>
                        <option value="Khánh Hòa">Khánh Hòa</option>
                        <option value="Kiên Giang">Kiên Giang</option>
                        <option value="Kon Tum">Kon Tum</option>
                        <option value="Lai Châu">Lai Châu</option>
                        <option value="Lâm Đồng">Lâm Đồng</option>
                        <option value="Lạng Sơn">Lạng Sơn</option>
                        <option value="Lào Cai">Lào Cai</option>
                        <option value="Long An">Long An</option>
                        <option value="Nam Định">Nam Định</option>
                        <option value="Nghệ An">Nghệ An</option>
                        <option value="Ninh Bình">Ninh Bình</option>
                        <option value="Ninh Thuận">Ninh Thuận</option>
                        <option value="Phú Thọ">Phú Thọ</option>
                        <option value="Phú Yên">Phú Yên</option>
                        <option value="Quảng Bình">Quảng Bình</option>
                        <option value="Quảng Nam">Quảng Nam</option>
                        <option value="Quảng Ngãi">Quảng Ngãi</option>
                        <option value="Quảng Ninh">Quảng Ninh</option>
                        <option value="Quảng Trị">Quảng Trị</option>
                        <option value="Sóc Trăng">Sóc Trăng</option>
                        <option value="Sơn La">Sơn La</option>
                        <option value="Tây Ninh">Tây Ninh</option>
                        <option value="Thái Bình">Thái Bình</option>
                        <option value="Thái Nguyên">Thái Nguyên</option>
                        <option value="Thanh Hóa">Thanh Hóa</option>
                        <option value="Thừa Thiên Huế">Thừa Thiên Huế</option>
                        <option value="Tiền Giang">Tiền Giang</option>
                        <option value="Trà Vinh">Trà Vinh</option>
                        <option value="Tuyên Quang">Tuyên Quang</option>
                        <option value="Vĩnh Long">Vĩnh Long</option>
                        <option value="Vĩnh Phúc">Vĩnh Phúc</option>
                        <option value="Yên Bái">Yên Bái</option>
                    </select>
                </div>

                <div class="search_buttons">
                    <button type="submit" class="btn-find">Search</button>
                    <button type="button" class="btn-advanced" id="btnAdvancedFilter">Filter</button>
                </div>
            </form>

            <div class="popular_search">
                <span>Popular search:</span>
                <ul>
                    <li><a href="job_search?keyword=PHP Developer">PHP Developer</a></li>
                    <li><a href="job_search?keyword=React Native">React Native</a></li>
                    <li><a href="job_search?keyword=Marketing Online">Marketing Online</a></li>
                    <li><a href="job_search?keyword=Data Analyst">Data Analyst</a></li>
                </ul>
            </div>
        </div>
        <!-- search-area-end -->

        <!-- job_listing_area -->
        <div class="job_listing_area plus_padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="recent_joblist_wrap">
                            <div class="recent_joblist white-bg ">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h4>Job Listing</h4>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="job_lists m-0">
                            <div class="row">
                                <c:forEach var="job" items="${jobs}">
                                    <div class="col-lg-12 col-md-12">
                                        <div class="single_jobs white-bg d-flex justify-content-between">
                                            <div class="jobs_left d-flex align-items-center">
                                                <div class="thumb">
                                                    <img src="img/svg_icon/1.svg" alt="">
                                                </div>
                                                <div class="jobs_conetent">
                                                    <a href="job_details?id=${job.jobPostID}">
                                                        <h4>${job.title}</h4>
                                                    </a>
                                                    <div class="links_locat d-flex align-items-center">
                                                        <div class="location">
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
                                                    <a href="job_details?id=${job.jobPostID}" class="boxed-btn3">Apply Now</a>
                                                    <form action="save_job" method="post" style="display:inline;">
                                                        <input type="hidden" name="jobId" value="${job.jobPostID}">
                                                        <button type="submit" class="save_job">
                                                            <i class="ti-heart"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                                <div class="date">
                                                    <%
                                                        model.JobPost jobObj = (model.JobPost) pageContext.getAttribute("job");
                                                        java.sql.Timestamp publishedTs = null;
                                                        java.sql.Timestamp dueTs = null;
                                                        if (jobObj != null) {
                                                            if (jobObj.getDayCreate() != null) {
                                                                publishedTs = java.sql.Timestamp.valueOf(jobObj.getDayCreate());
                                                            }
                                                            if (jobObj.getDueDate() != null) {
                                                                dueTs = java.sql.Timestamp.valueOf(jobObj.getDueDate());
                                                            }
                                                        }
                                                        pageContext.setAttribute("publishedDate", publishedTs);
                                                        pageContext.setAttribute("dueDateFmt", dueTs);
                                                    %>
                                                    <p>Published: <fmt:formatDate value="${publishedDate}" pattern="dd/MM/yyyy, HH:mm"/></p>
                                                    <p>Due Date: <fmt:formatDate value="${dueDateFmt}" pattern="dd/MM/yyyy, HH:mm"/></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- job_listing_area_end  -->

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

        <!-- Advanced Filter Modal -->
        <div class="advanced_filter_modal" id="advancedFilterModal">
            <div class="advanced_filter_box">
                <button class="advanced_filter_close" id="closeAdvancedFilter" title="Close">&times;</button>
                <h3>Advanced Search</h3>
                <form id="advancedFilterForm" action="job_search" method="get">
                    <!-- Keyword / Position -->
                    <div class="advanced_filter_group">
                        <label for="keyword">Expected Position / Keyword</label>
                        <input type="text" id="keyword" name="keyword"
                               placeholder="Enter your expected position or keyword">
                    </div>

                    <!-- Category -->
                    <div class="advanced_filter_group">
                        <label for="category">Occupation</label>
                        <select id="category" name="category">
                            <option value="">All occupations</option>
                            <option value="IT">IT</option>
                            <option value="Business">Business</option>
                            <option value="Marketing">Marketing</option>
                            <option value="Finance">Finance</option>
                            <option value="Education">Education</option>
                            <option value="Design">Design</option>
                            <option value="Human Resources">Human Resources</option>
                        </select>
                    </div>

                    <!-- Location -->
                    <div class="advanced_filter_group">
                        <label for="location">Location</label>
                        <select id="location" name="location">
                            <option value="">Nationwide</option>
                            <option value="Hà Nội">Hà Nội</option>
                            <option value="Hồ Chí Minh">Hồ Chí Minh</option>
                            <option value="Đà Nẵng">Đà Nẵng</option>
                            <option value="Cần Thơ">Cần Thơ</option>
                            <option value="An Giang">An Giang</option>
                            <option value="Bà Rịa - Vũng Tàu">Bà Rịa - Vũng Tàu</option>
                            <option value="Bắc Giang">Bắc Giang</option>
                            <option value="Bắc Kạn">Bắc Kạn</option>
                            <option value="Bạc Liêu">Bạc Liêu</option>
                            <option value="Bắc Ninh">Bắc Ninh</option>
                            <option value="Bến Tre">Bến Tre</option>
                            <option value="Bình Định">Bình Định</option>
                            <option value="Bình Dương">Bình Dương</option>
                            <option value="Bình Phước">Bình Phước</option>
                            <option value="Bình Thuận">Bình Thuận</option>
                            <option value="Cà Mau">Cà Mau</option>
                            <option value="Cao Bằng">Cao Bằng</option>
                            <option value="Đắk Lắk">Đắk Lắk</option>
                            <option value="Đắk Nông">Đắk Nông</option>
                            <option value="Điện Biên">Điện Biên</option>
                            <option value="Đồng Nai">Đồng Nai</option>
                            <option value="Đồng Tháp">Đồng Tháp</option>
                            <option value="Gia Lai">Gia Lai</option>
                            <option value="Hà Giang">Hà Giang</option>
                            <option value="Hà Nam">Hà Nam</option>
                            <option value="Hà Tĩnh">Hà Tĩnh</option>
                            <option value="Hải Dương">Hải Dương</option>
                            <option value="Hậu Giang">Hậu Giang</option>
                            <option value="Hòa Bình">Hòa Bình</option>
                            <option value="Hưng Yên">Hưng Yên</option>
                            <option value="Khánh Hòa">Khánh Hòa</option>
                            <option value="Kiên Giang">Kiên Giang</option>
                            <option value="Kon Tum">Kon Tum</option>
                            <option value="Lai Châu">Lai Châu</option>
                            <option value="Lâm Đồng">Lâm Đồng</option>
                            <option value="Lạng Sơn">Lạng Sơn</option>
                            <option value="Lào Cai">Lào Cai</option>
                            <option value="Long An">Long An</option>
                            <option value="Nam Định">Nam Định</option>
                            <option value="Nghệ An">Nghệ An</option>
                            <option value="Ninh Bình">Ninh Bình</option>
                            <option value="Ninh Thuận">Ninh Thuận</option>
                            <option value="Phú Thọ">Phú Thọ</option>
                            <option value="Phú Yên">Phú Yên</option>
                            <option value="Quảng Bình">Quảng Bình</option>
                            <option value="Quảng Nam">Quảng Nam</option>
                            <option value="Quảng Ngãi">Quảng Ngãi</option>
                            <option value="Quảng Ninh">Quảng Ninh</option>
                            <option value="Quảng Trị">Quảng Trị</option>
                            <option value="Sóc Trăng">Sóc Trăng</option>
                            <option value="Sơn La">Sơn La</option>
                            <option value="Tây Ninh">Tây Ninh</option>
                            <option value="Thái Bình">Thái Bình</option>
                            <option value="Thái Nguyên">Thái Nguyên</option>
                            <option value="Thanh Hóa">Thanh Hóa</option>
                            <option value="Thừa Thiên Huế">Thừa Thiên Huế</option>
                            <option value="Tiền Giang">Tiền Giang</option>
                            <option value="Trà Vinh">Trà Vinh</option>
                            <option value="Tuyên Quang">Tuyên Quang</option>
                            <option value="Vĩnh Long">Vĩnh Long</option>
                            <option value="Vĩnh Phúc">Vĩnh Phúc</option>
                            <option value="Yên Bái">Yên Bái</option>
                        </select>
                    </div>

                    <!-- Salary -->
                    <div class="advanced_filter_group">
                        <label>Salary Range (VND)</label>
                        <div class="d-flex">
                            <input type="number" id="minSalary" name="minSalary" placeholder="Min" class="form-control mr-2">
                            <input type="number" id="maxSalary" name="maxSalary" placeholder="Max" class="form-control">
                        </div>
                    </div>

                    <!-- Job Type -->
                    <div class="advanced_filter_group">
                        <label for="typeJob">Job Type</label>
                        <select id="typeJob" name="typeJob">
                            <option value="">Any</option>
                            <option value="Full-time">Full-time</option>
                            <option value="Part-time">Part-time</option>
                            <option value="Internship">Internship</option>
                            <option value="Remote">Remote</option>
                        </select>
                    </div>

                    <!-- Experience -->
                    <div class="advanced_filter_group">
                        <label for="numberExp">Experience (years)</label>
                        <select id="numberExp" name="numberExp">
                            <option value="">Any</option>
                            <option value="0">No Exp</option>
                            <option value="1">1 year</option>
                            <option value="2">2 years</option>
                            <option value="3">3 years</option>
                            <option value="5">5 years</option>
                            <option value="10">10+ years</option>
                        </select>
                    </div>

                    <div class="advanced_filter_actions">
                        <button type="button" class="advanced_filter_btn cancel" id="cancelAdvancedFilter">Cancel</button>
                        <button type="submit" class="advanced_filter_btn">Apply Filter</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var btnFilter = document.getElementById('btnAdvancedFilter');
                var modal = document.getElementById('advancedFilterModal');
                var closeBtn = document.getElementById('closeAdvancedFilter');
                var cancelBtn = document.getElementById('cancelAdvancedFilter');
                var form = document.getElementById('advancedFilterForm');

                // Mở modal
                btnFilter.addEventListener('click', function (e) {
                    e.preventDefault(); // tránh reload khi là <button>
                    modal.classList.add('active');
                });

                // Đóng modal
                closeBtn.addEventListener('click', function () {
                    modal.classList.remove('active');
                });
                cancelBtn.addEventListener('click', function () {
                    modal.classList.remove('active');
                });
                modal.addEventListener('click', function (e) {
                    if (e.target === modal)
                        modal.classList.remove('active');
                });

                // Submit form
                form.addEventListener('submit', function () {
                    // KHÔNG preventDefault => để form tự submit sang job_search
                    modal.classList.remove('active');
                });
            });
        </script>

        <script>
            $(function () {
                $("#slider-range").slider({
                    range: true,
                    min: 0,
                    max: 24600,
                    values: [750, 24600],
                    slide: function (event, ui) {
                        $("#amount").val("$" + ui.values[0] + " - $" + ui.values[1] + "/ Year");
                    }
                });
                $("#amount").val("$" + $("#slider-range").slider("values", 0) +
                        " - $" + $("#slider-range").slider("values", 1) + "/ Year");
            });
        </script>
    </body>
</html>
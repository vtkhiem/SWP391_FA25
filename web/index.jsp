<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Trang chủ - Job Board</title>
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

        <div class="main-content">
            <div class="job-explore">
                <!-- search-area_start -->
                <div class="search_container">
                    <form action="search" method="get" class="search_bar">
                        <div class="search_inputs">
                            <!-- Keyword -->
                            <input type="text" name="keyword" placeholder="Vị trí tuyển dụng">

                            <!-- Occupations -->
                            <select name="category">
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

                            <!-- Location -->
                            <select name="location">
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

                        <div class="search_buttons">
                            <button type="submit" class="btn-find"><i class="ti-search"></i></button>
                        </div>
                    </form>
                </div>
                <!-- search-area-end -->

                <!-- job_highlight_slider_start -->
                <div class="job_highlight_slider">
                    <button class="job_nav_arrow prev" aria-label="Prev">&lt;</button>

                    <div class="job_slide_container">
                        <!-- Một slide hiển thị 1 job -->
                        <c:forEach var="job" items="${highlightJobs}">
                            <div class="job_slide">
                                <div class="job_card_large">
                                    <div class="job_info_large">
                                        <h3 class="job_title_large">${job.title}</h3>
                                        <p class="job_salary_large">
                                            <i class="fa fa-money"></i> ${job.salary}
                                        </p>
                                        <p class="job_location_large">
                                            <i class="fa fa-map-marker"></i> ${job.location}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <button class="job_nav_arrow next" aria-label="Next">&gt;</button>
                </div>
                <!-- job_highlight_slider_end -->
            </div>

            <!-- industry_section_start -->
            <div class="hot_industry_section">
                <div class="hot_industry_header">
                    <div class="hot_industry_title">Featured Industries</div>
                    <a href="#" class="hot_industry_viewall">Browse More Industries</a>
                </div>
                <div class="hot_industry_nav">
                    <button class="hot_industry_arrow" aria-label="Prev">&lt;</button>
                    <div class="hot_industry_grid">
                        <div class="hot_industry_card">
                            <div class="hot_industry_icon">
                                <img src="img/svg_icon/business.svg" alt="Business">
                            </div>
                            <div class="hot_industry_name">Business</div>
                            <div class="hot_industry_jobs">1521 Jobs</div>
                        </div>
                        <div class="hot_industry_card">
                            <div class="hot_industry_icon">
                                <img src="img/svg_icon/construction.svg" alt="Architecture">
                            </div>
                            <div class="hot_industry_name">Architecture / Construction</div>
                            <div class="hot_industry_jobs">1018 Jobs</div>
                        </div>
                        <div class="hot_industry_card">
                            <div class="hot_industry_icon">
                                <img src="img/svg_icon/accounting.svg" alt="Accounting">
                            </div>
                            <div class="hot_industry_name">Accounting / Auditing</div>
                            <div class="hot_industry_jobs">1011 Jobs</div>
                        </div>
                        <div class="hot_industry_card">
                            <div class="hot_industry_icon">
                                <img src="img/svg_icon/it.svg" alt="IT">
                            </div>
                            <div class="hot_industry_name">IT / Telecommunications</div>
                            <div class="hot_industry_jobs">832 Jobs</div>
                        </div>
                        <div class="hot_industry_card">
                            <div class="hot_industry_icon">
                                <img src="img/svg_icon/factory.svg" alt="Manufacturing">
                            </div>
                            <div class="hot_industry_name">Manufacturing</div>
                            <div class="hot_industry_jobs">729 Jobs</div>
                        </div>
                    </div>
                    <button class="hot_industry_arrow" aria-label="Next">&gt;</button>
                </div>
            </div>
            <!-- industry_section_end -->

            <!-- job_listing_area_start  -->
            <div class="job_listing_area">
                <div class="job_listing_container">
                    <div class="job_listing_header">
                        <div class="job_listing_title">Featured Jobs</div>
                        <a href="jobs.html" class="job_listing_viewall">Browse More Job</a>
                    </div>
                    <div class="job_listing_nav">
                        <button class="job_nav_arrow" aria-label="Prev">&lt;</button>
                        <div class="job_listing_grid">
                            <!-- Job Card 1 -->
                            <div class="job_card">
                                <img src="img/svg_icon/1.svg" alt="VinUni" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Head of Marketing & ...</div>
                                    <div class="job_card_company">VinUniversity</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 2 -->
                            <div class="job_card">
                                <img src="img/svg_icon/2.svg" alt="Techcombank" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Internal Audit</div>
                                    <div class="job_card_company">Techcombank</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 3 -->
                            <div class="job_card">
                                <img src="img/svg_icon/3.svg" alt="Vietcombank" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Chuyên Viên Kiểm Đị...</div>
                                    <div class="job_card_company">Vietcombank</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 4 -->
                            <div class="job_card">
                                <img src="img/svg_icon/3.svg" alt="Vietcombank" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Chuyên Viên Kiểm Đị...</div>
                                    <div class="job_card_company">Vietcombank</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 5 -->
                            <div class="job_card">
                                <img src="img/svg_icon/2.svg" alt="Techcombank" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Chuyên Viên Cao Cấp...</div>
                                    <div class="job_card_company">Techcombank</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 6 -->
                            <div class="job_card">
                                <img src="img/svg_icon/4.svg" alt="Chailease" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Nhân Viên Quan Hệ K...</div>
                                    <div class="job_card_company">Chailease</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Bình Dương, Đồng Nai, Hồ Chí Minh</div>
                                </div>
                            </div>
                            <!-- Job Card 7 -->
                            <div class="job_card">
                                <img src="img/svg_icon/2.svg" alt="Techcombank" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Chuyên Gia Tài Chính...</div>
                                    <div class="job_card_company">Techcombank</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội</div>
                                </div>
                            </div>
                            <!-- Job Card 8 -->
                            <div class="job_card">
                                <img src="img/svg_icon/5.svg" alt="CityLand" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Giám Đốc Kinh Doanh...</div>
                                    <div class="job_card_company">CityLand</div>
                                    <div class="job_card_salary">Thương lượng</div>
                                    <div class="job_card_location">Hà Nội, Hồ Chí Minh</div>
                                </div>
                            </div>
                        </div>
                        <button class="job_nav_arrow" aria-label="Next">&gt;</button>
                    </div>
                </div>
            </div>
            <!-- job_listing_area_end  -->

            <!-- job_searcing_wrap  -->
            <div class="job_searcing_wrap overlay">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-5 offset-lg-1 col-md-6">
                            <div class="searching_text">
                                <h3>Tìm kiếm công việc?</h3>
                                <p>Chúng tôi cung cấp nền tảng tìm việc làm trực tuyến miễn phí!</p>
                                <a href="jobs" class="boxed-btn3">TÌM VIỆC NGAY</a>
                            </div>
                        </div>

                        <div class="col-lg-5 offset-lg-1 col-md-6">
                            <div class="searching_text">
                                <h3>Đang cần tuyển dụng?</h3>
                                <p>Chuyển đến trang dành cho doanh nghiệp</p>
                                <a href="employer.jsp" class="boxed-btn3">NHÀ TUYỂN DỤNG</a>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="text-center my-5">
                <form action="prepare" method="get">

                    <button type="submit" class="buy-btn">
                        <i class="ti-comment-alt"></i> Gửi phản hồi cho quản trị viên
                    </button>
                </form>
            </div>
            <!--job_searcing_wrap end-->  

            <!-- footer -->
            <jsp:include page="footer.jsp"/>
            <!-- footer --> 

            <script>
                const container = document.querySelector('.job_slide_container');
                const slides = document.querySelectorAll('.job_slide');
                const prev = document.querySelector('.job_nav_arrow.prev');
                const next = document.querySelector('.job_nav_arrow.next');

                let index = 0;

                function updateSlide() {
                    container.style.transform = `translateX(-${index * 100}%)`;
                }

                next.addEventListener('click', () => {
                    index = (index + 1) % slides.length;
                    updateSlide();
                });

                prev.addEventListener('click', () => {
                    index = (index - 1 + slides.length) % slides.length;
                    updateSlide();
                });
            </script>
    </body>
</html>
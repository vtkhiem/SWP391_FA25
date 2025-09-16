<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Home - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.png">

        <!-- CSS: Bootstrap first, then libraries, then main style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/themify-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flaticon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nice-select.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gijgo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slicknav.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom.css"> 
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="/views/header/header.jsp"/>
        <!-- header_end -->

        <div class="main-content">
            <div class="job-explore">
                <!-- search-area_start -->
                <div class="search_container">
                    <div class="search_bar">
                        <div class="search_inputs">
                            <input type="text" placeholder="Enter your expected position">
                            <select>
                                <option>All occupations</option>
                                <option>IT</option>
                                <option>Kinh doanh</option>
                                <option>Marketing</option>
                            </select>
                            <select>
                                <option>Nationwide</option>
                                <option>Hanoi</option>
                                <option>Ho Chi Minh City</option>
                                <option>Danang</option>
                            </select>
                        </div>

                        <div class="search_buttons">
                            <button class="btn-find">Search</button>
                            <button class="btn-advanced" id="btnAdvancedFilter">Filter</button>
                        </div>
                    </div>

                    <div class="popular_search">
                        <span>Popular search:</span>
                        <ul>
                            <li><a href="#">PHP Developer</a></li>
                            <li><a href="#">React Native</a></li>
                            <li><a href="#">Marketing Online</a></li>
                            <li><a href="#">Data Analyst</a></li>
                        </ul>
                    </div>
                </div>
                <!-- search-area-end -->

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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
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
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
                                </div>
                            </div>
                            <button class="job_nav_arrow" aria-label="Next">&gt;</button>
                        </div>
                    </div>
                </div>
                <!-- job_listing_area_end  -->
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
                                <h3>Looking for a Job?</h3>
                                <p>We provide online instant cash loans with quick approval </p>
                                <a href="#" class="boxed-btn3">Browse Job</a>
                            </div>
                        </div>
                        <div class="col-lg-5 offset-lg-1 col-md-6">
                            <div class="searching_text">
                                <h3>Looking for a Expert?</h3>
                                <p>We provide online instant cash loans with quick approval </p>
                                <a href="#" class="boxed-btn3">Post a Job</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- job_searcing_wrap end  -->

            <!-- Advanced Filter Modal -->
            <div class="advanced_filter_modal" id="advancedFilterModal">
                <div class="advanced_filter_box">
                    <button class="advanced_filter_close" id="closeAdvancedFilter" title="Close">&times;</button>
                    <h3>Advanced Search</h3>
                    <form id="advancedFilterForm">
                        <div class="advanced_filter_group">
                            <label for="position_main">Expected Position</label>
                            <input type="text" id="position_main" name="position_main" placeholder="Enter your expected position">
                        </div>
                        <div class="advanced_filter_group">
                            <label for="occupation_main">Occupation</label>
                            <select id="occupation_main" name="occupation_main">
                                <option>All occupations</option>
                                <option>IT</option>
                                <option>Kinh doanh</option>
                                <option>Marketing</option>
                            </select>
                        </div>
                        <div class="advanced_filter_group">
                            <label for="location_main">Location</label>
                            <select id="location_main" name="location_main">
                                <option>Nationwide</option>
                                <option>Hanoi</option>
                                <option>Ho Chi Minh City</option>
                                <option>Danang</option>
                            </select>
                        </div>
                        <!-- Bổ sung các phần nâng cao -->
                        <div class="advanced_filter_group">
                            <label for="salary">Salary Range</label>
                            <select id="salary" name="salary">
                                <option value="">Any</option>
                                <option value="1">&lt; 10,000,000 VND</option>
                                <option value="2">10,000,000 - 20,000,000 VND</option>
                                <option value="3">20,000,000 - 35,000,000 VND</option>
                                <option value="4">&gt; 35,000,000 VND</option>
                            </select>
                        </div>
                        <div class="advanced_filter_group">
                            <label for="jobtype">Job Type</label>
                            <select id="jobtype" name="jobtype">
                                <option value="">Any</option>
                                <option value="fulltime">Full-time</option>
                                <option value="parttime">Part-time</option>
                                <option value="intern">Internship</option>
                                <option value="remote">Remote</option>
                            </select>
                        </div>
                        <div class="advanced_filter_group">
                            <label for="experience">Experience Level</label>
                            <select id="experience" name="experience">
                                <option value="">Any</option>
                                <option value="entry">Entry Level</option>
                                <option value="junior">Junior</option>
                                <option value="senior">Senior</option>
                                <option value="manager">Manager</option>
                            </select>
                        </div>
                        <div class="advanced_filter_actions">
                            <button type="button" class="advanced_filter_btn cancel" id="cancelAdvancedFilter">Cancel</button>
                            <button type="submit" class="advanced_filter_btn">Apply Filter</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- footer -->
        <jsp:include page="/views/footer/footer.jsp"/>
        <!-- footer -->

        <script>
            function adjustContentMargin() {
                const header = document.querySelector(".header-area");
                const main = document.querySelector(".main-content");
                if (header && main) {
                    main.style.marginTop = header.offsetHeight + "px";
                }
            }

            // Chạy khi load trang
            window.addEventListener("load", adjustContentMargin);

            // Chạy lại khi resize (responsive)
            window.addEventListener("resize", adjustContentMargin);
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var btnFilter = document.getElementById('btnAdvancedFilter');
                var modal = document.getElementById('advancedFilterModal');
                var closeBtn = document.getElementById('closeAdvancedFilter');
                var cancelBtn = document.getElementById('cancelAdvancedFilter');
                var form = document.getElementById('advancedFilterForm');

                btnFilter.addEventListener('click', function () {
                    modal.classList.add('active');
                });
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
                form.addEventListener('submit', function (e) {
                    e.preventDefault();
                    // TODO: Xử lý dữ liệu filter nâng cao ở đây
                    modal.classList.remove('active');
                });
            });
        </script>
    </body>
</html>
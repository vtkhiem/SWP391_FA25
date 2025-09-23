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
                    <form action="job_search" method="get" class="search_bar">
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


            <!-- footer -->
            <jsp:include page="footer.jsp"/>
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
    </body>
</html>
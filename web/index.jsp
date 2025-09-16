<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Job Board</title>
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
        <!-- <link rel="stylesheet" href="css/responsive.css"> -->
        <style>
            .search_container {
                align-items: center;
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 4px 24px rgba(44, 62, 80, 0.08);
                padding: 18px 24px;
                margin: 32px auto 32px auto;
                max-width: 980px;
            }

            .search_bar {
                display: flex;
            }

            .search_inputs {
                display: flex;
                flex: 1;
                gap: 0;
            }

            .search_inputs input,
            .search_inputs select {
                flex: 1;
                padding: 13px 16px;
                border: none;
                font-size: 15px;
                background: #f2f4f8;
                transition: background 0.2s;
            }

            .search_inputs input:focus,
            .search_inputs select:focus {
                background: #e9eef6;
                outline: none;
            }

            .search_inputs input:first-child {
                border-radius: 10px 0 0 10px;
            }

            .search_inputs select:last-of-type {
                border-radius: 0 10px 10px 0;
            }

            .search_buttons {
                display: flex;
                gap: 0;
                margin-left: 18px;
                align-items: center;
            }

            .btn-find {
                background: #00D363;
                color: #fff;
                border: 1px solid #00D363;
                border-radius: 10px 0 0 10px;
                margin-right: 1px;
                padding: 13px 28px;
                font-weight: 600;
                font-size: 15px;
                cursor: pointer;
                box-shadow: 0 2px 8px rgba(67, 160, 71, 0.08);
                transition: background 0.2s;
            }

            .btn-find:hover {
                color: #00D363 !important;
                border: 1px solid #00D363;
                background: transparent;
            }

            .btn-advanced {
                background: #f2f4f8;
                color: #1976d2;
                border: none;
                border-radius: 0 10px 10px 0;
                /* Bo gÃ³c pháº£i */
                padding: 13px 22px;
                font-weight: 600;
                font-size: 15px;
                cursor: pointer;
                transition: background 0.2s, color 0.2s;
                margin-left: -1px;
            }

            .btn-advanced:hover {
                background: #e3eaf5;
                color: #1565c0;
            }

            .popular_search {
                display: flex;
                align-items: center;
                gap: 12px;
                max-width: 980px;
                margin-top: 18px;
                font-size: 15px;
            }

            .popular_search span {
                font-weight: 600;
                color: #444;
            }

            .popular_search ul {
                display: flex;
                gap: 12px;
                list-style: none;
                margin: 0;
                padding: 0;
            }

            .popular_search ul li a {
                text-decoration: none;
                color: #1976d2;
                font-weight: 500;
                padding: 5px 12px;
                border-radius: 6px;
                background: #f2f4f8;
                transition: background 0.2s, color 0.2s;
            }

            .popular_search ul li a:hover {
                background: #e3eaf5;
                color: #1565c0;
                text-decoration: underline;
            }

            .job_listing_area {
                padding-top: 32px;
                padding-bottom: 32px;
            }

            .job_listing_container {
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 2px 12px rgba(44, 62, 80, 0.08);
                padding: 24px 18px;
                margin: 0 auto;
                max-width: 980px;
                border: 1px solid #e6eaf0;
                overflow: hidden;
                box-sizing: border-box;
            }

            .job_listing_header {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 18px;
            }

            .job_listing_title {
                font-size: 22px;
                font-weight: bold;
                color: #222;
            }

            .job_listing_viewall {
                color: #1976d2;
                font-weight: 500;
                font-size: 16px;
                text-decoration: none;
                transition: color 0.2s;
            }

            .job_listing_viewall:hover {
                color: #1565c0;
                text-decoration: underline;
            }

            .job_listing_nav {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 18px;
                gap: 0;
            }

            .job_listing_nav .job_nav_arrow {
                flex-shrink: 0;
            }

            .job_listing_nav .job_listing_grid {
                flex: 1;
                margin: 0 16px;
            }

            .job_nav_arrow {
                background: #f2f4f8;
                color: #b0b8c9;
                border: none;
                border-radius: 50%;
                width: 36px;
                height: 36px;
                font-size: 18px;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background 0.2s, color 0.2s;
                cursor: pointer;
            }

            .job_nav_arrow:hover {
                background: #e3eaf5;
                color: #1565c0;
            }

            .job_listing_grid {
                display: grid;
                grid-template-columns: repeat(4, 1fr);
                grid-template-rows: repeat(2, 1fr);
                gap: 16px;
                align-items: stretch;
                width: 100%;
                min-width: 0;
                box-sizing: border-box;
            }

            .job_card {
                background: #fff;
                border: 1px solid #e6eaf0;
                border-radius: 12px;
                padding: 12px 10px 10px 10px;
                display: flex;
                flex-direction: row;
                align-items: flex-start;
                min-height: 90px;
                position: relative;
                box-shadow: 0 1px 4px rgba(44, 62, 80, 0.03);
                gap: 10px;
                box-sizing: border-box;
                min-width: 0;
            }

            .job_card_logo {
                width: 32px;
                height: 32px;
                object-fit: contain;
                margin-right: 8px;
                flex-shrink: 0;
            }

            .job_card_info {
                flex: 1;
                min-width: 0;
            }

            .job_card_title {
                font-size: 15px;
                font-weight: bold;
                color: #222;
                margin-bottom: 2px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 100%;
            }

            .job_card_company {
                font-size: 13px;
                color: #666;
                margin-bottom: 2px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 100%;
            }

            .job_card_salary {
                font-size: 13px;
                color: #ff7043;
                font-weight: 500;
                margin-bottom: 2px;
            }

            .job_card_location {
                font-size: 13px;
                color: #888;
                margin-bottom: 2px;
            }

            .job_card_hot {
                position: absolute;
                top: 8px;
                right: 8px;
                background: #ffe5e0;
                color: #ff7043;
                font-size: 11px;
                font-weight: 600;
                border-radius: 8px;
                padding: 1px 7px 1px 4px;
                display: flex;
                align-items: center;
                gap: 2px;
            }

            .job_card_hot i {
                font-size: 13px;
                color: #ff7043;
                margin-right: 2px;
            }

            @media (max-width: 1200px) {
                .job_listing_grid {
                    grid-template-columns: repeat(2, 1fr);
                    grid-template-rows: repeat(4, 1fr);
                }
            }

            @media (max-width: 768px) {
                .job_listing_grid {
                    grid-template-columns: 1fr;
                    grid-template-rows: repeat(8, 1fr);
                }
            }

            .single_jobs.card {
                border: 1px solid #e0e0e0;
                border-radius: 12px;
                padding: 20px;
                transition: box-shadow 0.3s ease;
                margin-bottom: 24px;
                height: 100%;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .single_jobs.card:hover {
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            }

            .single_jobs img {
                max-width: 50px;
                margin-bottom: 10px;
            }

            .single_jobs h4 {
                font-size: 18px;
                margin-bottom: 10px;
            }

            .pagination_area {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 24px;
                gap: 24px;
            }

            .pagination_area .btn-arrow {
                background: #f2f4f8;
                color: #1976d2;
                border: none;
                border-radius: 50%;
                width: 44px;
                height: 44px;
                font-size: 22px;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: background 0.2s, color 0.2s;
                cursor: pointer;
            }

            .pagination_area .btn-arrow:hover {
                background: #e3eaf5;
                color: #1565c0;
            }

            /* Ghi đè màu nút Bootstrap nếu muốn */
            .btn {
                background-color: #00D363;
                color: #fff;
                border-radius: 10px;
            }

            /* Responsive fix cho grid */
            @media (max-width: 991.98px) {

                .job_listing_container,
                .search_container,
                .popular_search {
                    max-width: 100%;
                    margin-left: 8px;
                    margin-right: 8px;
                }

                .single_jobs.card {
                    padding: 12px;
                }
            }

            /* Phần Ngành Nghề Trọng Điểm */
            .hot_industry_section {
                max-width: 1200px;
                margin: 32px auto 32px auto;
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 2px 12px rgba(44, 62, 80, 0.08);
                border: 1px solid #e6eaf0;
                overflow: hidden;
                box-sizing: border-box;
                padding: 24px 18px;
            }

            .hot_industry_header {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 18px;
            }

            .hot_industry_title {
                font-size: 22px;
                font-weight: bold;
                color: #222;
            }

            .hot_industry_viewall {
                color: #1976d2;
                font-weight: 500;
                font-size: 16px;
                text-decoration: none;
                transition: color 0.2s;
            }

            .hot_industry_viewall:hover {
                color: #1565c0;
                text-decoration: underline;
            }

            .hot_industry_nav {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 0;
                gap: 0;
            }

            .hot_industry_nav .hot_industry_arrow {
                flex-shrink: 0;
            }

            .hot_industry_nav .hot_industry_grid {
                flex: 1;
                margin: 0 16px;
            }

            .hot_industry_arrow {
                background: #f2f4f8;
                color: #b0b8c9;
                border: none;
                border-radius: 50%;
                width: 36px;
                height: 36px;
                font-size: 18px;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                transition: background 0.2s, color 0.2s;
            }

            .hot_industry_arrow:hover {
                background: #e3eaf5;
                color: #1976d2;
            }

            .hot_industry_grid {
                display: grid;
                grid-template-columns: repeat(5, 1fr);
                gap: 16px;
                align-items: stretch;
                width: 100%;
                min-width: 0;
                box-sizing: border-box;
            }

            .hot_industry_card {
                background: #fff;
                border: 1px solid #e6eaf0;
                border-radius: 12px;
                padding: 18px 10px 14px 10px;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-height: 120px;
                position: relative;
                box-shadow: 0 1px 4px rgba(44, 62, 80, 0.03);
                gap: 8px;
                box-sizing: border-box;
                min-width: 0;
                transition: box-shadow 0.2s;
            }

            .hot_industry_card:hover {
                box-shadow: 0 6px 20px rgba(44, 62, 80, 0.10);
                border-color: #1976d2;
            }

            .hot_industry_icon {
                width: 38px;
                height: 38px;
                margin-bottom: 8px;
                margin-top: 2px;
            }

            .hot_industry_icon img {
                width: 100%;
                height: 100%;
                object-fit: contain;
            }

            .hot_industry_name {
                font-size: 15px;
                font-weight: bold;
                color: #222;
                text-align: center;
                margin-bottom: 2px;
                margin-top: 0;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 100%;
            }

            .hot_industry_jobs {
                font-size: 13px;
                color: #7a8ca7;
                text-align: center;
                margin-bottom: 2px;
            }

            @media (max-width: 1200px) {
                .hot_industry_grid {
                    grid-template-columns: repeat(3, 1fr);
                }
            }

            @media (max-width: 991.98px) {
                .hot_industry_grid {
                    grid-template-columns: repeat(2, 1fr);
                }

                .hot_industry_card {
                    min-width: 120px;
                    max-width: 100%;
                    padding: 14px 6px 10px 6px;
                }
            }

            @media (max-width: 768px) {
                .hot_industry_grid {
                    grid-template-columns: 1fr;
                }

                .hot_industry_card {
                    min-width: 100px;
                    max-width: 100%;
                }
            }

            @media (max-width: 768px) {
                .search_bar {
                    flex-direction: column;
                }

                .search_inputs {
                    flex-direction: column;
                    gap: 0;
                }

                .search_inputs select,
                .search_buttons {
                    display: none !important;
                }

                .search_inputs input {
                    border-radius: 10px !important;
                }
            }

            .search_inputs select {
                appearance: none;
                -webkit-appearance: none;
                -moz-appearance: none;
                background: #f2f4f8 url("data:image/svg+xml,%3Csvg width='16' height='16' fill='gray' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M4 6l4 4 4-4' stroke='%237a8ca7' stroke-width='2' fill='none'/%3E%3C/svg%3E") no-repeat right 16px center/16px 16px;
                border: none;
                border-radius: 0;
                padding: 13px 40px 13px 16px;
                font-size: 15px;
                color: #222;
                transition: background 0.2s, box-shadow 0.2s;
                box-shadow: none;
                outline: none;
                cursor: pointer;
            }

            .search_inputs select:focus {
                background: #e9eef6 url("data:image/svg+xml,%3Csvg width='16' height='16' fill='gray' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M4 6l4 4 4-4' stroke='%2300D363' stroke-width='2' fill='none'/%3E%3C/svg%3E") no-repeat right 16px center/16px 16px;
                outline: none;
                box-shadow: 0 0 0 2px #00D36322;
            }

            .search_inputs select option {
                color: #222;
                background: #fff;
                font-size: 15px;
            }

            .search_container,
            .job_listing_container {
                max-width: 1200px;
            }

            /* Advanced Filter Modal */
            .advanced_filter_modal {
                display: none;
                position: fixed;
                z-index: 9999;
                left: 0;
                top: 0;
                width: 100vw;
                height: 100vh;
                background: rgba(44, 62, 80, 0.18);
                align-items: center;
                justify-content: center;
            }

            .advanced_filter_modal.active {
                display: flex;
            }

            .advanced_filter_box {
                background: #fff;
                border-radius: 16px;
                box-shadow: 0 6px 32px rgba(44, 62, 80, 0.15);
                padding: 32px 28px 24px 28px;
                min-width: 340px;
                max-width: 96vw;
                width: 400px;
                position: relative;
                animation: fadeInModal 0.2s;
            }

            @keyframes fadeInModal {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }

                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .advanced_filter_box h3 {
                font-size: 20px;
                font-weight: bold;
                margin-bottom: 18px;
                color: #222;
            }

            .advanced_filter_close {
                position: absolute;
                top: 18px;
                right: 18px;
                background: none;
                border: none;
                font-size: 22px;
                color: #888;
                cursor: pointer;
                transition: color 0.2s;
            }

            .advanced_filter_close:hover {
                color: #1976d2;
            }

            .advanced_filter_group {
                margin-bottom: 18px;
            }

            .advanced_filter_group label {
                display: block;
                font-size: 15px;
                font-weight: 500;
                margin-bottom: 7px;
                color: #444;
            }

            .advanced_filter_group input,
            .advanced_filter_group select {
                width: 100%;
                padding: 11px 14px;
                border-radius: 8px;
                border: 1px solid #e6eaf0;
                background: #f2f4f8;
                font-size: 15px;
                margin-bottom: 0;
                transition: border 0.2s;
            }

            .advanced_filter_group input:focus,
            .advanced_filter_group select:focus {
                border: 1.5px solid #1976d2;
                outline: none;
                background: #e9eef6;
            }

            .advanced_filter_actions {
                display: flex;
                justify-content: flex-end;
                gap: 12px;
                margin-top: 18px;
            }

            .advanced_filter_btn {
                background: #00D363;
                color: #fff;
                border: none;
                border-radius: 8px;
                padding: 10px 24px;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.2s;
            }

            .advanced_filter_btn:hover {
                background: #1976d2;
            }

            .advanced_filter_btn.cancel {
                background: #f2f4f8;
                color: #1976d2;
                border: 1px solid #e6eaf0;
            }

            .advanced_filter_btn.cancel:hover {
                background: #e3eaf5;
                color: #1565c0;
            }
        </style>
    </head>

    <body>
        <!-- header-start -->
        <header>
            <div class="header-area ">
                <div id="sticky-header" class="main-header-area">
                    <div class="container-fluid ">
                        <div class="header_bottom_border">
                            <div class="row align-items-center">
                                <div class="col-xl-3 col-lg-2">
                                    <div class="logo">
                                        <a href="index.jsp">
                                            <img src="img/logo.png" alt="">
                                        </a>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-7">
                                    <div class="main-menu  d-none d-lg-block">
                                        <nav>
                                            <ul id="navigation">
                                                <li><a href="index.jsp">home</a></li>
                                                <li><a href="jobs.html">Browse Job</a></li>
                                                <li><a href="#">pages <i class="ti-angle-down"></i></a>
                                                    <ul class="submenu">
                                                        <li><a href="candidate.html">Candidates </a></li>
                                                        <li><a href="job_details.html">job details </a></li>
                                                        <li><a href="elements.html">elements</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="#">blog <i class="ti-angle-down"></i></a>
                                                    <ul class="submenu">
                                                        <li><a href="blog.html">blog</a></li>
                                                        <li><a href="single-blog.html">single-blog</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="contact.html">Contact</a></li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 d-none d-lg-block">
                                    <div class="Appointment">
                                        <div class="phone_num d-none d-xl-block">
                                            <a href="login.jsp">Login</a>
                                        </div>
                                        <div class="d-none d-lg-block">
                                            <a class="boxed-btn3" href="#">Post a Job</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="mobile_menu d-block d-lg-none"></div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </header>
        <!-- header-end -->

        <!-- slider_area_start -->
        <div class="slider_area">
            <div class="single_slider  d-flex align-items-center slider_bg_1">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-7 col-md-6">
                            <div class="slider_text">
                                <h5 class="wow fadeInLeft" data-wow-duration="1s" data-wow-delay=".2s">4536+ Jobs listed
                                </h5>
                                <h3 class="wow fadeInLeft" data-wow-duration="1s" data-wow-delay=".3s">Find your Dream Job
                                </h3>
                                <p class="wow fadeInLeft" data-wow-duration="1s" data-wow-delay=".4s">We provide online
                                    instant cash loans with quick approval that suit your term length</p>
                                <div class="sldier_btn wow fadeInLeft" data-wow-duration="1s" data-wow-delay=".5s">
                                    <a href="#" class="boxed-btn3">Upload your Resume</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ilstration_img wow fadeInRight d-none d-lg-block text-right" data-wow-duration="1s"
                 data-wow-delay=".2s">
                <img src="img/banner/illustration.png" alt="">
            </div>
        </div>
        <!-- slider_area_end -->

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
                    <div class="job_listing_title">Job Listing</div>
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
        <!-- footer start -->
        <footer class="footer">
            <div class="footer_top">
                <div class="container">
                    <div class="row">
                        <div class="col-xl-3 col-md-6 col-lg-3">
                            <div class="footer_widget wow fadeInUp" data-wow-duration="1s" data-wow-delay=".3s">
                                <div class="footer_logo">
                                    <a href="#">
                                        <img src="img/logo.png" alt="">
                                    </a>
                                </div>
                                <p>
                                    finloan@support.com <br>
                                    +10 873 672 6782 <br>
                                    600/D, Green road, NewYork
                                </p>
                                <div class="socail_links">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <i class="ti-facebook"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="fa fa-google-plus"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="fa fa-twitter"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="fa fa-instagram"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="col-xl-2 col-md-6 col-lg-2">
                            <div class="footer_widget wow fadeInUp" data-wow-duration="1.1s" data-wow-delay=".4s">
                                <h3 class="footer_title">
                                    Company
                                </h3>
                                <ul>
                                    <li><a href="#">About </a></li>
                                    <li><a href="#"> Pricing</a></li>
                                    <li><a href="#">Carrier Tips</a></li>
                                    <li><a href="#">FAQ</a></li>
                                </ul>

                            </div>
                        </div>
                        <div class="col-xl-3 col-md-6 col-lg-3">
                            <div class="footer_widget wow fadeInUp" data-wow-duration="1.2s" data-wow-delay=".5s">
                                <h3 class="footer_title">
                                    Category
                                </h3>
                                <ul>
                                    <li><a href="#">Design & Art</a></li>
                                    <li><a href="#">Engineering</a></li>
                                    <li><a href="#">Sales & Marketing</a></li>
                                    <li><a href="#">Finance</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-xl-4 col-md-6 col-lg-4">
                            <div class="footer_widget wow fadeInUp" data-wow-duration="1.3s" data-wow-delay=".6s">
                                <h3 class="footer_title">
                                    Subscribe
                                </h3>
                                <form action="#" class="newsletter_form">
                                    <input type="text" placeholder="Enter your mail">
                                    <button type="submit">Subscribe</button>
                                </form>
                                <p class="newsletter_text">Esteem spirit temper too say adieus who direct esteem esteems
                                    luckily.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="copy-right_text wow fadeInUp" data-wow-duration="1.4s" data-wow-delay=".3s">
                <div class="container">
                    <div class="footer_border"></div>
                    <div class="row">
                        <div class="col-xl-12">
                            <p class="copy_right text-center">
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                Copyright &copy;
                                <script>document.write(new Date().getFullYear());</script> All rights reserved | This
                                template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a
                                    href="https://colorlib.com" target="_blank">Colorlib</a>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!--/ footer end  -->

        <!-- link that opens popup -->
        <!-- JS here -->
        <script src="js/vendor/modernizr-3.5.0.min.js"></script>
        <script src="js/vendor/jquery-1.12.4.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/isotope.pkgd.min.js"></script>
        <script src="js/ajax-form.js"></script>
        <script src="js/waypoints.min.js"></script>
        <script src="js/jquery.counterup.min.js"></script>
        <script src="js/imagesloaded.pkgd.min.js"></script>
        <script src="js/scrollIt.js"></script>
        <script src="js/jquery.scrollUp.min.js"></script>
        <script src="js/wow.min.js"></script>
        <script src="js/nice-select.min.js"></script>
        <script src="js/jquery.slicknav.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/plugins.js"></script>
        <script src="js/gijgo.min.js"></script>

        <!--contact js-->
        <script src="js/contact.js"></script>
        <script src="js/jquery.ajaxchimp.min.js"></script>
        <script src="js/jquery.form.js"></script>
        <script src="js/jquery.validate.min.js"></script>
        <script src="js/mail-script.js"></script>

        <script src="js/main.js"></script>
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
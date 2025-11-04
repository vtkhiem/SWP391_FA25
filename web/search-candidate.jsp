<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Xem hồ sơ ứng viên - Job Board</title>
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
        
        <style>
            .cv_view_area {
                padding: 60px 0;
                background: #f8f9fa;
            }
            
            .cv_container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 15px;
            }
            
            .cv_header_card {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 30px;
                margin-bottom: 30px;
            }
            
            .cv_header_content {
                display: flex;
                gap: 30px;
                align-items: flex-start;
            }
            
            .cv_avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                border: 4px solid #00d363;
                flex-shrink: 0;
            }
            
            .cv_header_info {
                flex: 1;
            }
            
            .cv_candidate_name {
                font-size: 32px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 10px;
            }
            
            .cv_candidate_title {
                font-size: 18px;
                color: #00d363;
                font-weight: 600;
                margin-bottom: 15px;
            }
            
            .cv_quick_info {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 15px;
                margin-bottom: 20px;
            }
            
            .cv_quick_item {
                display: flex;
                align-items: center;
                gap: 10px;
                color: #555;
                font-size: 14px;
            }
            
            .cv_quick_item i {
                color: #00d363;
                font-size: 16px;
            }
            
            .cv_header_actions {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
                margin-top: 20px;
            }
            
            .action_btn {
                padding: 10px 20px;
                border-radius: 5px;
                border: none;
                cursor: pointer;
                font-size: 14px;
                font-weight: 600;
                transition: all 0.3s ease;
                text-decoration: none;
                display: inline-flex;
                align-items: center;
                gap: 8px;
            }
            
            .btn_download {
                background: #00d363;
                color: white;
            }
            
            .btn_download:hover {
                background: #00b854;
                color: white;
            }
            
            .btn_contact {
                background: #3498db;
                color: white;
            }
            
            .btn_contact:hover {
                background: #2980b9;
                color: white;
            }
            
            .btn_shortlist {
                background: #f39c12;
                color: white;
            }
            
            .btn_shortlist:hover {
                background: #e67e22;
                color: white;
            }
            
            .btn_reject {
                background: #e74c3c;
                color: white;
            }
            
            .btn_reject:hover {
                background: #c0392b;
                color: white;
            }
            
            .cv_content_layout {
                display: grid;
                grid-template-columns: 1fr 350px;
                gap: 30px;
            }
            
            .cv_main_content {
                display: flex;
                flex-direction: column;
                gap: 20px;
            }
            
            .cv_section_card {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 25px;
            }
            
            .cv_section_title {
                font-size: 20px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 20px;
                padding-bottom: 10px;
                border-bottom: 2px solid #00d363;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            
            .cv_section_title i {
                color: #00d363;
                font-size: 22px;
            }
            
            .cv_objective_text {
                color: #555;
                font-size: 15px;
                line-height: 1.8;
                text-align: justify;
            }
            
            .cv_experience_item {
                margin-bottom: 25px;
                padding-bottom: 25px;
                border-bottom: 1px solid #eee;
            }
            
            .cv_experience_item:last-child {
                border-bottom: none;
                margin-bottom: 0;
                padding-bottom: 0;
            }
            
            .cv_experience_header {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 10px;
                flex-wrap: wrap;
                gap: 10px;
            }
            
            .cv_experience_title {
                font-size: 18px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 5px;
            }
            
            .cv_experience_company {
                color: #00d363;
                font-size: 15px;
                font-weight: 600;
            }
            
            .cv_experience_duration {
                color: #7e8d9f;
                font-size: 14px;
                white-space: nowrap;
                background: #f8f9fa;
                padding: 5px 12px;
                border-radius: 5px;
            }
            
            .cv_experience_description {
                color: #555;
                font-size: 14px;
                line-height: 1.7;
                margin-top: 10px;
            }
            
            .cv_experience_description ul {
                margin: 10px 0;
                padding-left: 20px;
            }
            
            .cv_experience_description li {
                margin-bottom: 8px;
            }
            
            .cv_education_item {
                margin-bottom: 20px;
                padding-bottom: 20px;
                border-bottom: 1px solid #eee;
            }
            
            .cv_education_item:last-child {
                border-bottom: none;
                margin-bottom: 0;
                padding-bottom: 0;
            }
            
            .cv_education_degree {
                font-size: 18px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 5px;
            }
            
            .cv_education_school {
                color: #00d363;
                font-size: 15px;
                font-weight: 600;
                margin-bottom: 5px;
            }
            
            .cv_education_info {
                color: #7e8d9f;
                font-size: 14px;
                display: flex;
                gap: 15px;
                flex-wrap: wrap;
            }
            
            .cv_skills_grid {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }
            
            .cv_skill_tag {
                padding: 10px 18px;
                background: #e8f5e9;
                color: #2e7d32;
                border-radius: 25px;
                font-size: 14px;
                font-weight: 600;
            }
            
            .cv_sidebar {
                display: flex;
                flex-direction: column;
                gap: 20px;
            }
            
            .cv_sidebar_card {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 20px;
            }
            
            .cv_sidebar_title {
                font-size: 16px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 15px;
                padding-bottom: 10px;
                border-bottom: 2px solid #00d363;
            }
            
            .cv_info_list {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }
            
            .cv_info_item {
                display: flex;
                flex-direction: column;
                gap: 5px;
            }
            
            .cv_info_label {
                font-size: 12px;
                color: #7e8d9f;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }
            
            .cv_info_value {
                font-size: 14px;
                color: #2c3e50;
                font-weight: 600;
            }
            
            .cv_language_item {
                margin-bottom: 15px;
            }
            
            .cv_language_item:last-child {
                margin-bottom: 0;
            }
            
            .cv_language_name {
                font-size: 14px;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 5px;
            }
            
            .cv_language_level {
                display: flex;
                align-items: center;
                gap: 10px;
            }
            
            .language_progress {
                flex: 1;
                height: 8px;
                background: #e0e0e0;
                border-radius: 10px;
                overflow: hidden;
            }
            
            .language_progress_bar {
                height: 100%;
                background: #00d363;
                border-radius: 10px;
                transition: width 0.3s ease;
            }
            
            .language_level_text {
                font-size: 12px;
                color: #7e8d9f;
                white-space: nowrap;
            }
            
            .cv_certificate_item {
                margin-bottom: 15px;
                padding-bottom: 15px;
                border-bottom: 1px solid #eee;
            }
            
            .cv_certificate_item:last-child {
                border-bottom: none;
                margin-bottom: 0;
                padding-bottom: 0;
            }
            
            .cv_certificate_name {
                font-size: 14px;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 3px;
            }
            
            .cv_certificate_org {
                font-size: 13px;
                color: #00d363;
                margin-bottom: 3px;
            }
            
            .cv_certificate_year {
                font-size: 12px;
                color: #7e8d9f;
            }
            
            .cv_application_status {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 15px;
                border-radius: 8px;
                margin-bottom: 15px;
            }
            
            .cv_status_label {
                font-size: 12px;
                opacity: 0.9;
                margin-bottom: 5px;
            }
            
            .cv_status_value {
                font-size: 16px;
                font-weight: 700;
            }
            
            .cv_match_score {
                text-align: center;
                padding: 20px;
            }
            
            .cv_score_circle {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                margin: 0 auto 15px;
                color: white;
            }
            
            .cv_score_number {
                font-size: 36px;
                font-weight: 700;
            }
            
            .cv_score_label {
                font-size: 12px;
            }
            
            .cv_score_text {
                font-size: 13px;
                color: #7e8d9f;
                text-align: center;
            }
            
            @media (max-width: 1024px) {
                .cv_content_layout {
                    grid-template-columns: 1fr;
                }
                
                .cv_sidebar {
                    order: -1;
                }
            }
            
            @media (max-width: 768px) {
                .cv_header_content {
                    flex-direction: column;
                    align-items: center;
                    text-align: center;
                }
                
                .cv_quick_info {
                    grid-template-columns: 1fr;
                }
                
                .cv_header_actions {
                    justify-content: center;
                }
                
                .cv_candidate_name {
                    font-size: 24px;
                }
            }
        </style>
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="header.jsp"/>
        <!-- header_end -->

        <div class="main-content">
            <div class="cv_view_area">
                <div class="cv_container">
                    <!-- Header Card -->
                    <div class="cv_header_card">
                        <div class="cv_header_content">
                            <img src="img/avatar-default.jpg" alt="Candidate" class="cv_avatar">
                            <div class="cv_header_info">
                                <h1 class="cv_candidate_name">Nguyễn Văn An</h1>
                                <div class="cv_candidate_title">Senior Java Developer</div>
                                
                                <div class="cv_quick_info">
                                    <div class="cv_quick_item">
                                        <i class="ti-email"></i>
                                        <span>nguyenvanan@email.com</span>
                                    </div>
                                    <div class="cv_quick_item">
                                        <i class="ti-mobile"></i>
                                        <span>0912 345 678</span>
                                    </div>
                                    <div class="cv_quick_item">
                                        <i class="ti-location-pin"></i>
                                        <span>Hà Nội, Việt Nam</span>
                                    </div>
                                    <div class="cv_quick_item">
                                        <i class="ti-calendar"></i>
                                        <span>5 năm kinh nghiệm</span>
                                    </div>
                                </div>
                                
                                <div class="cv_header_actions">
                                    <button class="action_btn btn_download">
                                        <i class="ti-download"></i> Tải CV
                                    </button>
                                    <button class="action_btn btn_contact">
                                        <i class="ti-email"></i> Liên hệ
                                    </button>
                                    <button class="action_btn btn_shortlist">
                                        <i class="ti-star"></i> Lưu ứng viên
                                    </button>
                                    <button class="action_btn btn_reject">
                                        <i class="ti-close"></i> Từ chối
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Content Layout -->
                    <div class="cv_content_layout">
                        <!-- Main Content -->
                        <div class="cv_main_content">
                            <!-- Career Objective -->
                            <div class="cv_section_card">
                                <div class="cv_section_title">
                                    <i class="ti-target"></i>
                                    Mục tiêu nghề nghiệp
                                </div>
                                <div class="cv_objective_text">
                                    Là một lập trình viên Java có 5 năm kinh nghiệm, tôi mong muốn phát triển sự nghiệp 
                                    trong lĩnh vực phát triển phần mềm tại một công ty công nghệ hàng đầu. Tôi có khả năng 
                                    làm việc độc lập và làm việc nhóm tốt, luôn học hỏi và cập nhật những công nghệ mới nhất. 
                                    Mục tiêu của tôi là trở thành một Kỹ sư phần mềm chuyên nghiệp, đóng góp vào sự thành 
                                    công của công ty thông qua các giải pháp công nghệ sáng tạo.
                                </div>
                            </div>

                            <!-- Work Experience -->
                            <div class="cv_section_card">
                                <div class="cv_section_title">
                                    <i class="ti-briefcase"></i>
                                    Kinh nghiệm làm việc
                                </div>
                                
                                <div class="cv_experience_item">
                                    <div class="cv_experience_header">
                                        <div>
                                            <div class="cv_experience_title">Senior Java Developer</div>
                                            <div class="cv_experience_company">FPT Software</div>
                                        </div>
                                        <div class="cv_experience_duration">01/2022 - Hiện tại</div>
                                    </div>
                                    <div class="cv_experience_description">
                                        <ul>
                                            <li>Phát triển và bảo trì các ứng dụng web enterprise sử dụng Java Spring Boot, xử lý hơn 100,000 requests/ngày</li>
                                            <li>Thiết kế và triển khai RESTful APIs cho hệ thống microservices phục vụ 50+ clients</li>
                                            <li>Tối ưu hóa hiệu suất cơ sở dữ liệu MySQL và PostgreSQL, giảm thời gian query xuống 40%</li>
                                            <li>Làm việc với Docker, Kubernetes để triển khai và quản lý ứng dụng trên môi trường cloud</li>
                                            <li>Hướng dẫn và review code cho 3 thành viên junior, cải thiện chất lượng code của team</li>
                                            <li>Tham gia các cuộc họp với khách hàng để thu thập yêu cầu và đưa ra giải pháp kỹ thuật</li>
                                        </ul>
                                    </div>
                                </div>
                                
                                <div class="cv_experience_item">
                                    <div class="cv_experience_header">
                                        <div>
                                            <div class="cv_experience_title">Java Developer</div>
                                            <div class="cv_experience_company">Viettel Solutions</div>
                                        </div>
                                        <div class="cv_experience_duration">03/2020 - 12/2021</div>
                                    </div>
                                    <div class="cv_experience_description">
                                        <ul>
                                            <li>Phát triển các tính năng mới cho hệ thống CRM phục vụ 200,000+ khách hàng</li>
                                            <li>Viết unit test và integration test đạt coverage 85%, đảm bảo chất lượng code</li>
                                            <li>Tham gia vào quy trình Agile/Scrum, hoàn thành 95% sprint goals đúng thời hạn</li>
                                            <li>Sửa lỗi và cải thiện hiệu suất của các module hiện có, giảm bug rate 30%</li>
                                            <li>Tích hợp các API của bên thứ ba (payment gateway, SMS service)</li>
                                        </ul>
                                    </div>
                                </div>
                                
                                <div class="cv_experience_item">
                                    <div class="cv_experience_header">
                                        <div>
                                            <div class="cv_experience_title">Junior Java Developer</div>
                                            <div class="cv_experience_company">CMC Corporation</div>
                                        </div>
                                        <div class="cv_experience_duration">06/2019 - 02/2020</div>
                                    </div>
                                    <div class="cv_experience_description">
                                        <ul>
                                            <li>Học hỏi và áp dụng Java Spring Framework trong các dự án thực tế</li>
                                            <li>Hỗ trợ team trong việc phát triển các chức năng backend cho ứng dụng quản lý nội bộ</li>
                                            <li>Viết tài liệu kỹ thuật API documentation sử dụng Swagger</li>
                                            <li>Tham gia testing và báo cáo bugs thông qua hệ thống JIRA</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <!-- Education -->
                            <div class="cv_section_card">
                                <div class="cv_section_title">
                                    <i class="ti-book"></i>
                                    Học vấn
                                </div>
                                
                                <div class="cv_education_item">
                                    <div class="cv_education_degree">Cử nhân Khoa học Máy tính</div>
                                    <div class="cv_education_school">Đại học Bách Khoa Hà Nội</div>
                                    <div class="cv_education_info">
                                        <span><i class="ti-calendar"></i> 2015 - 2019</span>
                                        <span><i class="ti-medall"></i> GPA: 3.6/4.0</span>
                                        <span><i class="ti-star"></i> Giỏi</span>
                                    </div>
                                </div>
                                
                                <div class="cv_education_item">
                                    <div class="cv_education_degree">Tốt nghiệp THPT</div>
                                    <div class="cv_education_school">THPT Chu Văn An</div>
                                    <div class="cv_education_info">
                                        <span><i class="ti-calendar"></i> 2012 - 2015</span>
                                        <span><i class="ti-star"></i> Xuất sắc</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Skills -->
                            <div class="cv_section_card">
                                <div class="cv_section_title">
                                    <i class="ti-cup"></i>
                                    Kỹ năng
                                </div>
                                <div class="cv_skills_grid">
                                    <span class="cv_skill_tag">Java</span>
                                    <span class="cv_skill_tag">Spring Boot</span>
                                    <span class="cv_skill_tag">Spring MVC</span>
                                    <span class="cv_skill_tag">Hibernate</span>
                                    <span class="cv_skill_tag">MySQL</span>
                                    <span class="cv_skill_tag">PostgreSQL</span>
                                    <span class="cv_skill_tag">MongoDB</span>
                                    <span class="cv_skill_tag">Docker</span>
                                    <span class="cv_skill_tag">Kubernetes</span>
                                    <span class="cv_skill_tag">RESTful API</span>
                                    <span class="cv_skill_tag">Microservices</span>
                                    <span class="cv_skill_tag">Git</span>
                                    <span class="cv_skill_tag">Jenkins</span>
                                    <span class="cv_skill_tag">JUnit</span>
                                    <span class="cv_skill_tag">Mockito</span>
                                    <span class="cv_skill_tag">Agile/Scrum</span>
                                </div>
                            </div>
                        </div>

                        <!-- Sidebar -->
                        <div class="cv_sidebar">
                            <!-- Application Status -->
                            <div class="cv_sidebar_card">
                                <div class="cv_application_status">
                                    <div class="cv_status_label">Trạng thái ứng tuyển</div>
                                    <div class="cv_status_value">Đang xem xét</div>
                                </div>
                                <div class="cv_info_list">
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Vị trí ứng tuyển</span>
                                        <span class="cv_info_value">Senior Java Developer</span>
                                    </div>
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Ngày ứng tuyển</span>
                                        <span class="cv_info_value">25/10/2025</span>
                                    </div>
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Mức lương mong muốn</span>
                                        <span class="cv_info_value">25-35 triệu</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Match Score -->
                            <div class="cv_sidebar_card">
                                <div class="cv_sidebar_title">Độ phù hợp</div>
                                <div class="cv_match_score">
                                    <div class="cv_score_circle">
                                        <div class="cv_score_number">85%</div>
                                        <div class="cv_score_label">PHÙ HỢP</div>
                                    </div>
                                    <div class="cv_score_text">
                                        Ứng viên có kỹ năng và kinh nghiệm phù hợp cao với yêu cầu công việc
                                    </div>
                                </div>
                            </div>

                            <!-- Personal Info -->
                            <div class="cv_sidebar_card">
                                <div class="cv_sidebar_title">Thông tin cá nhân</div>
                                <div class="cv_info_list">
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Ngày sinh</span>
                                        <span class="cv_info_value">15/05/1995 (29 tuổi)</span>
                                    </div>
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Giới tính</span>
                                        <span class="cv_info_value">Nam</span>
                                    </div>
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Tình trạng hôn nhân</span>
                                        <span class="cv_info_value">Độc thân</span>
                                    </div>
                                    <div class="cv_info_item">
                                        <span class="cv_info_label">Địa chỉ</span>
                                        <span class="cv_info_value">Quận Cầu Giấy, Hà Nội</span>
                                    </div>
                                </div>
                            </div>

                            <!-- Languages -->
                            <div class="cv_sidebar_card">
                                <div class="cv_sidebar_title">Ngoại ngữ</div>
                                <div class="cv_language_item">
                                    <div class="cv_language_name">Tiếng Anh</div>
                                    <div class="cv_language_level">
                                        <div class="language_progress">
                                            <div class="language_progress_bar" style="width: 85%"></div>
                                        </div>
                                        <span class="language_level_text">Khá</span>
                                    </div>
                                </div>
                                <div class="cv_language_item">
                                    <div class="cv_language_name">Tiếng Nhật</div>
                                    <div class="cv_language_level">
                                        <div class="language_progress">
                                            <div class="language_progress_bar" style="width: 50%
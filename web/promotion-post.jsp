<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Promotions & Discounts - RecruiterHub</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
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
        
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f8faff;
                line-height: 1.6;
            }

            /* Hero Section */
            .hero-section {
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                padding: 4rem 0;
                text-align: center;
                color: white;
                margin-bottom: 3rem;
            }

            .hero-title {
                font-size: 3rem;
                margin-bottom: 1rem;
                font-weight: bold;
                color: white;
            }

            .hero-subtitle {
                font-size: 1.2rem;
                opacity: 0.9;
                color: white;
            }


            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            .main-container {
                margin-top: 100px; /* chỉnh số px tùy menu cao bao nhiêu */
            }
            /* Filter Section */
            .filter-section {
                background: white;
                padding: 2rem;
                border-radius: 15px;
                margin-bottom: 2rem;
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
            }

            .filter-buttons {
                display: flex;
                gap: 1rem;
                flex-wrap: wrap;
            }

            .filter-btn {
                padding: 0.7rem 1.5rem;
                border: 2px solid #e8f2ff;
                background: white;
                border-radius: 25px;
                color: #337ab7;
                cursor: pointer;
                transition: all 0.3s ease;
                text-decoration: none;
            }

            .filter-btn.active,
            .filter-btn:hover {
                background: #1489f1;
                color: white;
                border-color: #1489f1;
            }

            /* Promotion Cards Grid */
            .promotions-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
                gap: 2rem;
                margin-bottom: 3rem;
            }

            /* Promotion Card */
            .promo-card {
                background: white;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
                position: relative;
            }

            .promo-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
            }

            .promo-card.priority-1,
            .promo-card.priority-2 {
                border: none;
            }

            .promo-badge {
                position: absolute;
                top: 15px;
                right: 15px;
                padding: 0.5rem 1rem;
                border-radius: 20px;
                font-size: 0.85rem;
                font-weight: bold;
                z-index: 10;
            }

            .badge-hot {
                background: #ff4757;
                color: white;
                animation: pulse 2s infinite;
            }

            .badge-active {
                background: #2ecc71;
                color: white;
            }

            .badge-new {
                background: #1489f1;
                color: white;
            }

            @keyframes pulse {
                0%, 100% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.05);
                }
            }

            .promo-image {
                width: 100%;
                height: 200px;
                object-fit: cover;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            }

            .promo-content {
                padding: 1.5rem;
            }

            .promo-title {
                font-size: 1.4rem;
                color: #333;
                margin-bottom: 0.8rem;
                font-weight: 600;
            }

            .promo-description {
                color: #666;
                margin-bottom: 1.5rem;
                font-size: 0.95rem;
                line-height: 1.6;
            }

            /* Discount Code Box */
            .discount-code-box {
                background: linear-gradient(135deg, #fff5e6 0%, #ffe6cc 100%);
                border: 2px dashed #ff9800;
                border-radius: 10px;
                padding: 1rem;
                margin-bottom: 1.5rem;
                text-align: center;
            }

            .discount-label {
                font-size: 0.85rem;
                color: #666;
                margin-bottom: 0.3rem;
            }

            .discount-code {
                font-size: 1.5rem;
                font-weight: bold;
                color: #ff9800;
                letter-spacing: 2px;
                font-family: 'Courier New', monospace;
            }

            .copy-code-btn {
                background: #ff9800;
                color: white;
                border: none;
                padding: 0.5rem 1.5rem;
                border-radius: 20px;
                margin-top: 0.5rem;
                cursor: pointer;
                font-weight: bold;
                transition: all 0.3s ease;
            }

            .copy-code-btn:hover {
                background: #f57c00;
                transform: scale(1.05);
            }

            /* Date Info */
            .promo-dates {
                display: flex;
                justify-content: space-between;
                padding: 1rem;
                background: #f8faff;
                border-top: 1px solid #e8f2ff;
            }

            .date-item {
                text-align: center;
            }

            .date-label {
                font-size: 0.75rem;
                color: #999;
                text-transform: uppercase;
            }

            .date-value {
                font-size: 0.9rem;
                color: #333;
                font-weight: 600;
            }

            .expired-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.7);
                display: flex;
                align-items: center;
                justify-content: center;
                z-index: 100;
            }

            .expired-text {
                background: #ff4757;
                color: white;
                padding: 1rem 2rem;
                border-radius: 30px;
                font-weight: bold;
                font-size: 1.2rem;
            }

            /* Service Type Badge */
            .service-badge {
                display: inline-block;
                padding: 0.4rem 1rem;
                background: #e8f2ff;
                color: #1489f1;
                border-radius: 20px;
                font-size: 0.85rem;
                margin-bottom: 1rem;
            }

            /* Empty State */
            .empty-state {
                text-align: center;
                padding: 4rem 2rem;
                background: white;
                border-radius: 15px;
                box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
                margin-top: 0;
                margin-bottom: 80px;
            }

            .empty-state h3 {
                color: #333;
                margin-bottom: 1rem;
            }

            .empty-state p {
                color: #666;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .hero-title {
                    font-size: 2rem;
                }

                .promotions-grid {
                    grid-template-columns: 1fr;
                }

                .header_bottom_border {
                    flex-direction: column;
                    gap: 1rem;
                }

                .main-menu ul {
                    flex-wrap: wrap;
                    gap: 1rem;
                }
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <div class="header-area">
            <div id="sticky-header" class="main-header-area">
                <div class="container-fluid ">
                    <div class="header_bottom_border">
                        <div class="row align-items-center">
                            <div class="col-xl-3 col-lg-2">
                                <div class="logo">
                                    <c:choose>
                                        <%-- Employer --%>
                                        <c:when test="${sessionScope.role eq 'Employer'}">
                                            <a href="employerServices">
                                                <img src="img/logo.png" alt="">
                                            </a>
                                        </c:when>

                                        <%-- Candidate/Guest --%>
                                        <c:otherwise>
                                            <a href="home">
                                                <img src="img/logo.png" alt="">
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-7">
                                <div class="main-menu d-none d-lg-block">
                                    <nav>
                                        <ul id="navigation">
                                            <c:choose>
                                                <%-- Employer --%>
                                                <c:when test="${sessionScope.role eq 'Employer'}">
                                                    <li><a href="employerServices">Trang chủ</a></li>
                                                    <li><a href="employerWall">DashBoard</a></li>
                                                    <li><a href="job_add">Đăng Công Việc</a></li>
                                                    <li><a href="employer_jobs">Xem Công Việc</a></li>
                                                    <li><a href="viewPublicCVs">Tìm ứng viên</a></li>
                                                    </c:when>

                                                <%-- Candidate/Guest --%>
                                                <c:otherwise>
                                                    <li><a href="login-employer.jsp">Đăng ký/ Đăng nhập cho nhà tuyển dụng</a></li>       
                                                </c:otherwise>
                                            </c:choose>

                                        </ul>
                                    </nav>
                                </div>
                            </div>
                            <div class="col-xl-3 col-lg-3 d-none d-lg-block">
                                <!-- Nếu đã login thì hiện tên user + Logout -->
                                <c:if test="${not empty sessionScope.user}">
                                    <div class="main-menu phone_num d-none d-xl-block">
                                        <nav>
                                            <ul id="navigation">
                                                <li>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.role eq 'Candidate'}"><a href="candidateProfile"><b style="color: white">Xin chào, ${sessionScope.user.candidateName}</b></a></c:when>
                                                        <c:when test="${sessionScope.role eq 'Employer'}"><a href="employerProfile"><b style="color: white">Xin chào, ${sessionScope.user.employerName}</b></a></c:when>
                                                        <c:otherwise>${sessionScope.user.username}</c:otherwise>
                                                    </c:choose>
                                                    <ul class="submenu">
                                                        <c:choose>
                                                            <c:when test="${sessionScope.role eq 'Candidate'}">
                                                                <li><a href="candidateProfile">Xem hồ sơ</a></li>
                                                                </c:when>
                                                                <c:when test="${sessionScope.role eq 'Employer'}">
                                                                <li><a href="employerProfile">Xem hồ sơ</a></li>
                                                                <li><a href="payments_history">Lịch sử giao dịch</a></li>
                                                                </c:when>
                                                            </c:choose>
                                                        <li><a href="logout">Đăng xuất</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="mobile_menu d-block d-lg-none"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Hero Section -->
        <div class="hero-section">
            <div class="container main-container">
                <h1 class="hero-title">Ưu đãi dành cho Nhà tuyển dụng khi Đăng ký</h1>
                <p class="hero-subtitle">Tiết kiệm chi phí khi chọn lựa những gói dịch vụ hấp dẫn với giá thành phù hợp</p>
            </div>
        </div>

        <!-- Main Content -->
        <div class="container">
            <!-- Filter Section -->
            <div class="filter-section">
                <div class="filter-buttons">
                    <a href="viewPromotionPosts" class="filter-btn">Tất cả</a>
                    <a href="filterPromotionPosts?isActive=1" class="filter-btn">Còn hiệu lực</a>
                    <a href="filterPromotionPosts?isActive=0" class="filter-btn">Các gói cũ</a>
                </div>
            </div>

            <!-- Promotions Grid -->
            <div class="promotions-grid">
                <!-- Sample Promotion Card 1 - Will be populated from database -->
                <c:forEach var="promo" items="${promotionPosts}">
                    <div class="promo-card priority-${promo.priorityLevel}" 
                         data-priority="${promo.priorityLevel}" 
                         data-active="${promo.isActive}">

                        <!-- Banner Image -->
                        <img src="${promo.bannerImage}" alt="${promo.title}" class="promo-image">

                        <!-- Content -->
                        <div class="promo-content">
                            <div class="service-badge">${promo.service}</div>
                            <h3 class="promo-title">${promo.title}</h3>
                            <p class="promo-description">${promo.content}</p>

                        </div>

                        <!-- Dates -->
                        <div class="promo-dates">
                            <div class="date-item">
                                <div class="date-label">Start Date</div>
                                <div class="date-value">
                                    <fmt:formatDate value="${promo.startDateFormatted}" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>
                            <div class="date-item">
                                <div class="date-label">End Date</div>
                                <div class="date-value">
                                    <fmt:formatDate value="${promo.endDateFormatted}" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>
                        </div>

                        <!-- Expired Overlay -->
                        <c:if test="${!promo.isActive}">
                            <div class="expired-overlay">
                                <div class="expired-text">HẾT HẠN</div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>

            <!-- Empty State (if no promotions) -->
            <c:if test="${empty promotionPosts}">
                <div class="empty-state">
                    <h3>Hiện không có gói cước nào</h3>
                    <p>Bạn hãy quay lại sau nhé!</p>
                </div>
            </c:if>
        </div>


        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <!-- Previous -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="viewPromotionPosts?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                    <!-- Page numbers -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="viewPromotionPosts?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Next -->
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="viewPromotionPosts?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </c:if>
        
        <jsp:include page="footer.jsp"/>
        <!-- JavaScript -->
        <script src="js/jquery-1.12.4.min.js"></script>
        <script>

            // Auto-hide expired promotions on load (optional)
            document.addEventListener('DOMContentLoaded', function () {
                console.log('Promotion page loaded');
            });
        </script>
    </body>
</html>
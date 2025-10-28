<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Quản lý ứng tuyển - Job Board</title>
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
            .apply_management_area {
                padding: 60px 0;
                background: #f8f9fa;
            }

            .apply_container {
                margin: 40px auto 0; /* cách header 40px */
                padding: 4rem 15px 0; /* gộp lại cho gọn */
                max-width: 1200px;
                margin: 0 auto;
            }

            .apply_header {
                margin-bottom: 40px;
            }

            .apply_title {
                font-size: 32px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 10px;
            }

            .apply_subtitle {
                color: #7e8d9f;
                font-size: 16px;
            }

            .apply_stats {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
                margin-bottom: 40px;
            }

            .stat_card {
                background: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                text-align: center;
                transition: transform 0.3s ease;
            }

            .stat_card:hover {
                transform: translateY(-5px);
            }

            .stat_number {
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 5px;
            }

            .stat_number.total {
                color: #3498db;
            }
            .stat_number.pending {
                color: #f39c12;
            }
            .stat_number.reviewing {
                color: #9b59b6;
            }
            .stat_number.accepted {
                color: #27ae60;
            }
            .stat_number.rejected {
                color: #e74c3c;
            }

            .stat_label {
                color: #7e8d9f;
                font-size: 14px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .apply_filters {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                margin-bottom: 30px;
                display: flex;
                gap: 15px;
                flex-wrap: wrap;
                align-items: center;
            }

            .filter_group {
                flex: 1;
                min-width: 200px;
            }

            .filter_group label {
                display: block;
                margin-bottom: 5px;
                font-weight: 600;
                color: #2c3e50;
                font-size: 14px;
            }

            .filter_group select,
            .filter_group input {
                width: 100%;
                padding: 10px 15px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
            }

            .filter_button {
                background: #00d363;
                color: white;
                padding: 10px 30px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-weight: 600;
                transition: background 0.3s ease;
                margin-top: 24px;
            }

            .filter_button:hover {
                background: #00b854;
            }

            .apply_list {
                display: flex;
                flex-direction: column;
                gap: 20px;
            }

            .apply_item {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 25px;
                transition: all 0.3s ease;
            }

            .apply_item:hover {
                box-shadow: 0 4px 16px rgba(0,0,0,0.12);
            }

            .apply_item_header {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 15px;
                flex-wrap: wrap;
                gap: 15px;
            }

            .apply_item_left {
                display: flex;
                gap: 20px;
                align-items: flex-start;
                flex: 1;
            }

            .apply_item_logo {
                width: 80px;
                height: 80px;
                object-fit: contain;
                border: 1px solid #eee;
                border-radius: 8px;
                padding: 10px;
                background: #f8f9fa;
            }

            .apply_item_info {
                flex: 1;
            }

            .apply_item_job {
                font-size: 20px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 5px;
            }

            .apply_item_company {
                color: #7e8d9f;
                font-size: 16px;
                margin-bottom: 10px;
            }

            .apply_item_meta {
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
                font-size: 14px;
                color: #7e8d9f;
            }

            .apply_item_meta i {
                margin-right: 5px;
            }

            .apply_item_right {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
                gap: 10px;
            }

            .apply_status {
                padding: 8px 20px;
                border-radius: 20px;
                font-size: 13px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .status_pending {
                background: #fff3cd;
                color: #856404;
            }

            .status_reviewing {
                background: #e7d4f7;
                color: #6f42c1;
            }

            .status_accepted {
                background: #d4edda;
                color: #155724;
            }

            .status_rejected {
                background: #f8d7da;
                color: #721c24;
            }

            .apply_date {
                font-size: 13px;
                color: #7e8d9f;
            }

            .apply_item_body {
                padding-top: 15px;
                border-top: 1px solid #eee;
            }

            .apply_item_details {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 15px;
                margin-bottom: 15px;
            }

            .detail_item {
                display: flex;
                flex-direction: column;
            }

            .detail_label {
                font-size: 12px;
                color: #7e8d9f;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                margin-bottom: 5px;
            }

            .detail_value {
                font-size: 14px;
                color: #2c3e50;
                font-weight: 600;
            }

            .apply_actions {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }

            .action_btn {
                padding: 8px 20px;
                border-radius: 5px;
                border: none;
                cursor: pointer;
                font-size: 14px;
                font-weight: 600;
                transition: all 0.3s ease;
                text-decoration: none;
                display: inline-block;
            }

            .btn_view {
                background: #3498db;
                color: white;
            }

            .btn_view:hover {
                background: #2980b9;
                color: white;
            }

            .btn_withdraw {
                background: #e74c3c;
                color: white;
            }

            .btn_withdraw:hover {
                background: #c0392b;
            }

            .btn_disabled {
                background: #95a5a6;
                color: white;
                cursor: not-allowed;
                opacity: 0.6;
            }

            .empty_state {
                text-align: center;
                padding: 60px 20px;
                background: white;
                border-radius: 8px;
            }

            .empty_state i {
                font-size: 80px;
                color: #ddd;
                margin-bottom: 20px;
            }

            .empty_state h3 {
                color: #7e8d9f;
                margin-bottom: 10px;
            }

            .empty_state p {
                color: #95a5a6;
                margin-bottom: 20px;
            }

            .pagination {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 40px;
            }

            .page_btn {
                padding: 10px 15px;
                border: 1px solid #ddd;
                background: white;
                border-radius: 5px;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .page_btn:hover {
                background: #00d363;
                color: white;
                border-color: #00d363;
            }

            .page_btn.active {
                background: #00d363;
                color: white;
                border-color: #00d363;
            }

            @media (max-width: 768px) {
                .apply_stats {
                    grid-template-columns: repeat(2, 1fr);
                }

                .apply_item_header {
                    flex-direction: column;
                }

                .apply_item_right {
                    width: 100%;
                    align-items: flex-start;
                }
            }
        </style>
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="header.jsp"/>
        <!-- header_end -->

        <div class="main-content">
            <div class="apply_management_area">
                <div class="apply_container">

                    <!-- Filters -->
                    <form action="filterApplyLog" method="get">
                        <div class="apply_filters">
                            <!-- Search box -->
                            <input type="text" class="form-control align-middle"
                                   name="txt" id="searchInput" value="${txt}"
                                   placeholder="Search by job title..."
                                   style="height: 38px; min-width: 250px;">

                            <div class="filter_group">
                                <select class="form-control" name="status" id="statusFilter" style="height: 38px; min-width: 150px;">
                                    <option value="" disabled <c:if test="${empty status}">selected</c:if> hidden>Filter by Status</option>
                                        <option value="Pending">Pending</option>
                                        <option value="Approved">Approved</option>
                                        <option value="Rejected">Rejected</option>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-sm btn-warning me-2">
                                    <i class="ti-filter"></i> Lọc
                                </button>
                            </div>
                        </form>


                        <!-- Apply List -->

                    <c:forEach var="d" items="${details}" varStatus="st">
                        <div class="apply_list">
                            <div class="apply_item">
                                <div class="apply_item_header">
                                    <div class="apply_item_left">
                                        <img src="${d.employer.imgLogo}" alt="Techcombank" class="apply_item_logo">
                                        <div class="apply_item_info">
                                            <div class="apply_item_job">${d.job.title}</div>
                                            <div class="apply_item_company">${d.employer.companyName}</div>
                                            <div class="apply_item_meta">
                                                <span><i class="ti-location-pin"></i>${d.job.location}</span>
                                                <span><i class="ti-money"></i>${d.job.offerMinFormatted},000 - ${d.job.offerMaxFormatted},000 VNĐ</span>
                                                <span><i class="ti-time"></i>${d.job.typeJob}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="apply_item_right">
                                        <span class="apply_status status_reviewing">${d.apply.status}</span>
                                        <span class="apply_date">
                                            Ứng tuyển: ${d.apply.dayCreateString}
                                        </span>
                                    </div>
                                </div>
                                <div class="apply_item_body">

                                    <div class="apply_actions">
                                        <a href="${pageContext.request.contextPath}/${d.cv.fileData} class="action_btn btn_view">
                                            <i class="ti-eye"></i> Xem chi tiết CV
                                        </a>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </c:forEach>

                    <!-- Pagination -->
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

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

        <!-- JS -->
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
        <script src="js/contact.js"></script>
        <script src="js/jquery.ajaxchimp.min.js"></script>
        <script src="js/jquery.form.js"></script>
        <script src="js/jquery.validate.min.js"></script>
        <script src="js/mail-script.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
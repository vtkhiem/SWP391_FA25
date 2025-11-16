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

                <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
        <style>
            .cv_management_area {
                padding: 60px 0;
                background: #f8f9fa;
            }

            .cv_container {
                margin: 40px auto 0; /* cách header 40px */
                padding: 4rem 15px 0; /* gộp lại cho gọn */
                max-width: 1200px;
                margin: 0 auto;
            }

            .cv_header {
                margin-bottom: 40px;
            }

            .cv_title {
                font-size: 32px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 10px;
            }

            .cv_subtitle {
                color: #7e8d9f;
                font-size: 16px;
            }

            .cv_stats {
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

            .cv_filters {
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

            .cv_list {
                display: flex;
                flex-direction: column;
                gap: 20px;
            }

            .cv_item {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
                padding: 25px;
                transition: all 0.3s ease;
            }

            .cv_item:hover {
                box-shadow: 0 4px 16px rgba(0,0,0,0.12);
            }

            .cv_item_header {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 15px;
                flex-wrap: wrap;
                gap: 15px;
            }

            .cv_item_left {
                display: flex;
                gap: 20px;
                align-items: flex-start;
                flex: 1;
            }

            .cv_item_logo {
                width: 80px;
                height: 80px;
                object-fit: contain;
                border: 1px solid #eee;
                border-radius: 8px;
                padding: 10px;
                background: #f8f9fa;
            }

            .cv_item_info {
                flex: 1;
            }

            .cv_item_job {
                font-size: 20px;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 5px;
            }

            .cv_item_company {
                color: #7e8d9f;
                font-size: 16px;
                margin-bottom: 10px;
            }

            .cv_item_meta {
                display: flex;
                gap: 20px;
                flex-wrap: wrap;
                font-size: 14px;
                color: #7e8d9f;
            }

            .cv_item_meta i {
                margin-right: 5px;
            }

            .cv_item_right {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
                gap: 10px;
            }

            .cv_status {
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

            .cv_date {
                font-size: 13px;
                color: #7e8d9f;
            }

            .cv_item_body {
                padding-top: 15px;
                border-top: 1px solid #eee;
            }

            .cv_item_details {
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

            .cv_actions {
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
                .cv_stats {
                    grid-template-columns: repeat(2, 1fr);
                }

                .cv_item_header {
                    flex-direction: column;
                }

                .cv_item_right {
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

        <!-- Toast Notification -->
        <c:if test="${not empty sessionScope.error}">
            <div class="toast-message error">${sessionScope.error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.message}">
            <div class="toast-message success">${sessionScope.message}</div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <div class="main-content">
            <div class="cv_management_area">
                <div class="cv_container">

                    <!-- Filters -->

                    <!-- Container -->
                    <form action="${pageContext.request.contextPath}/filterPublicCandidate" method="get"
                          class="container-fluid bg-light p-3 rounded shadow-sm mt-3 mb-4"
                          style="margin-left: 0;">

                        <div class="row g-2 align-items-center">
                            <!-- Search -->
                            <div class="col-md-3">
                                <input type="text" class="form-control"
                                       name="txt" id="searchInput" value="${txt}"
                                       placeholder="🔍 Search by position/field">
                            </div>

                            <div class="col-md-2">
                                <input type="number" name="offerMin" id="offerMin" placeholder="Min offer" class="form-control" value="${param.minSalary}">
                            </div>

                            <!-- Apply + Clear buttons -->
                            <div class="col-md-2 d-flex justify-content-start gap-2">
                                <button type="submit" class="btn btn-warning w-100">
                                    Apply
                                </button>
                                <button id="" type="reset" class="btn btn-secondary w-100">
                                    Clear
                                </button>
                            </div>
                        </div>





                        <!-- Error message -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger mt-3 mb-0 text-center">${errorMessage}</div>
                        </c:if>
                    </form>


                    <!-- Apply List -->

                    <c:forEach var="cv" items="${cvs}" varStatus="st">
                        <div class="cv_list">
                            <div class="cv_item">
                                <div class="cv_item_header">
                                    <div class="cv_item_left">
                                        <img src=${cv.candidate.avatar} class="cv_item_logo">
                                        <div class="cv_item_info">
                                            <div class="cv_it   em_job">${cv.fullName}</div>

                                            <div class="cv_item_meta">
                                                <span><i class="ti-location-pin"></i>${cv.address}</span>
                                                <span><i class="ti-location-pin"></i>${cv.field}</span>
                                                <span><i class="ti-time"></i>${cv.numberExp} năm</span>
                                                <span><i class="ti-money"></i>${cv.currentSalary},000 VNĐ</span>
                                                <span><i class="ti-time"></i>${cv.position}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="cv_item_right">
                                        <span class="cv_status status_reviewing" ><a href="${pageContext.request.contextPath}/${cv.fileData}">
                                                <i class="ti-eye"></i> Xem chi tiết CV
                                            </a></span>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </c:forEach>

                    <div class="pagination justify-content-center mt-4">
                        <ul class="pagination">

                            <!-- Previous -->
                            <li class="page-item ${page == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="?page=${page-1}&txt=${txt}&offerMin=${offerMin}">
                                    &lt;
                                </a>
                            </li>

                            <!-- Page numbers -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == page ? 'active' : ''}">
                                    <a class="page-link"
                                       href="?page=${i}&txt=${txt}&offerMin=${offerMin}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>

                            <!-- Next -->
                            <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="?page=${page+1}&txt=${txt}&offerMin=${offerMin}">
                                    &gt;
                                </a>
                            </li>

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
            <script>
                // --- 1. FILTER FORM ---
                const form = document.querySelector("form[action$='filterPublicCandidate']");
                const searchInput = document.getElementById("searchInput");
                const categoryFilter = document.getElementById("categoryFilter");
                const offerMin = document.getElementById("offerMin");


                // Xử lý nút Clear: reset form và reload lại danh sách gốc
                const clearBtn = form.querySelector("button[type='reset']");
                clearBtn.addEventListener("click", (e) => {
                    e.preventDefault();
                    searchInput.value = "";
                    offerMin.value = "";
                    // Gửi request về servlet với query string
                    const params = new URLSearchParams();
                    params.append("txt", searchInput.value);
                    params.append("offerMin", offerMin.value);

                    // Chuyển trang
                    window.location.href = `${form.action}?${params.toString()}`;
                        });
            </script>
    </body>
</html>
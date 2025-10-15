<%-- 
    Document   : employer_service_promo
    Created on : Oct 15, 2025, 7:34:40 PM
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Employer" %>

<%
    // ✅ Kiểm tra đăng nhập và phân quyền
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equalsIgnoreCase("Employer")) {
        response.sendRedirect("access-denied.jsp");
        return;
    }

%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dịch vụ & Khuyến mãi - Nhà tuyển dụng</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/custom.css">

        <style>
            body {
                background-color: #f8f9fa;
            }

            /* ===== BREADCRUMB ===== */
            .bradcam_area {
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                padding: 3rem 0;
                text-align: center;
                color: white;
            }

            .bradcam_text h3 {
                font-size: 2.2rem;
                margin-bottom: 0.5rem;
            }

            /* ===== PROMOTION SECTION ===== */
            .promo-section {
                margin-top: 3rem;
            }

            .promo-card {
                background: #fff;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                padding: 1.5rem;
                transition: transform 0.3s ease;
            }

            .promo-card:hover {
                transform: translateY(-5px);
            }

            .promo-code {
                font-weight: bold;
                color: #0d6efd;
            }

            /* ===== SERVICE SECTION ===== */
            .service-section {
                margin-top: 4rem;
            }

            .service-card {
                background: #fff;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                padding: 2rem;
                text-align: center;
                transition: transform 0.3s ease;
            }

            .service-card:hover {
                transform: translateY(-5px);
            }

            .service-price {
                font-size: 1.8rem;
                font-weight: bold;
                color: #1489f1;
                margin: 1rem 0;
            }

            .service-duration {
                color: #666;
            }

            .buy-btn {
                margin-top: 1rem;
                padding: 0.5rem 1.5rem;
                border-radius: 30px;
                background-color: #0d6efd;
                color: white;
                border: none;
                transition: all 0.3s ease;
            }

            .buy-btn:hover {
                background-color: #095bb5;
            }
        </style>
    </head>

    <body>

        <!-- ✅ Header -->
        <jsp:include page="header.jsp"/>

        <!-- ======= Breadcrumb ======= -->
        <div class="bradcam_area">
            <div class="container">
                <div class="bradcam_text">
                    <h3>Dịch vụ & Khuyến mãi</h3>
                    <p>Xem bảng giá và ưu đãi dành riêng cho Nhà tuyển dụng</p>
                </div>
            </div>
        </div>

        <div class="container">

            <!-- ✅ PROMOTIONS -->
            <div class="promo-section">
                <h4 class="text-primary mb-4"><i class="ti-gift"></i> Khuyến mãi đang diễn ra</h4>

                <div class="row g-4">
                    <c:forEach var="promo" items="${promotionList}">
                        <div class="col-md-6 col-lg-4">
                            <div class="promo-card">
                                <h5 class="promo-code">${promo.code}</h5>
                                <p>Giảm <strong><fmt:formatNumber value="${promo.discount * 100}" maxFractionDigits="0"/>%</strong></p>
                                <p class="text-muted small">Từ ${promo.dateSt} đến ${promo.dateEn}</p>
                                <p>${promo.description}</p>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty promotionList}">
                        <p class="text-muted text-center">Hiện chưa có khuyến mãi nào.</p>
                    </c:if>
                </div>
            </div>

            <!-- ✅ SERVICES -->
            <div class="service-section">
                <h4 class="text-primary mb-4"><i class="ti-briefcase"></i> Bảng giá dịch vụ</h4>

                <div class="row g-4">
                    <c:forEach var="s" items="${serviceList}">
                        <div class="col-md-4">
                            <div class="service-card">
                                <h5 class="fw-bold">${s.serviceName}</h5>
                                <div class="service-price">
                                    <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </div>
                                <p class="service-duration">${s.duration} ngày sử dụng</p>
                                <p class="small text-muted">${s.description}</p>

                                <c:if test="${not empty s.functions}">
                                    <ul class="text-start small list-unstyled mt-3">
                                        <c:forEach var="f" items="${s.functions}">
                                            <li>• ${f.functionName}</li>
                                            </c:forEach>
                                    </ul>
                                </c:if>

                                <form action="buyService" method="get">
                                    <input type="hidden" name="serviceID" value="${s.serviceID}">
                                    <button type="submit" class="buy-btn">Mua ngay</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty serviceList}">
                        <p class="text-muted text-center">Chưa có gói dịch vụ nào được mở bán.</p>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- ======= Footer ======= -->
        <jsp:include page="footer.jsp"/>

        <!-- JS -->
        <script src="js/bootstrap.bundle.min.js"></script>

    </body>
</html>

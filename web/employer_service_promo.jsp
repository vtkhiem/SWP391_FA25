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
                background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf1 100%);
            }

            .bradcam_area {
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                padding: 3rem 0;
                text-align: center;
                color: white;
                margin-bottom: 50px;
            }

            .bradcam_text h3 {
                font-size: 2.5rem;
                margin-bottom: 0.8rem;
                font-weight: 700;
            }

            .bradcam_text p {
                font-size: 1.1rem;
                opacity: 0.95;
            }

            .section-header {
                margin: 60px 0 35px 0;
                display: flex;
                align-items: center;
                gap: 12px;
            }

            .section-header h4 {
                font-size: 1.8rem;
                font-weight: 600;
                margin: 0;
                color: #172b4d;
            }

            .section-header.promo h4 {
                color: #00875a;
            }

            .section-header i {
                font-size: 2rem;
            }

            .pricing-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
                gap: 30px;
                margin-bottom: 40px;
            }

            .service-card {
                background: white;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                transition: all 0.3s ease;
                display: flex;
                flex-direction: column;
                border: 2px solid transparent;
            }

            .service-card:hover {
                transform: translateY(-8px);
                box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
            }

            .service-card.promo {
                border-color: #00d68f;
                position: relative;
            }

            .service-card.promo::before {
                content: '🎁 KHUYẾN MÃI';
                position: absolute;
                top: 15px;
                right: 15px;
                background: linear-gradient(135deg, #00d68f 0%, #00b875 100%);
                color: white;
                padding: 6px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: 700;
                z-index: 10;
            }

            .card-header-custom {
                background: linear-gradient(135deg, #1a2332 0%, #2d3e50 100%);
                padding: 25px;
                color: white;
                border-top: 4px solid #1489f1;
            }

            .service-card.promo .card-header-custom {
                border-top-color: #00d68f;
            }

            .card-header-custom h5 {
                font-size: 1.4rem;
                font-weight: 700;
                margin: 0;
            }

            .card-body-custom {
                padding: 30px;
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            .price-section {
                margin-bottom: 20px;
            }

            .service-price {
                font-size: 1.1rem;
                color: #8993a4;
                text-decoration: line-through;
                margin-bottom: 8px;
            }

            .final-price {
                font-size: 2.2rem;
                font-weight: 700;
                color: #1489f1;
                margin-bottom: 10px;
            }

            .service-card.promo .final-price {
                color: #e74c3c;
            }

            .discount-badge {
                display: inline-block;
                background: linear-gradient(135deg, #00d68f 0%, #00b875 100%);
                color: white;
                padding: 6px 14px;
                border-radius: 20px;
                font-size: 14px;
                font-weight: 600;
                margin-bottom: 15px;
            }

            .service-duration {
                color: #5e6c84;
                font-size: 15px;
                margin-bottom: 12px;
                display: flex;
                align-items: center;
                gap: 6px;
            }

            .service-duration::before {
                content: '⏱';
                font-size: 18px;
            }

            .service-description {
                color: #8993a4;
                font-size: 14px;
                line-height: 1.6;
                margin-bottom: 20px;
            }

            .features-title {
                color: #8993a4;
                font-size: 13px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                margin-bottom: 12px;
            }

            .feature-list {
                list-style: none;
                padding: 0;
                margin: 0 0 25px 0;
                flex: 1;
            }

            .feature-list li {
                display: flex;
                align-items: flex-start;
                margin-bottom: 10px;
                font-size: 14px;
                line-height: 1.5;
                color: #172b4d;
            }

            .feature-list li::before {
                content: '✓';
                color: #00d68f;
                font-weight: bold;
                font-size: 16px;
                margin-right: 10px;
                flex-shrink: 0;
                margin-top: 2px;
            }

            .buy-btn {
                width: 100%;
                padding: 14px;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                color: white;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .buy-btn:hover {
                background: linear-gradient(135deg, #0f6bb8 0%, #0a5a9e 100%);
                transform: translateY(-2px);
                box-shadow: 0 6px 20px rgba(20, 137, 241, 0.4);
            }

            .service-card.promo .buy-btn {
                background: linear-gradient(135deg, #00d68f 0%, #00b875 100%);
            }

            .service-card.promo .buy-btn:hover {
                background: linear-gradient(135deg, #00b875 0%, #009960 100%);
                box-shadow: 0 6px 20px rgba(0, 214, 143, 0.4);
            }

            .empty-message {
                text-align: center;
                color: #8993a4;
                font-size: 16px;
                padding: 60px 20px;
                background: white;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            }

            .feedback-section {
                text-align: center;
                margin: 80px 0 60px 0;
            }

            .feedback-btn {
                padding: 16px 40px;
                border: none;
                border-radius: 30px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                background: linear-gradient(135deg, #5e72e4 0%, #4c63d2 100%);
                color: white;
                display: inline-flex;
                align-items: center;
                gap: 10px;
                box-shadow: 0 4px 15px rgba(94, 114, 228, 0.3);
            }

            .feedback-btn:hover {
                background: linear-gradient(135deg, #4c63d2 0%, #3b52c1 100%);
                transform: translateY(-3px);
                box-shadow: 0 6px 20px rgba(94, 114, 228, 0.4);
            }

            @media (max-width: 768px) {
                .pricing-grid {
                    grid-template-columns: 1fr;
                }

                .bradcam_text h3 {
                    font-size: 2rem;
                }

                .section-header h4 {
                    font-size: 1.5rem;
                }

                .final-price {
                    font-size: 1.8rem;
                }
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

        <!-- Toast Notification -->
        <c:if test="${not empty message}">
            <div class="toast-message success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="toast-message error">${error}</div>
        </c:if>

        <div class="container">
            <!-- ======= DỊCH VỤ CÓ KHUYẾN MÃI ======= -->
            <div class="section-header promo">
                <i class="ti-gift"></i>
                <h4>Dịch vụ đang được khuyến mãi</h4>
            </div>

            <div class="pricing-grid">
                <c:forEach var="s" items="${serviceList}">
                    <c:set var="finalPrice" value="${finalPrices[s.serviceID]}" />

                    <c:if test="${finalPrice lt s.price}">
                        <div class="service-card promo">
                            <div class="card-header-custom">
                                <h5>${s.serviceName}</h5>
                            </div>
                            <div class="card-body-custom">
                                <div class="price-section">
                                    <!-- Giá gốc -->
                                    <div class="service-price">
                                        <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                    </div>

                                    <!-- Giá sau giảm -->
                                    <div class="final-price">
                                        <fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                    </div>

                                    <!-- Phần trăm tiết kiệm -->
                                    <div class="discount-badge">
                                        Tiết kiệm 
                                        <fmt:formatNumber 
                                            value="${(1 - finalPrice / s.price) * 100}" 
                                            maxFractionDigits="0"
                                            />%
                                    </div>
                                </div>

                                <div class="service-duration"><b>${s.duration} ngày sử dụng</b></div>

                                <c:if test="${not empty s.description}">
                                    <div class="service-description">${s.description}</div>
                                </c:if>

                                <c:if test="${not empty s.functions}">
                                    <div class="features-title">Quyền lợi đặc biệt</div>
                                    <ul class="feature-list">
                                        <c:forEach var="f" items="${s.functions}">
                                            <li>${f.functionName}</li>
                                            </c:forEach>
                                    </ul>
                                </c:if>

                                <form action="buyService" method="get">
                                    <input type="hidden" name="serviceID" value="${s.serviceID}">
                                    <button type="submit" class="buy-btn">Mua ngay</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <c:set var="hasPromo" value="false" />
            <c:forEach var="s" items="${serviceList}">
                <c:if test="${finalPrices[s.serviceID] lt s.price}">
                    <c:set var="hasPromo" value="true" />
                </c:if>
            </c:forEach>

            <c:if test="${empty serviceList or hasPromo eq 'false'}">
                <div class="empty-message">Hiện chưa có dịch vụ nào được giảm giá.</div>
            </c:if>

            <!-- ======= DỊCH VỤ GIÁ GỐC ======= -->
            <div class="section-header">
                <i class="ti-briefcase"></i>
                <h4>Dịch vụ không có khuyến mãi</h4>
            </div>

            <div class="pricing-grid">
                <c:forEach var="s" items="${serviceList}">
                    <c:set var="finalPrice" value="${finalPrices[s.serviceID]}" />

                    <c:if test="${finalPrice eq s.price}">
                        <div class="service-card">
                            <div class="card-header-custom">
                                <h5>${s.serviceName}</h5>
                            </div>
                            <div class="card-body-custom">
                                <div class="price-section">
                                    <div class="final-price">
                                        <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                    </div>
                                </div>

                                <div class="service-duration">${s.duration} ngày sử dụng</div>

                                <c:if test="${not empty s.description}">
                                    <div class="service-description">${s.description}</div>
                                </c:if>

                                <c:if test="${not empty s.functions}">
                                    <div class="features-title">Quyền lợi đặc biệt</div>
                                    <ul class="feature-list">
                                        <c:forEach var="f" items="${s.functions}">
                                            <li>${f.functionName}</li>
                                            </c:forEach>
                                    </ul>
                                </c:if>

                                <form action="buyService" method="get">
                                    <input type="hidden" name="serviceID" value="${s.serviceID}">
                                    <button type="submit" class="buy-btn">Mua ngay</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <c:set var="hasNonPromo" value="false" />
            <c:forEach var="s" items="${serviceList}">
                <c:if test="${finalPrices[s.serviceID] eq s.price}">
                    <c:set var="hasNonPromo" value="true" />
                </c:if>
            </c:forEach>

            <c:if test="${empty serviceList or hasNonPromo eq 'false'}">
                <div class="empty-message">Hiện chưa có dịch vụ nào không giảm giá.</div>
            </c:if>
        </div>

        <!-- ======= Footer ======= -->
        <jsp:include page="footer.jsp"/>

        <!-- JS -->
        <script src="js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const toasts = document.querySelectorAll(".toast-message");
                toasts.forEach((toast, index) => {
                    Object.assign(toast.style, {
                        position: "fixed",
                        top: `${20 + index * 60}px`,
                        right: "-350px",
                        opacity: "1",
                        transition: "all 0.6s ease",
                        zIndex: "9999",
                        padding: "12px 20px",
                        borderRadius: "6px",
                        color: "#fff",
                        fontWeight: "500",
                        boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
                        minWidth: "250px",
                        textAlign: "center",
                        backgroundColor: toast.classList.contains("success") ? "#28a745" : "#dc3545"
                    });

                    setTimeout(() => (toast.style.right = "20px"), 200 + index * 150);

                    setTimeout(() => {
                        toast.style.right = "-350px";
                        toast.style.opacity = "0";
                    }, 4000 + index * 150);
                });
            });
        </script>
    </body>
</html>
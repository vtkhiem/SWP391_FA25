<%-- 
    Document   : buy_service
    Created on : Oct 16, 2025, 2:25:32 AM
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
            <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <meta charset="UTF-8">
    <title>Thanh toán dịch vụ</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { max-width: 650px; margin: 80px auto; padding: 2rem; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        .price { font-size: 1.5rem; font-weight: bold; color: #0d6efd; }
        .promo-list { background: #fff; padding: 1rem; border-radius: 10px; max-height: 200px; overflow-y: auto; }
    </style>
</head>
<body>

<div class="card">
    <h4 class="text-center text-primary mb-4">Xác nhận mua dịch vụ</h4>

    <c:if test="${not empty service}">
        <p><strong>Tên dịch vụ:</strong> ${service.serviceName}</p>
        <p><strong>Thời hạn:</strong> ${service.duration} ngày</p>
        <p><strong>Giá:</strong>
            <span class="price">
                <fmt:formatNumber value="${service.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
            </span>
        </p>
        <hr>

        <form action="registerService" method="post">
            <input type="hidden" name="serviceID" value="${service.serviceID}">
            
            <div class="mb-3">
                <label class="form-label fw-bold">Nhập mã khuyến mãi (nếu có)</label>
                <input type="text" class="form-control" name="promoCode" placeholder="Nhập mã khuyến mãi...">
            </div>

            <div class="promo-list mt-3">
                <strong>Hoặc chọn từ danh sách khuyến mãi đang diễn ra:</strong>
                <ul class="list-unstyled mt-2">
                    <c:forEach var="p" items="${promotionList}">
                        <li>
                            <span class="badge bg-success">${p.code}</span> 
                            - Giảm <fmt:formatNumber value="${p.discount * 100}" maxFractionDigits="0"/>%
                            (<fmt:formatDate value="${p.dateSt}" pattern="dd/MM/yyyy"/> - 
                            <fmt:formatDate value="${p.dateEn}" pattern="dd/MM/yyyy"/>)
                        </li>
                    </c:forEach>
                    <c:if test="${empty promotionList}">
                        <li class="text-muted">Không có khuyến mãi nào đang hoạt động.</li>
                    </c:if>
                </ul>
            </div>

            <div class="d-grid mt-4">
                <button type="submit" class="btn btn-primary">Thanh toán ngay</button>
            </div>
        </form>
    </c:if>

    <c:if test="${empty service}">
        <div class="alert alert-warning text-center mt-3">Không tìm thấy dịch vụ!</div>
    </c:if>

    <div class="text-center mt-3">
        <a href="employerServices" class="text-decoration-none text-secondary">← Quay lại</a>
    </div>
</div>

</body>
</html>

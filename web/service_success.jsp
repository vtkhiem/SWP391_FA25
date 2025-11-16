<%-- 
    Document   : service_s
    Created on : Oct 16, 2025, 4:14:08 AM
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <title>Đăng ký thành công</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .card { max-width: 600px; margin: 80px auto; padding: 2rem; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; }
    </style>
</head>
<body>

<div class="card">
    <h4 class="text-success mb-3">🎉 Đăng ký dịch vụ thành công!</h4>

    <p><strong>Dịch vụ:</strong> ${service.serviceName}</p>
    <p><strong>Thời hạn:</strong> ${service.duration} ngày</p>

    <c:if test="${not empty appliedPromo}">
        <p><strong>Mã khuyến mãi:</strong> ${appliedPromo.code} (-<fmt:formatNumber value="${appliedPromo.discount * 100}" maxFractionDigits="0"/>%)</p>
    </c:if>

    <p><strong>Giá thanh toán:</strong>
        <fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
    </p>

    <div class="mt-4">
        <a href="employerServices" class="btn btn-primary">Về trang chính</a>
    </div>
</div>

</body>
</html>

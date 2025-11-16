<%-- 
    Document   : listPromotion
    Created on : Oct 9, 2025, 4:28:41 PM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <title>Danh sách khuyến mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4 rounded-3">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="text-primary mb-0">Danh sách khuyến mãi</h3>
            <a href="addPromotion.jsp" class="btn btn-success">+ Thêm khuyến mãi mới</a>
        </div>

        <!-- Thông báo -->
        <c:if test="${not empty message}">
            <div class="alert alert-info text-center">${message}</div>
        </c:if>

        <!-- Bảng danh sách khuyến mãi -->
        <table class="table table-hover table-bordered align-middle text-center">
            <thead class="table-primary">
                <tr>
                    <th>ID</th>
                    <th>Mã khuyến mãi</th>
                    <th>Giảm (%)</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Ngày tạo</th>
                    <th>Mô tả</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${promotionList}">
                    <tr>
                        <td>${p.promotionID}</td>
                        <td class="fw-bold">${p.code}</td>
                        <td><fmt:formatNumber value="${p.discount}" maxFractionDigits="2"/>%</td>
                        <td><fmt:formatDate value="${p.dateSt}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td><fmt:formatDate value="${p.dateEn}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td><fmt:formatDate value="${p.dateCr}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td class="text-start">${p.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${p.status}">
                                    <span class="badge bg-success">Hoạt động</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Ngừng</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                     
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Khi không có dữ liệu -->
        <c:if test="${empty promotionList}">
            <p class="text-center text-muted">Hiện chưa có khuyến mãi nào.</p>
        </c:if>
    </div>
</div>

</body>
</html>
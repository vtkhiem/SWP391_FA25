<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách gói dịch vụ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4 rounded-3">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="text-primary mb-0">Danh sách gói dịch vụ</h3>
            <a href="loadAddService" class="btn btn-success">+ Thêm dịch vụ mới</a>
        </div>

        <!-- Thông báo -->
        <c:if test="${not empty message}">
            <div class="alert alert-info text-center">${message}</div>
        </c:if>

        <table class="table table-hover table-bordered align-middle text-center">
            <thead class="table-primary">
                <tr>
                    <th>ID</th>
                    <th>Tên dịch vụ</th>
                    <th>Giá (VNĐ)</th>
                    <th>Thời hạn (ngày)</th>
                    <th>Mô tả</th>
                    <th>Chức năng đi kèm</th>
                    <th>Khuyến mãi áp dụng</th>
                    <th>Trạng thái hiển thị</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${serviceList}">
                    <tr>
                        <td>${s.serviceID}</td>
                        <td class="fw-bold text-start">${s.serviceName}</td>
                        <td>
                            <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                        </td>
                        <td>${s.duration}</td>
                        <td class="text-start">${s.description}</td>

                        <!-- ✅ Danh sách chức năng -->
                        <td class="text-start">
                            <c:choose>
                                <c:when test="${not empty s.functions}">
                                    <ul class="list-unstyled mb-0">
                                        <c:forEach var="f" items="${s.functions}">
                                            <li>• ${f.functionName}</li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Không có</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- ✅ Danh sách khuyến mãi -->
                        <td class="text-start">
                            <c:choose>
                                <c:when test="${not empty s.promotions}">
                                    <ul class="list-unstyled mb-0">
                                        <c:forEach var="p" items="${s.promotions}">
                                            <li>
                                                <strong>${p.code}</strong> - Giảm 
                                                <fmt:formatNumber value="${p.discount}" maxFractionDigits="0"/>%
                                                <br/>
                                                <small class="text-muted">${p.dateSt} → ${p.dateEn}</small>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted">Không có</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- ✅ Trạng thái hiển thị -->
                        <td>
                            <c:choose>
                                <c:when test="${s.isVisible}">
                                    <span class="badge bg-success mb-2">Hiển thị</span><br>
                                    <a href="editVisible?id=${s.serviceID}" 
                                       class="btn btn-outline-secondary btn-sm">
                                        Ẩn đi
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary mb-2">Đang ẩn</span><br>
                                    <a href="editVisible?id=${s.serviceID}" 
                                       class="btn btn-outline-primary btn-sm">
                                        Hiển thị
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <!-- ✅ Nút thao tác -->
                        <td>
                            <a href="deleteService?id=${s.serviceID}" 
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('Bạn có chắc muốn xóa dịch vụ này không?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty serviceList}">
            <p class="text-center text-muted">Hiện chưa có dịch vụ nào được tạo.</p>
        </c:if>
    </div>
</div>

</body>
</html>
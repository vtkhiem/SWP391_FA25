<%-- 
    Document   : addService
    Created on : Oct 9, 2025, 3:06:08 AM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm gói dịch vụ mới</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="card shadow p-4 rounded-3">
                <h3 class="text-center mb-4 text-primary">Thêm Gói Dịch Vụ Mới</h3>

                <!-- Hiển thị thông báo nếu có -->
                <c:if test="${not empty message}">
                    <div class="alert alert-info text-center">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-info text-center">${error}</div>
                </c:if>

                <form action="addService" method="post">
                    <!-- Ẩn input để servlet biết đang gọi action nào -->
                    <input type="hidden" name="action" value="add">

                    <div class="mb-3">
                        <label class="form-label fw-bold">Tên dịch vụ</label>
                        <input type="text" name="serviceName" class="form-control" placeholder="Nhập tên gói dịch vụ" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Giá (VNĐ)</label>
                        <input type="number" step="0.01" name="price" class="form-control" placeholder="Ví dụ: 199000" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Thời hạn (ngày)</label>
                        <input type="number" name="duration" class="form-control" placeholder="Ví dụ: 30" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Mô tả</label>
                        <textarea name="description" class="form-control" rows="4" placeholder="Mô tả ngắn gọn về dịch vụ..."></textarea>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Chương trình khuyến mãi</label>
                        <select name="promotionID" class="form-select" multiple size="5">
                            <!-- Load danh sách khuyến mãi -->
                            <c:forEach var="promo" items="${promotionList}">
                                <option value="${promo.promotionID}">
                                    ${promo.code} - Giảm ${promo.discount * 100}%
                                </option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Giữ Ctrl (hoặc Cmd trên Mac) để chọn nhiều khuyến mãi.</div>
                    </div>

                    <div class="form-check mb-3">
                        <input type="checkbox" name="isVisible" class="form-check-input" id="visibleCheck" checked>
                        <label class="form-check-label" for="visibleCheck">Hiển thị dịch vụ này</label>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn btn-success px-4">Thêm Dịch Vụ</button>
                        <a href="listService" class="btn btn-secondary px-4">Hủy</a>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>

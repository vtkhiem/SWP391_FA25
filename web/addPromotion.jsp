<%-- 
    Document   : addPromotion
    Created on : Oct 9, 2025, 4:36:18 PM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm khuyến mãi mới</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="card shadow p-4 rounded-3">
                <h3 class="text-primary mb-4">Thêm khuyến mãi mới</h3>

                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>

                <form action="addPromotion" method="post">
                    <div class="mb-3">
                        <label class="form-label">Mã khuyến mãi</label>
                        <input type="text" name="code" class="form-control" required placeholder="VD: SALE2025">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Giảm giá (%)</label>
                        <input type="number" name="discount" step="0.01" min="0" max="100" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Ngày bắt đầu</label>
                        <input type="datetime-local" name="dateSt" class="form-control" required
                               min="<%= java.time.LocalDateTime.now().toString().substring(0,16) %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Ngày kết thúc</label>
                        <input type="datetime-local" name="dateEn" class="form-control" required
                               min="<%= java.time.LocalDateTime.now().toString().substring(0,16) %>">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" rows="3" class="form-control" placeholder="Mô tả khuyến mãi..."></textarea>
                    </div>



                    <div class="d-flex justify-content-between">
                        <a href="listPromotion" class="btn btn-secondary">← Quay lại</a>
                        <button type="submit" class="btn btn-primary">Thêm khuyến mãi</button>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>
<%-- 
    Document   : addService
    Created on : Oct 9, 2025
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
            <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4 rounded-3">
        <h3 class="text-center mb-4 text-primary">Thêm Gói Dịch Vụ Mới</h3>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty message}">
            <div class="alert alert-success text-center">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center">${error}</div>
        </c:if>

        <form action="addService" method="post">
            <input type="hidden" name="action" value="add">

            <!-- Tên dịch vụ -->
            <div class="mb-3">
                <label class="form-label fw-bold">Tên dịch vụ</label>
                <input type="text" name="serviceName" class="form-control" placeholder="Nhập tên gói dịch vụ" required>
            </div>

            <!-- Giá -->
            <div class="mb-3">
                <label class="form-label fw-bold">Giá (VNĐ)</label>
                <input type="number" step="0.01" name="price" class="form-control" placeholder="Ví dụ: 199000" required>
            </div>

            <!-- Thời hạn -->
            <div class="mb-3">
                <label class="form-label fw-bold">Thời hạn (ngày)</label>
                <input type="number" name="duration" class="form-control" placeholder="Ví dụ: 30" required>
            </div>

            <!-- Mô tả -->
            <div class="mb-3">
                <label class="form-label fw-bold">Mô tả</label>
                <textarea name="description" class="form-control" rows="4" placeholder="Mô tả ngắn gọn về dịch vụ..."></textarea>
            </div>

            <!-- Chức năng (Functions) -->
            <div class="mb-3">
                <label class="form-label fw-bold">Chức năng đi kèm</label>
                <div class="row">
                    <c:forEach var="func" items="${functionList}">
                        <div class="col-md-4 mb-2">
                            <div class="form-check">
                                <input class="form-check-input function-checkbox" 
                                       type="checkbox" 
                                       name="functionIDs" 
                                       value="${func.functionID}" 
                                       id="func-${func.functionID}">
                                <label class="form-check-label" for="func-${func.functionID}">
                                    ${func.functionName}
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Nếu chọn JobPost thì hiển thị thêm số lượng -->
            <div class="mb-3" id="jobPostAmountGroup" style="display:none;">
                <label class="form-label fw-bold">Số lượng bài đăng được phép</label>
                <input type="number" name="jobPostAmount" class="form-control" placeholder="Ví dụ: 10">
                <div class="form-text">Để trống hoặc nhập 0 nếu không giới hạn.</div>
            </div>

            <!-- Khuyến mãi -->
            <div class="mb-3">
                <label class="form-label fw-bold">Chương trình khuyến mãi</label>
                <select name="promotionIDs" class="form-select" multiple size="5">
                    <c:forEach var="promo" items="${promotionList}">
                        <option value="${promo.promotionID}">
                            ${promo.code} - Giảm ${promo.discount * 100}%
                        </option>
                    </c:forEach>
                </select>
                <div class="form-text">Giữ Ctrl (Cmd) để chọn nhiều khuyến mãi.</div>
            </div>

            <!-- Hiển thị -->
            <div class="form-check mb-3">
                <input type="checkbox" name="isVisible" class="form-check-input" id="visibleCheck" checked>
                <label class="form-check-label" for="visibleCheck">Hiển thị dịch vụ này</label>
            </div>

            <!-- Submit -->
            <div class="text-center">
                <button type="submit" class="btn btn-success px-4">Thêm Dịch Vụ</button>
                <a href="listService" class="btn btn-secondary px-4">Hủy</a>
            </div>
        </form>
    </div>
</div>

<!-- JS tự ẩn/hiện ô nhập số lượng JobPost -->
<script>
document.querySelectorAll('.function-checkbox').forEach(cb => {
    cb.addEventListener('change', function() {
        const jobPostBox = document.getElementById('jobPostAmountGroup');
        const jobPostSelected = Array.from(document.querySelectorAll('.function-checkbox'))
            .some(cb => cb.checked && cb.nextElementSibling.textContent.trim().toLowerCase().includes("jobpost"));
        jobPostBox.style.display = jobPostSelected ? 'block' : 'none';
    });
});
</script>

</body>
</html>

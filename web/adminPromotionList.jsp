<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="model.Admin" %>
<%
    // Lấy thông tin Admin user và kiểm tra quyền truy cập như trang Candidates
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("Admin")) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
    Admin user = (Admin) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Promotions</title>
    <style>
        /* CSS được copy từ trang Candidates */
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

        .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }

        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; display:inline-block; }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
        .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
        .btn-sm { padding:6px 10px; font-size:12px; border-radius:6px; margin-top:4px; }
        .btn-danger { border-color:#fecaca; color:#b91c1c; background:#fff; }
        .btn-danger:hover { background:#fee2e2; }
        .btn-success { border-color:#d1fae5; color:#065f46; background:#fff; }
        .btn-success:hover { background:#d1fae5; }
        .btn-outline-danger { border-color:#fecaca; color:#b91c1c; background:#fff; }
        .btn-outline-danger:hover { background:#fee2e2; }
        .btn-secondary { background:#e5e7eb; color:#4b5563; border-color:#e5e7eb; }

        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

        .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
        .table-head { padding:16px 16px 0 16px; font-weight:700; font-size:18px; display:flex; justify-content:space-between; align-items:center; }
        table { width:100%; border-collapse:collapse; }
        thead th {
            text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb;
            background:#fafafa;
        }
        tbody td { padding:14px 16px; border-bottom:1px solid #f0f2f5; vertical-align:top; }
        tbody tr:hover { background:#fafafa; }

        .col-id { width:70px; font-variant-numeric:tabular-nums; color:#374151; text-align:center; }
        .col-code { width:120px; font-weight:700; text-align:center; }
        .col-discount { width:80px; text-align:center; font-variant-numeric:tabular-nums; }
        .col-des{ width:300px; font-size:13px; text-align:center}
        .col-date { width:120px; font-size:13px; text-align:center; }
        .col-status { width:100px; text-align:center; }
        .col-actions { width:200px; text-align:center; }

        .badge { display:inline-block; padding:4px 8px; border-radius:9999px; font-size:12px; font-weight:600; }
        .bg-success-new { background-color:#d1fae5; color:#065f46; }
        .bg-warning-new { background-color:#fef3c7; color:#92400e; }
        .empty { color:#6b7280; padding:20px; text-align:center; }

        .d-inline-custom { display:inline-block; margin:0 2px; }
    </style>
</head>
<body>

<div class="navbar">
    <a>Admin Dashboard</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/candidates" class="nav-link">Candidates</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/employers" class="nav-link">Employers</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/listService" class="nav-link">Services</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminPromotion" class="nav-link" style="text-decoration:underline;">Promotions</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/staffs" class="nav-link">Staffs</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
</div>

<div class="container">
    <div class="table-card">
        <div class="table-head">
            <span>Danh Sách Khuyến Mãi</span>
        
        </div>

        <c:if test="${not empty message}">
            <div style="padding:10px 16px; margin:16px 16px 0 16px; border-radius:8px; background-color:#dbeafe; color:#1e40af; border:1px solid #93c5fd; text-align:center;">${message}</div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th class="col-id">ID</th>
                    <th class="col-code">Mã</th>
                    <th class="col-discount">Giảm (%)</th>
                    <th class="col-des">Mô tả</th>
                    <th class="col-date">Bắt đầu</th>
                    <th class="col-date">Kết thúc</th>
                    <th class="col-date">Ngày tạo</th>
                    <th class="col-status">Trạng thái</th>
                    <th class="col-actions">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty promotionList}">
                        <c:forEach var="p" items="${promotionList}">
                            <tr>
                                <td class="col-id">${p.promotionID}</td>
                                <td class="col-code">${p.code}</td>
                                <td class="col-discount">${p.discount}</td>
                                <td class="col-des">${p.description}</td>
                                <td class="col-date">${p.dateSt}</td>
                                <td class="col-date">${p.dateEn}</td>
                                <td class="col-date">${p.dateCr}</td>
                                <td class="col-status">
                                    <c:choose>
                                        <c:when test="${p.status}">
                                            <span class="badge bg-success-new">Đã duyệt</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning-new">Chờ duyệt</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="col-actions">
                                    <c:if test="${!p.status}">
                                        <form action="verifyPromo" method="post" class="d-inline-custom">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">
                                            <button class="btn btn-success btn-sm" type="submit">Duyệt</button>
                                        </form>

                                        <form action="deletePromo" method="post" class="d-inline-custom"
                                              onsubmit="return confirm('Bạn có chắc muốn từ chối (xóa) khuyến mãi ${p.code}?');">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">
                                            <button class="btn btn-danger btn-sm" type="submit">Từ chối</button>
                                        </form>
                                    </c:if>

                                    <c:if test="${p.status}">
                                        <button class="btn btn-secondary btn-sm" disabled>Đã duyệt</button>
                                        <form action="deletePromo" method="post" class="d-inline-custom"
                                              onsubmit="return confirm('Bạn có chắc muốn xóa khuyến mãi ${p.code}?');">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">
                                            <button class="btn btn-outline-danger btn-sm" type="submit">Xóa</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="9" class="empty">Hiện chưa có khuyến mãi nào.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
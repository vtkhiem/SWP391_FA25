<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="model.Admin" %>
<%
   
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
    
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <meta charset="UTF-8">
    <title>Admin Dashboard - Services</title>
    <style>
       
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

        .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }

     
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
        .col-name { font-weight:700; }
        .col-price { width:120px; text-align:right; font-variant-numeric:tabular-nums; }
        .col-duration { width:80px; text-align:center; font-variant-numeric:tabular-nums; }
        .col-visible { width:120px; text-align:center; }
        .col-actions { width:90px; text-align:center; }

        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; display:inline-block; }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
        .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
        .btn-sm { padding:6px 10px; font-size:12px; border-radius:6px; margin-top:4px; }
        .btn-danger { border-color:#fecaca; color:#b91c1c; background:#fff; }
        .btn-danger:hover { background:#fee2e2; }

        .badge { display:inline-block; padding:4px 8px; border-radius:9999px; font-size:12px; font-weight:600; }
        .bg-success { background-color:#d1fae5; color:#065f46; }
        .bg-secondary { background-color:#e5e7eb; color:#4b5563; }
        .text-muted-small { font-size:11px; color:#6b7280; }
        .text-left-list { list-style:disc; padding-left:20px; margin:0; }
        .text-left-list li { margin-bottom:4px; }
        .empty { color:#6b7280; padding:20px; text-align:center; }

        .btn-outline-secondary { border-color:#d1d5db; color:#4b5563; background:#fff; }
        .btn-outline-secondary:hover { background:#f9fafb; border-color:#9ca3af; }
        .btn-outline-primary { border-color:#bfdbfe; color:#1d4ed8; background:#fff; }
        .btn-outline-primary:hover { background:#eff6ff; border-color:#60a5fa; }
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
    <a href="${pageContext.request.contextPath}/listService" class="nav-link" style="text-decoration:underline;">Services</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminPromotion" class="nav-link">Promotions</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/staffs" class="nav-link">Staffs</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
</div>

<div class="container">
    <div class="table-card">
        <div class="table-head">
            <span>Danh Sách Gói Dịch Vụ</span>
            <a href="loadAddService" class="btn primary">+ Thêm mới</a>
        </div>

        <c:if test="${not empty message}">
            <div style="padding:10px 16px; margin:16px 16px 0 16px; border-radius:8px; background-color:#dbeafe; color:#1e40af; border:1px solid #93c5fd; text-align:center;">${message}</div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th class="col-id">ID</th>
                    <th class="col-name">Tên dịch vụ</th>
                    <th class="col-price">Giá (VNĐ)</th>
                    <th class="col-duration">Thời hạn (ngày)</th>
                    <th>Mô tả</th>
                    <th>Chức năng đi kèm</th>
                    <th>Khuyến mãi áp dụng</th>
                    <th class="col-visible">Trạng thái</th>
                    <th class="col-actions">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty serviceList}">
                        <c:forEach var="s" items="${serviceList}">
                            <tr>
                                <td class="col-id">${s.serviceID}</td>
                                <td class="col-name">${s.serviceName}</td>
                                <td class="col-price">
                                    <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="" maxFractionDigits="0"/>₫
                                </td>
                                <td class="col-duration">${s.duration}</td>
                                <td>${s.description}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${not empty s.functions}">
                                            <ul class="text-left-list">
                                                <c:forEach var="f" items="${s.functions}">
                                                    <li>${f.functionName}</li>
                                                </c:forEach>
                                            </ul>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted-small">—</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${not empty s.promotions}">
                                            <ul class="text-left-list">
                                                <c:forEach var="p" items="${s.promotions}">
                                                    <li>
                                                        <strong>${p.code}</strong> - Giảm 
                                                        <fmt:formatNumber value="${p.discount}" maxFractionDigits="0"/>%
                                                        <div class="text-muted-small">${p.dateSt} → ${p.dateEn}</div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted-small">—</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="col-visible">
                                    <c:choose>
                                        <c:when test="${s.isVisible}">
                                            <span class="badge bg-success">Hiển thị</span><br>
                                            <a href="editVisible?id=${s.serviceID}" 
                                               class="btn btn-outline-secondary btn-sm">
                                                Ẩn đi
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Đang ẩn</span><br>
                                            <a href="editVisible?id=${s.serviceID}" 
                                               class="btn btn-outline-primary btn-sm">
                                                Hiển thị
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="col-actions">
                                    <a href="deleteService?id=${s.serviceID}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa dịch vụ ${s.serviceName} không?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="9" class="empty">Hiện chưa có dịch vụ nào được tạo.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        
        </div>
</div>
</body>
</html>
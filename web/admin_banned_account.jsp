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
    <meta charset="UTF-8">
    <title>Admin Dashboard - Banned Accounts</title>
    <style>
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

        .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }

        .searchbar-wrap { background:#e9eef7; padding:14px 16px; display:flex; justify-content:center; border-bottom:1px solid #dbe2f1; }
        .searchbar { width:100%; max-width:900px; display:flex; gap:10px; align-items:center; background:#fff; border:1px solid #e5e7eb; border-radius:12px; padding:8px; }
        .searchbar input[type="text"]{ flex:1; padding:12px 14px; border:none; outline:none; font-size:14px; }
        .searchbar select { border:none; outline:none; padding:10px 8px; font-size:14px; background:transparent; }
        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
        .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }

        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

        .total-card { background:#ffffff; border-radius:14px; padding:22px 20px; margin-bottom:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); }
        .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
        .total-num { font-size:36px; font-weight:800; }

        .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
        .table-head { padding:16px 16px 0 16px; font-weight:700; font-size:18px; }
        table { width:100%; border-collapse:collapse; }
        thead th {
            text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb;
            background:#fafafa;
        }
        tbody td { padding:14px 16px; border-bottom:1px solid #f0f2f5; }
        tbody tr:hover { background:#fafafa; }
        .col-id { width:80px; font-variant-numeric:tabular-nums; color:#374151; }
        .col-email { font-weight:700; }
        .col-role { width:120px; }
        .col-reason { max-width:300px; }
        .col-date { width:180px; color:#6b7280; font-size:13px; }
        .col-actions { width:150px; text-align:right; }
        
        .badge { display:inline-block; padding:4px 10px; border-radius:999px; font-size:12px; font-weight:600; border:1px solid; }
        .badge-candidate { background:#dbeafe; color:#1e40af; border-color:#93c5fd; }
        .badge-employer { background:#fef3c7; color:#92400e; border-color:#fcd34d; }
        
        .btn-unban { border-color:#86efac; color:#166534; background:#fff; }
        .btn-unban:hover { background:#dcfce7; }

        .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; padding:14px 16px; background:#fff; }
        .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
        .pagination .active { background:#003366; color:#fff; border-color:#003366; }
        .empty { color:#6b7280; padding:20px; text-align:center; }

        .alert { padding:12px 16px; margin-bottom:16px; border-radius:8px; font-size:14px; }
        .alert-success { background:#dcfce7; color:#166534; border:1px solid #86efac; }
        .alert-error { background:#fee2e2; color:#991b1b; border:1px solid #fecaca; }

        /* Modal Styles */
        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 1000;
            align-items: center;
            justify-content: center;
        }
        .modal-overlay.active {
            display: flex;
        }
        .modal-content {
            background: #fff;
            border-radius: 16px;
            padding: 24px;
            width: 90%;
            max-width: 600px;
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
            max-height: 80vh;
            overflow-y: auto;
        }
        .modal-header {
            font-size: 20px;
            font-weight: 700;
            margin-bottom: 16px;
            color: #111827;
        }
        .modal-body {
            margin-bottom: 20px;
        }
        .modal-info {
            background: #f9fafb;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 16px;
            font-size: 14px;
            line-height: 1.6;
        }
        .modal-info strong {
            color: #374151;
        }
        .modal-footer {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        .btn-cancel {
            background: #fff;
            border: 1px solid #e5e7eb;
            color: #374151;
        }
        .btn-cancel:hover {
            background: #f9fafb;
        }
        .btn-confirm {
            background: #16a34a;
            border: 1px solid #16a34a;
            color: #fff;
        }
        .btn-confirm:hover {
            background: #15803d;
        }
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
    <a href="${pageContext.request.contextPath}/adminPromotion" class="nav-link">Promotions</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/staffs" class="nav-link">Staffs</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link">Jobs</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/bannedAccountList" class="nav-link" style="text-decoration:underline;">Banned</a>
</div>

<div class="searchbar-wrap">
    <form class="searchbar" method="get" action="">
        <input type="text" name="q" placeholder="Tìm theo email hoặc lý do ban" value="${q}"/>
        <select name="role">
            <option value="" ${empty role ? 'selected' : ''}>Tất cả vai trò</option>
            <option value="candidate" ${role == 'candidate' ? 'selected' : ''}>Candidate</option>
            <option value="employer" ${role == 'employer' ? 'selected' : ''}>Employer</option>
        </select>
        <button class="btn primary" type="submit">Tìm kiếm</button>
    </form>
</div>

<div class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <div class="total-card">
        <div class="total-title">Tổng tài khoản bị ban</div>
        <div class="total-num">${total}</div>
    </div>

    <div class="table-card">
        <div class="table-head">Danh Sách Tài Khoản Bị Ban</div>
        <table>
            <thead>
                <tr>
                    <th class="col-id">ID</th>
                    <th class="col-email">Email</th>
                    <th class="col-role">Vai trò</th>
                    <th class="col-reason">Lý do ban</th>
             
                    <th class="col-actions">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty bannedList}">
                        <c:forEach var="banned" items="${bannedList}">
                            <tr>
                                <td class="col-id">${banned.emailBannedId}</td>
                                <td class="col-email">${banned.email}</td>
                                <td class="col-role">
                                    <c:choose>
                                        <c:when test="${banned.role == 'Candidate'}">
                                            <span class="badge badge-candidate">Candidate</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-employer">Employer</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="col-reason">
                                    <c:choose>
                                        <c:when test="${banned.reason.length() > 50}">
                                            ${banned.reason.substring(0, 50)}...
                                        </c:when>
                                        <c:otherwise>
                                            ${banned.reason}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                             
                                <td class="col-actions">
                                    <button class="btn btn-unban" type="button" 
                                            onclick="openUnbanModal(${banned.emailBannedId}, '${banned.email}', '${banned.reason}')">
                                        Unban
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="6" class="empty">Không có tài khoản bị ban</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:url var="pageUrl" value="">
                    <c:param name="page" value="${i}"/>
                    <c:if test="${not empty q}">
                        <c:param name="q" value="${q}"/>
                    </c:if>
                    <c:if test="${not empty roleParam}">
                        <c:param name="role" value="${roleParam}"/>
                    </c:if>
                </c:url>
                <c:choose>
                    <c:when test="${i == page}">
                        <span class="active">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageUrl}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Unban Modal -->
<div id="unbanModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">Gỡ ban tài khoản</div>
        <form id="unbanForm" method="post" action="${pageContext.request.contextPath}/unban">
            <div class="modal-body">
                <p style="margin-bottom: 16px; color: #6b7280;">
                    Bạn có chắc chắn muốn gỡ ban cho tài khoản này?
                </p>
                <div class="modal-info">
                    <div style="margin-bottom: 8px;">
                        <strong>Email:</strong> <span id="modalEmail"></span>
                    </div>
                    <div>
                        <strong>Lý do ban:</strong><br>
                        <span id="modalReason" style="color: #6b7280;"></span>
                    </div>
                </div>
                <input type="hidden" id="bannedId" name="id">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-cancel" onclick="closeUnbanModal()">Hủy</button>
                <button type="submit" class="btn btn-confirm">Xác nhận Unban</button>
            </div>
        </form>
    </div>
</div>

<script>
    function openUnbanModal(bannedId, email, banReason) {
        document.getElementById('bannedId').value = bannedId;
        document.getElementById('modalEmail').textContent = email;
        document.getElementById('modalReason').textContent = banReason;
        document.getElementById('unbanModal').classList.add('active');
    }

    function closeUnbanModal() {
        document.getElementById('unbanModal').classList.remove('active');
    }

    // Close modal when clicking outside
    document.getElementById('unbanModal').addEventListener('click', function(e) {
        if (e.target === this) {
            closeUnbanModal();
        }
    });

    // Close modal with Escape key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeUnbanModal();
        }
    });

    // Auto-hide alerts
    window.addEventListener('DOMContentLoaded', function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(function(alert) {
            setTimeout(function() {
                alert.style.display = 'none';
            }, 5000);
        });
    });
</script>

</body>
</html>
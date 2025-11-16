<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

        .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }

        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

        .welcome-section {
            background:#ffffff; border-radius:14px; padding:28px 24px; margin-bottom:20px;
            box-shadow:0 2px 8px rgba(0,0,0,.06);
        }
        .welcome-title { font-size:28px; font-weight:800; margin-bottom:8px; color:#111827; }
        .welcome-subtitle { font-size:15px; color:#6b7280; }

        .stats-grid {
            display:grid; grid-template-columns:repeat(auto-fit, minmax(260px, 1fr)); gap:16px; margin-bottom:20px;
        }
        .stat-card {
            background:#ffffff; border-radius:14px; padding:22px 20px; box-shadow:0 2px 8px rgba(0,0,0,.06);
            border-left:4px solid #e5e7eb; transition: all 0.3s ease;
        }
        .stat-card:hover { transform: translateY(-4px); box-shadow:0 4px 12px rgba(0,0,0,.1); }
        .stat-card.blue { border-left-color:#003366; }
        .stat-card.orange { border-left-color:#ff7a00; }
        .stat-card.green { border-left-color:#16a34a; }
        .stat-card.red { border-left-color:#dc2626; }
        .stat-label { color:#6b7280; font-size:13px; margin-bottom:8px; text-transform:uppercase; font-weight:600; }
        .stat-number { font-size:32px; font-weight:800; color:#111827; margin-bottom:4px; }
        .stat-change { font-size:12px; color:#6b7280; }
        .stat-change.up { color:#16a34a; }
        .stat-change.down { color:#dc2626; }

        .dashboard-grid {
            display:grid; grid-template-columns:2fr 1fr; gap:16px; margin-bottom:20px;
        }

        .card {
            background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06);
            border:1px solid #e5e7eb; overflow:hidden;
        }
        .card-header {
            padding:16px 20px; font-weight:700; font-size:16px; border-bottom:1px solid #e5e7eb;
            background:#fafafa;
        }
        .card-body { padding:20px; }

        .activity-item {
            padding:12px 0; border-bottom:1px solid #f0f2f5; display:flex; gap:12px; align-items:start;
        }
        .activity-item:last-child { border-bottom:none; }
        .activity-icon {
            width:36px; height:36px; border-radius:50%; display:flex; align-items:center;
            justify-content:center; font-weight:700; font-size:14px; flex-shrink:0;
        }
        .activity-icon.blue { background:#dbeafe; color:#003366; }
        .activity-icon.orange { background:#ffedd5; color:#ff7a00; }
        .activity-icon.green { background:#dcfce7; color:#16a34a; }
        .activity-text { flex:1; }
        .activity-title { font-weight:600; font-size:14px; color:#111827; margin-bottom:2px; }
        .activity-time { font-size:12px; color:#6b7280; }

        .quick-stats {
            display:flex; flex-direction:column; gap:12px;
        }
        .quick-stat-item {
            padding:14px; background:#fafafa; border-radius:10px; display:flex;
            justify-content:space-between; align-items:center;
        }
        .quick-stat-label { font-size:13px; color:#6b7280; font-weight:600; }
        .quick-stat-value { font-size:20px; font-weight:800; color:#111827; }

        .pending-actions {
            display:flex; flex-direction:column; gap:10px;
        }
        .pending-item {
            padding:12px 14px; background:#fff7ed; border-left:3px solid #ff7a00;
            border-radius:8px; font-size:14px;
        }
        .pending-count {
            display:inline-block; background:#ff7a00; color:#fff; padding:2px 8px;
            border-radius:12px; font-size:12px; font-weight:700; margin-left:6px;
        }

        table { width:100%; border-collapse:collapse; }
        thead th {
            text-align:left; font-size:12px; color:#6b7280; padding:10px 12px;
            border-bottom:1px solid #e5e7eb; background:#fafafa; font-weight:600;
        }
        tbody td { padding:12px; border-bottom:1px solid #f0f2f5; font-size:14px; }
        tbody tr:hover { background:#fafafa; }

        .badge {
            display:inline-block; padding:4px 10px; border-radius:12px; font-size:11px;
            font-weight:700; text-transform:uppercase;
        }
        .badge.active { background:#dcfce7; color:#16a34a; }
        .badge.pending { background:#fef3c7; color:#ca8a04; }
        .badge.banned { background:#fee2e2; color:#dc2626; }

        @media (max-width: 968px) {
            .dashboard-grid { grid-template-columns:1fr; }
            .stats-grid { grid-template-columns:repeat(auto-fit, minmax(200px, 1fr)); }
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/admin_home.jsp" class="nav-link" style="text-decoration:underline;">Admin Dashboard</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/admin/candidates" class="nav-link">Candidates</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/admin/employers" class="nav-link">Employers</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/listService" class="nav-link">Services</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/adminPromotion" class="nav-link">Promotions</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/sale" class="nav-link">Orders</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/admin/staffs" class="nav-link">Staffs</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link">Jobs</a>
        <span class="divider">|</span>
        <a href="${pageContext.request.contextPath}/bannedAccountList" class="nav-link">Banned</a>
    </div>

    <div class="container">
        <!-- Welcome Section -->
        <div class="welcome-section">
            <div class="welcome-title">Welcome back, <%= user != null ? user.getUsername() : "Admin" %></div>
            <div class="welcome-subtitle">Here's what's happening with your platform today</div>
        </div>



       
    </div>
</body>
</html>
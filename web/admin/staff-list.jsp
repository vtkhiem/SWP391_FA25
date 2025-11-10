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
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Admin Dashboard - Staffs</title>
<style>

    body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

    .navbar {
        background:#003366; 
        color:#fff; padding:10px 20px; display:flex; gap:10px;
    }
    .navbar a { color:#fff; text-decoration:none; font-weight:normal; }
    .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
    .navbar .nav-link:hover { text-decoration:underline; }
    .navbar .divider { color:#fff; margin:0 5px; }

    .searchbar-wrap { background:#e9eef7; padding:14px 16px; display:flex; justify-content:center; border-bottom:1px solid #dbe2f1; }
    .searchbar { width:100%; max-width:1180px; display:flex; gap:10px; align-items:center; background:#fff; border:1px solid #e5e7eb; border-radius:12px; padding:8px; }
    .searchbar input[type="text"]{ flex:1; padding:12px 14px; border:none; outline:none; font-size:14px; }
    .searchbar select{ border:none; outline:none; padding:10px 8px; font-size:14px; background:transparent; }

    .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; display:inline-block;}
    .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
    .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
    .btn-danger { border-color:#fecaca; color:#b91c1c; background:#fff; padding:8px 12px; border-radius:8px; font-size:13px; }
    .btn-danger:hover { background:#fee2e2; }

    .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

    .total-card { background:#ffffff; border-radius:14px; padding:22px 20px; margin-bottom:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; }
    .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
    .total-num { font-size:36px; font-weight:800; line-height:1; }

    .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
    .table-head { 
        padding:16px 16px 0 16px; 
        font-weight:700; 
        font-size:18px; 
        display:flex; 
        justify-content:space-between; 
        align-items:center; 
    }
    
    table { width:100%; border-collapse:collapse; min-width:600px; }
    thead th {
        text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb;
        background:#fafafa; white-space:nowrap; font-weight:700;
    }
    tbody td { padding:14px 16px; border-bottom:1px solid #f0f2f5; vertical-align:middle; white-space:nowrap; }
    tbody tr:hover { background:#fafafa; }

    .col-id { width:100px; font-variant-numeric:tabular-nums; color:#374151; }
    .col-username { width:auto; font-weight:700; }
    .col-roles { width:auto; }
    .col-actions { width:150px; text-align:right; white-space:nowrap; }

    .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; padding:14px 16px; border-top:1px solid #e5e7eb; background:#fff; }
    .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
    .pagination .active { background:#003366; color:#fff; border-color:#003366; } 
    .empty { color:#6b7280; padding:20px; }

</style>
</head>
<body>
<div class="navbar">
     <a href="${pageContext.request.contextPath}/admin_home.jsp" class="nav-link">Admin Dashboard</a>
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
    <input type="text" name="q" placeholder="Tìm staff theo username" value="${q}"/>
    <select name="role">
      <option value="" ${empty param.role ? 'selected' : ''}>Tất cả</option>
      <option value="2" ${param.role == '2' ? 'selected' : ''}>Marketing Staff</option>
      <option value="3" ${param.role == '3' ? 'selected' : ''}>Sale</option>
    </select>

    <button class="btn primary" type="submit">Tìm kiếm</button>
  </form>
</div>

<div class="container">
  <div class="total-card">
    <div class="total-title">Tổng nhân viên</div>
    <div class="total-num">${total}</div>
  </div>
  
  <div class="table-card">
    <div class="table-head">
        <span>Danh Sách Nhân Viên</span>
         <form class="add" method="post" action="add-staff">  
    <button class="btn primary" type="submit">Add</button>
    </form>
    </div>

    <div class="table-scroll">
      <table>
        <thead>
          <tr>
            <th class="col-id">ID</th>
            <th class="col-username">Username</th>
            <th class="col-roles">Roles</th>
            <th class="col-actions">Action</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty staffs}">
              <c:forEach var="s" items="${staffs}">
                <tr>
                  <td class="col-id">${s.adminId}</td>
                  <td class="col-username">${s.username}</td>
                  <td class="col-roles">${s.roles}</td>
                  <td class="col-actions">
                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/staff/remove"
                          style="display:inline-block"
                          onsubmit="return confirm('Gỡ staff ${s.username} khỏi vai trò?');">
                      <input type="hidden" name="id" value="${s.adminId}">
                      <button class="btn btn-danger" type="submit">Ban</button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr><td colspan="4" class="empty">Không có dữ liệu</td></tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
    
    <div class="pagination">
      <c:forEach var="i" begin="1" end="${totalPages}">
        <c:url var="pageUrl" value="">
          <c:param name="page" value="${i}"/>
          <c:if test="${not empty q}">
            <c:param name="q" value="${q}"/>
          </c:if>
          <c:if test="${not empty param.role}">
            <c:param name="role" value="${param.role}"/>
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
</body>
</html>
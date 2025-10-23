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
  <title>Thêm nhân sự (Marketing / Sale)</title>
  <style>
    body { font-family: Arial, sans-serif; background:#f7f7f7; padding:24px; }
    .card { max-width:520px; margin:auto; background:#fff; padding:20px; border-radius:12px; box-shadow:0 4px 16px rgba(0,0,0,.08); }
    .row { margin-bottom:12px; }
    label { display:block; font-weight:600; margin-bottom:6px; }
    input, select { width:100%; padding:10px; border:1px solid #e5e7eb; border-radius:8px; }

    .actions { display:flex; gap:12px; margin-top:16px; }

    .btn {
      display:inline-block; padding:10px 14px; border-radius:12px; border:1px solid #e5e7eb;
      background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer;
    }
    a.btn:link, a.btn:visited, a.btn:hover, a.btn:active { color:inherit; text-decoration:none; }
    .btn-primary { background:#ff7a00; border-color:#ff7a00; color:#fff !important; }
    a.btn.btn-primary:link, a.btn.btn-primary:visited, a.btn.btn-primary:hover, a.btn.btn-primary:active {
      color:#fff !important; text-decoration:none;
    }
    .btn-primary:hover { filter:brightness(1.05); box-shadow:0 2px 6px rgba(0,0,0,.08); }

    .error { color:#b91c1c; margin-bottom:10px; }
    .ok { color:#0f766e; margin-bottom:10px; }
    small { color:#6b7280; }
  </style>
</head>
<body>
  <div class="card">
    <div style="display:flex; justify-content:flex-end; margin-bottom:12px;">
      <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/staffs">← Quay về Staffs</a>
    </div>

    <h2>Thêm nhân sự</h2>

    <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
    <c:if test="${not empty message}"><div class="ok">${message}</div></c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/add-staff" autocomplete="off">
      <div class="row">
        <label for="username">Username</label>
        <input id="username" name="username" value="${param.username}" required autocomplete="username"/>
      </div>

      <div class="row">
        <label for="password">Mật khẩu</label>
        <input id="password" type="text" name="password" required autocomplete="new-password"/>
       
      </div>

      <div class="row">
        <label for="roleId">Vai trò</label>
        <select id="roleId" name="roleId" required>
          <option value="">-- Chọn vai trò --</option>
          <option value="2" ${param.roleId == '2' ? 'selected' : ''}>Marketing Staff</option>
          <option value="3" ${param.roleId == '3' ? 'selected' : ''}>Sale</option>
        </select>
      </div>

      <div class="actions">
        <button class="btn btn-primary" type="submit">Thêm</button>
      </div>
    </form>
  </div>
</body>
</html>

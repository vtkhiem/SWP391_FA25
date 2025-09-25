<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    // Check quyền role
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
<title>Thêm ứng viên</title>
<style>
  body{margin:0;font-family:Arial, sans-serif;background:#f3f4f6;color:#111827}
  .navbar{background:#003366;color:#fff;display:flex;align-items:center;justify-content:space-between;padding:12px 20px}
  .brand a{color:#fff;text-decoration:none;font-weight:700}
  .container{max-width:900px;margin:20px auto;padding:0 16px}
  .card{background:#fff;border-radius:14px;box-shadow:0 2px 8px rgba(0,0,0,.06);padding:20px}
  .title{margin:0 0 12px}
  .alert{background:#fee2e2;color:#991b1b;padding:10px;border-radius:8px;margin-bottom:12px}
  .row{display:grid;grid-template-columns:180px 1fr;gap:12px;margin:10px 0}
  .row input{padding:10px;border:1px solid #e5e7eb;border-radius:8px}
  .btn{display:inline-block;padding:10px 14px;border-radius:10px;border:1px solid #e5e7eb;background:#fff;text-decoration:none;color:#111827;font-weight:600;cursor:pointer}
  .btn.primary{background:#ff7a00;color:#fff;border-color:#ff7a00}
</style>
</head>
<body>
  <div class="navbar">
    <div class="brand"><a href="${pageContext.request.contextPath}/admin/candidates">Admin Dashboard</a></div>
    <div></div>
  </div>

  <div class="container">
    <p><a class="btn" href="${pageContext.request.contextPath}/admin/candidates">← Quay lại</a></p>

    <div class="card">
      <h2 class="title">Thêm ứng viên</h2>

      <c:if test="${not empty error}">
        <div class="alert">${error}</div>
      </c:if>


      <form method="post" action="${pageContext.request.contextPath}/admin/candidate/add" accept-charset="UTF-8">
        <div class="row"><div>Tên *</div>
          <input name="name" value="${param.name}" required>
        </div>
        <div class="row"><div>Email *</div>
          <input name="email" type="email" value="${param.email}" required>
        </div>
        <div class="row"><div>Số điện thoại *</div>
          <input name="phone" value="${param.phone}" maxlength="15" required>
        </div>
        <div class="row"><div>Địa chỉ</div>
          <input name="address" value="${param.address}">
        </div>
        <div class="row"><div>Quốc tịch</div>
          <input name="nationality" value="${empty param.nationality ? 'Việt Nam' : param.nationality}">
        </div>
        <div class="row"><div>Mật khẩu *</div>
          <input name="password" type="text" minlength="6" value="${param.password}" required>
        </div>

        <div style="margin-top:12px;">
          <button class="btn primary" type="submit">Lưu</button>
          <a class="btn" href="${pageContext.request.contextPath}/admin/candidates">Hủy</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>

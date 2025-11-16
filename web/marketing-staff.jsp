<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.Admin" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("MarketingStaff")) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
    Admin user = (Admin) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Marketing Staff Dashboard</title>
<link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
<style>
  body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

  .navbar { background:#00366d; color:#fff; display:flex; align-items:center; justify-content:space-between; padding:12px 20px; position:sticky; top:0; z-index:10; }
  .brand a { color:#fff; text-decoration:none; font-weight:700; }
  .navbar .right { display:flex; gap:12px; align-items:center; }
  .navbar .right a { color:#fff; text-decoration:none; }

  .container { width:100%; max-width:1000px; margin:18px auto; padding:0 16px; }

  .cards { display:grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap:14px; }

  .card { background:#fff; border:1px solid #e5e7eb; border-radius:14px; padding:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); display:flex; flex-direction:column; gap:10px; }
  .card h3 { margin:0; font-size:18px; }
  .card p { margin:0; color:#6b7280; font-size:14px; line-height:1.5; }
  .actions { margin-top:10px; display:flex; gap:10px; flex-wrap:wrap; }

  .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; cursor:pointer; font-weight:600; text-decoration:none; color:#111827; display:inline-block; }
  .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
  .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
</style>
</head>
<body>
<div class="navbar">
  <div class="brand">
    <a>Marketing Dashboard</a>
  </div>
  <div class="right">
    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
  </div>
</div>

<div class="container">
  <div class="cards">
    <!-- Khuyến mãi -->
    <div class="card">
      <h3>Đăng tin khuyến mãi</h3>
      <p>Tạo mới tin khuyến mãi để thu hút ứng viên & nhà tuyển dụng.</p>
      <div class="actions">
        <a class="btn primary" href="${pageContext.request.contextPath}/post_promotion">Tạo khuyến mãi</a>
      </div>
    </div>

    <div class="card">
      <h3>Quản lý khuyến mãi</h3>
      <p>Xem và chỉnh sửa các chương trình khuyến mãi đã đăng.</p>
      <div class="actions">
        <a class="btn primary" href="${pageContext.request.contextPath}/listPromotion">Xem danh sách</a>
      </div>
    </div>

    <div class="card">
      <h3>Thống kê Marketing</h3>
      <p>Phân tích hiệu quả các chiến dịch theo thời gian.</p>
      <div class="actions">
        <a class="btn primary" href="${pageContext.request.contextPath}/marketingStatistics">Xem thống kê</a>
      </div>
    </div>
      
  </div>
</div>
</body>
</html>

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
<meta charset="UTF-8"><title>Chi tiết Employer</title>
<style>
  body{margin:0;font-family:Arial, sans-serif;background:#f3f4f6;color:#111827}
  .navbar{background:#00366d;color:#fff;display:flex;align-items:center;justify-content:space-between;padding:12px 20px}
  .brand a{color:#fff;text-decoration:none;font-weight:700}
  .container{max-width:1000px;margin:20px auto;padding:0 16px}
  .btn{display:inline-block;padding:10px 14px;border-radius:10px;border:1px solid #e5e7eb;background:#fff;text-decoration:none;color:#111827;font-weight:600}
  .card{background:#fff;border-radius:14px;box-shadow:0 2px 8px rgba(0,0,0,.06);padding:20px}
  .profile{display:grid;grid-template-columns:160px 1fr;gap:20px;align-items:center}
  .logo{width:150px;height:150px;border-radius:16px;object-fit:cover;background:#eee}
  .row{display:grid;grid-template-columns:180px 1fr;gap:12px;margin:8px 0}
  .label{color:#6b7280}
</style>
</head>
<body>
<div class="navbar">
  <div class="brand"><a href="${pageContext.request.contextPath}/admin/employers">Employers</a></div><div></div>
</div>

<div class="container">
  <p><a class="btn" href="${pageContext.request.contextPath}/admin/employers">← Quay lại danh sách</a></p>

  <div class="card">
    <div class="profile">
      <c:choose>
        <c:when test="${not empty item.imgLogo}">
          <img class="logo" src="${pageContext.request.contextPath}/uploads/${item.imgLogo}" alt="logo">
        </c:when>
        <c:otherwise>
          <img class="logo" src="${pageContext.request.contextPath}/static/img/company-default.png" alt="logo">
        </c:otherwise>
      </c:choose>

      <div>
        <h2 style="margin:0 0 8px;">${item.employerName}</h2>
        <div class="row"><div class="label">Công ty</div><div><c:out value="${item.companyName}"/></div></div>
        <div class="row"><div class="label">Email</div><div>${item.email}</div></div>
        <div class="row"><div class="label">Số điện thoại</div><div>${item.phoneNumber}</div></div>
        <div class="row"><div class="label">Địa điểm</div><div>${item.location}</div></div>
        <div class="row"><div class="label">Website</div><div><c:out value="${item.urlWebsite}"/></div></div>
        <div class="row"><div class="label">Mã số thuế</div><div><c:out value="${item.taxCode}"/></div></div>
        <div class="row"><div class="label">Mô tả</div><div style="white-space:pre-wrap;"><c:out value="${item.description}"/></div></div>
        <div class="row"><div class="label">Mã Employer</div><div>${item.employerId}</div></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>

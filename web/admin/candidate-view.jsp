<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chi tiết ứng viên</title>
<style>
  body{margin:0;font-family:Arial, sans-serif;background:#f3f4f6;color:#111827}
  .navbar{background:#003366;color:#fff;display:flex;align-items:center;justify-content:space-between;padding:12px 20px}
  .brand a{color:#fff;text-decoration:none;font-weight:700}
  .container{max-width:1000px;margin:20px auto;padding:0 16px}
  .btn{display:inline-block;padding:10px 14px;border-radius:10px;border:1px solid #e5e7eb;background:#fff;text-decoration:none;color:#111827;font-weight:600}
  .card{background:#fff;border-radius:14px;box-shadow:0 2px 8px rgba(0,0,0,.06);padding:20px}
  .profile{display:grid;grid-template-columns:160px 1fr;gap:20px;align-items:center}
  .avatar{width:150px;height:150px;border-radius:999px;object-fit:cover;background:#eee}
  .row{display:grid;grid-template-columns:180px 1fr;gap:12px;margin:8px 0}
  .label{color:#6b7280}
</style>
</head>
<body>
  <div class="navbar">
    <div class="brand"><a href="${pageContext.request.contextPath}/admin/candidates">Admin Dashboard</a></div>
    <div></div>
  </div>

  <div class="container">
    <p><a class="btn" href="${pageContext.request.contextPath}/admin/candidates">← Quay lại danh sách</a></p>

    <div class="card">
      <div class="profile">
        <c:choose>
          <c:when test="${not empty item.avatar}">
            <img class="avatar" src="${pageContext.request.contextPath}/uploads/${item.avatar}" alt="avatar">
          </c:when>
          <c:otherwise>
            <img class="avatar" src="${pageContext.request.contextPath}/static/img/avatar-default.png" alt="avatar">
          </c:otherwise>
        </c:choose>

        <div>
          <h2 style="margin:0 0 8px;">${item.candidateName}</h2>

          <div class="row"><div class="label">Email</div><div>${item.email}</div></div>
          <div class="row"><div class="label">Số điện thoại</div><div>${item.phoneNumber}</div></div>
          <div class="row"><div class="label">Địa chỉ</div><div>${item.address}</div></div>
          <div class="row"><div class="label">Quốc tịch</div><div>${item.nationality}</div></div>
          <div class="row"><div class="label">Mã ứng viên</div><div>${item.candidateId}</div></div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

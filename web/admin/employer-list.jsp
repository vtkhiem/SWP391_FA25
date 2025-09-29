<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8"><title>Admin Dashboard - Employers</title>
<style>
  body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }
  .navbar { background:#00366d; color:#fff; display:flex; align-items:center; justify-content:space-between; padding:12px 20px; position:sticky; top:0; z-index:10; }
  .brand a { color:#fff; text-decoration:none; font-weight:700; }
  .searchbar-wrap { background:#e9eef7; padding:14px 16px; display:flex; justify-content:center; border-bottom:1px solid #dbe2f1; }
  .searchbar { width:100%; max-width:900px; display:flex; gap:10px; align-items:center; background:#fff; border:1px solid #e5e7eb; border-radius:12px; padding:8px; }
  .searchbar input{ flex:1; padding:12px 14px; border:none; outline:none; font-size:14px; }
  .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; cursor:pointer; font-weight:600; text-decoration:none; color:#111827;}
  .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
  .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }
  .total-card { background:#fff; border-radius:14px; padding:22px 20px; margin-bottom:12px; box-shadow:0 2px 8px rgba(0,0,0,.06); }
  .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; } .total-num{ font-size:36px; font-weight:800; }
  .toolbar { display:flex; justify-content:flex-end; margin:10px 0; }
  .employer-card { background:#fff; border-radius:10px; padding:16px; margin-bottom:12px; display:flex; align-items:center; gap:16px; box-shadow:0 2px 6px rgba(0,0,0,.06); }
  .employer-card img.logo { width:60px; height:60px; border-radius:12px; object-fit:cover; background:#eee; }
  .muted { color:#6b7280; font-size:13px; }
  .actions { display:flex; gap:8px; align-items:center; }
  .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; margin-top:14px; }
  .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
  .pagination .active { background:#00366d; color:#fff; border-color:#00366d; }
  .alert { padding:10px; border-radius:8px; margin-bottom:10px; }
  .ok { background:#f0fdf4; color:#166534; } .err{ background:#fee2e2; color:#991b1b; }
</style>
</head>
<body>
<div class="navbar">
  <div class="brand">
    <a href="${pageContext.request.contextPath}/admin/employers">Admin Dashboard</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/admin/candidates" style="color:#fff; text-decoration:none;">Candidates</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/admin/employers" style="color:#fff; text-decoration:none;">Employers</a>
</div>
  <div></div>
</div>

<div class="searchbar-wrap">
  <form class="searchbar" method="get" action="">
    <input type="text" name="q" placeholder="Tìm employer theo tên, email, công ty, SĐT" value="${q}"/>
    <button class="btn primary" type="submit">Tìm kiếm</button>
  </form>
</div>

<div class="container">

  <div class="total-card">
    <div class="total-title">Tổng employer</div>
    <div class="total-num">${total}</div>
   
  </div>

  <div class="toolbar">
    <a class="btn" href="${pageContext.request.contextPath}/admin/employer/add">+ Thêm employer</a>
  </div>

  <h3>Danh Sách Employer</h3>

  <c:forEach var="e" items="${employers}">
    <div class="employer-card">
      <c:choose>
        <c:when test="${not empty a}">
          <img class="logo" src="${pageContext.request.contextPath}/uploads/${e.imgLogo}" alt="logo">
        </c:when>
        <c:otherwise>
          <img class="logo" src="${pageContext.request.contextPath}/static/img/company-default.png" alt="logo">
        </c:otherwise>
      </c:choose>

      <div style="flex:1;">
        <div style="font-weight:700; font-size:16px;">${e.employerName}</div>
        <div class="muted">Công ty: <b><c:out value="${e.companyName}"/></b></div>
        <div class="muted">Email: ${e.email} • SĐT: ${e.phoneNumber}</div>
        <div class="muted">Địa điểm: ${e.location}</div>
        <c:if test="${not empty e.urlWebsite}">
          <div class="muted">Website:
            <a href="//${e.urlWebsite}" target="_blank" rel="noopener noreferrer">${e.urlWebsite}</a>
          </div>
        </c:if>
       
      </div>

      <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/admin/employer/view?id=${e.employerId}">View</a>
        <form method="post" action="${pageContext.request.contextPath}/admin/employer/delete"
              onsubmit="return confirm('Xoá employer ${e.employerName}?');">
          <input type="hidden" name="id" value="${e.employerId}">
          <button class="btn" style="border-color:#fecaca;color:#b91c1c;background:#fff;">Delete</button>
        </form>
      </div>
    </div>
  </c:forEach>

  <c:if test="${empty employers}">
    <div class="muted" style="padding:24px;">Không có dữ liệu</div>
  </c:if>

  <div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPages}">
      <c:choose>
        <c:when test="${i == page}">
            <span class="active">${i}</span>
        </c:when>
        <c:otherwise>
            <a href="?page=${i}&q=${q}">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>
</div>
</body>
</html>

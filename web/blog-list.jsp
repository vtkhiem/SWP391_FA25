<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="model.Admin" %>
<%
    // (tuỳ chọn) chặn truy cập nếu không phải MarketingStaff
    String role = (String) session.getAttribute("role");
    if (role == null || !"MarketingStaff".equals(role)) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
    Admin user = (Admin) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quản lý Blog Post</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <style>
    :root{ --bg:#f3f4f6; --card:#fff; --text:#111827; --muted:#6b7280; --line:#e5e7eb; }

    *{box-sizing:border-box}
    body{margin:0; font-family:Arial, Helvetica, sans-serif; background:var(--bg); color:var(--text)}

    /* ===== HEADER (giống trang Marketing Dashboard) ===== */
    .navbar { background:#00366d; color:#fff; display:flex; align-items:center; justify-content:space-between;
              padding:12px 20px; position:sticky; top:0; z-index:10; }
    .brand a { color:#fff; text-decoration:none; font-weight:700; }
    .navbar .right { display:flex; gap:12px; align-items:center; }
    .navbar .right a { color:#fff; text-decoration:none; }

    /* ===== PAGE CONTENT ===== */
    .wrap{max-width:1100px; margin:18px auto; padding:0 16px}
    h1{font-size:24px; margin:12px 0 16px}

    .table-card{background:var(--card); border:1px solid var(--line); border-radius:12px; overflow:hidden;
                box-shadow:0 2px 8px rgba(0,0,0,.06)}
    table{width:100%; border-collapse:collapse}
    thead th{background:#f8fafc; font-size:12px; color:#6b7280; text-transform:uppercase; letter-spacing:.5px; text-align:left; padding:12px}
    tbody td{padding:12px; border-top:1px solid var(--line); vertical-align:middle}
    .title-cell a{color:#111; text-decoration:none}
    .title-cell a:hover{text-decoration:underline}
    .small{color:var(--muted); font-size:12px}

    .badge{font-size:12px; padding:4px 8px; border-radius:999px; display:inline-block; font-weight:700}
    .badge.pub{background:#dcfce7; color:#166534}   /* published */
    .badge.arc{background:#e5e7eb; color:#374151}   /* archived */

    .actions a{padding:6px 10px; border-radius:8px; text-decoration:none; border:1px solid var(--line); color:#111; background:#fff}
    .actions a:hover{box-shadow:0 2px 6px rgba(0,0,0,.08)}

    .pagination{display:flex; gap:8px; justify-content:flex-end; padding:12px; border-top:1px solid var(--line); background:#fafafa}
    .page-btn{padding:6px 10px; border-radius:8px; text-decoration:none; color:#111; background:#e5e7eb}
    .page-btn.active{background:#111; color:#fff}
  </style>
</head>
<body>

  <!-- HEADER Marketing -->
  <div class="navbar">
    <div class="brand">
      <a>Marketing Dashboard</a>
    </div>
    <div class="right">
      <a href="${pageContext.request.contextPath}/logStaff">Quay về trang chủ</a>
      <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </div>
  </div>

  <!-- NỘI DUNG -->
  <div class="wrap">
    <h1>Quản lý Blog Post</h1>

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th style="width:70px;">ID</th>
            <th>Title</th>
            <th style="width:220px;">Category</th>
            <th style="width:140px;">Published</th>
            <th style="width:120px;">Status</th>
            <th style="width:130px;">Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${items}">
            <tr>
              <td>${p.postID}</td>
              <td class="title-cell">
                <a href="${pageContext.request.contextPath}${p.url}" target="_blank" title="Xem bài viết">${p.title}</a>
                <div class="small">${p.url}</div>
              </td>
              <td>${p.categoryName}</td>
              <td>
                <c:if test="${not empty p.publishedDate}">
                  <fmt:formatDate value="${p.publishedDate}" pattern="dd/MM/yyyy"/>
                </c:if>
              </td>
              <td>
                <c:choose>
                  <c:when test="${p.status}"><span class="badge pub">published</span></c:when>
                  <c:otherwise><span class="badge arc">archived</span></c:otherwise>
                </c:choose>
              </td>
              <td class="actions">
                <a href="${pageContext.request.contextPath}/blog-list/edit?id=${p.postID}">View detail</a>
              </td>
            </tr>
          </c:forEach>

          <c:if test="${empty items}">
            <tr><td colspan="6" style="text-align:center; padding:20px; color:#6b7280">Chưa có bài viết.</td></tr>
          </c:if>
        </tbody>
      </table>

      <c:if test="${totalPages > 1}">
        <div class="pagination">
          <c:forEach var="i" begin="1" end="${totalPages}">
            <a class="page-btn ${i == page ? 'active' : ''}"
               href="${pageContext.request.contextPath}/blog-list?page=${i}">${i}</a>
          </c:forEach>
        </div>
      </c:if>
    </div>
  </div>

</body>
</html>

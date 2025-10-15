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
<title>Admin Dashboard - Employers</title>
<style>
  body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

  .navbar { background:#00366d; color:#fff; display:flex; align-items:center; justify-content:space-between; padding:12px 20px; position:sticky; top:0; z-index:10; }
  .brand a { color:#fff; text-decoration:none; font-weight:700; }
  .navbar a.nav { color:#fff; text-decoration:none; }
  .navbar .divider { margin:0 6px; color:#fff; }

  .searchbar-wrap { background:#e9eef7; padding:14px 16px; display:flex; justify-content:center; border-bottom:1px solid #dbe2f1; }
  .searchbar { width:100%; max-width:900px; display:flex; gap:10px; align-items:center; background:#fff; border:1px solid #e5e7eb; border-radius:12px; padding:8px; }
  .searchbar input{ flex:1; padding:12px 14px; border:none; outline:none; font-size:14px; }
  .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; cursor:pointer; font-weight:600; text-decoration:none; color:#111827; }
  .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
  .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }
  .btn-danger { border-color:#fecaca; color:#b91c1c; background:#fff; }
  .btn-danger:hover { background:#fee2e2; }
  .btn-success { border-color:#86efac; color:#166534; background:#fff; }
  .btn-success:hover { background:#dcfce7; }

  .container { width:80%; max-width:100%; margin:18px auto; padding:0 16px; }

  .total-card { background:#ffffff; border:1px solid #e5e7eb; border-radius:14px; padding:20px; box-shadow:0 2px 8px rgba(0,0,0,.06); margin-bottom:12px; }
  .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
  .total-num { font-size:36px; font-weight:800; line-height:1; }

  .toolbar { display:flex; justify-content:flex-end; margin:10px 0; }

  .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
  .table-head { padding:16px 16px 0 16px; font-weight:700; font-size:18px; }


  .table-scroll { overflow-x: visible; }

  table {
    width:100%;
    border-collapse:collapse;
    min-width:0;          
    table-layout:fixed;   
  }

  thead th {
      text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb;
      background:#fafafa;
      white-space:normal;        
      word-wrap:break-word;
      overflow-wrap:anywhere;
  }
  tbody td {
      padding:14px 16px; border-bottom:1px solid #f0f2f5; vertical-align:middle;
      white-space:normal;         
      word-wrap:break-word;
      overflow-wrap:anywhere;
  }
  tbody tr:hover { background:#fafafa; }

  .col-id { width:6%; font-variant-numeric:tabular-nums; color:#374151; }
  .col-name { width:16%; }
  .col-company { width:14%; }
  .col-email { width:16%; }
  .col-phone { width:10%; }
  .col-loc { width:12%; }
  .col-web { width:12%; }
  .col-tax { width:8%; }
  .col-status { width:6%; }
  .col-actions { width:10%; text-align:right; white-space:nowrap; } 

  .col-web a,
  .col-email,
  .col-tax,
  .col-company,
  .col-loc,
  .col-name {
    word-break:break-word;
  }

  .badge { display:inline-block; padding:6px 10px; border-radius:999px; font-size:12px; font-weight:700; border:1px solid #e5e7eb; }
  .badge-ok { background:#ecfdf5; color:#065f46; border-color:#a7f3d0; }
  .badge-no { background:#fef2f2; color:#991b1b; border-color:#fecaca; }

  .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; padding:14px 16px; background:#fff; }
  .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
  .pagination .active { background:#00366d; color:#fff; border-color:#00366d; }
</style>

</head>
<body>
<div class="navbar">
  <div class="brand">
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
  </div>
  <div></div>
</div>

<div class="searchbar-wrap">
  <form class="searchbar" method="get" action="">
    <input type="text" name="q" placeholder="Tìm employer theo tên, email, công ty, SĐT" value="${q}"/>

    <select name="status" style="border:none; outline:none; padding:10px 8px; font-size:14px;">
      <option value=""  ${empty status ? 'selected' : ''}>Tất cả</option>
      <option value="true"  ${status == 'true'  ? 'selected' : ''}>Verified</option>
      <option value="false" ${status == 'false' ? 'selected' : ''}>Not Verified</option>
    </select>

    <button class="btn primary" type="submit">Tìm kiếm</button>
  </form>
</div>

<div class="container">
  <div class="total-card">
    <div class="total-title">Tổng nhà tuyển dụng</div>
    <div class="total-num">${total}</div>
  </div>

  <div class="table-card">
    <div class="table-head">Danh Sách nhà tuyển dụng</div>
    <div class="table-scroll">
      <table>
        <thead>
          <tr>
            <th class="col-id">ID</th>
            <th class="col-name">Tên</th>
            <th class="col-company">Công ty</th>
            <th class="col-email">Email</th>
            <th class="col-phone">SĐT</th>
            <th class="col-loc">Địa điểm</th>
            <th class="col-web">Website</th>
            <th class="col-tax">TaxCode</th>
            <th class="col-status">Status</th>
            <th class="col-actions">Action</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty employers}">
              <c:forEach var="e" items="${employers}">
                <tr>
                  <td class="col-id">${e.employerId}</td>
                  <td class="col-name"><strong>${e.employerName}</strong></td>
                  <td class="col-company"><c:out value="${e.companyName}"/></td>
                  <td class="col-email">${e.email}</td>
                  <td class="col-phone">${e.phoneNumber}</td>
                  <td class="col-loc">${e.location}</td>
                  <td class="col-web">
                    <c:if test="${not empty e.urlWebsite}">
                      <a href="//${e.urlWebsite}" target="_blank" rel="noopener noreferrer">${e.urlWebsite}</a>
                    </c:if>
                    <c:if test="${empty e.urlWebsite}">—</c:if>
                  </td>
                  <td class="col-tax">${e.taxCode}</td>

                  <!-- STATUS: boolean -->
                  <td class="col-status">
                    <c:choose>
                      <c:when test="${e.status}">
                        <span class="badge badge-ok">Verified</span>
                      </c:when>
                      <c:otherwise>
                        <span class="badge badge-no">Not Verified</span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td class="col-actions">
                    <c:if test="${not e.status}">
                      <form method="post"
                            action="${pageContext.request.contextPath}/admin/employer/verify"
                            style="display:inline-block; margin-right:8px;"
                            onsubmit="return confirm('Xác minh employer ${e.employerName}?');">
                        <input type="hidden" name="id" value="${e.employerId}">
                        <button class="btn btn-success" type="submit">Verify</button>
                      </form>
                    </c:if>

                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/employer/delete"
                          style="display:inline-block"
                          onsubmit="return confirm('Banned employer ${e.employerName}?');">
                      <input type="hidden" name="id" value="${e.employerId}">
                      <button class="btn btn-danger" type="submit">Ban</button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr><td colspan="10" style="color:#6b7280;padding:20px;">Không có dữ liệu</td></tr>
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
          <c:if test="${not empty status}">
            <c:param name="status" value="${status}"/>
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

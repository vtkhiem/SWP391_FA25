<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <title>Admin Dashboard - Quản lý Bài Đăng</title>
    <style>
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }

        .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }
        .navbar .nav-link.active { color:#ff7a00; text-decoration:underline; }

        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
        .btn.primary { background:#ff7a00; color:#fff; border-color:#ff7a00; }

        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; box-sizing: border-box; }

        .total-card { background:#ffffff; border-radius:14px; padding:22px 20px; margin-bottom:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); }
        .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
        .total-num { font-size:36px; font-weight:800; }

        .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
        .table-head { padding:16px 16px 0 16px; font-weight:700; font-size:18px; }
        table { width:100%; border-collapse:collapse; }
        thead th {
            text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb;
            background:#fafafa;
        }
        tbody td { padding:14px 16px; border-bottom:1px solid #f0f2f5; font-size: 14px; }
        tbody tr:hover { background:#fafafa; }
        .col-id { width:80px; font-variant-numeric:tabular-nums; color:#374151; }
        .col-name { font-weight:700; }
        .col-actions { width:180px; text-align:right; }
        .btn-danger { border-color:#fecaca; color:#b91c1c; background:#fff; }
        .btn-danger:hover { background:#fee2e2; }

        .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; padding:14px 16px; background:#fff; }
        .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
        .pagination .active { background:#003366; color:#fff; border-color:#003366; }
        .empty { color:#6b7280; padding:20px; text-align: center; }

        .filter-card { background:#ffffff; border-radius:14px; padding:20px; margin-bottom:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); }
        .filter-grid { display:grid; grid-template-columns: repeat(3, 1fr); gap: 16px; }
        .filter-grid .form-group { display:flex; flex-direction:column; }
        .filter-grid .form-group label { margin-bottom: 6px; font-size: 14px; color: #374151; font-weight: 600; }
        .filter-grid input[type="text"],
        .filter-grid input[type="number"],
        .filter-grid select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            font-size: 14px;
            background: #fff;
            box-sizing: border-box;
        }
        .filter-grid .salary-group { display: flex; gap: 8px; }
        .filter-grid .submit-btn { grid-column: 3 / 4; align-self: end; }
        
        /* CSS cho Trạng thái */
        .status-badge {
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }
        .status-visible {
            background-color: #d1fae5; /* Green light */
            color: #065f46; /* Green dark */
        }
        .status-hidden {
            background-color: #fee2e2; /* Red light */
            color: #991b1b; /* Red dark */
        }
        
    </style>
</head>
<body>

<div class="navbar">
    <a>Admin Dashboard</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link active">Bài Đăng</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/candidates" class="nav-link">Candidates</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/employers" class="nav-link">Employers</a>
    </div>

<div class="container">

    <div class="filter-card">
        <h4 style="font-weight:700; font-size:18px; margin-bottom: 16px;">Bộ lọc bài đăng</h4>
        <form id="filterForm" action="adminJobpostList" method="get" class="filter-grid">
            
            <div class="form-group">
                <label>Vị trí tuyển dụng</label>
                <input type="text" name="keyword" placeholder="Vị trí tuyển dụng..." value="${param.keyword}">
            </div>
            <div class="form-group">
                <label>Ngành nghề</label>
                <select name="category">
                    <option value="">Tất cả ngành nghề</option>
                    <option value="Công nghệ thông tin" ${param.category == 'Công nghệ thông tin' ? 'selected' : ''}>Công nghệ thông tin</option>
                    </select>
            </div>
            <div class="form-group">
                <label>Vị trí</label>
                <select name="location">
                    <option value="">Toàn quốc</option>
                    <option value="Hà Nội" ${param.location == 'Hà Nội' ? 'selected' : ''}>TP. Hà Nội</option>
                    </select>
            </div>
            <div class="form-group">
                <label>Mức lương</label>
                <div class="salary-group">
                    <input type="number" name="minSalary" placeholder="Từ..." value="${param.minSalary}">
                    <input type="number" name="maxSalary" placeholder="Đến..." value="${param.maxSalary}">
                </div>
            </div>
            <div class="form-group">
                <label>Yêu cầu kinh nghiệm</label>
                <select name="numberExp">
                    <option value="" ${param.numberExp == '' ? 'selected' : ''}>Tất cả</option>
                    <option value="0" ${param.numberExp == '0' ? 'selected' : ''}>Không yêu cầu kinh nghiệm</option>
                    </select>
            </div>
            <div class="form-group">
                <label>Hình thức làm việc</label>
                <select name="jobType">
                    <option value="" ${param.jobType == '' ? 'selected' : ''}>Tất cả</option>
                    <option value="Full-time" ${param.jobType == 'Full-time' ? 'selected' : ''}>Toàn thời gian (Full-time)</option>
                    </select>
            </div>
            <div class="form-group submit-btn">
                <button type="submit" class="btn primary" style="width:100%;">Lọc</button>
            </div>
        </form>
    </div>

    <div class="total-card">
        <div class="total-title">Tổng Bài Đăng</div>
        <div class="total-num">${totalJobs}</div>
    </div>

    <div class="table-card">
        <div class="table-head">Danh Sách Bài Đăng</div>
        <table>
            <thead>
                <tr>
                    <th class="col-id">Post ID</th>
                    <th class="col-id">Emp ID</th> <th>Tiêu đề</th>
                    <th>Vị trí</th>
                    <th>Hình thức</th>
                    <th>Trạng thái</th> <th class="col-actions">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty jobs}">
                        <c:forEach var="job" items="${jobs}">
                            <tr>
                                <td class="col-id">${job.jobPostID}</td>
                                <td class="col-id">${job.employerID}</td> <td class="col-name">${job.title}</td>
                                <td>${job.location}</td>
                                <td>${job.typeJob}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${job.visible}">
                                            <span class="status-badge status-visible">Đang hiện</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-hidden">Đã ẩn</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="col-actions">
                                    <a href="job_details?id=${job.jobPostID}" class="btn">Xem</a>
                                   <c:if test="${job.visible}">
                                        <form method="post" action="adminHideJob"
                                              style="display:inline-block;margin-left:8px;"
                                              onsubmit="return confirm('Bạn có chắc muốn ẩn bài đăng \'${job.title}\'?');">
                                            <input type="hidden" name="jobPostID" value="${job.jobPostID}">
                                            <button class="btn btn-danger" type="submit">Ẩn</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="7" class="empty">Không có dữ liệu</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="pagination">
            <c:url var="pageUrl" value="adminJobpostList">
                <c:if test="${not empty param.keyword}"><c:param name="keyword" value="${param.keyword}"/></c:if>
                <c:if test="${not empty param.category}"><c:param name="category" value="${param.category}"/></c:if>
                <c:if test="${not empty param.location}"><c:param name="location" value="${param.location}"/></c:if>
                <c:if test="${not empty param.minSalary}"><c:param name="minSalary" value="${param.minSalary}"/></c:if>
                <c:if test="${not empty param.maxSalary}"><c:param name="maxSalary" value="${param.maxSalary}"/></c:if>
                <c:if test="${not empty param.numberExp}"><c:param name="numberExp" value="${param.numberExp}"/></c:if>
                <c:if test="${not empty param.jobType}"><c:param name="jobType" value="${param.jobType}"/></c:if>
            </c:url>
            
            <c:set var="separator" value="${fn:contains(pageUrl, '?') ? '&' : '?'}" />

            <c:if test="${currentPage > 1}">
                <a href="${pageUrl}${separator}page=${currentPage-1}">&lt;</a>
            </c:if>

            <c:forEach var="i" begin="1" end="${noOfPages}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span class="active">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageUrl}${separator}page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

             <c:if test="${currentPage < noOfPages}">
                <a href="${pageUrl}${separator}page=${currentPage+1}">&gt;</a>
            </c:if>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("filterForm");
        const minInput = document.querySelector('input[name="minSalary"]');
        const maxInput = document.querySelector('input[name="maxSalary"]');

        form.addEventListener("submit", function (e) {
            const min = parseFloat(minInput.value);
            const max = parseFloat(maxInput.value);

            if (!isNaN(min) && !isNaN(max) && max < min) {
                e.preventDefault(); 
                alert("Mức lương tối đa phải lớn hơn hoặc bằng mức lương tối thiểu!");
            }
        });
    });
</script>

</body>
</html>
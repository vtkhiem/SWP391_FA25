<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="model.ServiceEmployerHistory, java.time.format.DateTimeFormatter"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="model.Admin" %>
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

            body {
                margin:0;
                font-family: Arial, sans-serif;
                background:#f3f4f6;
                color:#111827;
            }

            .navbar {

                background:#003366;
                color:#fff;
                padding:10px 20px;
                display:flex;
                gap:10px;
            }
            .navbar a {
                color:#fff;
                text-decoration:none;
                font-weight:normal;
            }
            .navbar .nav-link {
                color:#fff;
                text-decoration:none;
                font-weight:bold;
            }
            .navbar .nav-link:hover {
                text-decoration:underline;
            }
            .navbar .divider {
                color:#fff;
                margin:0 5px;
            }

            .searchbar-wrap {
                background:#e9eef7;
                padding:14px 16px;
                display:flex;
                justify-content:center;
                border-bottom:1px solid #dbe2f1;
            }
            .searchbar {
                width:100%;
                max-width:1180px;
                display:flex;
                gap:10px;
                align-items:center;
                background:#fff;
                border:1px solid #e5e7eb;
                border-radius:12px;
                padding:8px;
            }
            .searchbar input[type="text"]{
                flex:1;
                padding:12px 14px;
                border:none;
                outline:none;
                font-size:14px;
            }
            .searchbar select {
                border:none;
                outline:none;
                padding:10px 8px;
                font-size:14px;
                background:transparent;
            }

            .btn {
                padding:10px 14px;
                border-radius:10px;
                border:1px solid #e5e7eb;
                background:#fff;
                text-decoration:none;
                color:#111827;
                font-weight:600;
                cursor:pointer;
            }
            .btn:hover {
                box-shadow:0 2px 6px rgba(0,0,0,.08)
            }
            .btn.primary {
                background:#ff7a00;
                color:#fff;
                border-color:#ff7a00;
            }

            .btn-danger {
                border-color:#fecaca;
                color:#b91c1c;
                background:#fff;
                padding:8px 12px;
                border-radius:8px;
                font-size:13px;
            }
            .btn-danger:hover {
                background:#fee2e2;
            }
            .btn-success {
                border-color:#86efac;
                color:#166534;
                background:#fff;
                padding:8px 12px;
                border-radius:8px;
                font-size:13px;
            }
            .btn-success:hover {
                background:#dcfce7;
            }

            .container {
                width:100%;
                max-width:1880px;
                margin:18px auto;
                padding:0 16px;
            }

            .total-card {
                background:#ffffff;
                border-radius:14px;
                padding:22px 20px;
                margin-bottom:16px;
                box-shadow:0 2px 8px rgba(0,0,0,.06);
                border:1px solid #e5e7eb;
            }
            .total-title {
                color:#6b7280;
                font-size:14px;
                margin-bottom:6px;
            }
            .total-num {
                font-size:36px;
                font-weight:800;
                line-height:1;
            }

            .table-card {
                background:#ffffff;
                border-radius:14px;
                box-shadow:0 2px 8px rgba(0,0,0,.06);
                border:1px solid #e5e7eb;
                overflow:hidden;
            }
            .table-head {
                padding:16px 16px 0 16px;
                font-weight:700;
                font-size:18px;
            }

            table {
                width:100%;
                border-collapse:collapse;
                table-layout:fixed;
            }
            thead th {
                text-align:left;
                font-size:13px;
                color:#6b7280;
                padding:12px 16px;
                border-bottom:1px solid #e5e7eb;
                background:#fafafa;
                white-space:normal;
                word-wrap:break-word;
                overflow-wrap:anywhere;
                font-weight:700;
            }
            tbody td {
                padding:14px 16px;
                border-bottom:1px solid #f0f2f5;
                vertical-align:middle;
                white-space:normal;
                word-wrap:break-word;
                overflow-wrap:anywhere;
            }
            tbody tr:hover {
                background:#fafafa;
            }

            .col-id {
                width:4%;
                font-variant-numeric:tabular-nums;
                color:#374151;
            }
            .col-name {
                width:15%;
                font-weight:700;
            }
            .col-company {
                width:12%;
            }
            .col-email {
                width:20%;
            }
            .col-phone {
                width:10%;
            }
            .col-loc {
                width:10%;
            }
            .col-web {
                width:20%;
            }
            .col-tax {
                width:15%;
            }
            .col-status {
                width:10%;
                text-align:center;
            }
            .col-actions {
                width:15%;
                text-align:right;
                white-space:nowrap;
            }

            .badge {
                display:inline-block;
                padding:4px 8px;
                border-radius:999px;
                font-size:12px;
                font-weight:600;
                border:1px solid;
            }
            .badge-ok {
                background:#ecfdf5;
                color:#065f46;
                border-color:#a7f3d0;
            }
            .badge-no {
                background:#fef2f2;
                color:#991b1b;
                border-color:#fecaca;
            }

            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 25px;
                list-style: none;
                gap: 5px;
            }

            .pagination .page-item {
                display: inline-block;
            }

            .pagination .page-link {
                display: inline-block;
                padding: 8px 14px;
                color: #007bff;
                border: 1px solid #dee2e6;
                border-radius: 6px;
                text-decoration: none;
                background-color: #fff;
                transition: all 0.25s ease;
                font-weight: 500;
            }

            .pagination .page-link:hover {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }

            .pagination .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
                color: #fff;
                cursor: default;
            }

            .pagination .page-item.disabled .page-link {
                color: #aaa;
                background-color: #f8f9fa;
                border-color: #dee2e6;
                cursor: not-allowed;
                pointer-events: none;
            }

            .pagination .page-link {
                min-width: 36px;
                text-align: center;
            }

            .pagination .page-link:focus {
                outline: none;
                box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.3);
            }

            .empty {
                color:#6b7280;
                padding:20px;
            }
        </style>

    </head>
    <body>
        <div class="navbar">
            <a>Admin Dashboard</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/admin/candidates" class="nav-link">Candidates</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/admin/employers" class="nav-link" style="text-decoration:underline;">Employers</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/listService" class="nav-link">Services</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/adminPromotion" class="nav-link">Promotions</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/admin/staffs" class="nav-link">Staffs</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
        </div>

        <div class="searchbar-wrap">
            <form class="searchbar" method="get" action="">
                <input type="text" name="q" placeholder="Tìm employer theo tên, email, công ty, SĐT" value="${q}"/>

                <select name="status">
                    <option value="" ${empty status ? 'selected' : ''}>Tất cả</option>
                    <option value="true" ${status == 'true' ? 'selected' : ''}>Verified</option>
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

                <!-- payment-history-area -->
                <div class="payment_history_area">
                    <div class="container p-0">
                        <div class="row">
                            <div class="col-12">
                                <div class="table-responsive mt-3">
                                    <table class="table table-hover table-bordered">
                                        <thead class="thead-light">
                                            <tr>
                                                <th style="width:60px">No</th>
                                                <th style="width:250px">Tên dịch vụ</th>
                                                <th style="width:200px">Ngày đăng ký</th>
                                                <th style="width:200px">Ngày hết hạn</th>
                                                <th style="width:180px">Trạng thái thanh toán</th>
                                                <th style="width:180px">Loại hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="payment" items="${payments}" varStatus="st">
                                                <tr>
                                                    <td>${st.index + 1}</td>
                                                    <td>${payment.serviceName}</td>

                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty payment.registerDate}">
                                                                <fmt:formatDate value="${payment.registerDate}" pattern="dd/MM/yyyy, HH:mm"/>
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty payment.expirationDate}">
                                                                <fmt:formatDate value="${payment.expirationDate}" pattern="dd/MM/yyyy, HH:mm"/>
                                                            </c:when>
                                                            <c:otherwise>-</c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <td>${payment.paymentStatus}</td>
                                                    <td>${payment.actionType}</td>
                                                </tr>
                                            </c:forEach>

                                            <c:if test="${empty payments}">
                                                <tr>
                                                    <td colspan="6" class="text-center">Không có lịch sử thanh toán nào.</td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="pagination">
                                    <ul class="pagination">
                                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="?id=${id}&page=${currentPage-1}">
                                                &lt;
                                            </a>
                                        </li>

                                        <c:forEach var="i" begin="1" end="${noOfPages}">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="?id=${id}&page=${i}">
                                                    ${i}
                                                </a>
                                            </li>
                                        </c:forEach>

                                        <li class="page-item ${currentPage == noOfPages ? 'disabled' : ''}">
                                            <a class="page-link" href="?id=${id}&page=${currentPage+1}">
                                                &gt;
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- payment_history_area_end -->
            </div>
        </div>
    </body>
</html>
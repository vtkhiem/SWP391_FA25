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
                flex-wrap: wrap;
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
                max-width:1200px;
                margin: 18px auto;
                padding: 0 16px;
                box-sizing: border-box;
            }

            .table-scroll {
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
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
                display:flex;
                gap:8px;
                align-items:center;
                justify-content:flex-end;
                padding:14px 16px;
                border-top:1px solid #e5e7eb;
                background:#fff;
            }
            .pagination a, .pagination span {
                padding:8px 12px;
                border-radius:8px;
                border:1px solid #e5e7eb;
                background:#fff;
                text-decoration:none;
                color:inherit;
            }
            .pagination .active {
                background:#003366;
                color:#fff;
                border-color:#003366;
            }
            .empty {
                color:#6b7280;
                padding:20px;
            }

            /* Modal Styles */
            .modal-overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                z-index: 1000;
                align-items: center;
                justify-content: center;
            }
            .modal-overlay.active {
                display: flex;
            }
            .modal-content {
                background: #fff;
                border-radius: 16px;
                padding: 24px;
                width: 90%;
                max-width: 500px;
                box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
            }
            .modal-header {
                font-size: 20px;
                font-weight: 700;
                margin-bottom: 16px;
                color: #111827;
            }
            .modal-body {
                margin-bottom: 20px;
            }
            .modal-label {
                display: block;
                font-size: 14px;
                font-weight: 600;
                color: #374151;
                margin-bottom: 8px;
            }
            .modal-input {
                width: 100%;
                padding: 12px;
                border: 1px solid #e5e7eb;
                border-radius: 8px;
                font-size: 14px;
                font-family: Arial, sans-serif;
                resize: vertical;
                min-height: 100px;
            }
            .modal-input:focus {
                outline: none;
                border-color: #ff7a00;
                box-shadow: 0 0 0 3px rgba(255, 122, 0, 0.1);
            }
            .modal-footer {
                display: flex;
                gap: 10px;
                justify-content: flex-end;
            }
            .btn-cancel {
                background: #fff;
                border: 1px solid #e5e7eb;
                color: #374151;
            }
            .btn-cancel:hover {
                background: #f9fafb;
            }
            .btn-confirm {
                background: #dc2626;
                border: 1px solid #dc2626;
                color: #fff;
            }
            .btn-confirm:hover {
                background: #b91c1c;
            }
            .toast-message {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 14px 22px;
                border-radius: 8px;
                color: #fff;
                font-weight: 600;
                font-size: 15px;
                z-index: 9999;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                opacity: 0;
                transform: translateX(100%);
                transition: all 0.5s ease;
            }

            /* Kiểu cho thông báo thành công */
            .toast-message.success {
                background-color: #16a34a; /* Màu xanh lá */
            }

            /* Kiểu cho thông báo lỗi */
            .toast-message.error {
                background-color: #dc2626; /* Màu đỏ */
            }

            /* Animation khi hiện */
            .toast-message.show {
                opacity: 1;
                transform: translateX(0);
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <a href="${pageContext.request.contextPath}/admin_home.jsp" class="nav-link">Admin Dashboard</a>
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

            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/adminFeedbackList" class="nav-link">Feedback</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link">Jobs</a>
            <span class="divider">|</span>
            <a href="${pageContext.request.contextPath}/bannedAccountList" class="nav-link" style="text-decoration:underline;">Banned</a>
        </div>
        <c:if test="${not empty sessionScope.message}">
            <div id="toast" class="toast-message success show">
                ${sessionScope.message}
            </div>
            <%-- Xóa attribute khỏi session sau khi đã hiển thị --%>
            <c:remove var="message" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div id="toast" class="toast-message error show">
                ${sessionScope.error}
            </div>
            <%-- Xóa attribute khỏi session sau khi đã hiển thị --%>
            <c:remove var="error" scope="session"/>
        </c:if>

        <div class="searchbar-wrap">
            <form class="searchbar" method="get" action="">
                <input type="text" name="q" placeholder="Tìm nhà tuyển dụng theo tên, email, công ty, SĐT" value="${q}"/>

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
                                            <td class="col-name">${e.employerName}</td>
                                            <td class="col-company"><c:out value="${e.companyName}"/></td>
                                            <td class="col-email">${e.email}</td>
                                            <td class="col-phone">${e.phoneNumber}</td>
                                            <td class="col-loc">${e.location}</td>
                                            <td class="col-web">
                                                <c:if test="${not empty e.urlWebsite}">
                                                    <a href="<c:out value='${e.urlWebsite.startsWith("http") ? e.urlWebsite : "//" += e.urlWebsite}'/>" target="_blank" rel="noopener noreferrer">${e.urlWebsite}</a>
                                                </c:if>
                                                <c:if test="${empty e.urlWebsite}">—</c:if>
                                                </td>
                                                <td class="col-tax">${e.taxCode}</td>
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
                                                <form method="get"
                                                      action="${pageContext.request.contextPath}/admin/employer/details"
                                                      style="display:inline-block">
                                                    <input type="hidden" name="id" value="${e.employerId}">
                                                    <button class="btn btn-success" type="submit">History</button>
                                                </form>
                                                <button class="btn btn-danger" type="button" 
                                                        onclick="openBanModal(${e.employerId}, '${e.employerName}', '${e.companyName}')">
                                                    Ban
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="10" class="empty">Không có dữ liệu</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <div class="pagination">
                    <div class="container">
                        <a href="${pageContext.request.contextPath}/admin/exportEmployerList"><button class="btn primary" type="submit">Xuất dữ liệu</button></a>
                    </div>
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

        <!-- Ban Modal -->
        <div id="banModal" class="modal-overlay">
            <div class="modal-content">
                <div class="modal-header">Ban nhà tuyển dụng</div>
                <form id="banForm" method="post" action="${pageContext.request.contextPath}/employerBan">
                    <div class="modal-body">
                        <p style="margin-bottom: 16px; color: #6b7280;">
                            Bạn có chắc chắn muốn ban nhà tuyển dụng <strong id="employerName"></strong>
                            <span id="companyInfo" style="color: #9ca3af;"></span>?
                        </p>
                        <label class="modal-label" for="banReason">Lý do ban <span style="color: #dc2626;">*</span></label>
                        <textarea 
                            id="banReason" 
                            name="banReason" 
                            class="modal-input" 
                            placeholder="Nhập lý do ban nhà tuyển dụng..."
                            required></textarea>
                        <input type="hidden" id="employerId" name="id">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-cancel" onclick="closeBanModal()">Hủy</button>
                        <button type="submit" class="btn btn-confirm">Xác nhận Ban</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            function openBanModal(employerId, employerName, companyName) {
                document.getElementById('employerId').value = employerId;
                document.getElementById('employerName').textContent = employerName;

                // Show company name if available
                const companyInfo = document.getElementById('companyInfo');
                if (companyName && companyName.trim() !== '') {
                    companyInfo.textContent = '(' + companyName + ')';
                } else {
                    companyInfo.textContent = '';
                }

                document.getElementById('banReason').value = '';
                document.getElementById('banModal').classList.add('active');
            }

            function closeBanModal() {
                document.getElementById('banModal').classList.remove('active');
            }

            // Close modal when clicking outside
            document.getElementById('banModal').addEventListener('click', function (e) {
                if (e.target === this) {
                    closeBanModal();
                }
            });

            // Close modal with Escape key
            document.addEventListener('keydown', function (e) {
                if (e.key === 'Escape') {
                    closeBanModal();
                }
            });
            document.addEventListener('DOMContentLoaded', (event) => {
                const toast = document.getElementById('toast');
                if (toast) {
                    // Sau 3 giây (3000ms), bắt đầu làm mờ
                    setTimeout(() => {
                        toast.style.opacity = '0';
                    }, 3000);

                    // Sau 3.5 giây (3500ms), hoàn toàn ẩn đi
                    setTimeout(() => {
                        toast.style.display = 'none';
                    }, 3500);
                }
            });
        </script>

    </body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equalsIgnoreCase("Sale")) {
        response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
        return;
    }
%>
<fmt:setLocale value="vi_VN" scope="session"/>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Sale - Danh sách đơn hàng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <style>
            /* --- CSS y nguyên từ code cũ --- */
            :root{
                --brand:#00366d;
                --muted:#6b7280;
                --bg:#f3f4f6;
                --card:#ffffff;
                --bd:#e5e7eb;
            }
            * {
                box-sizing: border-box;
            }
            body {
                margin:0;
                font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial, sans-serif;
                background:var(--bg);
                color:#111827;
            }

            .navbar {
                background:var(--brand);
                color:#fff;
                display:flex;
                align-items:center;
                justify-content:space-between;
                padding:12px 20px;
                position:sticky;
                top:0;
                z-index:10;
            }
            .brand a {
                color:#fff;
                text-decoration:none;
                font-weight:700;
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
            .container {
                max-width:1200px;
                margin:20px auto;
                padding:0 16px;
            }

            .cards {
                display:grid;
                grid-template-columns: repeat(2, 1fr);
                gap:12px;
                margin:16px 0;
            }
            .card {
                background:var(--card);
                padding:16px;
                border-radius:12px;
                box-shadow:0 1px 3px rgba(0,0,0,.08);
            }
            .card h3 {
                margin:0 0 6px;
                font-size:15px;
                color:var(--muted);
            }
            .card .value {
                font-size:22px;
                font-weight:700;
            }

            table {
                width:100%;
                border-collapse:collapse;
                background:var(--card);
                border-radius:12px;
                overflow:hidden;
                box-shadow:0 1px 3px rgba(0,0,0,.08);
            }
            th, td {
                padding:10px 12px;
                border-bottom:1px solid #eee;
                text-align:left;
                font-size:14px;
            }
            th {
                background:#fafafa;
                font-weight:600;
            }
            tr:hover {
                background:#f9fafb;
            }
            .clickable-row {
                cursor:pointer;
            }

            .status {
                padding:4px 8px;
                border-radius:10px;
                font-size:12px;
                display:inline-block;
            }
            .status.pending {
                background:#fff7ed;
                color:#9a3412;
            }
            .status.paid, .status.completed, .status.success {
                background:#ecfdf5;
                color:#065f46;
            }
            .status.cancelled, .status.failed {
                background:#fef2f2;
                color:#991b1b;
            }

            .pagination{
                display:flex;
                gap:8px;
                align-items:center;
                justify-content:flex-end;
                padding:14px 16px;
                border-top:1px solid #e5e7eb;
                background:#fff;
            }
            .pagination a, .pagination span{
                padding:8px 12px;
                border-radius:8px;
                border:1px solid #e5e7eb;
                background:#fff;
                text-decoration:none;
                color:inherit;
            }
            .pagination .active{
                background:#003366;
                color:#fff;
                border-color:#003366;
            }

            .pagination .left {
                flex-shrink: 0;
            }

            .pagination .right {
                display: flex;
                gap: 8px;
                align-items: center;
            }
            .page-link {
                padding:6px 10px;
                border:1px solid var(--bd);
                background:#fff;
                border-radius:8px;
                text-decoration:none;
                color:#111827;
            }
            .page-link.active {
                background:var(--brand);
                color:#fff;
                border-color:var(--brand);
            }
            .muted {
                color:var(--muted);
                font-size:13px;
            }

            .modal-backdrop {
                position: fixed;
                inset: 0;
                background: rgba(0,0,0,.35);
                display:none;
                align-items:center;
                justify-content:center;
            }
            .modal {
                width: 420px;
                max-width: 92vw;
                background:#fff;
                border-radius:12px;
                box-shadow:0 10px 30px rgba(0,0,0,.25);
                overflow: hidden;
            }
            .modal header {
                padding:12px 16px;
                background:#00366d;
                color:#fff;
                font-weight:600;
                display:flex;
                justify-content:space-between;
                align-items:center;
            }
            .modal .content {
                padding:16px;
            }
            .modal .row {
                display:flex;
                gap:8px;
                margin-bottom:10px;
            }
            .modal .label {
                width:120px;
                color:#6b7280;
            }
            .modal .value {
                flex:1;
                font-weight:600;
            }
            .modal .actions {
                padding:12px 16px;
                display:flex;
                justify-content:flex-end;
                gap:8px;
                background:#fafafa;
            }
        </style>
    </head>
    <body>
        <div class="navbar">
            <div class="brand"><a href="#">Sale Console</a></div>
        </div>

        <div class="container">
            <!-- FILTER FORM -->
            <form action="${pageContext.request.contextPath}/filterOrderList" method="get"
                  class="d-flex align-items-center gap-2 mt-3 mb-3 px-3">
                <input type="hidden" name="page" value="${page}">
                <input type="hidden" name="size" value="${size}">

                <input type="text" class="form-control align-middle"
                       name="txt" id="searchInput" value="${txt}"
                       placeholder="Search by customer name or email"
                       style="height: 38px; min-width: 250px;">

                <select class="form-control" name="service" id="serviceFilter" style="height: 38px; min-width: 180px;">
                    <option value="" <c:if test="${empty selectedService}">selected</c:if>>Filter by service</option>
                    <c:forEach var="s" items="${services}">
                        <option value="${s.serviceID}" <c:if test="${selectedService eq s.serviceID}">selected</c:if>>
                            ${s.serviceName}
                        </option>
                    </c:forEach>
                </select>

                <select class="form-control" name="promotion" id="promotionFilter" style="height: 38px; min-width: 180px;">
                    <option value="" <c:if test="${empty selectedPromotion}">selected</c:if>>Filter by promotion</option>
                    <c:forEach var="p" items="${promotions}">
                        <option value="${p.code}" <c:if test="${selectedPromotion eq p.code}">selected</c:if>>
                            ${p.code}
                        </option>
                    </c:forEach>
                </select>

                <select class="form-control" name="status" id="statusFilter" style="height: 38px; min-width: 150px;">
                    <option value="" <c:if test="${empty selectedStatus}">selected</c:if>>Filter by Status</option>
                    <option value="Pending" <c:if test="${selectedStatus eq 'Pending'}">selected</c:if>>Pending</option>
                    <option value="Paid" <c:if test="${selectedStatus eq 'Paid'}">selected</c:if>>Paid</option>
                    <option value="Cancelled" <c:if test="${selectedStatus eq 'Cancelled'}">selected</c:if>>Cancelled</option>
                    </select>

                    <button type="submit" class="btn btn-sm btn-warning me-2" style="height:38px;">Apply</button>
                    <button type="reset" class="btn btn-sm btn-secondary" style="height:38px;">Clear</button>
                </form>

                <!-- CARDS -->
                <div class="cards">
                    <div class="card">
                        <h3>Tổng số đơn</h3>
                        <div class="value">${totalRows}</div>
                </div>
                <div class="card">
                    <h3>Tổng doanh thu</h3>
                    <div class="value">
                        <fmt:formatNumber value="${revenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                    </div>
                </div>
            </div>

            <!-- TABLE -->
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Ngày đặt</th>
                        <th>Nhà tuyển dụng</th>
                        <th>Gói</th>
                        <th>Khuyến mãi</th>
                        <th>Giảm (%)</th>
                        <th>Trạng thái</th>
                        <th>Phương thức</th>
                        <th>Ngày kết thúc</th>
                        <th>Tổng tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="totalAmount" value="0"/>
                    <c:forEach var="o" items="${orders}">
                        <tr class="clickable-row" data-buyer-name="${fn:escapeXml(o.employerName)}" data-buyer-email="${fn:escapeXml(o.employerEmail)}">
                            <td>${o.orderId}</td>
                            <td><fmt:formatDate value="${o.date}" pattern="dd/MM/yyyy"/></td>
                            <td><c:out value="${o.employerName}"/></td>
                            <td><c:out value="${o.serviceName}"/></td>
                            <td><c:choose>
                                    <c:when test="${not empty o.promotionCode}"><c:out value="${o.promotionCode}"/></c:when>
                                    <c:otherwise><span class="muted">Không áp dụng</span></c:otherwise>
                                </c:choose></td>
                            <td><c:choose>
                                    <c:when test="${o.discountPercent != null}"><c:out value="${o.discountPercent}"/>%</c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose></td>
                            <td><span class="status ${o.status}"><c:out value="${o.status}"/></span></td>
                            <td><c:out value="${o.payMethod}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${o.endDate != null}">
                                        <fmt:formatDate value="${o.endDate}" pattern="dd/MM/yyyy"/>
                                    </c:when>
                                    <c:otherwise>—</c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatNumber value="${o.finalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>
                        </tr>
                        <c:set var="totalAmount" value="${totalAmount + o.finalAmount}"/>
                    </c:forEach>
                    <tr>
                        <td colspan="9" style="text-align:right;font-weight:bold;">Tổng cộng:</td>
                        <td style="font-weight:bold;">
                            <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                        </td>
                    </tr>

                </tbody>
            </table>

            <!-- PAGINATION -->
            <div class="pagination">
                <div class="container">
                    <a href="exportOrderList"><button class="btn primary" type="submit">Xuất dữ liệu</button></a>
                </div>
                <div class="container">
                    <a href="viewOrderByQuarter"><button class="btn primary" type="submit">Doanh thu theo quý</button></a>
                </div>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a class="page-link ${i==page?'active':''}"
                       href="?page=${i}&size=${size}&txt=${fn:escapeXml(txt)}&service=${fn:escapeXml(selectedService)}&promotion=${fn:escapeXml(selectedPromotion)}&status=${fn:escapeXml(selectedStatus)}">
                        ${i}
                    </a>
                </c:forEach>
            </div>
        </div>

        <!-- MODAL -->
        <div id="buyerModal" class="modal-backdrop">
            <div class="modal" role="dialog" aria-modal="true" aria-labelledby="buyerModalTitle">
                <header>
                    <div id="buyerModalTitle">Thông tin người mua</div>
                </header>
                <div class="content">
                    <div class="row">
                        <div class="label">Tên:</div>
                        <div class="value" id="buyerName">—</div>
                    </div>
                    <div class="row">
                        <div class="label">Email:</div>
                        <div class="value" id="buyerEmail">—</div>
                    </div>
                </div>
                <div class="actions">
                    <button id="buyerModalOk" class="page-link" style="border-color:var(--brand);">Đóng</button>
                </div>
            </div>
        </div>

        <script>
            // --- CLEAR BUTTON ---
            const form = document.querySelector("form[action$='filterOrderList']");
            const searchInput = document.getElementById("searchInput");
            const serviceFilter = document.getElementById("serviceFilter");
            const promotionFilter = document.getElementById("promotionFilter");
            const statusFilter = document.getElementById("statusFilter");
            const clearBtn = form.querySelector("button[type='reset']");

            clearBtn.addEventListener("click", (e) => {
                e.preventDefault();
                searchInput.value = "";
                serviceFilter.value = "";
                promotionFilter.value = "";
                statusFilter.value = "";
                window.location.href = "${pageContext.request.contextPath}/filterOrderList";
            });

            // --- MODAL ---
            (function () {
                const modal = document.getElementById('buyerModal');
                const nameEl = document.getElementById('buyerName');
                const emailEl = document.getElementById('buyerEmail');
                const okBtn = document.getElementById('buyerModalOk');

                function openModal(name, email) {
                    nameEl.textContent = name || '—';
                    emailEl.textContent = email || '—';
                    modal.style.display = 'flex';
                }
                function closeModal() {
                    modal.style.display = 'none';
                }

                table.querySelectorAll('tr').forEach(tr => {
                    if (tr.querySelector('td[colspan]'))
                        return; // skip row thông báo "Không có dữ liệu"
                    const td = tr.cells[6]; // cột tổng tiền
                    if (td)
                        total += parseCurrency(td.textContent);
                });


                okBtn.addEventListener('click', closeModal);
                modal.addEventListener('click', e => {
                    if (e.target === modal)
                        closeModal();
                });
                document.addEventListener('keydown', e => {
                    if (e.key === 'Escape' && modal.style.display === 'flex')
                        closeModal();
                });
            })();

        </script>
    </body>
</html>

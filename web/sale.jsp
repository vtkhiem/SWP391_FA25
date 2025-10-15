<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"Sale".equalsIgnoreCase(role)) {
        response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
        return;
    }
    if (request.getAttribute("orders") == null) {
        response.sendRedirect(request.getContextPath() + "/sale");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sales Dashboard - Orders</title>
    <style>
        :root { --nav:#00366d; --bg:#f3f4f6; --txt:#111827; --card:#ffffff; --muted:#6b7280; }
        * { box-sizing:border-box; }
        body { margin:0; font-family: Arial, Helvetica, sans-serif; background:#f3f4f6; color:#111827; }
        .navbar { background:#00366d; color:#fff; padding:10px 20px; display:flex; align-items:center; gap:10px; }
        .navbar a { color:#fff; text-decoration:none; font-weight:bold; }
        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

        .totals { display:flex; gap:16px; flex-wrap:wrap; }
        .total-card { flex:1 1 260px; background:#ffffff; border-radius:14px; padding:22px 20px; margin-bottom:16px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; }
        .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
        .total-num { font-size:36px; font-weight:800; }

        .table-card { background:#ffffff; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); border:1px solid #e5e7eb; overflow:hidden; }
        .table-head { padding:16px 16px 0 16px; font-weight:700; font-size:18px; }
        table { width:100%; border-collapse:collapse; }
        thead th { text-align:left; font-size:13px; color:#6b7280; padding:12px 16px; border-bottom:1px solid #e5e7eb; background:#fafafa; }
        tbody td { padding:14px 16px; border-bottom:1px solid #f0f2f5; vertical-align:top; }
        tbody tr:hover { background:#fafafa; }
        .col-id { width:90px; font-variant-numeric:tabular-nums; color:#374151; }
        .col-amount { width:160px; font-variant-numeric:tabular-nums; font-weight:700; text-align:right; }
        .muted { color:#6b7280; font-size:12px; }

        .badge { display:inline-block; padding:4px 8px; border-radius:999px; font-size:12px; }
        .badge.paid { background:#dcfce7; color:#166534; }
        .badge.pending { background:#fef9c3; color:#854d0e; }
        .badge.other { background:#e5e7eb; color:#374151; }

        .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; padding:14px 16px; background:#fff; }
        .pagination a, .pagination span { padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit; }
        .pagination .active { background:#00366d; color:#fff; border-color:#00366d; }
        .empty { color:#6b7280; padding:20px; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="<c:url value='/sale'/>">Sales Dashboard</a>
</div>

<div class="container">
    <div class="totals">
        <div class="total-card">
            <div class="total-title">Tổng đơn (tất cả)</div>
            <div class="total-num">${totalItems}</div>
        </div>
        <div class="total-card">
            <div class="total-title">Tổng doanh thu (tất cả)</div>
            <div class="total-num">${totalRevenue}</div>
        </div>
    </div>

    <div class="table-card">
        <div class="table-head">Danh Sách Đơn Hàng</div>
        <table>
            <thead>
                <tr>
                    <th class="col-id">ID</th>
                    <th>Employer</th>
                    <th>Service</th>
                    <th class="col-amount">Số tiền</th>
                    <th>Pay Method</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Duration</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty orders}">
                        <fmt:setLocale value="vi_VN"/>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td class="col-id">${o.orderID}</td>
                                <td>
                                    <div>
                                        <c:choose>
                                            <c:when test="${employerNames[o.employerID] != null}">
                                                ${employerNames[o.employerID]}
                                            </c:when>
                                            <c:otherwise>Employer #${o.employerID}</c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                                <td>
                                    <div>
                                        <c:choose>
                                            <c:when test="${serviceNames[o.serviceID] != null}">
                                                ${serviceNames[o.serviceID]}
                                            </c:when>
                                            <c:otherwise>Service #${o.serviceID}</c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                                <td class="col-amount">
                                    <c:choose>
                                        <c:when test="${not empty o.amount}">
                                            <fmt:formatNumber value="${o.amount}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                        </c:when>
                                        <c:otherwise>0,00</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>${o.payMethod}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${o.status == 'paid' || o.status == 'completed'}"><span class="badge paid">${o.status}</span></c:when>
                                        <c:when test="${o.status == 'pending'}"><span class="badge pending">pending</span></c:when>
                                        <c:otherwise><span class="badge other">${o.status}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${o.date}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${o.duration != null}">${o.duration}</c:when>
                                        <c:otherwise>—</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="8" class="empty">Không có đơn hàng.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == page}">
                        <span class="active">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

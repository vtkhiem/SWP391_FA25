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
  :root{ --brand:#00366d; --muted:#6b7280; --bg:#f3f4f6; --card:#ffffff; --bd:#e5e7eb; }
  * { box-sizing: border-box; }
  body { margin:0; font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial, sans-serif; background:var(--bg); color:#111827; }

  .navbar { background:var(--brand); color:#fff; display:flex; align-items:center; justify-content:space-between; padding:12px 20px; position:sticky; top:0; z-index:10; }
  .brand a { color:#fff; text-decoration:none; font-weight:700; }
  .container { max-width:1200px; margin:20px auto; padding:0 16px; }

  .cards { display:grid; grid-template-columns: repeat(2, 1fr); gap:12px; margin:16px 0; }
  .card { background:var(--card); padding:16px; border-radius:12px; box-shadow:0 1px 3px rgba(0,0,0,.08); }
  .card h3 { margin:0 0 6px; font-size:15px; color:var(--muted); }
  .card .value { font-size:22px; font-weight:700; }

  table { width:100%; border-collapse:collapse; background:var(--card); border-radius:12px; overflow:hidden; box-shadow:0 1px 3px rgba(0,0,0,.08); }
  th, td { padding:10px 12px; border-bottom:1px solid #eee; text-align:left; font-size:14px; }
  th { background:#fafafa; font-weight:600; }
  tr:hover { background:#f9fafb; }
  .clickable-row { cursor:pointer; }

  .status { padding:4px 8px; border-radius:10px; font-size:12px; display:inline-block; }
  .status.pending { background:#fff7ed; color:#9a3412; }
  .status.paid, .status.completed, .status.success { background:#ecfdf5; color:#065f46; }
  .status.cancelled, .status.failed { background:#fef2f2; color:#991b1b; }

  .pagination { display:flex; gap:8px; align-items:center; justify-content:flex-end; margin-top:12px; flex-wrap:wrap; }
  .page-link { padding:6px 10px; border:1px solid var(--bd); background:#fff; border-radius:8px; text-decoration:none; color:#111827; }
  .page-link.active { background:var(--brand); color:#fff; border-color:var(--brand); }
  .muted { color:var(--muted); font-size:13px; }

  .modal-backdrop { position: fixed; inset: 0; background: rgba(0,0,0,.35); display:none; align-items:center; justify-content:center; }
  .modal { width: 420px; max-width: 92vw; background:#fff; border-radius:12px; box-shadow:0 10px 30px rgba(0,0,0,.25); overflow: hidden; }
  .modal header { padding:12px 16px; background:#00366d; color:#fff; font-weight:600; display:flex; justify-content:space-between; align-items:center; }
  .modal .content { padding:16px; }
  .modal .row { display:flex; gap:8px; margin-bottom:10px; }
  .modal .label { width:120px; color:#6b7280; }
  .modal .value { flex:1; font-weight:600; }
  .modal .actions { padding:12px 16px; display:flex; justify-content:flex-end; gap:8px; background:#fafafa; }
</style>
</head>
<body>
  <div class="navbar">
    <div class="brand"><a href="#">Sale Console</a></div>
  </div>

  <div class="container">
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
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Ngày đặt</th>
          <th>Nhà tuyển dụng</th>
          <th>Gói</th>
          <th>Số tiền gốc</th>
          <th>Khuyến mãi</th>
          <th>Giảm (%)</th>
          <th>Giá sau KM</th>
          <th>Phương thức</th>
          <th>Thời lượng</th>
          <th>Trạng thái</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="o" items="${orders}">
          <tr class="clickable-row"
              data-buyer-name="${fn:escapeXml(o.employerName)}"
              data-buyer-email="${fn:escapeXml(o.employerEmail)}">
            <td>${o.orderId}</td>
           
            <td><c:out value="${o.date}"/></td>
            <td><c:out value="${o.employerName}"/></td>
            <td><c:out value="${o.serviceName}"/></td>

            <td><fmt:formatNumber value="${o.amount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>

            <td>
              <c:choose>
                <c:when test="${not empty o.promotionCode}">
                  <span title="Mã khuyến mãi"><c:out value="${o.promotionCode}"/></span>
                </c:when>
                <c:otherwise><span class="muted">Không áp dụng</span></c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${o.discountPercent != null}">
                  <c:out value="${o.discountPercent}"/>%
                </c:when>
                <c:otherwise>—</c:otherwise>
              </c:choose>
            </td>

            <td><fmt:formatNumber value="${o.finalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></td>

            <td><c:out value="${o.payMethod}"/></td>
            <td>
              <c:choose>
                <c:when test="${o.duration != null}">
                  <c:out value="${o.duration}"/> <span class="muted">ngày</span>
                </c:when>
                <c:otherwise>—</c:otherwise>
              </c:choose>
            </td>
            <td><span class="status ${o.status}"><c:out value="${o.status}"/></span></td>
          </tr>
        </c:forEach>
        <c:if test="${empty orders}">
          <tr><td colspan="11" class="muted" style="text-align:center;">Không có dữ liệu</td></tr>
        </c:if>
      </tbody>
    </table>
    <div class="pagination">
      <c:forEach var="i" begin="1" end="${totalPages}">
        <a class="page-link ${i==page?'active':''}" href="?page=${i}&size=${pageSize}">${i}</a>
      </c:forEach>
    </div>
  </div>
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
  (function(){
    const modal = document.getElementById('buyerModal');
    const nameEl = document.getElementById('buyerName');
    const emailEl = document.getElementById('buyerEmail');
    const okBtn = document.getElementById('buyerModalOk');

    function openModal(name, email){
      nameEl.textContent = name || '—';
      emailEl.textContent = email || '—';
      modal.style.display = 'flex';
    }
    function closeModal(){
      modal.style.display = 'none';
    }

    document.querySelectorAll('tr.clickable-row').forEach(tr => {
      tr.addEventListener('click', () => {
        const name = tr.getAttribute('data-buyer-name');
        const email = tr.getAttribute('data-buyer-email');
        openModal(name, email);
      });
    });
    okBtn.addEventListener('click', closeModal);
    modal.addEventListener('click', (e) => {
      if (e.target === modal) closeModal(); 
    });
    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && modal.style.display === 'flex') closeModal();
    });
  })();
</script>

</body>
</html>

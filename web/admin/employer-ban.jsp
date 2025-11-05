<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.Admin" %>
<%@ page import="model.Employer" %>
<%@ page import="model.Ban" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("Admin")) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Ban Employer</title>
<style>
  body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }
  .wrap { max-width:860px; margin:24px auto; background:#fff; border:1px solid #e5e7eb; border-radius:14px; box-shadow:0 2px 8px rgba(0,0,0,.06); }
  .head { padding:16px 20px; border-bottom:1px solid #e5e7eb; display:flex; align-items:center; justify-content:space-between; }
  .title { font-size:18px; font-weight:800; }
  .content { padding:20px; }
  .row { display:flex; gap:12px; margin-bottom:10px; align-items:center; }
  .label { width:160px; color:#6b7280; }
  .val { font-weight:700; }
  .status { padding:8px 12px; border-radius:10px; display:inline-block; }
  .ok { background:#ecfdf5; color:#065f46; border:1px solid #a7f3d0; }
  .warn { background:#fff7ed; color:#9a3412; border:1px solid #fed7aa; }
  .form-card { margin-top:18px; border-top:1px dashed #e5e7eb; padding-top:18px; }
  .form-row { margin-bottom:14px; }
  .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; }
  .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08); }
  .btn.primary { background:#003366; color:#fff; border-color:#003366; }
  input[type="datetime-local"] { padding:10px; border:1px solid #e5e7eb; border-radius:8px; }
  .error { background:#fef2f2; color:#991b1b; border:1px solid #fecaca; padding:10px; border-radius:8px; margin-bottom:10px; }
  .msg { background:#ecfeff; color:#164e63; border:1px solid #a5f3fc; padding:10px; border-radius:8px; margin-bottom:10px; }
</style>
</head>
<body>
<%
    model.Employer employer = (model.Employer) request.getAttribute("employer");
    model.Ban activeBan = (model.Ban) request.getAttribute("activeBan");
%>
<div class="wrap">
  <div class="head">
    <div class="title">Cấm Nhà Tuyển Dụng</div>
    <a class="btn" href="<c:url value='/admin/employers'/>">← Quay lại danh sách</a>
  </div>
  <div class="content">
    <c:if test="${not empty param.msg && param.msg eq 'ban_ok'}">
      <div class="msg">Tạo lệnh cấm thành công.</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>

    <div class="row"><div class="label">Employer ID</div><div class="val">${employer.employerId}</div></div>
    <div class="row"><div class="label">Tên</div><div class="val">${employer.employerName}</div></div>
    <div class="row"><div class="label">Email</div><div class="val">${employer.email}</div></div>
    <div class="row">
      <div class="label">Trạng thái</div>
      <div class="val">
        <c:choose>
          <c:when test="${not empty activeBan}">
            <span class="status warn">ĐANG BỊ CẤM
              <c:choose>
                <c:when test="${activeBan.bannedUntil == null}">(Vĩnh viễn)</c:when>
                <c:otherwise>(Đến:
                  <%
                    if (activeBan != null && activeBan.getBannedUntil() != null) {
                      java.time.ZonedDateTime z = activeBan.getBannedUntil()
                        .atZone(java.time.ZoneId.of("UTC"))
                        .withZoneSameInstant(java.time.ZoneId.of("Asia/Bangkok"));
                      out.print(z.toLocalDate()+" "+z.toLocalTime()+" (GMT+7)");
                    }
                  %>
                )</c:otherwise>
              </c:choose>
            </span>
          </c:when>
          <c:otherwise><span class="status ok">KHÔNG BỊ CẤM</span></c:otherwise>
        </c:choose>
      </div>
    </div>

    <div class="form-card">
      <form method="post" action="<c:url value='/admin/employers/ban'/>" onsubmit="return validateBanForm();">
        <input type="hidden" name="employerId" value="${employer.employerId}">
        <input type="hidden" name="backTo" value="${param.backTo}">

        <div class="form-row">
          <label><input type="radio" name="banType" value="temp" checked onclick="togglePicker(true)"> Cấm có thời hạn</label>
          &nbsp;&nbsp;
          <label><input type="radio" name="banType" value="permanent" onclick="togglePicker(false)"> Cấm vĩnh viễn</label>
        </div>

        <div class="form-row" id="pickerRow">
         
          <input type="datetime-local" id="bannedUntil" name="bannedUntil">
        </div>

        <div class="form-row">
          <button type="submit" class="btn primary">Tạo lệnh cấm</button>

        </div>
      </form>
    </div>
  </div>
</div>

<script>
function togglePicker(show){ const r=document.getElementById('pickerRow'); const i=document.getElementById('bannedUntil'); r.style.display=show?'block':'none'; if(!show) i.value=''; }
(function setMinNow(){
  const input=document.getElementById('bannedUntil');
  const now=new Date();
  now.setSeconds(0,0);
  const iso=new Date(now.getTime()-now.getTimezoneOffset()*60000).toISOString().slice(0,16);
  input.min=iso;
  const plus7=new Date(now.getTime()+7*24*60*60*1000);
  const isoPlus7=new Date(plus7.getTime()-plus7.getTimezoneOffset()*60000).toISOString().slice(0,16);
  input.value=isoPlus7;
})();
function validateBanForm(){
  const type=document.querySelector('input[name="banType"]:checked').value;
  if(type==='temp'){
    const v=document.getElementById('bannedUntil').value;
    if(!v){ alert('Vui lòng chọn thời điểm kết thúc cấm.'); return false; }
  }
  return true;
}
</script>
</body>
</html>

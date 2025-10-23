<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chỉnh sửa bài viết</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
  :root{ --bg:#f3f4f6; --card:#fff; --line:#e5e7eb; --text:#0f172a; --muted:#64748b; --brand:#16a34a;}
  *{box-sizing:border-box}
  body{margin:0; font-family:Arial,Helvetica,sans-serif; background:var(--bg); color:var(--text)}
  .navbar{background:#00366d; color:#fff; display:flex; align-items:center; justify-content:space-between; padding:12px 20px; position:sticky; top:0; z-index:10}
  .navbar a{color:#fff; text-decoration:none}

  .wrap{max-width:900px; margin:18px auto; padding:0 16px;}
  .card{background:#fff; border:1px solid var(--line); border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,.06); padding:16px}

  h1{font-size:22px; margin:0 0 12px}
  label{font-weight:700; font-size:13px; color:#111827}
  input[type=text], textarea{
    width:100%; padding:10px 12px; border:1px solid var(--line); border-radius:10px; outline:none;
    font-size:14px; background:#fff;
  }
  textarea{min-height:360px; resize:vertical}
  .row{display:grid; gap:10px; margin-bottom:12px}
  .muted{color:var(--muted); font-size:12px}

  .actions{display:flex; gap:10px; margin-top:12px; flex-wrap:wrap}
  .btn{padding:10px 14px; border-radius:10px; border:1px solid var(--line); background:#fff; cursor:pointer; font-weight:600}
  .btn.primary{background:#16a34a; color:#fff; border-color:#16a34a}
  .btn:disabled{opacity:.6; cursor:not-allowed}

  /* switch */
  .switch{position:relative; display:inline-block; width:48px; height:26px; vertical-align:middle}
  .switch input{display:none}
  .slider{position:absolute; cursor:pointer; inset:0; background:#e5e7eb; border-radius:999px; transition:.2s}
  .slider:before{content:""; position:absolute; height:20px; width:20px; left:3px; top:3px; background:#fff; border-radius:50%; transition:.2s; box-shadow:0 1px 3px rgba(0,0,0,.2)}
  input:checked + .slider{background:#16a34a}
  input:checked + .slider:before{transform:translateX(22px)}
  .status-label{font-size:13px; margin-left:8px; vertical-align:middle}
  .pill{display:inline-block; padding:2px 8px; border-radius:999px; background:#dcfce7; color:#166534; font-size:12px; font-weight:700}
  .pill.gray{background:#e5e7eb; color:#374151}
</style>
</head>
<body>

<!-- Header Marketing -->
<div class="navbar">
  <div><a href="${pageContext.request.contextPath}/marketing">Marketing Dashboard</a></div>
  <div><a href="${pageContext.request.contextPath}/blog-list">Quay lại danh sách</a></div>
</div>

<div class="wrap">
  <div class="card">
    <h1>Chỉnh sửa bài viết (#${post.postID})</h1>

    <c:if test="${param.saved == '1'}">
      <div class="muted" style="margin-bottom:8px;color:#16a34a;font-weight:700">Đã lưu thay đổi.</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/blog-list/edit">
      <input type="hidden" name="postID" value="${post.postID}"/>

      <div class="row">
        <label>Tiêu đề</label>
        <input type="text" name="title" value="${post.title}" required>
      </div>

      <div class="row">
        <label>Category name</label>
        <input type="text" name="categoryName" value="${post.categoryName}">
      </div>

      <div class="row">
        <label>Nội dung (HTML)</label>
        <textarea name="contentHtml" id="contentHtml"><c:out value="${empty detail ? '' : detail.contentHtml}"/></textarea>
        <div class="muted">Bạn có thể dán HTML đơn giản (p, h2, ul/li...).</div>
      </div>

      <div class="row" style="align-items:center; grid-template-columns:auto 1fr;">
        <label class="switch">
          <input type="checkbox" name="status" <c:if test="${post.status}">checked</c:if> onchange="setStatusLabel(this)">
          <span class="slider"></span>
        </label>
        <span id="status-label" class="status-label">
          <c:choose>
            <c:when test="${post.status}"><span class="pill">published</span></c:when>
            <c:otherwise><span class="pill gray">archived</span></c:otherwise>
          </c:choose>
        </span>
      </div>

      <div class="actions">
        <button type="submit" class="btn primary">Lưu thay đổi</button>
        <a class="btn" href="${pageContext.request.contextPath}${post.url}" target="_blank">Xem bài ngoài site</a>
      </div>
    </form>
  </div>
</div>

<script>
  function setStatusLabel(cb){
    const el = document.getElementById('status-label');
    el.innerHTML = cb.checked
      ? '<span class="pill">published</span>'
      : '<span class="pill gray">archived</span>';
  }
</script>
</body>
</html>

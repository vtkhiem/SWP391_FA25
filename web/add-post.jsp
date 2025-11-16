<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Thêm bài viết</title>
  <style>
    :root{ --bg:#f3f4f6; --card:#fff; --line:#e5e7eb; --text:#111827; --muted:#64748b; --brand:#00366d;}
    body{margin:0; font-family:Arial,Helvetica,sans-serif; background:var(--bg); color:var(--text)}
    .wrap{max-width:900px; margin:24px auto; padding:0 16px}
    .card{background:var(--card); border:1px solid var(--line); border-radius:12px; padding:20px}
    h1{margin:0 0 16px}
    .grid{display:grid; grid-template-columns:1fr 1fr; gap:16px}
    .row{margin-bottom:14px}
    label{display:block; font-weight:600; margin-bottom:6px; color:#0f172a}
    input[type="text"], input[type="url"], textarea{
      width:100%; padding:10px; border:1px solid var(--line); border-radius:8px; font-size:14px
    }
    textarea{min-height:160px}
    .muted{color:var(--muted); font-size:12px}
    .actions{display:flex; gap:12px; margin-top:16px}
    .btn{background:var(--brand); color:#fff; border:none; padding:10px 16px; border-radius:10px; cursor:pointer}
    .btn.secondary{background:#6b7280}
    .hr{height:1px; background:var(--line); margin:16px 0}
  </style>
          <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
</head>
<body>
  <div class="wrap">
    <h1>Thêm bài viết</h1>
    <div class="card">
      <form method="post" action="${pageContext.request.contextPath}/blog-list/add" enctype="multipart/form-data">
        <div class="row">
          <label for="title">Tiêu đề</label>
          <input type="text" id="title" name="title" required placeholder="Nhập tiêu đề bài viết">
         
        </div>

        <div class="grid">
          <div class="row">
            <label for="url">URL</label>
            <input type="text" id="url" name="url" required placeholder="vd: 9-meo-phong-van-ghi-diem">
          </div>
          <div class="row">
            <label for="category">Category</label>
            <input type="text" id="category" name="category" required placeholder="vd: Career Tips">
          </div>
        </div>

        <div class="row">
          <label for="excerpt">Mô tả ngắn</label>
          <textarea id="excerpt" name="excerpt" maxlength="800" required placeholder="Tóm tắt nội dung bài viết..."></textarea>
        </div>

        <div class="row">
          <label for="coverFile">Upload ảnh bìa</label>
          <input type="file" id="coverFile" name="coverFile" required accept="image/*">
        </div>

        <div class="hr"></div>

        <div class="row">
          <label for="contentHtml">Nội dung HTML</label>
          <textarea id="contentHtml" name="contentHtml" required placeholder="<h2>Tiêu đề</h2><p>Nội dung...</p>"></textarea>
          <div class="muted">Nội dung sẽ lưu vào bảng BlogPostDetail.ContentHtml</div>
        </div>

        <div class="actions">
          <button class="btn" type="submit">Lưu bài viết</button>
          <a class="btn secondary" href="${pageContext.request.contextPath}/blog-list">Hủy</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Blog</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />

  <style>
    :root{
      --text:#111827; --muted:#6b7280; --bg:#f8fafc; --card:#fff; --brand:#16a34a;
      --header-h: 72px;
      --gap-lg: 20px;
    }
    *{box-sizing:border-box}
    html, body { height:100%; }
    body{
      margin:0; display:flex; flex-direction:column; min-height:100vh;
      font-family:Arial,Helvetica,sans-serif; background:var(--bg); color:var(--text);
    }
    main.page-content{ flex:1 0 auto; padding-top: calc(var(--header-h) + 16px); }

    .container-blog{max-width:1200px;margin:24px auto;padding:0 16px}
    h1.page-title{font-size:32px;margin:0 0 12px}
    h2.section-title{font-size:22px;margin:24px 0 12px;color:#0f172a}

    .featured-grid{ display:grid; grid-template-columns:repeat(12, minmax(0,1fr));
      gap:var(--gap-lg); margin-bottom:10px }
    .featured-left{grid-column:span 6}
    .featured-right{grid-column:span 6; display:flex; flex-direction:column; gap:var(--gap-lg)}

    .card{
      background:var(--card); border-radius:14px;
      box-shadow:0 6px 16px rgba(0,0,0,.06); overflow:hidden;
      display:flex; flex-direction:column; position:relative;
      transition: box-shadow .2s ease;
    }
    .card:hover{ box-shadow:0 10px 24px rgba(0,0,0,.08); }
    .link-cover{ position:absolute; inset:0; z-index:1; text-indent:-9999px; }
    .thumb{width:100%; height:220px; object-fit:cover; display:block}
    .thumb-sm{height:120px}
    .meta{color:var(--brand); font-weight:700; font-size:12px; margin:12px 16px 6px; text-transform:uppercase; letter-spacing:.2px}
    .title{font-size:18px; font-weight:700; margin:0 16px 8px; line-height:1.35}

    a.title, .title a{ color:#111 !important; text-decoration:none !important; position:relative; z-index:2 }
    a.title:visited, .title a:visited{ color:#111 !important; }
    a.title:hover, .title a:hover{ color:#0f172a; text-decoration:none !important; }


    .sub{color:var(--muted); font-size:12px; margin:0 16px 10px;
         display:flex; gap:8px; align-items:center; flex-wrap:wrap; position:relative; z-index:2}
    .excerpt{color:#334155; font-size:14px; margin:0 16px 16px; position:relative; z-index:2}

    .cards{display:grid; gap:var(--gap-lg); grid-template-columns:repeat(auto-fill, minmax(260px, 1fr))}
    .cards .card{min-width:0}

    .pagination{display:flex; gap:8px; justify-content:center; margin:24px 0}
    .page-btn{padding:8px 12px; background:#e5e7eb; border-radius:8px; text-decoration:none; color:#111}
    .page-btn.active{background:#111; color:#fff}

    @media (max-width: 992px){
      .featured-left, .featured-right { grid-column: span 12; }
      .thumb{height:180px}
      :root{ --header-h: 64px; }
    }
  </style>
</head>
<body>

  <jsp:include page="header.jsp"/>

  <main class="page-content">
    <div class="container-blog">
      <h1 class="page-title"></h1>

      <h2 class="section-title">Bài viết nổi bật</h2>
      <div class="featured-grid">
        <c:if test="${not empty featured}">
          <c:set var="first" value="${featured[0]}"/>

          <div class="featured-left card">
            <a class="link-cover" href="${pageContext.request.contextPath}${first.url}" aria-label="${first.title}">Xem</a>
            <img class="thumb" src="${pageContext.request.contextPath}${first.coverImageUrl}" alt="${first.title}">
            <div class="meta">${first.categoryName}</div>
            <h2 class="title"><a href="${pageContext.request.contextPath}${first.url}">${first.title}</a></h2>
            <div class="sub">
              <span>${first.authorName}</span>
              <span>&bull;</span>
              <c:if test="${not empty first.publishedDate}">
                <span><fmt:formatDate value="${first.publishedDate}" pattern="dd/MM/yyyy"/></span>
              </c:if>
            </div>
            <p class="excerpt">${first.excerpt}</p>
          </div>
        </c:if>

        <div class="featured-right">
          <c:forEach var="b" items="${featured}" begin="1">
            <div class="card" style="display:grid;grid-template-columns:1fr 160px;gap:10px;align-items:center">
              <a class="link-cover" href="${pageContext.request.contextPath}${b.url}" aria-label="${b.title}">Xem</a>
              <div>
                <div class="meta">${b.categoryName}</div>
                <h3 class="title" style="margin-top:0">
                  <a href="${pageContext.request.contextPath}${b.url}">${b.title}</a>
                </h3>
                <div class="sub">
                  <span>${b.authorName}</span>
                  <span>&bull;</span>
                  <c:if test="${not empty b.publishedDate}">
                    <span><fmt:formatDate value="${b.publishedDate}" pattern="dd/MM/yyyy"/></span>
                  </c:if>
                </div>
              </div>
              <img class="thumb thumb-sm" src="${pageContext.request.contextPath}${b.coverImageUrl}" alt="${b.title}">
            </div>
          </c:forEach>
        </div>
      </div>

      <h2 class="section-title">Danh sách bài viết</h2>
      <div class="cards">
        <c:forEach var="b" items="${items}">
          <div class="card">
            <a class="link-cover" href="${pageContext.request.contextPath}${b.url}" aria-label="${b.title}">Xem</a>
            <img class="thumb" src="${pageContext.request.contextPath}${b.coverImageUrl}" alt="${b.title}">
            <div class="meta">${b.categoryName}</div>
            <a href="${pageContext.request.contextPath}${b.url}" class="title">
              ${b.title}
            </a>
            <div class="sub">
              <span>${b.authorName}</span>
              <span>&bull;</span>
              <c:if test="${not empty b.publishedDate}">
                <span><fmt:formatDate value="${b.publishedDate}" pattern="dd/MM/yyyy"/></span>
              </c:if>
            </div>
            <p class="excerpt"><c:out value="${b.excerpt}"/></p>
          </div>
        </c:forEach>
      </div>

      <c:if test="${totalPages > 1}">
        <div class="pagination">
          <c:forEach var="i" begin="1" end="${totalPages}">
            <a class="page-btn ${i == page ? 'active' : ''}"
               href="${pageContext.request.contextPath}/blogs?page=${i}">${i}</a>
          </c:forEach>
        </div>
      </c:if>

    </div>
  </main>

  <jsp:include page="footer.jsp"/>

</body>
</html>

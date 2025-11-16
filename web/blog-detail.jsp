<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title><c:out value="${empty detail.seoTitle ? post.title : detail.seoTitle}"/></title>
        <meta name="description" content="${detail.seoDescription}">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">


        <style>
            :root {
                --text:#0f172a;
                --muted:#64748b;
                --brand:#16a34a;
                --bg:#f8fafc;
                --header-h:96px;
            }
            *{
                box-sizing:border-box
            }
            html, body {
                height:100%
            }
            body{
                margin:0;
                display:flex;
                flex-direction:column;
                min-height:100vh;
                font-family:Arial, Helvetica, sans-serif;
                background:var(--bg);
                color:var(--text);
            }

            main{
                flex:1 0 auto;
                padding-top: calc(var(--header-h) + 24px);
            }
            .container{
                max-width:860px;
                margin:0 auto;
                padding:0 16px 48px;
            }

            .category{
                color:var(--brand);
                font-weight:700;
                text-transform:uppercase;
                font-size:12px;
                margin:6px 0 8px
            }
            h1{
                font-size:32px;
                line-height:1.25;
                margin:0 0 10px
            }
            .meta{
                font-size:13px;
                color:var(--muted);
                margin-bottom:16px;
                display:flex;
                gap:8px;
                align-items:center;
                flex-wrap:wrap
            }

            .cover{
                width:100%;
                height:320px;
                object-fit:cover;
                border-radius:12px;
                margin:12px 0 20px;
                display:block
            }
            .content{
                font-size:16px;
                line-height:1.75;
                color:#0f172a;
            }
            .content h2{
                font-size:22px;
                margin:22px 0 8px
            }
            .content p{
                margin:12px 0
            }
            .content ul{
                padding-left:20px
            }

            @media (max-width: 992px){
                :root{
                    --header-h: 76px;
                }
                .cover{
                    height:240px;
                }
                h1{
                    font-size:28px;
                }
            }
        </style>
    </head>
    <body>

        <jsp:include page="header.jsp"/>

        <main>
            <div class="container">
                <div class="category">${post.categoryName}</div>

                <h1>${post.title}</h1>

                <div class="meta">
                    <span>${post.authorName}</span>
                    <c:if test="${not empty post.publishedDate}">
                        <span>&bull;</span>
                        <span><fmt:formatDate value="${post.publishedDate}" pattern="dd/MM/yyyy"/></span>
                    </c:if>
                </div>

                <c:if test="${not empty post.coverImageUrl}">
                    <img class="cover" src="${pageContext.request.contextPath}${post.coverImageUrl}" alt="${post.title}">
                </c:if>

                <div class="content">
                    <c:choose>
                        <c:when test="${not empty detail && not empty detail.contentHtml}">
                            <c:out value="${detail.contentHtml}" escapeXml="false"/>
                        </c:when>
                        <c:otherwise>
                            <p><c:out value="${post.excerpt}"/></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </main>
        <jsp:include page="footer.jsp"/>
        <script>
            (function () {
                function applyOffset() {
                    var header = document.getElementById('sticky-header');
                    var main = document.querySelector('main');
                    if (!header || !main)
                        return;
                    var h = header.offsetHeight || 0;
                    main.style.paddingTop = (h + 24) + 'px';
                }
                window.addEventListener('load', applyOffset);
                window.addEventListener('resize', applyOffset);
            })();
        </script>
    </body>
</html>

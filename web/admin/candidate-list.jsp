<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Candidates</title>
    <style>
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }
 
        .navbar {
        background: #003366;
        color: #fff;
        padding: 10px 20px;
        font-family: Arial, sans-serif;
        display: flex;
        gap: 10px;
    }
    .navbar .nav-link {
        color: #fff;
        text-decoration: none;
        font-weight: bold;
    }
    .navbar .nav-link:hover {
        text-decoration: underline;
    }
    .navbar .divider {
        color: #fff;
        margin: 0 5px;
    }
        .brand { font-weight:700; font-size:18px; }
        .brand a { color:#fff; text-decoration:none; }

      
        .searchbar-wrap {
            background:#e9eef7;
            padding:14px 16px;
            display:flex; justify-content:center;
            border-bottom:1px solid #dbe2f1;
        }
        .searchbar {
            width:100%; max-width:900px;
            display:flex; gap:10px; align-items:center;
            background:#fff; border:1px solid #e5e7eb; border-radius:12px;
            padding:8px;
        }
        .searchbar input[type="text"]{
            flex:1; padding:12px 14px; border:none; outline:none; font-size:14px;
        }
        .btn { padding:10px 14px; border-radius:10px; border:1px solid transparent; cursor:pointer; font-weight:600; }
        .btn.primary { background:#ff7a00; color:#fff; }

      
        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; }

     
        .total-card {
            background:#ffffff; border-radius:14px; padding:22px 20px; margin-bottom:16px;
            box-shadow:0 2px 8px rgba(0,0,0,.06);
        }
        .total-title { color:#6b7280; font-size:14px; margin-bottom:6px; }
        .total-num   { font-size:36px; font-weight:800; }

   
        .candidates h3 { margin:12px 0; }
        .candidate-card {
            background:#fff; border-radius:10px; padding:16px; margin-bottom:12px;
            display:flex; align-items:center; gap:16px;
            box-shadow:0 2px 6px rgba(0,0,0,.06);
        }
        .candidate-card img {
            width:60px; height:60px; border-radius:50%; object-fit:cover; background:#eee;
        }
        .muted { color:#6b7280; font-size:13px; }

        .pagination {
            display:flex; gap:8px; align-items:center; justify-content:flex-end; margin-top:14px;
        }
        .pagination a, .pagination span {
            padding:8px 12px; border-radius:8px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:inherit;
        }
        .pagination .active { background:#003366; color:#fff; border-color:#003366; }
        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600 }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }

    </style>
</head>
<body>


<div class="navbar">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">Admin Dashboard</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/candidates" class="nav-link">Candidates</a>
    <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/admin/employers" class="nav-link">Employers</a>
</div>
    <div></div>
</div>


<div class="searchbar-wrap">
    <form class="searchbar" method="get" action="">
        <input type="text" name="q" placeholder="Tìm ứng viên theo tên, email, SĐT" value="${q}"/>
        <button class="btn primary" type="submit">Tìm kiếm</button>
    </form>
</div>
       

<div class="container">
    <div class="total-card">
        <div class="total-title">Tổng ứng viên</div>
        <div class="total-num">${total}</div>
       
    </div>

    <div class="candidates">
        <h3>Danh Sách Ứng Viên</h3>

        <c:forEach var="c" items="${candidates}">
            <div class="candidate-card">
                <c:choose>
                    <c:when test="${not empty c.avatar}">
                        <img src="${pageContext.request.contextPath}/uploads/${c.avatar}" alt="">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/static/img/avatar-default.png" alt="">
                    </c:otherwise>
                </c:choose>
                <div style="flex:1;">
                    <div style="font-weight:700; font-size:16px;">${c.candidateName}</div>
                    <div class="muted">Email: ${c.email} • SĐT: ${c.phoneNumber}</div>
                    <div class="muted">Địa chỉ: ${c.address} • Quốc tịch: ${c.nationality}</div>
                </div>
                <div style="display:flex; gap:8px;">
                     <a class="btn" href="${pageContext.request.contextPath}/admin/candidate/view?id=${c.candidateId}">
                         View
                     </a>
                    <form method="post" action="${pageContext.request.contextPath}/admin/candidate/delete"onsubmit="return confirm('Xóa ứng viên ${c.candidateName}?');">
                            <input type="hidden" name="id" value="${c.candidateId}">
                                <button class="btn" style="border-color:#fecaca;color:#b91c1c;background:#fff;">Delete
                                </button>
                    </form>
                </div>
             
            </div>
        </c:forEach>

        <c:if test="${empty candidates}">
            <div class="muted" style="padding:24px;">Không có dữ liệu</div>
        </c:if>

   
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == page}">
                        <span class="active">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}&q=${q}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>

</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Thêm taglib để định dạng số --%>
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
    <title>Chi tiết: ${job.title}</title>
    <style>
        /* CSS CHUNG (Copy từ trang list) */
        body { margin:0; font-family: Arial, sans-serif; background:#f3f4f6; color:#111827; }
        .navbar { background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px; }
        .navbar .nav-link { color:#fff; text-decoration:none; font-weight:bold; }
        .navbar .nav-link:hover { text-decoration:underline; }
        .navbar .divider { color:#fff; margin:0 5px; }
        .navbar .nav-link.active { color:#ff7a00; text-decoration:underline; }
        .btn { padding:10px 14px; border-radius:10px; border:1px solid #e5e7eb; background:#fff; text-decoration:none; color:#111827; font-weight:600; cursor:pointer; }
        .btn:hover { box-shadow:0 2px 6px rgba(0,0,0,.08) }
        .container { width:100%; max-width:1180px; margin:18px auto; padding:0 16px; box-sizing: border-box; }
        
        /* CSS cho Trạng thái */
        .status-badge { padding: 4px 8px; border-radius: 12px; font-size: 12px; font-weight: 600; }
        .status-visible { background-color: #d1fae5; color: #065f46; }
        .status-hidden { background-color: #fee2e2; color: #991b1b; }

        /* CSS RIÊNG CHO TRANG NÀY */
        .back-link {
            display: inline-block;
            margin-bottom: 16px;
            color: #003366;
            text-decoration: none;
            font-weight: 600;
        }
        .back-link:hover { text-decoration: underline; }

        .detail-card {
            background: #ffffff;
            border-radius: 14px;
            box-shadow: 0 2px 8px rgba(0,0,0,.06);
            border: 1px solid #e5e7eb;
            overflow: hidden;
        }
        
        .card-header {
            padding: 20px 24px;
            border-bottom: 1px solid #e5e7eb;
        }
        .card-header h1 {
            margin: 0;
            font-size: 28px;
            color: #111827;
        }
        .card-header .sub-header {
            font-size: 16px;
            color: #6b7280;
            margin-top: 8px;
        }
        .sub-header span { margin-right: 16px; }

        .detail-layout {
            display: flex;
            gap: 24px;
            padding: 24px;
        }
        
        .main-content {
            flex: 2; /* Chiếm 2/3 không gian */
            line-height: 1.6;
        }
        .main-content h2 {
            font-size: 20px;
            border-bottom: 1px solid #eee;
            padding-bottom: 8px;
            margin-top: 0;
        }
        .main-content p, .main-content li {
            color: #374151;
        }

        .sidebar-summary {
            flex: 1; /* Chiếm 1/3 không gian */
            background: #fafafa;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            padding: 20px;
            height: fit-content; /* Chỉ cao vừa đủ nội dung */
        }
        .sidebar-summary h3 {
            font-size: 18px;
            margin-top: 0;
            margin-bottom: 16px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }
        .summary-item {
            margin-bottom: 14px;
            font-size: 15px;
        }
        .summary-item strong {
            display: block;
            color: #6b7280;
            font-size: 13px;
            font-weight: 600;
            margin-bottom: 4px;
            text-transform: uppercase;
        }
        .summary-item span {
            color: #111827;
            font-weight: 600;
        }

    </style>
</head>
<body>

 <div class="navbar">
    <a>Admin Dashboard</a>
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
    <%-- Đánh dấu link 'Jobs' là active --%>
    <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link active">Jobs</a>
</div>
<div class="container">

    <a href="${pageContext.request.contextPath}/adminJobpostList" class="back-link">&larr; Quay lại danh sách</a>

    <div class="detail-card">
        <div class="card-header">
            <h1>${job.title}</h1>
            <div class="sub-header">
                <span>Employer ID: ${job.employerID}</span>
                <span>Tại: ${job.location}</span>
            </div>
        </div>
        
        <div class="detail-layout">
            
            <div class="main-content">
                <h2>Mô tả công việc</h2>
                <div>
                    <%-- Dùng c:out để an toàn, nhưng nếu mô tả có HTML (như <p>, <ul>) 
                         thì chỉ cần ${job.description} là đủ.
                         Nếu mô tả là plain text có xuống dòng, dùng CSS: style="white-space: pre-wrap;"
                    --%>
                    <div style="white-space: pre-wrap;">${job.description}</div>
                </div>
            </div>
            
            <div class="sidebar-summary">
                <h3>Thông tin tóm tắt</h3>
                
                <div class="summary-item">
                    <strong>Trạng thái</strong>
                    <span>
                        <c:choose>
                            <c:when test="${job.visible}">
                                <span class="status-badge status-visible">Đang hiện</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-badge status-hidden">Đã ẩn</span>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <div class="summary-item">
                    <strong>Mức lương</strong>
                    <span>
                        <c:choose>
                            <c:when test="${job.offerMin > 0 && job.offerMax > 0}">
                                <fmt:formatNumber value="${job.offerMin}" type="currency" currencyCode="VND" maxFractionDigits="0" /> - <fmt:formatNumber value="${job.offerMax}" type="currency" currencyCode="VND" maxFractionDigits="0" />
                            </c:when>
                            <c:otherwise>
                                Thoả thuận
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <div class="summary-item">
                    <strong>Ngành nghề</strong>
                    <span>${job.category}</span>
                </div>

                <div class="summary-item">
                    <strong>Hình thức</strong>
                    <span>${job.typeJob}</span>
                </div>

                <div class="summary-item">
                    <strong>Vị trí</strong>
                    <span>${job.position}</span>
                </div>

                <div class="summary-item">
                    <strong>Kinh nghiệm</strong>
                    <span>
                        <c:choose>
                            <c:when test="${job.numberExp > 0}">
                                ${job.numberExp} năm
                            </c:when>
                            <c:otherwise>
                                Không yêu cầu
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <div class="summary-item">
                    <strong>Ngày đăng</strong>
                    <%-- Lưu ý: DAO của bạn dùng LocalDateTime, nên fmt:formatDate có thể không chạy.
                         Nếu ngày tháng hiển thị dạng '2025-11-05T10:30:00' là chấp nhận được cho admin.
                         Nếu muốn đẹp, bạn cần sửa DAO để trả về java.util.Date
                    --%>
                    <span>${job.dayCreate}</span>
                </div>

                <div class="summary-item">
                    <strong>Hạn nộp</strong>
                    <span>${job.dueDate}</span>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
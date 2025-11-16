<%-- 
    Document   : adminFeedback
    Created on : Oct 17, 2025
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
    <style>
          .navbar {
            background:#003366; color:#fff; padding:10px 20px; display:flex; gap:10px;
        }
        body {
            background-color: #f8f9fa;
            padding: 30px;
        }
        .card {
            border-radius: 15px;
        }
        .badge {
            font-size: 0.9rem;
            cursor: pointer;
        }
        .tab-content {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="navbar">
     <a href="${pageContext.request.contextPath}/admin_home.jsp" class="nav-link">Admin Dashboard</a>
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
          <a href="${pageContext.request.contextPath}/adminJobpostList" class="nav-link">Jobs</a>
           <span class="divider">|</span>
    <a href="${pageContext.request.contextPath}/bannedAccountList" class="nav-link" style="text-decoration:underline;">Banned</a>
</div>
<div class="container">
   
    <h2 class="text-center mb-4">📨 Danh sách Feedback</h2>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" id="feedbackTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="all-tab" data-bs-toggle="tab" data-bs-target="#all" type="button">
                Tất cả
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="employer-tab" data-bs-toggle="tab" data-bs-target="#employers" type="button">
                Từ Employer
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="candidate-tab" data-bs-toggle="tab" data-bs-target="#candidates" type="button">
                Từ Candidate
            </button>
        </li>
    </ul>

    <!-- Tab contents -->
    <div class="tab-content">

        <!-- ✅ TẤT CẢ FEEDBACK -->
        <div class="tab-pane fade show active" id="all" role="tabpanel">
            <c:choose>
                <c:when test="${empty listAll}">
                    <div class="alert alert-secondary mt-3">Chưa có feedback nào.</div>
                </c:when>
                <c:otherwise>
                    <div class="row mt-3">
                        <c:forEach var="fb" items="${listAll}">
                            <div class="col-md-6 mb-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title">${fb.subject}</h5>
                                       
                                        <p class="card-text text-muted">${fb.content}</p>

                                        <!-- ✅ HIỂN THỊ TYPE -->
                                        <c:forEach var="type" items="${feedbackTypeMap[fb.feedbackID]}">
                                            <c:choose>
                                                <c:when test="${type.typeFeedbackName eq 'Dịch vụ'}">
                                                    <button type="button" class="badge bg-info text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#serviceModalAll${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:when test="${type.typeFeedbackName eq 'Khuyến mãi'}">
                                                    <button type="button" class="badge bg-warning text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#promoModalAll${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${type.typeFeedbackName}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>

                                        <!-- ✅ STATUS -->
                                        <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                                            ${fb.status}
                                        </span>

                                        <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                                        <c:if test="${not empty fb.adminResponse}">
                                            <div class="alert alert-success mt-2 p-2">
                                                <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                                            </div>
                                        </c:if>

                                        <!-- ✅ FORM PHẢN HỒI -->
                                        <button class="btn btn-sm btn-outline-primary mt-3" 
                                                type="button"
                                                data-bs-toggle="collapse" 
                                                data-bs-target="#replyFormAll${fb.feedbackID}">
                                            💬 Soạn phản hồi
                                        </button>

                                        <div class="collapse mt-3" id="replyFormAll${fb.feedbackID}">
                                            <form action="respondFeedback" method="post">
                                                <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
                                                  <input type="hidden" name="email" value="${fb.email}">
                                                <c:choose>
                                                    <c:when test="${not empty fb.employerID}">
                                                        <input type="hidden" name="role" value="employer">
                                                    </c:when>
                                                    <c:when test="${not empty fb.candidateID}">
                                                        <input type="hidden" name="role" value="candidate">
                                                    </c:when>
                                                </c:choose>
                                                <div class="mb-2">
                                                    <textarea name="adminResponse" class="form-control" rows="3" 
                                                              placeholder="Nhập phản hồi của bạn..."></textarea>
                                                </div>
                                                <div class="mb-2">
                                                    <select name="newStatus" class="form-select">
                                                        <option value="Resolved">Đã xử lý</option>
                                                        <option value="Pending" selected>Chưa xử lý</option>
                                                    </select>
                                                </div>
                                                <button type="submit" class="btn btn-success btn-sm">Gửi phản hồi</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL DỊCH VỤ -->
                            <div class="modal fade" id="serviceModalAll${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-info text-white">
                                            <h5 class="modal-title">Chi tiết Dịch vụ</h5>
                                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Service ID:</strong> ${fb.serviceID}</p>
                                            <p><strong>Tên dịch vụ:</strong> ${serviceNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL KHUYẾN MÃI -->
                            <div class="modal fade" id="promoModalAll${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-warning text-dark">
                                            <h5 class="modal-title">Chi tiết Khuyến mãi</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Promotion ID:</strong> ${fb.promotionID}</p>
                                            <p><strong>Tên khuyến mãi:</strong> ${promotionNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- ✅ TAB EMPLOYERS -->
        <div class="tab-pane fade" id="employers" role="tabpanel">
            <c:choose>
                <c:when test="${empty listEmployers}">
                    <div class="alert alert-secondary mt-3">Chưa có feedback nào.</div>
                </c:when>
                <c:otherwise>
                    <div class="row mt-3">
                        <c:forEach var="fb" items="${listEmployers}">
                            <div class="col-md-6 mb-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title">${fb.subject}</h5>
                                        
                                        <p class="card-text text-muted">${fb.content}</p>

                                        <!-- ✅ HIỂN THỊ TYPE -->
                                        <c:forEach var="type" items="${feedbackTypeMap[fb.feedbackID]}">
                                            <c:choose>
                                                <c:when test="${type.typeFeedbackName eq 'Dịch vụ'}">
                                                    <button type="button" class="badge bg-info text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#serviceModalEmp${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:when test="${type.typeFeedbackName eq 'Khuyến mãi'}">
                                                    <button type="button" class="badge bg-warning text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#promoModalEmp${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${type.typeFeedbackName}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>

                                        <!-- ✅ STATUS -->
                                        <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                                            ${fb.status}
                                        </span>

                                        <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                                        <c:if test="${not empty fb.adminResponse}">
                                            <div class="alert alert-success mt-2 p-2">
                                                <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                                            </div>
                                        </c:if>

                                        <!-- ✅ FORM PHẢN HỒI -->
                                        <button class="btn btn-sm btn-outline-primary mt-3" 
                                                type="button"
                                                data-bs-toggle="collapse" 
                                                data-bs-target="#replyFormEmp${fb.feedbackID}">
                                            💬 Soạn phản hồi
                                        </button>

                                        <div class="collapse mt-3" id="replyFormEmp${fb.feedbackID}">
                                            <form action="respondFeedback" method="post">
                                                <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
                                                <input type="hidden" name="role" value="employer">
                                                <div class="mb-2">
                                                    <textarea name="adminResponse" class="form-control" rows="3" 
                                                              placeholder="Nhập phản hồi của bạn..."></textarea>
                                                </div>
                                                <div class="mb-2">
                                                    <select name="newStatus" class="form-select">
                                                        <option value="Resolved">Đã xử lý</option>
                                                        <option value="Pending" selected>Chưa xử lý</option>
                                                    </select>
                                                </div>
                                                <button type="submit" class="btn btn-success btn-sm">Gửi phản hồi</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL DỊCH VỤ -->
                            <div class="modal fade" id="serviceModalEmp${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-info text-white">
                                            <h5 class="modal-title">Chi tiết Dịch vụ</h5>
                                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Service ID:</strong> ${fb.serviceID}</p>
                                            <p><strong>Tên dịch vụ:</strong> ${serviceNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL KHUYẾN MÃI -->
                            <div class="modal fade" id="promoModalEmp${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-warning text-dark">
                                            <h5 class="modal-title">Chi tiết Khuyến mãi</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Promotion ID:</strong> ${fb.promotionID}</p>
                                            <p><strong>Tên khuyến mãi:</strong> ${promotionNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- ✅ TAB CANDIDATES -->
        <div class="tab-pane fade" id="candidates" role="tabpanel">
            <c:choose>
                <c:when test="${empty listCandidates}">
                    <div class="alert alert-secondary mt-3">Chưa có feedback nào.</div>
                </c:when>
                <c:otherwise>
                    <div class="row mt-3">
                        <c:forEach var="fb" items="${listCandidates}">
                            <div class="col-md-6 mb-3">
                                <div class="card shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title">${fb.subject}</h5>
                                        <p class="card-text text-muted">${fb.content}</p>

                                        <!-- ✅ HIỂN THỊ TYPE -->
                                        <c:forEach var="type" items="${feedbackTypeMap[fb.feedbackID]}">
                                            <c:choose>
                                                <c:when test="${type.typeFeedbackName eq 'Dịch vụ'}">
                                                    <button type="button" class="badge bg-info text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#serviceModalCan${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:when test="${type.typeFeedbackName eq 'Khuyến mãi'}">
                                                    <button type="button" class="badge bg-warning text-dark border-0"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#promoModalCan${fb.feedbackID}">
                                                        ${type.typeFeedbackName}
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">${type.typeFeedbackName}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>

                                        <!-- ✅ STATUS -->
                                        <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                                            ${fb.status}
                                        </span>

                                        <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                                        <c:if test="${not empty fb.adminResponse}">
                                            <div class="alert alert-success mt-2 p-2">
                                                <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                                            </div>
                                        </c:if>

                                        <!-- ✅ FORM PHẢN HỒI -->
                                        <button class="btn btn-sm btn-outline-primary mt-3" 
                                                type="button"
                                                data-bs-toggle="collapse" 
                                                data-bs-target="#replyFormCan${fb.feedbackID}">
                                            💬 Soạn phản hồi
                                        </button>

                                        <div class="collapse mt-3" id="replyFormCan${fb.feedbackID}">
                                            <form action="respondFeedback" method="post">
                                                <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
                                                <input type="hidden" name="role" value="candidate">
                                                <div class="mb-2">
                                                    <textarea name="adminResponse" class="form-control" rows="3" 
                                                              placeholder="Nhập phản hồi của bạn..."></textarea>
                                                </div>
                                                <div class="mb-2">
                                                    <select name="newStatus" class="form-select">
                                                        <option value="Resolved">Đã xử lý</option>
                                                        <option value="Pending" selected>Chưa xử lý</option>
                                                    </select>
                                                </div>
                                                <button type="submit" class="btn btn-success btn-sm">Gửi phản hồi</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL DỊCH VỤ -->
                            <div class="modal fade" id="serviceModalCan${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-info text-white">
                                            <h5 class="modal-title">Chi tiết Dịch vụ</h5>
                                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Service ID:</strong> ${fb.serviceID}</p>
                                            <p><strong>Tên dịch vụ:</strong> ${serviceNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- ✅ MODAL KHUYẾN MÃI -->
                            <div class="modal fade" id="promoModalCan${fb.feedbackID}" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header bg-warning text-dark">
                                            <h5 class="modal-title">Chi tiết Khuyến mãi</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p><strong>Promotion ID:</strong> ${fb.promotionID}</p>
                                            <p><strong>Tên khuyến mãi:</strong> ${promotionNameMap[fb.feedbackID]}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
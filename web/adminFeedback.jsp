<%-- 
    Document   : adminFeedback
    Created on : Oct 17, 2025, 10:11:56 AM
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
        <style>
            body {
                background-color: #f8f9fa;
                padding: 30px;
            }
            .card {
                border-radius: 15px;
            }
            .badge {
                font-size: 0.9rem;
            }
            .tab-content {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
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

                <!-- Tất cả -->
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

                                                <span class="badge bg-info">${fb.type}</span>
                                                <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                                                    ${fb.status}
                                                </span>

                                                <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                                                <c:if test="${not empty fb.adminResponse}">
                                                    <div class="alert alert-success mt-2 p-2">
                                                        <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                                                    </div>
                                                </c:if>

                                                <!-- ✅ Nút mở form phản hồi -->
                                                <button class="btn btn-sm btn-outline-primary mt-3" 
                                                        type="button"
                                                        data-bs-toggle="collapse" 
                                                        data-bs-target="#replyForm${fb.feedbackID}">
                                                    💬 Soạn phản hồi
                                                </button>

                                                <!-- ✅ Form phản hồi ẩn -->
                                                <div class="collapse mt-3" id="replyForm${fb.feedbackID}">
                                                    <form action="respondFeedback" method="post">
                                                        <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
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
                                </c:forEach>

                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Employer -->
                <div class="tab-pane fade" id="employers" role="tabpanel">
                    <c:choose>
                        <c:when test="${empty listEmployers}">
                            <div class="alert alert-secondary mt-3">Chưa có feedback nào từ Employer.</div>
                        </c:when>
                        <c:otherwise>
                            <div class="row mt-3">
                                <c:forEach var="fb" items="${listEmployers}">
    <div class="col-md-6 mb-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">${fb.subject}</h5>
                <p class="card-text text-muted">${fb.content}</p>

                <span class="badge bg-info">${fb.type}</span>
                <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                    ${fb.status}
                </span>

                <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                <c:if test="${not empty fb.adminResponse}">
                    <div class="alert alert-success mt-2 p-2">
                        <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                    </div>
                </c:if>

                <!-- ✅ Nút mở form phản hồi -->
                <button class="btn btn-sm btn-outline-primary mt-3" 
                        type="button"
                        data-bs-toggle="collapse" 
                        data-bs-target="#replyForm${fb.feedbackID}">
                    💬 Soạn phản hồi
                </button>

                <!-- ✅ Form phản hồi ẩn -->
                <div class="collapse mt-3" id="replyForm${fb.feedbackID}">
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
</c:forEach>

                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Candidate -->
                <div class="tab-pane fade" id="candidates" role="tabpanel">
                    <c:choose>
                        <c:when test="${empty listCandidates}">
                            <div class="alert alert-secondary mt-3">Chưa có feedback nào từ Candidate.</div>
                        </c:when>
                        <c:otherwise>
                            <div class="row mt-3">
                                <c:forEach var="fb" items="${listCandidates}">
    <div class="col-md-6 mb-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">${fb.subject}</h5>
                <p class="card-text text-muted">${fb.content}</p>

                <span class="badge bg-info">${fb.type}</span>
                <span class="badge bg-${fb.status == 'Pending' ? 'warning' : 'success'}">
                    ${fb.status}
                </span>

                <p class="mt-2 mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>

                <c:if test="${not empty fb.adminResponse}">
                    <div class="alert alert-success mt-2 p-2">
                        <strong>Phản hồi từ Admin:</strong> ${fb.adminResponse}
                    </div>
                </c:if>

                <!-- ✅ Nút mở form phản hồi -->
                <button class="btn btn-sm btn-outline-primary mt-3" 
                        type="button"
                        data-bs-toggle="collapse" 
                        data-bs-target="#replyForm${fb.feedbackID}">
                    💬 Soạn phản hồi
                </button>

                <!-- ✅ Form phản hồi ẩn -->
                <div class="collapse mt-3" id="replyForm${fb.feedbackID}">
                    <form action="respondFeedback" method="post">
                         <input type="hidden" name="role" value="candidate">
                        <input type="hidden" name="feedbackID" value="${fb.feedbackID}">
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


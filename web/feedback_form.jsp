<%@page import="model.Employer"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    String role = (String) session.getAttribute("role");
    
    // Kiểm tra cơ bản: Nếu role là null, chuyển hướng ngay.
    if (role == null) {
        response.sendRedirect("access-denied.jsp");
        return;
    }

    Integer employerID = null;
    Integer candidateID = null;
    Object userObject = session.getAttribute("user");

    // 1. Phân quyền cho Employer
    if (role.equalsIgnoreCase("Employer")) {
        // Kiểm tra đối tượng người dùng (Employer)
        if (userObject == null || !(userObject instanceof Employer)) {
            response.sendRedirect("login-employer.jsp");
            return;
        }
        Employer empl = (Employer) userObject;
        employerID = empl.getEmployerId();
        
    // 2. Phân quyền cho Candidate
    } else if (role.equalsIgnoreCase("Candidate")) {
        // Giả định CandidateID được lưu dưới dạng Integer trong session attribute "candidateID"
        candidateID = (Integer) session.getAttribute("candidateId");
        
        // **LỖ HỔNG ĐƯỢC SỬA:** Nếu là Candidate nhưng không có ID, coi như chưa đăng nhập hoặc lỗi session.
        if (candidateID == null) {
            response.sendRedirect("login-candidate.jsp"); // hoặc trang đăng nhập phù hợp
            return;
        }
    } else {
        // Trường hợp role có giá trị nhưng không phải Employer/Candidate (có thể là Admin, hoặc giá trị rác)
        response.sendRedirect("access-denied.jsp");
        return;
    }
    

    
%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
        <meta charset="UTF-8">
        <title>Gửi phản hồi</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .feedback-card {
                max-width: 650px;
                margin: 9rem auto 3rem auto;
                background: #fff;
                padding: 2rem;
                border-radius: 15px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }
            .btn-submit {
                background-color: #0d6efd;
                color: white;
                border-radius: 30px;
                border: none;
                padding: 0.6rem 1.5rem;
            }
            .btn-submit:hover {
                background-color: #095bb5;
            }
            .form-check {
                margin-bottom: 0.5rem;
            }
        </style>
    </head>
    <body>

        <jsp:include page="header.jsp"/>



        <div class="container">
            <div class="feedback-card">
                <h4 class="text-center text-primary mb-3">
                    <i class="ti-comment-alt"></i> Gửi phản hồi cho quản trị viên
                </h4>

                <!-- ================= EMPLOYER FEEDBACK ================= -->
                <c:if test="${role eq 'Employer'}">
                    <form action="sendFeedback" method="post">


                        <div class="mb-3">
                            <label class="form-label fw-bold">Chủ đề</label>
                            <input type="text" name="subject" class="form-control" placeholder="Nhập tiêu đề phản hồi..." required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Loại phản hồi</label>
                            <div class="row">
                                <c:forEach var="t" items="${typeFeedbackList}">
                                    <div class="col-md-6">
                                        <div class="form-check">
                                            <input class="form-check-input type-checkbox" 
                                                   type="checkbox" 
                                                   name="typeFeedbackIDs" 
                                                   value="${t.typeFeedbackID}" 
                                                   id="type-${t.typeFeedbackID}">
                                            <label class="form-check-label" for="type-${t.typeFeedbackID}">
                                                ${t.typeFeedbackName}
                                            </label>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <!-- ========== DỊCH VỤ ========== -->
                        <div class="mb-3" id="serviceGroup" style="display:none;">
                            <label class="form-label fw-bold">Dịch vụ bạn muốn phản hồi</label>
                            <select name="serviceID" class="form-select">
                                <option value="">-- Chọn dịch vụ --</option>
                                <c:if test="${not empty serviceList}">
                                    <c:forEach var="t" items="${serviceList}">
                                        <option value="${t.serviceID}">${t.serviceName}</option>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty serviceList}">
                                    <option disabled>(Không có dịch vụ khả dụng)</option>
                                </c:if>
                            </select>
                        </div>

                        <!-- ========== KHUYẾN MÃI ========== -->
                        <div class="mb-3" id="promotionGroup" style="display:none;">
                            <label class="form-label fw-bold">Khuyến mãi bạn muốn phản hồi</label>
                            <select name="promotionID" class="form-select">
                                <option value="">-- Chọn khuyến mãi --</option>
                               <c:if test="${not empty promotionList}">
                                    <c:forEach var="pp" items="${promotionList}">
                                        <option value="${pp.promotionID}">${pp.code}</option>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty promotionList}">
                                    <option disabled>(Không có dịch vụ khả dụng)</option>
                                </c:if>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Nội dung</label>
                            <textarea name="content" class="form-control" rows="6" placeholder="Nhập nội dung phản hồi..." required></textarea>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn-submit">Gửi phản hồi</button>
                        </div>
                    </form>
                </c:if>

                <!-- ================= CANDIDATE FEEDBACK ================= -->
                <c:if test="${role eq 'Candidate'}">
                    <form action="sendFeedbackCan" method="post">


                        <div class="mb-3">
                            <label class="form-label fw-bold">Chủ đề</label>
                            <input type="text" name="subject" class="form-control" placeholder="Nhập tiêu đề phản hồi..." required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Loại phản hồi</label>
                         <c:forEach var="t" items="${typeFeedbackList}">
                                    <div class="col-md-6">
                                        <div class="form-check">
                                            <input class="form-check-input type-checkbox" 
                                                   type="checkbox" 
                                                   name="typeFeedbackIDs" 
                                                   value="${t.typeFeedbackID}" 
                                                   id="type-${t.typeFeedbackID}">
                                            <label class="form-check-label" for="type-${t.typeFeedbackID}">
                                                ${t.typeFeedbackName}
                                            </label>
                                        </div>
                                    </div>
                                </c:forEach>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Nội dung</label>
                            <textarea name="content" class="form-control" rows="6" placeholder="Nhập nội dung phản hồi..." required></textarea>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn-submit">Gửi phản hồi</button>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>
    </body>


    <!-- ========== JavaScript xử lý hiển thị nhóm dịch vụ/khuyến mãi ========== -->
    <script>
        document.querySelectorAll('.type-checkbox').forEach(cb => {
            cb.addEventListener('change', function () {
                const checkboxes = Array.from(document.querySelectorAll('.type-checkbox'));

                // Kiểm tra loại phản hồi
                const serviceSelected = checkboxes.some(cb =>
                    cb.checked && cb.nextElementSibling.textContent.trim().toLowerCase().includes("dịch vụ")
                );
                const promotionSelected = checkboxes.some(cb =>
                    cb.checked && cb.nextElementSibling.textContent.trim().toLowerCase().includes("khuyến mãi")
                );

                // Hiển thị/ẩn nhóm tương ứng
                document.getElementById('serviceGroup').style.display = serviceSelected ? 'block' : 'none';
                document.getElementById('promotionGroup').style.display = promotionSelected ? 'block' : 'none';
            });
        });
    </script>



</html>

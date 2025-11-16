<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>CV Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <style>
            .toast-message {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 14px 22px;
                border-radius: 8px;
                color: #fff;
                font-weight: 600;
                font-size: 15px;
                z-index: 9999;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
                opacity: 0;
                transform: translateX(100%);
                transition: all 0.5s ease;
            }

            /* Kiểu cho thông báo thành công */
            .toast-message.success {
                background-color: #16a34a; /* Màu xanh lá */
            }

            /* Kiểu cho thông báo lỗi */
            .toast-message.error {
                background-color: #dc2626; /* Màu đỏ */
            }

            /* Animation khi hiện */
            .toast-message.show {
                opacity: 1;
                transform: translateX(0);
            }
        </style>
    </head>

    <body>
        <!-- Toast Notification -->
        <c:if test="${not empty sessionScope.error}">
            <div class="toast-message error">${sessionScope.error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.message}">
            <div class="toast-message success">${sessionScope.message}</div>
            <c:remove var="message" scope="session"/>
        </c:if>
        <!-- header-start -->
        <jsp:include page="header.jsp"/>
        <!-- header-end -->

        <!-- bradcam_area  -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Manage Your CVs</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <div class="container">
            <div class="row">
                <div class="col-12 my-5">
                    <div class="cv-list">
                        <c:choose>
                            <c:when test="${not empty cvList}">
                                <c:forEach var="cv" items="${cvList}">
                                    <div class="single_jobs white-bg d-flex justify-content-between mb-3 p-3 rounded shadow-sm">
                                        <div class="jobs_left d-flex align-items-center">
                                            <div class="thumb me-3">
                                                <img src="img/svg_icon/1.svg" alt="">
                                            </div>
                                            <div class="jobs_conetent">
                                                <h4>${cv.fullName}</h4>
                                                <p><strong>Position:</strong> ${cv.position}</p>
                                                <p><strong>Experience:</strong> ${cv.numberExp} years</p>
                                                <p><strong>Field:</strong> ${cv.field}</p>
                                                <p><strong>Email:</strong> ${cv.email}</p>
                                                <p>
                                                    <i class="fa fa-calendar"></i> Created: 
                                                    <fmt:formatDate value="${cv.dayCreate}" pattern="yyyy-MM-dd"/>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="jobs_right">
                                            <div class="apply_now">
                                                <a class="boxed-btn3" href="edit-cv?id=${cv.CVID}">Edit</a>
                                                <a class="boxed-btn3" href="delete-cv?id=${cv.CVID}"
                                                   onclick="return confirm('Are you sure you want to delete this CV?');">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p class="text-center mt-4">You have no CVs yet.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Add New CV Button -->
                    <div class="row">
                        <div class="col-12 text-center mt-5">
                            <a href="cv-create.jsp" class="boxed-btn3">Create New CV</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <footer class="footer">
            <!-- footer content -->
        </footer>

        <!-- JS -->
        <script src="js/bootstrap.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const toasts = document.querySelectorAll(".toast-message");
                toasts.forEach((toast, index) => {
                    Object.assign(toast.style, {
                        position: "fixed",
                        top: `${20 + index * 60}px`,
                        right: "-350px",
                        opacity: "1",
                        transition: "all 0.6s ease",
                        zIndex: "9999",
                        padding: "12px 20px",
                        borderRadius: "6px",
                        color: "#fff",
                        fontWeight: "500",
                        boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
                        minWidth: "250px",
                        textAlign: "center",
                        backgroundColor: toast.classList.contains("success") ? "#28a745" : "#dc3545"
                    });

                    setTimeout(() => (toast.style.right = "20px"), 200 + index * 150);

                    setTimeout(() => {
                        toast.style.right = "-350px";
                        toast.style.opacity = "0";
                    }, 4000 + index * 150);
                });
            });
        </script>
    </body>
</html>

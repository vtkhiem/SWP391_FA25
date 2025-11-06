<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>${job.title} - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS: Bootstrap first, then libraries, then main style -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/nice-select.css">
        <link rel="stylesheet" href="css/gijgo.css">
        <link rel="stylesheet" href="css/animate.min.css">
        <link rel="stylesheet" href="css/slicknav.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/custom.css"> 
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="header.jsp"/>
        <!-- header_end -->

        <!-- bradcam_area -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Chi tiết công việc</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area -->

        <!-- Toast Notification -->
        <c:if test="${not empty message}">
            <div class="toast-message success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="toast-message error">${error}</div>
        </c:if>

        <div class="job_details_area">
            <div class="container">
                <div class="row">
                    <!-- Main job detail -->
                    <div class="col-lg-8">
                        <div class="job_details_header">
                            <div class="single_jobs white-bg d-flex justify-content-between">
                                <div class="jobs_left d-flex align-items-center">
                                    <div class="thumb">
                                        <img src="img/svg_icon/1.svg" alt="">
                                    </div>
                                    <div class="jobs_conetent">
                                        <a href="redirectWall?jobpostID=${job.jobPostID}">
                                            <h4>${job.title}</h4>
                                        </a>
                                        <div class="links_locat d-flex align-items-center">
                                            <div class="location">
                                                <p><i class="fa fa-map-marker"></i> ${job.location}</p>
                                            </div>
                                            <div class="location">
                                                <p><i class="fa fa-clock-o"></i> ${job.typeJob}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="jobs_right">
                                    <div class="apply_now">
                                        <c:choose>
                                            <c:when test="${isSaved}">
                                                <form action="unsave_job" method="post" style="display:inline;">
                                                    <input type="hidden" name="jobId" value="${job.jobPostID}">
                                                    <button type="submit" class="save_job">
                                                        <i class="ti-heart-broken"></i>
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="save_job" method="post" style="display:inline;">
                                                    <input type="hidden" name="jobId" value="${job.jobPostID}">
                                                    <button type="submit" class="save_job">
                                                        <i class="ti-heart"></i>
                                                    </button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${not empty sessionScope.user}">
                                <div class="single_jobs white-bg justify-content-between">
                                    <form action="selectCV" method="post">
                                        <input type="hidden" name="jobId" value="${job.jobPostID}">
                                        <button type="submit" class="boxed-btn3 w-100">Ứng tuyển ngay</button>    
                                    </form>
                                </div>
                            </c:if>
                        </div>

                        <!-- Description -->
                        <div class="descript_wrap white-bg">
                            <div class="single_wrap">
                                <h4>Mô tả công việc:</h4>
                                <p>${job.description}</p>
                            </div>
                        </div>
                    </div>

                    <!-- Sidebar -->
                    <div class="col-lg-4">
                        <div class="job_sumary">
                            <div class="summery_header">
                                <h3>Thông tin chi tiết</h3>
                            </div>
                            <div class="job_content">
                                <ul>
                                    <%
                                        java.time.LocalDateTime dc = ((model.JobPost) request.getAttribute("job")).getDayCreate();
                                        java.time.LocalDateTime dd = ((model.JobPost) request.getAttribute("job")).getDueDate();
                                        java.sql.Timestamp pubTs = (dc != null) ? java.sql.Timestamp.valueOf(dc) : null;
                                        java.sql.Timestamp dueTs = (dd != null) ? java.sql.Timestamp.valueOf(dd) : null;
                                        pageContext.setAttribute("publishedDate", pubTs);
                                        pageContext.setAttribute("dueDateFmt", dueTs);
                                    %>
                                    <li>Ngày đăng: <span><fmt:formatDate value="${publishedDate}" pattern="dd/MM/yyyy, HH:mm"/></span></li>
                                    <li>Hạn cuối: <span><fmt:formatDate value="${dueDateFmt}" pattern="dd/MM/yyyy, HH:mm"/></span></li>
                                    <li>Yêu cầu kinh nghiệm:
                                        <span>
                                            <c:choose>
                                                <c:when test="${job.numberExp == 0}">Không yêu cầu kinh nghiệm</c:when>
                                                <c:otherwise>${job.numberExp} năm </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </li>
                                    <li>Mức lương:
                                        <span>
                                            <fmt:formatNumber value="${job.offerMin}" type="number" maxFractionDigits="0"/> -
                                            <fmt:formatNumber value="${job.offerMax}" type="number" maxFractionDigits="0"/> VNĐ
                                        </span>
                                    </li>
                                    <li>Vị trí công việc: <span>${job.position}</span></li>
                                    <li>Hình thức làm việc: <span>${job.typeJob}</span></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

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
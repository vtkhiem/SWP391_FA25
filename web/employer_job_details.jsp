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

        <div class="job_details_area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="job_details_header">
                            <div class="single_jobs white-bg d-flex justify-content-between">
                                <div class="jobs_left d-flex align-items-center">
                                    <div class="thumb">
                                        <img src="img/svg_icon/1.svg" alt="">
                                    </div>
                                    <div class="jobs_conetent">
                                        <h4>${job.title}</h4>
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
                                        <a class="btn btn-sm btn-info m-1" href="viewApply?jobId=${job.jobPostID}"><i class="ti-folder"></i></a>
                                        <a class="btn btn-sm btn-warning m-1" href="job_edit?id=${job.jobPostID}"><i class="ti-write"></i></a>
                                        <c:choose>
                                            <c:when test="${job.visible}">
                                                <form action="hide_job" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn ẩn công việc này?');">
                                                    <input type="hidden" name="jobId" value="${job.jobPostID}">
                                                    <button type="submit" class="btn btn-sm btn-danger m-1">
                                                        <i class="ti-lock"></i>
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="visible_job" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc chắn muốn hiện công việc này?');">
                                                    <input type="hidden" name="jobId" value="${job.jobPostID}">
                                                    <button type="submit" class="btn btn-sm btn-success m-1">
                                                        <i class="ti-unlock"></i>
                                                    </button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="descript_wrap white-bg">
                            <div class="single_wrap d-flex">
                                <div class="col-8">
                                    <h4>Mô tả công việc:</h4>
                                    <p>${job.description}</p>
                                </div>
                                <div class="col-4">
                                    <h4>Thông tin chi tiết:</h4>
                                    <%
                                        java.time.LocalDateTime dc = ((model.JobPost) request.getAttribute("job")).getDayCreate();
                                        java.time.LocalDateTime dd = ((model.JobPost) request.getAttribute("job")).getDueDate();
                                        java.sql.Timestamp pubTs = (dc != null) ? java.sql.Timestamp.valueOf(dc) : null;
                                        java.sql.Timestamp dueTs = (dd != null) ? java.sql.Timestamp.valueOf(dd) : null;
                                        pageContext.setAttribute("publishedDate", pubTs);
                                        pageContext.setAttribute("dueDateFmt", dueTs);
                                    %>
                                    <p>Ngày đăng: <span><fmt:formatDate value="${publishedDate}" pattern="dd/MM/yyyy, HH:mm"/></span></p>
                                    <p>Hạn cuối: <span><fmt:formatDate value="${dueDateFmt}" pattern="dd/MM/yyyy, HH:mm"/></span></p>
                                    <p>Yêu cầu kinh nghiệm:
                                        <span>
                                            <c:choose>
                                                <c:when test="${job.numberExp == 0}">Không yêu cầu kinh nghiệm</c:when>
                                                <c:otherwise>${job.numberExp} năm </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </p>
                                    <p>Mức lương:
                                        <span>
                                            <fmt:formatNumber value="${job.offerMin}" type="number" maxFractionDigits="0"/> -
                                            <fmt:formatNumber value="${job.offerMax}" type="number" maxFractionDigits="0"/> VNĐ
                                        </span>
                                    </p>
                                    <p>Vị trí công việc: <span>${job.position}</span></p>
                                    <p>Hình thức làm việc: <span>${job.typeJob}</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->
    </body>
</html>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>${companyName} - Job Board</title>
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
        <jsp:include page="header.jsp"/>

        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text d-flex align-items-center gap-3">
                            <div class="job_logo_large">
                                <c:choose>
                                    <c:when test="${not empty ImgLogo}">
                                        <img src="${ImgLogo}" alt="${companyName}" />
                                    </c:when>
                                    <c:otherwise>
                                        <img src="img/svg_icon/1.svg" alt="${job.companyName}" />
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                <h1 class="text-white mb-3">${companyName}</h1>
                                <h5 class="text-white mb-4">${description}</h5>
                                <h5 class="text-white mb-2"><i class="ti ti-location-pin mr-2"></i>${location}</h5>
                                <h5 class="text-white mb-2"><i class="ti ti-email mr-2"></i>${email}</h5>
                                <h5 class="text-white"><i class="ti ti-link mr-2"></i>${URLWebsite}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- job_listing_area -->
        <div class="job_listing_area plus_padding">
            <div class="container">
                <div class="row">
                    <!-- Danh sách việc làm -->
                    <div class="col-12">
                        <div class="recent_joblist_wrap">
                            <div class="recent_joblist white-bg mb-3">
                                <div class="row align-items-center">
                                    <div class="col-md-6">
                                        <h3>Công việc tiêu biểu</h3>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="job_lists m-0">
                            <div class="row">
                                <c:forEach var="job" items="${wallJobs}">
                                    <div class="col-lg-12 col-md-12 mb-3">
                                        <div class="single_jobs white-bg d-flex justify-content-between p-3 rounded shadow-sm">
                                            <div class="jobs_left d-flex align-items-center">
                                                <div class="my-thumb me-3">
                                                    <c:choose>
                                                        <c:when test="${not empty job.imageUrl}">
                                                            <img src="${job.imageUrl}" alt="Avatar">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="img/svg_icon/1.svg" alt="">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="jobs_conetent">
                                                    <a href="job_details?id=${job.jobPostID}">
                                                        <h4>${job.title}</h4>
                                                    </a>
                                                    <div class="links_locat d-flex align-items-center">
                                                        <div class="location me-3">
                                                            <p><i class="fa fa-map-marker"></i> ${job.location}</p>
                                                        </div>
                                                        <div class="location">
                                                            <p><i class="fa fa-clock-o"></i> ${job.typeJob}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="jobs_right">
                                                <div class="apply_now justify-content-center">
                                                    <a href="job_details?id=${job.jobPostID}&currentPage=${param.page}" class="boxed-btn3">Xem Ngay</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- job_listing_area_end  -->

        <jsp:include page="footer.jsp"/>
    </body>
</html>
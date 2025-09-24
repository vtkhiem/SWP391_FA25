<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Details - Job Board</title>
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
                            <h3>Job Details</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area -->

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
                                        <a class="heart_mark" href="#"><i class="ti-heart"></i></a>
                                        <!-- Nút Edit Job -->
                                        <a href="job_edit?id=${job.jobPostID}" class="boxed-btn3 ml-2">Edit Job</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="descript_wrap white-bg">
                            <div class="single_wrap">
                                <h4>Job Description</h4>
                                <p>${job.description}</p>
                            </div>
                        </div>

                        <!-- Apply form -->
                        <div class="apply_job_form white-bg">
                            <h4>Apply for the job</h4>
                            <form action="apply" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="jobId" value="${job.jobPostID}">
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <input type="text" class="form-control" name="name" placeholder="Your name" required>
                                    </div>
                                    <div class="col-md-6 mb-2">
                                        <input type="email" class="form-control" name="email" placeholder="Email" required>
                                    </div>
                                    <div class="col-md-12 mb-2">
                                        <input type="url" class="form-control" name="portfolio" placeholder="Portfolio link">
                                    </div>
                                    <div class="col-md-12 mb-2">
                                        <input type="file" class="form-control" name="cvFile" required>
                                    </div>
                                    <div class="col-md-12 mb-2">
                                        <textarea class="form-control" name="coverLetter" rows="5" placeholder="Cover letter"></textarea>
                                    </div>
                                    <div class="col-md-12">
                                        <button class="boxed-btn3 w-100" type="submit">Apply Now</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Sidebar -->
                    <div class="col-lg-4">
                        <div class="job_sumary">
                            <div class="summery_header">
                                <h3>Job Summary</h3>
                            </div>
                            <div class="job_content">
                                <ul>
                                    <li>Published on: <span>${job.dayCreate}</span></li>
                                    <li>Experience: <span>${job.numberExp} years</span></li>
                                    <li>Salary: <span>${job.offerMin} - ${job.offerMax}</span></li>
                                    <li>Location: <span>${job.location}</span></li>
                                    <li>Job Nature: <span>${job.typeJob}</span></li>
                                </ul>
                            </div>
                        </div>
                        <div class="share_wrap d-flex">
                            <span>Share at:</span>
                            <ul>
                                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                            </ul>
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
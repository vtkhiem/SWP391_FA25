<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>CV Management</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- CSS here -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <link rel="stylesheet" href="css/nice-select.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/gijgo.css">
        <link rel="stylesheet" href="css/slicknav.css">
        <link rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <!-- header-start -->
        <header>
            <div class="header-area ">
                <div id="sticky-header" class="main-header-area">
                    <div class="container-fluid ">
                        <div class="header_bottom_border">
                            <div class="row align-items-center">
                                <div class="col-xl-3 col-lg-2">
                                    <div class="logo">
                                        <a href="index.jsp">
                                            <img src="img/logo.png" alt="">
                                        </a>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-7">
                                    <div class="main-menu  d-none d-lg-block">
                                        <nav>
                                            <ul id="navigation">
                                                <li><a href="index.jsp">home</a></li>
                                                <li><a href="jobs.html">Browse Job</a></li>
                                                <li><a href="#">pages <i class="ti-angle-down"></i></a>
                                                    <ul class="submenu">
                                                        <li><a href="candidate.html">Candidates </a></li>
                                                        <li><a href="job_details.html">job details </a></li>
                                                        <li><a href="elements.html">elements</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="#">blog <i class="ti-angle-down"></i></a>
                                                    <ul class="submenu">
                                                        <li><a href="blog.html">blog</a></li>
                                                        <li><a href="single-blog.html">single-blog</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="#">CV <i class="ti-angle-down"></i></a>
                                                    <ul class="submenu">
                                                        <li><a href="cv-create.jsp">CV Create</a></li>
                                                        <li><a href="${pageContext.request.contextPath}/cv-manager">CV Manager</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="contact.html">Contact</a></li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 d-none d-lg-block">
                                    <div class="Appointment">
                                        <div class="phone_num d-none d-xl-block">
                                            <a href="login.jsp">Log in</a>
                                        </div>
                                        <div class="d-none d-lg-block">
                                            <a class="boxed-btn3" href="#">Post a Job</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="mobile_menu d-block d-lg-none"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
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

        <!-- CV management area -->
        <div class="job_listing_area">
            <div class="container">
                <div class="row">
                    <div class="col-12 my-5">
                        <c:choose>
                            <c:when test="${empty cvList}">
                                <!-- Show when no CVs exist -->
                                <div class="text-center">
                                    <div class="no-cv-message mb-4">
                                        <h4>You haven't created any CVs yet</h4>
                                        <p>Create your first CV to get started!</p>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/cv-create.jsp" class="boxed-btn3">
                                        <i class="fa fa-plus"></i> Create Your First CV
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <!-- Display CV List -->
                                <div class="cv_list_container">
                                    <c:forEach items="${cvList}" var="cv">
                                        <div class="single_cv white-bg d-flex justify-content-between mb-4">
                                            <div class="cv_info d-flex align-items-center">
                                                <div class="cv_icon mr-3">
                                                    <i class="fa fa-file-text fa-3x text-primary"></i>
                                                </div>
                                                <div class="cv_content">
                                                    <h4 class="mb-2">${cv.position}</h4>
                                                    <div class="cv_details">
                                                        <span class="mr-4"><i class="fa fa-user"></i> ${cv.fullName}</span>
                                                        <span class="mr-4"><i class="fa fa-calendar"></i> Created: <fmt:formatDate value="${cv.dayCreate}" pattern="dd/MM/yyyy"/></span>
                                                        <span><i class="fa fa-briefcase"></i> Experience: ${cv.numberExp} years</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="cv_actions d-flex align-items-center">
                                                <a href="${pageContext.request.contextPath}/download-cv?id=${cv.CVID}" class="boxed-btn3 mr-2" title="Download CV">
                                                    <i class="fa fa-download"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/edit-cv?id=${cv.CVID}" class="boxed-btn3 mr-2" title="Edit CV">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <a href="javascript:void(0)" onclick="confirmDelete(${cv.CVID})" class="boxed-btn3" title="Delete CV">
                                                    <i class="fa fa-trash"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    
                                    <!-- Add New CV Button -->
                                    <div class="text-right mt-4">
                                        <a href="${pageContext.request.contextPath}/cv-create.jsp" class="boxed-btn3">
                                            <i class="fa fa-plus"></i> Create New CV
                                        </a>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

<!-- Add this JavaScript for delete confirmation -->
<script>
function confirmDelete(cvId) {
    if (confirm('Are you sure you want to delete this CV?')) {
        window.location.href = '${pageContext.request.contextPath}/delete-cv?id=' + cvId;
    }
}
</script>

        <!-- footer start -->
        <footer class="footer">
            <!-- ... (footer content similar to blog.html) ... -->
        </footer>
        <!--/ footer end  -->

        <!-- JS here -->

    </body>
</html>
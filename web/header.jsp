<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
        <header>
            <div class="header-area">
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
                                    <div class="main-menu d-none d-lg-block">
                                        <nav>
                                            <ul id="navigation">
                                                <c:choose>
                                                    <%-- Nếu là Employer --%>
                                                    <c:when test="${sessionScope.role eq 'Employer'}">
                                                        <li><a href="job_post.jsp">Create Job</a></li>
                                                        <li><a href="employer_jobs?id=${sessionScope.user.employerId}">View Jobs</a></li>
                                                        <li><a href="viewApply">View Apply</a></li>
                                                        </c:when>

                                                    <%-- Nếu chưa login hoặc là Candidate --%>
                                                    <c:otherwise>
                                                        <li><a href="index.jsp">Home</a></li>
                                                        <li><a href="jobs">Browse Job</a></li>
                                                        <li><a href="#">Blog</a></li>
                                                        <li><a href="#">CV<i class="ti-angle-down"></i></a>
                                                            <ul class="submenu">
                                                                <li><a href="cv-create.jsp">CV Create</a></li>
                                                                <li><a href="cv-list">CV Management</a></li>
                                                            </ul>
                                                        </li>
                                                        <li><a href="contact.html">Contact</a></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 d-none d-lg-block">
                                    <div class="Appointment">

                                        <!-- Nếu chưa login thì hiện nút Login -->
                                        <c:if test="${empty sessionScope.user}">
                                            <div class="phone_num d-none d-xl-block">
                                                <a href="login.jsp">Login</a>
                                            </div>
                                            <div class="d-none d-lg-block">
                                                <a class="boxed-btn3" href="employer.jsp">Employer</a>
                                            </div>
                                        </c:if>

                                        <!-- Nếu đã login thì hiện tên user + Logout -->
                                        <c:if test="${not empty sessionScope.user}">
                                            <div class="phone_num d-none d-xl-block">
                                                Xin chào, 
                                                <a href="profile">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.role eq 'Candidate'}">
                                                            ${sessionScope.user.candidateName}
                                                        </c:when>
                                                        <c:when test="${sessionScope.role eq 'Employer'}">
                                                            ${sessionScope.user.employerName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${sessionScope.user.username}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a> |
                                                <a href="logout">Logout</a>
                                            </div>
                                        </c:if>
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
    </body>
</html>
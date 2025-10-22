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
    </head>

    <body>
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
                                    <form action="applyJob" method="post">
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
                                                    <intput type="hidden" name="jobId" value=${jobId}>
                                                    <input type="hidden" name="cvId" value="${cv.CVID}>">
                                                    <button type="submit">Ứng tuyển</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
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
    </body>
</html>

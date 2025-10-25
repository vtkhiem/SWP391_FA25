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

        <style>
            /* Modal */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                inset: 0;
                background-color: rgba(0,0,0,0.5);
            }

            .modal-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 30px;
                border-radius: 10px;
                width: 90%;
                max-width: 400px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            }

            .modal-content input {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 6px;
            }

            .error {
                color: #ff4444;
                text-align: center;
                display: none;
            }

            .notification {
                position: fixed;
                top: 25px;
                right: 25px;
                padding: 15px 20px;
                background-color: #28a745;
                color: white;
                border-radius: 5px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.2);
                display: none;
            }

            .notification.error {
                background-color: #e53935;
            }

            /*button*/
            .button-group {
                margin-top: 20px;
                text-align: center;
            }

            button {
                background-color: #1d2671;
                color: #fff;
                border: none;
                border-radius: 25px;
                padding: 12px 25px;
                margin: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: 0.3s;
            }

            button:hover {
                background-color: #c33764;
                transform: scale(1.05);
            }
        </style>
        
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
                                                        <button type="submit" class="boxed-btn3 w-100">Ứng tuyển</button>
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

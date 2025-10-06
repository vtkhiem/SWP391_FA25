<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.ApplyDetail, java.time.format.DateTimeFormatter"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

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

        <style>
            /* ===== BREADCRUMB ===== */
            .bradcam_area {
                background: linear-gradient(135deg, #1489f1 0%, #0f6bb8 100%);
                padding: 4rem 0;
                text-align: center;
                color: white;
            }

            .bradcam_text h3 {
                font-size: 2.5rem;
                margin-bottom: 1rem;
            }

            .breadcrumb {
                color: rgba(255, 255, 255, 0.8);
                font-size: 1.1rem;
            }

            .search-box input {
                border-radius: 25px;
                padding-left: 15px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                transition: all 0.3s ease;
            }

            .search-box input:focus {
                border-color: #1489f1;
                box-shadow: 0 0 0 0.2rem rgba(20,137,241,0.25);
            }

            .filter-box select {
                border-radius: 25px;
                padding-left: 10px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                transition: all 0.3s ease;
            }

            .filter-box select:focus {
                border-color: #1489f1;
                box-shadow: 0 0 0 0.2rem rgba(20,137,241,0.25);
            }
        </style>
    </head>
    <body>

        <!-- Header Start -->
        <jsp:include page="header.jsp"/>
        <!-- Header End -->
        <%
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        %>
        <!-- Employer Info -->
        <c:if test="${not empty sessionScope.user and sessionScope.role == 'Employer'}">
            <div class="employer-info" style="padding: 1rem; background-color: #f5f5f5; border-bottom: 1px solid #ddd;">
                <p><strong>Welcome,</strong> ${sessionScope.user.employerName}</p>

            </div>
        </c:if>

        <!-- Breadcrumb Area -->
        <div class="bradcam_area">
            <div class="container">
                <div class="bradcam_text">
                    <h3>Application Management</h3>
                </div>
            </div>
        </div>

        <!-- Search & Filter Controls -->
        <div class="d-flex justify-content-between align-items-center mt-3 mb-3 px-3">
            <!-- Search box -->
            <form action="searchCandidate" method="post">
                <div class="search-box">
                    <input type="text" class="form-control" id="searchInput" placeholder="Search by name or email...">
                </div>
            </form>


            <!-- Filter dropdown -->
            <div class="filter-box">
                <select class="form-control" id="experienceFilter">
                    <option value="">Filter by Experience</option>
                    <option value="0-1">0 - 1 years</option>
                    <option value="2-3">2 - 3 years</option>
                    <option value="4-5">4 - 5 years</option>
                    <option value="5+">5+ years</option>
                </select>
            </div>
        </div>

        <div class="apply_listing_by_job">
            <div class="container-fluid p-0">
                <div class="row">
                    <div class="col-lg-12">
                        <form action="job_delete_bulk" method="post" onsubmit="return confirm('Are you sure want to delete selected jobs?');">
                            <div class="table-responsive mt-3">
                                <table class="table table-hover table-bordered">
                                    <thead class="thead-light">
                                        <tr>
                                            <th style="width:40px">
                                                <input type="checkbox" id="selectAll"/>
                                            </th>
                                            <th style="width:48px">No</th>
                                            <th style="width:160px">Candidate</th>
                                            <th style="width:160px">Email</th>
                                            <th style="width:120px">Experience</th>
                                            <th style="width:160px">Current salary</th>
                                            <th style="width:160px">Applied</th>
                                            <th style="width:220px">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="d" items="${details}" varStatus="st">
                                            <tr>
                                                <td>
                                                    <input type="checkbox" name="applyIds" value="${d.apply.applyId}" class="jobCheckbox"/>
                                                </td>
                                                <td>${st.index + 1}</td>

                                                <td>${d.cv.fullName}</td>
                                                <td>${d.cv.email}</td>
                                                <td>${d.cv.numberExp}</td>
                                                <td>${d.cv.currentSalary}</td>
                                                <td>
                                                    <%
                                                        ApplyDetail d = (ApplyDetail) pageContext.getAttribute("d");
                                                        if (d.getApply().getDayCreate() != null) {
                                                            out.print(d.getApply().getDayCreate().format(dtf));
                                                        } else {
                                                            out.print("-");
                                                        }
                                                    %>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty details}">
                                            <tr>
                                                <td colspan="9" class="text-center">No apply yet.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>


                        </form>
                    </div>
                </div>
            </div>
        </div>


    </body>
</html>

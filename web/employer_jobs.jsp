<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="model.JobPost, java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Employer Jobs - Job Board</title>
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

        <!-- bradcam_area  -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>${sessionScope.user.employerName}'s Jobs</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <%
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        %>

        <!-- job_listing_area -->
        <div class="job_listing_area">
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
                                            <th style="width:160px">Title</th>
                                            <th style="width:160px">Location</th>
                                            <th style="width:120px">Type</th>
                                            <th style="width:160px">Salary</th>
                                            <th style="width:160px">Deadline</th>
                                            <th style="width:160px">Published</th>
                                            <th style="width:220px">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="job" items="${jobs}" varStatus="st">
                                            <tr>
                                                <td>
                                                    <input type="checkbox" name="jobIds" value="${job.jobPostID}" class="jobCheckbox"/>
                                                </td>
                                                <td>${st.index + 1}</td>

                                                <td>
                                                    <a href="job_details?id=${job.jobPostID}">
                                                        ${job.title}
                                                    </a>
                                                    <div class="small text-muted">
                                                        <c:out value="${job.category}"/>
                                                        <c:if test="${not empty job.position}"> — <c:out value="${job.position}"/></c:if>
                                                        </div>
                                                    </td>

                                                    <td>${job.location}</td>
                                                <td>${job.typeJob}</td>

                                                <td>
                                                    <c:out value="${job.offerMin}"/> 
                                                    <c:if test="${not empty job.offerMax}"> - <c:out value="${job.offerMax}"/></c:if>
                                                </td>

                                                <td>
                                                    <%
                                                        JobPost j = (JobPost) pageContext.getAttribute("job");
                                                        if (j != null && j.getDueDate() != null) {
                                                            out.print(j.getDueDate().format(dtf));
                                                        } else {
                                                            out.print("-");
                                                        }
                                                    %>
                                                </td>

                                                <td>
                                                    <%
                                                        JobPost j2 = (JobPost) pageContext.getAttribute("job");
                                                        if (j2 != null && j2.getDayCreate() != null) {
                                                            out.print(j2.getDayCreate().format(dtf));
                                                        } else {
                                                            out.print("-");
                                                        }
                                                    %>
                                                </td>

                                                <td>
                                                    <div >
                                                        <a class="btn btn-sm btn-info me-2" href="viewApply?id=${job.jobPostID}">View Applies</a>

                                                        <c:if test="${(sessionScope.employer != null and sessionScope.employer.employerID eq job.employerID) 
                                                                      or (sessionScope.user != null and sessionScope.user.employerId eq job.employerID)}">
                                                              <a class="btn btn-sm btn-warning me-2" href="job_edit?id=${job.jobPostID}">Edit</a>
                                                              <form action="job_delete" method="post" style="display:inline-block;"
                                                                    onsubmit="return confirm('Are you sure you want to delete this job?');">
                                                                  <input type="hidden" name="id" value="${job.jobPostID}"/>
                                                                  <button type="submit" class="btn btn-sm btn-danger me-2">Delete</button>
                                                              </form>
                                                        </c:if>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <c:if test="${empty jobs}">
                                            <tr>
                                                <td colspan="9" class="text-center">No jobs found.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <button type="submit" class="btn btn-danger mt-2">Delete Selected</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- job_listing_area_end -->

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->

        <script>
            document.getElementById("selectAll").addEventListener("click", function () {
                const checkboxes = document.querySelectorAll(".jobCheckbox");
                checkboxes.forEach(cb => cb.checked = this.checked);
            });

            $(function () {
                $("#slider-range").slider({
                    range: true,
                    min: 0,
                    max: 24600,
                    values: [750, 24600],
                    slide: function (event, ui) {
                        $("#amount").val("$" + ui.values[0] + " - $" + ui.values[1] + "/ Year");
                    }
                });
                $("#amount").val("$" + $("#slider-range").slider("values", 0) +
                        " - $" + $("#slider-range").slider("values", 1) + "/ Year");
            });
        </script>
    </body>
</html>
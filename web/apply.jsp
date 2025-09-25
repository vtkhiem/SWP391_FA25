<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>
        <link rel="stylesheet" href="css/employer.css">
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

        <!-- Header Start -->
        <jsp:include page="employer-header.jsp"/>
        <!-- Header End -->

        <!-- Employer Info -->
        <c:if test="${not empty sessionScope.user and sessionScope.role == 'Employer'}">
            <div class="employer-info" style="padding: 1rem; background-color: #f5f5f5; border-bottom: 1px solid #ddd;">
                <p><strong>Welcome,</strong> ${sessionScope.user.employerName}</p>
                <p>Email: ${sessionScope.user.email}</p>
                <p>Role: ${sessionScope.role}</p>
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

        <!-- job_listing_area_start  -->
        <div class="job_listing_area">
            <div class="job_listing_container">
                <div class="job_listing_header">
                    <div class="job_listing_title">Featured Jobs</div>
                    <a href="jobs.html" class="job_listing_viewall">Browse More Job</a>
                </div>
                <div class="job_listing_nav">
                    <button class="job_nav_arrow" aria-label="Prev">&lt;</button>
                    <div class="job_listing_grid">
                        <c:forEach var="j" items="${jobList}">
                            <form action="viewApply" method="post" class="job_card_form">
                                <!-- Hidden input để gửi jobId -->
                                <input type="hidden" name="jobId" value="${j.jobPostID}" />

                                <div class="job_card" style="cursor: pointer;" onclick="this.closest('form').submit();">
                                    <img src="img/svg_icon/1.svg" alt="Logo" class="job_card_logo">
                                    <div class="job_card_info">
                                        <div class="job_card_title">${j.title}</div>
                                        <div class="job_card_salary">${j.offerMin}</div>
                                        <div class="job_card_location">${j.location}</div>
                                    </div>
                                    <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
                                </div>
                            </form>
                        </c:forEach>

                    </div>
                    <button class="job_nav_arrow" aria-label="Next">&gt;</button>
                </div>
            </div>
        </div>
        <!-- job_listing_area_end  -->

        <!-- Recent Applications Section -->
        <div class="card-container">
            <div class="applications-header">
                <h2 class="section-title" style="margin-bottom: 0;">Recent Applications</h2>
                <form action="searchApply" method="post">
                    <div class="search-filter-container">
                        <input value="${sValue}" name="txt" type="text" class="search-box" placeholder="Search candidates...">
                        <div class="status-filters">
                            <a href="#" class="filter-btn active" onclick="filterByStatus('all')">All</a>
                            <a href="#" class="filter-btn" onclick="filterByStatus('new')">New</a>
                            <a href="#" class="filter-btn" onclick="filterByStatus('reviewed')">Reviewed</a>
                            <a href="#" class="filter-btn" onclick="filterByStatus('interview')">Interview</a>
                            <a href="#" class="filter-btn" onclick="filterByStatus('hired')">Hired</a>
                            <a href="#" class="filter-btn" onclick="filterByStatus('rejected')">Rejected</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="item-list" id="applications-list">
            <c:forEach var="d" items="${applyDetails}">
                <div class="application-item fade-in-item" data-status="${d.apply.status}" style="border-left: 4px solid #2196f3;">
                    <div class="application-header">
                        <div class="applicant-info">
                            <h4>${d.candidate.candidateName}</h4>
                            <div class="applicant-details" style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; align-items: start;">
                                <span><strong>Job:</strong> ${d.job.title}</span>
                                <span><strong>Email:</strong> ${d.candidate.email}</span>
                                <span><strong>Phone:</strong> ${d.candidate.phoneNumber}</span>
                                <span><strong>Experience:</strong> ${d.cv.numberExp} years</span>
                                <span><strong>Notes:</strong> ${d.apply.note}</span>
                                <span><strong style="grid-column: 1 / -1;">Applied:</strong> ${d.apply.dayCreate}</span>
                            </div>
                        </div>
                    </div>
                    <div class="application-actions">
                        <div class="action-item">
                            <div class="action-label">View CV</div>
                            <a href="${d.cv.fileData}" class="btn btn-primary">Open CV</a>
                        </div>
                        <form action="status" method="post" class="status-form">
                            <!-- Gửi applyId để servlet biết bản ghi nào cần update -->
                            <input type="hidden" name="applyId" value="${d.apply.applyId}" />

                            <select name="status" class="status-dropdown" onchange="this.form.submit()">
                                <option value="Pending" ${d.apply.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                <option value="Hired"  ${d.apply.status == 'Hired' ? 'selected' : ''}>Hired</option>
                                <option value="Rejected" ${d.apply.status == 'Rejected' ? 'selected' : ''}>Rejected</option>
                            </select>

                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

    </body>
</html>

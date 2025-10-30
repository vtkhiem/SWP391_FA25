<%-- 
    Document   : employerWall
    Created on : Oct 30, 2025, 1:01:08 AM
    Author     : vuthienkhiem
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ===== EMPLOYER WALL START ===== -->
<div class="container mt-5">
    <h3 class="mb-4">Your Wall</h3>

    <c:if test="${empty wallJobs}">
        <div class="alert alert-info">You haven’t added any jobs to your wall yet.</div>
    </c:if>

    <div class="row">
        <c:forEach var="job" items="${wallJobs}">
            <div class="col-md-6 col-lg-4 mb-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title text-primary">${job.title}</h5>
                        <p class="card-text">
                            <i class="ti-location-pin text-muted"></i> ${job.location}<br>
                            <i class="ti-briefcase text-muted"></i> ${job.position}<br>
                            💰 ${job.offerMinFormatted} - ${job.offerMaxFormatted}<br>
                            🕓 Posted: ${job.dayCreate}
                        </p>
                    </div>
                    <div class="card-footer bg-white border-top-0 d-flex justify-content-between align-items-center">
                        <div>
                            <c:choose>
                                <c:when test="${job.activeOnWall}">
                                    <a href="toggleWallStatus?jobPostId=${job.jobPostID}&active=false"
                                       class="btn btn-sm btn-outline-danger">
                                        <i class="ti-eye-off"></i> Hide
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="toggleWallStatus?jobPostId=${job.jobPostID}&active=true"
                                       class="btn btn-sm btn-outline-success">
                                        <i class="ti-eye"></i> Show
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="d-flex gap-2">
                            <c:if test="${job.activeOnWall}">
                                <c:choose>
                                    <c:when test="${job.pinned}">
                                        <a href="pinJob?employerId=${sessionScope.user.employerId}&jobpostId=${job.jobPostID}&action=unpin"
                                           class="btn btn-sm btn-warning">
                                            <i class="ti-pin2"></i> Unpin
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="pinJob?employerId=${sessionScope.user.employerId}&jobpostId=${job.jobPostID}&action=pin"
                                           class="btn btn-sm btn-outline-warning">
                                            <i class="ti-pin"></i> Pin
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <a href="jobDetail?jobPostId=${job.jobPostID}" 
                               class="btn btn-sm btn-outline-primary">
                                <i class="ti-search"></i> View
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- ===== EMPLOYER WALL END ===== -->

<style>
    .card {
        border-radius: 12px;
        transition: all 0.3s ease;
    }

    .card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 25px rgba(0,0,0,0.1);
    }

    .card-title {
        font-weight: 600;
        font-size: 1.1rem;
    }
</style>

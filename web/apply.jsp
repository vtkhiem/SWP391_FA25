<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>
        <!-- CSS here -->
        <link rel="stylesheet" href="css/employer.css">
    </head>
    <body>

        <!-- Header Start -->
        <header class="header-area">
            <div class="container-fluid">
                <div class="header_bottom_border">
                    <div class="logo">
                        <a href="index.jsp">
                            <img src="img/logo.png" alt="">
                        </a>
                    </div>
                    <nav class="main-menu">
                        <ul>
                            <li><a href="create-job.jsp">Create Job</a></li>
                            <li><a href="edit-job.jsp">Edit Job</a></li>
                            <li class="active"><a href="apply.jsp">View Apply</a></li>
                        </ul>
                    </nav>
                    <div class="user-info">
                        <a href="#" class="profile-btn">Admin</a>
                    </div>
                </div>
            </div>
        </header>
        <!-- Header End -->

        <!-- Breadcrumb Area -->
        <div class="bradcam_area">
            <div class="container">
                <div class="bradcam_text">
                    <h3>Application Management</h3>
                    <p class="breadcrumb">Review and manage candidate applications</p>
                </div>
            </div>
        </div>

        <!-- Applications Content -->
        <div class="job_listing_area">
            <div class="container">
                <!-- Dashboard Stats -->
                <div class="dashboard-stats">
                    <div class="stat-card">
                        <div class="stat-number">156</div>
                        <div class="stat-label">Total Applications</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">24</div>
                        <div class="stat-label">New Applications</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">12</div>
                        <div class="stat-label">Interviews Scheduled</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">8</div>
                        <div class="stat-label">Hired This Month</div>
                    </div>
                </div>

                <!-- Recent Applications Section -->

                <div class="card-container">
                    <div class="applications-header">
                        <h2 class="section-title" style="margin-bottom: 0;">Recent Applications</h2>

                        <div class="search-filter-container">
                            <input type="text" class="search-box" placeholder="Search candidates..." id="searchInput">
                            <div class="status-filters">
                                <a href="#" class="filter-btn active" onclick="filterByStatus('all')">All</a>
                                <a href="#" class="filter-btn" onclick="filterByStatus('new')">New</a>
                                <a href="#" class="filter-btn" onclick="filterByStatus('reviewed')">Reviewed</a>
                                <a href="#" class="filter-btn" onclick="filterByStatus('interview')">Interview</a>
                                <a href="#" class="filter-btn" onclick="filterByStatus('hired')">Hired</a>
                                <a href="#" class="filter-btn" onclick="filterByStatus('rejected')">Rejected</a>
                            </div>
                        </div>
                    </div>

                    <div class="item-list" id="applications-list">
                        <c:forEach var="d" items="${applyDetails}">
                            <div class="application-item fade-in-item" 
                                 data-status="${d.status.statusName}" 
                                 style="border-left: 4px solid #2196f3;">

                                <div class="application-header">
                                    <div class="applicant-info">
                                        <h4>${d.candidate.candidateName}</h4>
                                        <div class="applicant-details">
                                            <span><strong>Job:</strong> ${d.job.title}</span>
                                            <span><strong>Email:</strong> ${d.candidate.email}</span>
                                            <span><strong>Phone:</strong> ${d.candidate.phoneNumber}</span>
                                            <span><strong>Experience:</strong> ${d.cv.numberExp} years</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="application-actions">
                                    <div class="action-item">
                                        <div class="action-label">View CV</div>
                                        <a href="${d.cv.fileData}" class="btn btn-primary">Open CV</a>

                                    </div>
                                    <div class="action-item">
                                        <div class="action-label">Rate</div>
                                        <button class="btn btn-info" >9.2/10</button>
                                    </div>
                                    <div class="action-item">
                                        <div class="action-label">Status</div>
                                        <span class="status-badge status-hired">${d.status.statusName}</span>
                                    </div>
                                    <div class="action-item">
                                        <div class="action-label">Action</div>
                                        <button class="btn btn-success" >View Offer</button>
                                    </div>
                                </div>
                                <!--app sumary was here--> 
                                <div class="application-summary">
                                    <span>
                                        <strong>Applied:</strong>
                                        <span class="apply-time" data-date="${d.apply.dayCreate}">${d.apply.dayCreate}</span>

                                    </span>
                                </div>

                            </div>
                        </c:forEach>
                    </div>

                    </body>
                    </html>
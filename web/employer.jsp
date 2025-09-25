<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>
        <!-- CSS here -->
        <link rel="stylesheet" href="css/employer.css">
    </head>
    <body>

        <!-- Header Start -->
        <jsp:include page="employer-header.jsp"/>

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


                <!-- Quick Actions -->
                <div class="quick-actions">
                    <h2 class="section-title">Quick Actions</h2>
                    <div class="actions-grid">
                        <a href="job_post.jsp" class="action-btn" onclick="showCreateJobForm()">
                            <div class="action-content">
                                <h4>Create New Job</h4>
                                <p>Post a new job opening and start receiving applications</p>
                            </div>
                        </a>
                        <a href="jobs" class="action-btn" onclick="showEditJobSection()">
                            <div class="action-content">
                                <h4>Edit Existing Jobs</h4>
                                <p>Update job details, requirements, and status</p>
                            </div>
                        </a>
                        <a href="#view-apply" class="action-btn" onclick="showApplicationsSection()">
                            <div class="action-content">
                                <h4>View Applications</h4>
                                <p>Review and manage candidate applications</p>
                            </div>
                        </a>
                    </div>
                </div>

                <!-- Job Management Section -->
                <div class="job-management">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                        <h2 class="section-title" style="margin-bottom: 0;">Your Job Postings</h2>
                        <button class="btn btn-primary" onclick="showCreateJobForm()">New Job</button>
                    </div>

                    <!-- Job Filters -->
                    <div class="job-filters">
                        <a href="#" class="filter-btn active" onclick="filterJobs('all')">All Jobs</a>
                        <a href="#" class="filter-btn" onclick="filterJobs('active')">Active</a>
                        <a href="#" class="filter-btn" onclick="filterJobs('paused')">Paused</a>
                        <a href="#" class="filter-btn" onclick="filterJobs('closed')">Closed</a>
                    </div>

                    <!-- Job List -->
                    <div class="job-list">
                        <div class="job-item" data-status="active">
                            <div class="job-header">
                                <div class="job-info">
                                    <h4>Senior Frontend Developer</h4>
                                    <div class="job-meta">
                                        <span>Remote</span>
                                        <span>Full-time</span>
                                        <span>$80,000 - $120,000</span>
                                        <span style="color: #28a745;">Active</span>
                                    </div>
                                </div>
                                <div class="job-actions">
                                    <a href="#" class="btn btn-primary" onclick="viewApplications('frontend-dev')">23 Applications</a>
                                    <a href="#" class="btn btn-secondary" onclick="editJob('frontend-dev')">Edit</a>
                                    <button class="btn btn-danger" onclick="deleteJob('frontend-dev')">Delete</button>
                                </div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 5 days ago</span>
                                <span><strong>Views:</strong> 156</span>
                                <span><strong>Applications:</strong> 23</span>
                                <span><strong>Interviews:</strong> 5</span>
                            </div>
                        </div>

                        <div class="job-item" data-status="active">
                            <div class="job-header">
                                <div class="job-info">
                                    <h4>Marketing Manager</h4>
                                    <div class="job-meta">
                                        <span>New York, NY</span>
                                        <span>Full-time</span>
                                        <span>$70,000 - $90,000</span>
                                        <span style="color: #28a745;">Active</span>
                                    </div>
                                </div>
                                <div class="job-actions">
                                    <a href="#" class="btn btn-primary" onclick="viewApplications('marketing-mgr')">18 Applications</a>
                                    <a href="#" class="btn btn-secondary" onclick="editJob('marketing-mgr')">Edit</a>
                                    <button class="btn btn-danger" onclick="deleteJob('marketing-mgr')">Delete</button>
                                </div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 1 week ago</span>
                                <span><strong>Views:</strong> 89</span>
                                <span><strong>Applications:</strong> 18</span>
                                <span><strong>Interviews:</strong> 3</span>
                            </div>
                        </div>

                        <div class="job-item" data-status="paused">
                            <div class="job-header">
                                <div class="job-info">
                                    <h4>UI/UX Designer</h4>
                                    <div class="job-meta">
                                        <span>San Francisco, CA</span>
                                        <span>Full-time</span>
                                        <span>$75,000 - $95,000</span>
                                        <span style="color: #ffc107;">Paused</span>
                                    </div>
                                </div>
                                <div class="job-actions">
                                    <a href="#" class="btn btn-primary" onclick="viewApplications('ui-designer')">31 Applications</a>
                                    <a href="#" class="btn btn-secondary" onclick="editJob('ui-designer')">Edit</a>
                                    <button class="btn btn-danger" onclick="deleteJob('ui-designer')">Delete</button>
                                </div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 2 weeks ago</span>
                                <span><strong>Views:</strong> 203</span>
                                <span><strong>Applications:</strong> 31</span>
                                <span><strong>Interviews:</strong> 7</span>
                            </div>
                        </div>

                        <div class="job-item" data-status="closed">
                            <div class="job-header">
                                <div class="job-info">
                                    <h4>Backend Developer</h4>
                                    <div class="job-meta">
                                        <span>Austin, TX</span>
                                        <span>Full-time</span>
                                        <span>$85,000 - $110,000</span>
                                        <span style="color: #dc3545;">Closed</span>
                                    </div>
                                </div>
                                <div class="job-actions">
                                    <a href="#" class="btn btn-primary" onclick="viewApplications('backend-dev')">45 Applications</a>
                                    <a href="#" class="btn btn-secondary" onclick="editJob('backend-dev')">Edit</a>
                                    <button class="btn btn-danger" onclick="deleteJob('backend-dev')">Delete</button>
                                </div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 1 month ago</span>
                                <span><strong>Views:</strong> 312</span>
                                <span><strong>Applications:</strong> 45</span>
                                <span><strong>Hired:</strong> 1 candidate</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>


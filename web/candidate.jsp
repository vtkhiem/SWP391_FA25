<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Job Management</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f8faff;
                line-height: 1.6;
            }

            /* Header & Navbar */
            .header-area {
                background: #1489f1;
                padding: 1rem 0;
                box-shadow: 0 2px 10px rgba(20, 137, 241, 0.2);
                position: sticky;
                top: 0;
                z-index: 1000;
            }

            .container-fluid {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            .header_bottom_border {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .logo a {
                color: white;
                font-size: 1.8rem;
                font-weight: bold;
                text-decoration: none;
            }

            .main-menu ul {
                display: flex;
                list-style: none;
                gap: 2rem;
                margin: 0;
            }

            .main-menu ul li a {
                color: white;
                text-decoration: none;
                padding: 0.8rem 1.5rem;
                border-radius: 25px;
                transition: all 0.3s ease;
                font-weight: 500;
                text-transform: capitalize;
            }

            .main-menu ul li a:hover {
                background: rgba(255, 255, 255, 0.1);
                transform: translateY(-2px);
            }

            .main-menu ul li.active a {
                background: rgba(255, 255, 255, 0.2);
            }

            .user-info {
                display: flex;
                align-items: center;
                gap: 1rem;
            }

            .user-info .profile-btn {
                background: rgba(255, 255, 255, 0.1);
                color: white;
                padding: 0.8rem 1.5rem;
                border-radius: 25px;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .user-info .profile-btn:hover {
                background: rgba(255, 255, 255, 0.2);
            }

            /* Breadcrumb */
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

            /* Main Content */
            .job_listing_area {
                padding: 3rem 0;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            /* Dashboard Stats */
            .dashboard-stats {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 2rem;
                margin-bottom: 3rem;
            }

            .stat-card {
                background: white;
                padding: 2rem;
                border-radius: 15px;
                box-shadow: 0 5px 20px rgba(20, 137, 241, 0.1);
                text-align: center;
                transition: transform 0.3s ease;
            }

            .stat-card:hover {
                transform: translateY(-5px);
            }

            .stat-number {
                font-size: 2.5rem;
                font-weight: bold;
                color: #1489f1;
                margin-bottom: 0.5rem;
            }

            .stat-label {
                color: #666;
                font-size: 1rem;
            }

            /* Quick Actions */
            .quick-actions {
                background: white;
                border-radius: 15px;
                padding: 2rem;
                margin-bottom: 3rem;
                box-shadow: 0 5px 20px rgba(20, 137, 241, 0.1);
            }

            .section-title {
                color: #333;
                font-size: 1.8rem;
                margin-bottom: 1.5rem;
                font-weight: 600;
            }

            .actions-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 1.5rem;
            }

            .action-btn {
                background: #f8faff;
                border: 2px solid #e8f2ff;
                padding: 2rem;
                border-radius: 12px;
                text-decoration: none;
                color: #333;
                transition: all 0.3s ease;
                display: block;
            }

            .action-btn:hover {
                background: #1489f1;
                color: white;
                transform: translateY(-3px);
                box-shadow: 0 10px 30px rgba(20, 137, 241, 0.3);
            }

            .action-content h4 {
                font-size: 1.2rem;
                margin-bottom: 0.5rem;
            }

            .action-content p {
                opacity: 0.8;
                font-size: 0.9rem;
            }

            /* Job Management Section */
            .job-management {
                background: white;
                border-radius: 15px;
                padding: 2rem;
                box-shadow: 0 5px 20px rgba(20, 137, 241, 0.1);
            }

            .job-filters {
                display: flex;
                gap: 1rem;
                margin-bottom: 2rem;
                flex-wrap: wrap;
            }

            .filter-btn {
                padding: 0.8rem 1.5rem;
                border: 2px solid #e8f2ff;
                background: #f8faff;
                border-radius: 25px;
                color: #333;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .filter-btn.active,
            .filter-btn:hover {
                background: #1489f1;
                color: white;
                border-color: #1489f1;
            }

            /* Job List */
            .job-list {
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }

            .job-item {
                background: #f8faff;
                border: 2px solid #e8f2ff;
                border-radius: 12px;
                padding: 1.5rem;
                transition: all 0.3s ease;
            }

            .job-item:hover {
                border-color: #1489f1;
                transform: translateX(5px);
            }

            .job-header {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 1rem;
            }

            .job-info h4 {
                color: #333;
                font-size: 1.3rem;
                margin-bottom: 0.5rem;
            }

            .job-meta {
                display: flex;
                gap: 1.5rem;
                color: #666;
                font-size: 0.9rem;
            }

            .job-meta span {
                display: flex;
                align-items: center;
                gap: 0.3rem;
            }

            .job-actions {
                display: flex;
                gap: 0.5rem;
            }

            .btn {
                padding: 0.6rem 1rem;
                border: none;
                border-radius: 6px;
                text-decoration: none;
                font-size: 0.9rem;
                transition: all 0.3s ease;
                cursor: pointer;
            }

            .btn-primary {
                background: #1489f1;
                color: white;
            }

            .btn-secondary {
                background: #f0f0f0;
                color: #333;
            }

            .btn-danger {
                background: #ff4757;
                color: white;
            }

            .btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            }

            .job-stats {
                margin-top: 1rem;
                padding-top: 1rem;
                border-top: 1px solid #e8f2ff;
                display: flex;
                gap: 2rem;
                color: #666;
                font-size: 0.9rem;
            }

            /* Mobile Responsive */
            @media (max-width: 768px) {
                .header_bottom_border {
                    flex-direction: column;
                    gap: 1rem;
                }

                .main-menu ul {
                    flex-wrap: wrap;
                    justify-content: center;
                }

                .main-menu ul li a {
                    padding: 0.6rem 1rem;
                    font-size: 0.9rem;
                }

                .bradcam_text h3 {
                    font-size: 2rem;
                }

                .actions-grid {
                    grid-template-columns: 1fr;
                }

                .job-header {
                    flex-direction: column;
                    gap: 1rem;
                }

                .job-meta {
                    flex-direction: column;
                    gap: 0.5rem;
                }

                .job-actions {
                    width: 100%;
                    justify-content: space-between;
                }
            }

            /* Animations */
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .stat-card {
                animation: fadeInUp 0.6s ease forwards;
                opacity: 0;
            }

            .stat-card:nth-child(1) {
                animation-delay: 0.1s;
            }
            .stat-card:nth-child(2) {
                animation-delay: 0.2s;
            }
            .stat-card:nth-child(3) {
                animation-delay: 0.3s;
            }
            .stat-card:nth-child(4) {
                animation-delay: 0.4s;
            }
        </style>
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
                            <li class="active"><a href="#create-job">Create Job</a></li>
                            <li><a href="#edit-job">Edit Job</a></li>
                            <li><a href="#view-apply">View Apply</a></li>
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
                    <h3>Job Management Dashboard</h3>
                    <p class="breadcrumb">Manage your job postings and applications</p>
                </div>
            </div>
        </div>

        <!-- Dashboard Content -->
        <div class="job_listing_area">
            <div class="container">
                <!-- Dashboard Stats -->
                <div class="dashboard-stats">
                    <div class="stat-card">
                        <div class="stat-number">24</div>
                        <div class="stat-label">Active Jobs</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-number">156</div>
                        <div class="stat-label">Total Applications</div>
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
                        <a href="#create-job" class="action-btn" onclick="showCreateJobForm()">
                            <div class="action-content">
                                <h4>Create New Job</h4>
                                <p>Post a new job opening and start receiving applications</p>
                            </div>
                        </a>
                        <a href="#edit-job" class="action-btn" onclick="showEditJobSection()">
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

        <script>
            // Navigation functionality
            function showCreateJobForm() {
                alert('Navigate to Create Job page - This would open a form to create new job posting');
            }

            function showEditJobSection() {
                alert('Navigate to Edit Jobs section - This would show list of jobs to edit');
            }

            function showApplicationsSection() {
                alert('Navigate to Applications section - This would show all applications received');
            }

            // Job management functions
            function viewApplications(jobId) {
                alert(`View applications for job: ${jobId}`);
            }

            function editJob(jobId) {
                alert(`Edit job: ${jobId}`);
            }

            function deleteJob(jobId) {
                if (confirm(`Are you sure you want to delete this job posting?`)) {
                    alert(`Job ${jobId} deleted successfully`);
                    // Here you would remove the job item from the DOM
                }
            }

            // Filter jobs
            function filterJobs(status) {
                const filterBtns = document.querySelectorAll('.filter-btn');
                const jobItems = document.querySelectorAll('.job-item');

                // Update active filter button
                filterBtns.forEach(btn => btn.classList.remove('active'));
                event.target.classList.add('active');

                // Filter job items
                jobItems.forEach(item => {
                    if (status === 'all' || item.dataset.status === status) {
                        item.style.display = 'block';
                    } else {
                        item.style.display = 'none';
                    }
                });
            }

            // Navbar active state
            document.querySelectorAll('.main-menu ul li a').forEach(link => {
                link.addEventListener('click', function (e) {
                    e.preventDefault();
                    document.querySelectorAll('.main-menu ul li').forEach(li => li.classList.remove('active'));
                    this.parentElement.classList.add('active');
                });
            });

            // Counter animation for stats
            function animateCounters() {
                const counters = document.querySelectorAll('.stat-number');
                counters.forEach(counter => {
                    const target = parseInt(counter.textContent);
                    let current = 0;
                    const increment = target / 30;

                    const timer = setInterval(() => {
                        current += increment;
                        if (current >= target) {
                            counter.textContent = target;
                            clearInterval(timer);
                        } else {
                            counter.textContent = Math.floor(current);
                        }
                    }, 50);
                });
            }

            // Initialize animations when page loads
            document.addEventListener('DOMContentLoaded', function () {
                setTimeout(animateCounters, 500);
            });
        </script>
    </body>
</html>
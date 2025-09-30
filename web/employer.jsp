<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/employer.css">
        
    </head>
    <body>

        <!-- Header Start -->
        <jsp:include page="header.jsp"/>
        <!-- Header End -->

        <!-- Breadcrumb Area -->
        <div class="bradcam_area">
            <div class="container">
                <div class="bradcam_text">
                    <h3>Application Management</h3>
                    
                </div>
            </div>
        </div>

        <!-- Dashboard Stats -->
        <div class="container" style="margin-top: 3rem;">
            <div class="dashboard-stats">
                <div class="stat-card fade-in-item">
                    <div class="stat-number">156</div>
                    <div class="stat-label">Total Applications</div>
                </div>
                <div class="stat-card fade-in-item">
                    <div class="stat-number">24</div>
                    <div class="stat-label">New Applications</div>
                </div>
                <div class="stat-card fade-in-item">
                    <div class="stat-number">12</div>
                    <div class="stat-label">Interviews Scheduled</div>
                </div>
                <div class="stat-card fade-in-item">
                    <div class="stat-number">8</div>
                    <div class="stat-label">Hired This Month</div>
                </div>
            </div>
        </div>

        <!-- Job Listing Area -->
        <div class="job_listing_area">
            <div class="job_listing_container">
                <div class="job_listing_header">
                    <div class="job_listing_title">Your Job Postings</div>
                    <a href="#" class="job_listing_viewall">Manage All Jobs</a>
                </div>
                <div class="job_listing_nav">
                    <button class="job_nav_arrow" aria-label="Prev">&lt;</button>
                    <div class="job_listing_grid">
                        <form class="job_card_form">
                            <div class="job_card">
                                <img src="https://via.placeholder.com/50" alt="Logo" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Senior Frontend Developer</div>
                                    <div class="job_card_salary">$80,000 - $120,000</div>
                                    <div class="job_card_location">Remote</div>
                                </div>
                                <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
                            </div>
                        </form>

                        <form class="job_card_form">
                            <div class="job_card">
                                <img src="https://via.placeholder.com/50" alt="Logo" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Marketing Manager</div>
                                    <div class="job_card_salary">$70,000 - $90,000</div>
                                    <div class="job_card_location">New York, NY</div>
                                </div>
                                <div class="job_card_hot"><i class="fa fa-fire"></i>Hot</div>
                            </div>
                        </form>

                        <form class="job_card_form">
                            <div class="job_card">
                                <img src="https://via.placeholder.com/50" alt="Logo" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">UI/UX Designer</div>
                                    <div class="job_card_salary">$75,000 - $95,000</div>
                                    <div class="job_card_location">San Francisco, CA</div>
                                </div>
                            </div>
                        </form>

                        <form class="job_card_form">
                            <div class="job_card">
                                <img src="https://via.placeholder.com/50" alt="Logo" class="job_card_logo">
                                <div class="job_card_info">
                                    <div class="job_card_title">Backend Developer</div>
                                    <div class="job_card_salary">$85,000 - $110,000</div>
                                    <div class="job_card_location">Austin, TX</div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <button class="job_nav_arrow" aria-label="Next">&gt;</button>
                </div>
            </div>
        </div>

        <!-- Recent Applications Section -->
        <div class="card-container">
            <div class="applications-header">
                <h2 class="section-title" style="margin-bottom: 0;">Recent Applications</h2>
                <div class="search-filter-container">
                    <input type="text" class="search-box" placeholder="Search candidates...">
                    <div class="status-filters">
                        <a href="#" class="filter-btn active">All</a>
                        <a href="#" class="filter-btn">New</a>
                        <a href="#" class="filter-btn">Reviewed</a>
                        <a href="#" class="filter-btn">Interview</a>
                        <a href="#" class="filter-btn">Hired</a>
                        <a href="#" class="filter-btn">Rejected</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="item-list">
            <!-- Application Item 1 -->
            <div class="application-item fade-in-item" style="border-left: 4px solid #2196f3;">
                <div class="application-header">
                    <div class="applicant-info">
                        <h4>John Anderson</h4>
                        <div class="applicant-details">
                            <span><strong>Job:</strong> Senior Frontend Developer</span>
                            <span><strong>Email:</strong> john.anderson@email.com</span>
                            <span><strong>Phone:</strong> +1 (555) 123-4567</span>
                            <span><strong>Experience:</strong> 5 years</span>
                            <span><strong>Notes:</strong> Excellent React skills, previous experience at tech startups</span>
                            <span style="grid-column: 1 / -1;"><strong>Applied:</strong> 2 days ago</span>
                        </div>
                    </div>
                </div>
                <div class="application-actions">
                    <div class="action-item">
                        <div class="action-label">View CV</div>
                        <a href="#" class="btn btn-primary">Open CV</a>
                    </div>
                    <form class="status-form">
                        <div class="action-label">Application Status</div>
                        <select class="status-dropdown">
                            <option value="Pending" selected>Pending</option>
                            <option value="Hired">Hired</option>
                            <option value="Rejected">Rejected</option>
                        </select>
                    </form>
                </div>
            </div>

            <!-- Application Item 2 -->
            <div class="application-item fade-in-item" style="border-left: 4px solid #4caf50;">
                <div class="application-header">
                    <div class="applicant-info">
                        <h4>Sarah Martinez</h4>
                        <div class="applicant-details">
                            <span><strong>Job:</strong> Marketing Manager</span>
                            <span><strong>Email:</strong> sarah.martinez@email.com</span>
                            <span><strong>Phone:</strong> +1 (555) 234-5678</span>
                            <span><strong>Experience:</strong> 7 years</span>
                            <span><strong>Notes:</strong> Strong digital marketing background, SEO expert</span>
                            <span style="grid-column: 1 / -1;"><strong>Applied:</strong> 3 days ago</span>
                        </div>
                    </div>
                </div>
                <div class="application-actions">
                    <div class="action-item">
                        <div class="action-label">View CV</div>
                        <a href="#" class="btn btn-primary">Open CV</a>
                    </div>
                    <form class="status-form">
                        <div class="action-label">Application Status</div>
                        <select class="status-dropdown">
                            <option value="Pending">Pending</option>
                            <option value="Hired" selected>Hired</option>
                            <option value="Rejected">Rejected</option>
                        </select>
                    </form>
                </div>
            </div>

            <!-- Application Item 3 -->
            <div class="application-item fade-in-item" style="border-left: 4px solid #ff9800;">
                <div class="application-header">
                    <div class="applicant-info">
                        <h4>Michael Chen</h4>
                        <div class="applicant-details">
                            <span><strong>Job:</strong> UI/UX Designer</span>
                            <span><strong>Email:</strong> michael.chen@email.com</span>
                            <span><strong>Phone:</strong> +1 (555) 345-6789</span>
                            <span><strong>Experience:</strong> 4 years</span>
                            <span><strong>Notes:</strong> Portfolio shows excellent design sense, Figma expert</span>
                            <span style="grid-column: 1 / -1;"><strong>Applied:</strong> 5 days ago</span>
                        </div>
                    </div>
                </div>
                <div class="application-actions">
                    <div class="action-item">
                        <div class="action-label">View CV</div>
                        <a href="#" class="btn btn-primary">Open CV</a>
                    </div>
                    <form class="status-form">
                        <div class="action-label">Application Status</div>
                        <select class="status-dropdown">
                            <option value="Pending" selected>Pending</option>
                            <option value="Hired">Hired</option>
                            <option value="Rejected">Rejected</option>
                        </select>
                    </form>
                </div>
            </div>

            <!-- Application Item 4 -->
            <div class="application-item fade-in-item" style="border-left: 4px solid #9c27b0;">
                <div class="application-header">
                    <div class="applicant-info">
                        <h4>Emily Thompson</h4>
                        <div class="applicant-details">
                            <span><strong>Job:</strong> Backend Developer</span>
                            <span><strong>Email:</strong> emily.thompson@email.com</span>
                            <span><strong>Phone:</strong> +1 (555) 456-7890</span>
                            <span><strong>Experience:</strong> 6 years</span>
                            <span><strong>Notes:</strong> Strong Python and Node.js skills, cloud architecture experience</span>
                            <span style="grid-column: 1 / -1;"><strong>Applied:</strong> 1 week ago</span>
                        </div>
                    </div>
                </div>
                <div class="application-actions">
                    <div class="action-item">
                        <div class="action-label">View CV</div>
                        <a href="#" class="btn btn-primary">Open CV</a>
                    </div>
                    <form class="status-form">
                        <div class="action-label">Application Status</div>
                        <select class="status-dropdown">
                            <option value="Pending" selected>Pending</option>
                            <option value="Hired">Hired</option>
                            <option value="Rejected">Rejected</option>
                        </select>
                    </form>
                </div>
            </div>
        </div>

        <script>
            // Filter buttons functionality
            document.querySelectorAll('.filter-btn').forEach(btn => {
                btn.addEventListener('click', function (e) {
                    e.preventDefault();
                    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                    this.classList.add('active');
                });
            });

            // Job card click functionality
            document.querySelectorAll('.job_card').forEach(card => {
                card.addEventListener('click', function () {
                    console.log('Job card clicked');
                });
            });
        </script>

    </body>
</html>
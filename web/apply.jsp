<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Applications - Job Management</title>
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
                            <li><a href="#create-job">Create Job</a></li>
                            <li><a href="#edit-job">Edit Job</a></li>
                            <li class="active"><a href="#view-apply">View Apply</a></li>
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
                    <h3>View Applications</h3>
                    <p class="breadcrumb">Review and manage candidate applications for your job postings</p>
                </div>
            </div>
        </div>

        <!-- Applications Content -->
        <div class="job_listing_area">
            <div class="container">
                <!-- Job Selection Section -->
                <div class="job-selection">
                    <h2 class="section-title">Select a Job to View Applications</h2>
                    <div class="job-cards">
                        <div class="job-card" onclick="selectJob('frontend-dev', 'Senior Frontend Developer', 23)">
                            <div class="job-card-header">
                                <div>
                                    <div class="job-title">Senior Frontend Developer</div>
                                    <div class="job-meta">
                                        <span>Remote</span>
                                        <span>Full-time</span>
                                        <span>$80,000 - $120,000</span>
                                    </div>
                                </div>
                                <div class="application-count">23 Applications</div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 5 days ago</span>
                                <span><strong>Views:</strong> 156</span>
                            </div>
                        </div>

                        <div class="job-card" onclick="selectJob('marketing-mgr', 'Marketing Manager', 18)">
                            <div class="job-card-header">
                                <div>
                                    <div class="job-title">Marketing Manager</div>
                                    <div class="job-meta">
                                        <span>New York, NY</span>
                                        <span>Full-time</span>
                                        <span>$70,000 - $90,000</span>
                                    </div>
                                </div>
                                <div class="application-count">18 Applications</div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 1 week ago</span>
                                <span><strong>Views:</strong> 89</span>
                            </div>
                        </div>

                        <div class="job-card" onclick="selectJob('ui-designer', 'UI/UX Designer', 31)">
                            <div class="job-card-header">
                                <div>
                                    <div class="job-title">UI/UX Designer</div>
                                    <div class="job-meta">
                                        <span>San Francisco, CA</span>
                                        <span>Full-time</span>
                                        <span>$75,000 - $95,000</span>
                                    </div>
                                </div>
                                <div class="application-count">31 Applications</div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 2 weeks ago</span>
                                <span><strong>Views:</strong> 203</span>
                            </div>
                        </div>

                        <div class="job-card" onclick="selectJob('backend-dev', 'Backend Developer', 45)">
                            <div class="job-card-header">
                                <div>
                                    <div class="job-title">Backend Developer</div>
                                    <div class="job-meta">
                                        <span>Austin, TX</span>
                                        <span>Full-time</span>
                                        <span>$85,000 - $110,000</span>
                                    </div>
                                </div>
                                <div class="application-count">45 Applications</div>
                            </div>
                            <div class="job-stats">
                                <span><strong>Posted:</strong> 1 month ago</span>
                                <span><strong>Views:</strong> 312</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Applications Section -->
                <div class="applications-section" id="applications-section">
                    <div class="applications-header">
                        <h2 class="section-title" id="selected-job-title">Applications for Selected Job</h2>
                        <div class="search-filter-container">
                            <input type="text" class="search-box" placeholder="Search by candidate name or email..." onkeyup="searchApplications(this.value)">
                            <div class="status-filters">
                                <button class="filter-btn active" onclick="filterApplications('all')">All</button>
                                <button class="filter-btn" onclick="filterApplications('new')">New</button>
                                <button class="filter-btn" onclick="filterApplications('reviewed')">Reviewed</button>
                                <button class="filter-btn" onclick="filterApplications('interview')">Interview</button>
                                <button class="filter-btn" onclick="filterApplications('hired')">Hired</button>
                                <button class="filter-btn" onclick="filterApplications('rejected')">Rejected</button>
                            </div>
                        </div>
                    </div>

                    <div class="application-list" id="application-list">
                        <!-- Applications will be loaded here dynamically -->
                    </div>
                </div>
            </div>
        </div>

        <script>
            // Sample application data for different jobs
            const applicationData = {
                'frontend-dev': [
                    {
                        id: 1,
                        name: 'John Smith',
                        email: 'john.smith@email.com',
                        phone: '+1 (555) 123-4567',
                        experience: '5 years',
                        status: 'new',
                        appliedDate: '2 days ago',
                        resume: 'john_smith_resume.pdf',
                        coverLetter: 'Available'
                    },
                    {
                        id: 2,
                        name: 'Sarah Johnson',
                        email: 'sarah.j@email.com',
                        phone: '+1 (555) 234-5678',
                        experience: '3 years',
                        status: 'reviewed',
                        appliedDate: '3 days ago',
                        resume: 'sarah_johnson_resume.pdf',
                        coverLetter: 'Available'
                    },
                    {
                        id: 3,
                        name: 'Mike Chen',
                        email: 'mike.chen@email.com',
                        phone: '+1 (555) 345-6789',
                        experience: '7 years',
                        status: 'interview',
                        appliedDate: '5 days ago',
                        resume: 'mike_chen_resume.pdf',
                        coverLetter: 'Not provided'
                    }
                ],
                'marketing-mgr': [
                    {
                        id: 4,
                        name: 'Emily Davis',
                        email: 'emily.davis@email.com',
                        phone: '+1 (555) 456-7890',
                        experience: '4 years',
                        status: 'new',
                        appliedDate: '1 day ago',
                        resume: 'emily_davis_resume.pdf',
                        coverLetter: 'Available'
                    },
                    {
                        id: 5,
                        name: 'Robert Wilson',
                        email: 'robert.w@email.com',
                        phone: '+1 (555) 567-8901',
                        experience: '6 years',
                        status: 'hired',
                        appliedDate: '1 week ago',
                        resume: 'robert_wilson_resume.pdf',
                        coverLetter: 'Available'
                    }
                ]
            };

            let currentJobId = null;
            let currentApplications = [];

            function selectJob(jobId, jobTitle, applicationCount) {
                // Remove previous selection
                document.querySelectorAll('.job-card').forEach(card => {
                    card.classList.remove('selected');
                });

                // Select current job card
                event.currentTarget.classList.add('selected');

                currentJobId = jobId;
                currentApplications = applicationData[jobId] || [];

                // Show applications section
                document.getElementById('applications-section').classList.add('active');
                document.getElementById('selected-job-title').textContent = `Applications for ${jobTitle} (${applicationCount})`;

                // Load applications
                loadApplications(currentApplications);

                // Scroll to applications section
                document.getElementById('applications-section').scrollIntoView({behavior: 'smooth'});
            }

            function loadApplications(applications) {
                const applicationList = document.getElementById('application-list');

                if (applications.length === 0) {
                    applicationList.innerHTML = `
                        <div class="empty-state">
                            <h3>No Applications Found</h3>
                            <p>No applications have been submitted for this job yet.</p>
                        </div>
                    `;
                    return;
                }

                applicationList.innerHTML = applications.map(app => `
                    <div class="application-item" data-status="${app.status}" data-name="${app.name.toLowerCase()}" data-email="${app.email.toLowerCase()}">
                        <div class="application-header">
                            <div class="applicant-info">
                                <h4>${app.name}</h4>
                                <div class="applicant-details">
                                    <span>? ${app.email}</span>
                                    <span>? ${app.phone}</span>
                                    <span>? ${app.experience} experience</span>
                                    <span>? Applied ${app.appliedDate}</span>
                                </div>
                            </div>
                            <div class="application-actions">
                                <span class="status-badge status-${app.status}">${app.status.charAt(0).toUpperCase() + app.status.slice(1)}</span>
                                <a href="#" class="btn btn-primary" onclick="viewResume(${app.id})">View Resume</a>
                                <button class="btn btn-success" onclick="scheduleInterview(${app.id})">Interview</button>
                                <button class="btn btn-warning" onclick="markReviewed(${app.id})">Mark Reviewed</button>
                                <button class="btn btn-danger" onclick="rejectApplication(${app.id})">Reject</button>
                            </div>
                        </div>
                        <div class="application-summary">
                            <span><strong>Resume:</strong> ${app.resume}</span>
                            <span><strong>Cover Letter:</strong> ${app.coverLetter}</span>
                        </div>
                    </div>
                `).join('');
            }

            function filterApplications(status) {
                // Update active filter button
                document.querySelectorAll('.filter-btn').forEach(btn => {
                    btn.classList.remove('active');
                });
                event.currentTarget.classList.add('active');

                // Filter applications
                const applications = document.querySelectorAll('.application-item');
                applications.forEach(app => {
                    if (status === 'all' || app.dataset.status === status) {
                        app.style.display = 'block';
                    } else {
                        app.style.display = 'none';
                    }
                });
            }

            function searchApplications(searchTerm) {
                const applications = document.querySelectorAll('.application-item');
                const term = searchTerm.toLowerCase();

                applications.forEach(app => {
                    const name = app.dataset.name;
                    const email = app.dataset.email;

                    if (name.includes(term) || email.includes(term)) {
                        app.style.display = 'block';
                    } else {
                        app.style.display = 'none';
                    }
                });
            }

            // Placeholder functions for actions
            function viewResume(id) {
                alert(`Viewing resume for application ID: ${id}`);
            }

            function scheduleInterview(id) {
                alert(`Scheduling interview for application ID: ${id}`);
            }

            function markReviewed(id) {
                alert(`Marking application ID: ${id} as reviewed`);
            }

            function rejectApplication(id) {
                if (confirm('Are you sure you want to reject this application?')) {
                    alert(`Rejecting application ID: ${id}`);
                }
            }
        </script>
    </body>
</html>
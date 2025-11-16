<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Recruiter Dashboard - Applications</title>

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

                <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

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
            /* ===== DASHBOARD STATS ===== */
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
        </style>
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
        <jsp:include page="employerWall.jsp"/>
        




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
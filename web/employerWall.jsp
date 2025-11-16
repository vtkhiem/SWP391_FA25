<%-- 
    Document   : employerWall
    Created on : Oct 30, 2025, 1:01:08 AM
    Author     : vuthienkhiem
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
            <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
</head>
<!-- ===== EMPLOYER WALL START ===== -->
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-6">
            <h3 class="mb-4">Your Wall</h3>
            <c:if test="${empty wallJobs}">
                <div class="alert alert-info">You haven't added any jobs to your wall yet.</div>
            </c:if>
            
            <!-- Job Posts Feed -->
            <c:forEach var="job" items="${wallJobs}">
                <div class="job-post-card mb-4">
                    <div class="card shadow-sm">
                        <!-- Post Header -->
                        <div class="card-header bg-white border-bottom">
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center">
                                    <div class="post-icon me-3">
                                        <i class="ti-briefcase text-primary"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-0 fw-bold">${job.title}</h6>
                                        <small class="text-muted">
                                            <i class="ti-time"></i> Posted ${job.dayCreate}
                                        </small>
                                    </div>
                                </div>
                                <c:if test="${job.pinned}">
                                    <span class="badge bg-warning">
                                        <i class="ti-pin2"></i> Pinned
                                    </span>
                                </c:if>
                            </div>
                        </div>
                        
                        <!-- Post Content -->
                        <div class="card-body">
                            <div class="job-details mb-3">
                                <div class="detail-item mb-2">
                                    <i class="ti-location-pin text-primary me-2"></i>
                                    <span>${job.location}</span>
                                </div>
                                <div class="detail-item mb-2">
                                    <i class="ti-briefcase text-primary me-2"></i>
                                    <span>${job.position}</span>
                                </div>
                                <div class="detail-item mb-2">
                                    <i class="ti-money text-primary me-2"></i>
                                    <span class="fw-semibold">${job.offerMinFormatted} - ${job.offerMaxFormatted}</span>
                                </div>
                            </div>
                            
                            <!-- Status Badge -->
                            <div class="mb-3">
                                <c:choose>
                                    <c:when test="${job.activeOnWall}">
                                        <span class="badge bg-success">
                                            <i class="ti-eye"></i> Active on Wall
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">
                                            <i class="ti-eye-off"></i> Hidden from Wall
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        
                        <!-- Post Actions -->
                        <div class="card-footer bg-white border-top">
                            <div class="d-flex justify-content-between align-items-center">
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
                                </div>
                                
                             
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!-- ===== EMPLOYER WALL END ===== -->

<style>
    .job-post-card {
        animation: fadeInUp 0.4s ease-out;
    }
    
    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    .card {
        border-radius: 12px;
        border: 1px solid #e5e7eb;
        transition: all 0.3s ease;
    }
    
    .card:hover {
        box-shadow: 0 8px 24px rgba(0,0,0,0.12);
        transform: translateY(-2px);
    }
    
    .card-header {
        padding: 1rem 1.25rem;
        border-radius: 12px 12px 0 0 !important;
    }
    
    .post-icon {
        width: 40px;
        height: 40px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.2rem;
        color: white;
    }
    
    .job-details {
        background: #f9fafb;
        padding: 1rem;
        border-radius: 8px;
    }
    
    .detail-item {
        display: flex;
        align-items: center;
        font-size: 0.95rem;
    }
    
    .detail-item i {
        font-size: 1.1rem;
    }
    
    .card-footer {
        padding: 1rem 1.25rem;
        border-radius: 0 0 12px 12px !important;
    }
    
    .btn-sm {
        padding: 0.4rem 0.9rem;
        font-size: 0.875rem;
        border-radius: 6px;
        font-weight: 500;
    }
    
    .badge {
        padding: 0.4rem 0.75rem;
        font-weight: 500;
        border-radius: 6px;
    }
    
    .gap-2 {
        gap: 0.5rem !important;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
        .card-footer .d-flex {
            flex-direction: column;
            gap: 0.75rem;
        }
        
        .card-footer .d-flex > div {
            width: 100%;
        }
        
        .card-footer .btn-sm {
            width: 100%;
        }
    }
</style>
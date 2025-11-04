<%-- 
    Document   : viewEmployerWall
    Created on : Nov 3, 2025, 2:37:15 AM
    Author     : vuthienkhiem
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ===== EMPLOYER WALL START ===== -->
<jsp:include page="header.jsp"/>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-6">
            <div class="wall-header mb-4">
                <h3 class="fw-bold">Tường của nhà tuyển dụng</h3>
                <h5 class="text-primary">${companyName}</h5>
            </div>
            
            <c:if test="${empty wallJobs}">
                <div class="alert alert-info shadow-sm">
                    <i class="ti-info-alt me-2"></i>
                    Nhà tuyển dụng này chưa đăng bài viết nào lên tường.
                </div>
            </c:if>
            
            <!-- Job Posts Feed -->
            <c:forEach var="job" items="${wallJobs}">
                <div class="job-post-card mb-4">
                    <div class="card shadow-sm">
                        <!-- Post Header -->
                        <div class="card-header bg-white border-bottom">
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center flex-grow-1">
                                    <div class="post-icon me-3">
                                        <i class="ti-briefcase"></i>
                                    </div>
                                    <div class="flex-grow-1">
                                        <h5 class="mb-1 fw-bold text-dark">${job.title}</h5>
                                        <small class="text-muted d-flex align-items-center">
                                            <i class="ti-time me-1"></i> 
                                            Đăng ${job.dayCreate}
                                        </small>
                                    </div>
                                </div>
                                <c:if test="${job.pinned}">
                                    <span class="badge bg-warning">
                                        <i class="ti-pin2"></i> Ghim
                                    </span>
                                </c:if>
                            </div>
                        </div>
                        
                        <!-- Post Content -->
                        <div class="card-body p-4">
                            <div class="job-details">
                                <div class="detail-item mb-3">
                                    <div class="detail-icon">
                                        <i class="ti-location-pin"></i>
                                    </div>
                                    <div class="detail-content">
                                        <span class="detail-label">Địa điểm</span>
                                        <span class="detail-value">${job.location}</span>
                                    </div>
                                </div>
                                <div class="detail-item mb-3">
                                    <div class="detail-icon">
                                        <i class="ti-briefcase"></i>
                                    </div>
                                    <div class="detail-content">
                                        <span class="detail-label">Vị trí</span>
                                        <span class="detail-value">${job.position}</span>
                                    </div>
                                </div>
                                <div class="detail-item">
                                    <div class="detail-icon">
                                        <i class="ti-money"></i>
                                    </div>
                                    <div class="detail-content">
                                        <span class="detail-label">Mức lương</span>
                                        <span class="detail-value salary">${job.offerMinFormatted} - ${job.offerMaxFormatted}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Post Actions -->
                        <div class="card-footer bg-light border-top">
                            <div class="d-flex justify-content-center">
                                <a href="job_details?id=${job.jobPostID}" 
                                   class="btn btn-primary btn-view-detail">
                                    <i class="ti-search me-2"></i> Xem chi tiết công việc
                                </a>
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
    .wall-header {
        margin-top: 10%;
        text-align: center;
        padding: 1.5rem;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 16px;
        color: white;
        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
    }
    
    .wall-header h3 {
        margin-bottom: 0.5rem;
        font-size: 1.5rem;
    }
    
    .wall-header h5 {
        margin-bottom: 0;
        color: rgba(255, 255, 255, 0.95);
        font-weight: 600;
    }
    
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
        border-radius: 16px;
        border: 1px solid #e5e7eb;
        transition: all 0.3s ease;
        overflow: hidden;
    }
    
    .card:hover {
        box-shadow: 0 12px 32px rgba(0,0,0,0.15);
        transform: translateY(-4px);
    }
    
    .card-header {
        padding: 1.5rem 1.75rem;
        border-radius: 16px 16px 0 0 !important;
    }
    
    .card-header h5 {
        font-size: 1.25rem;
        color: #1a202c;
    }
    
    .post-icon {
        width: 50px;
        height: 50px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
        color: white;
        flex-shrink: 0;
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }
    
    .card-body {
        padding: 2rem 1.75rem;
    }
    
    .job-details {
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        padding: 1.75rem;
        border-radius: 12px;
        border: 1px solid #dee2e6;
    }
    
    .detail-item {
        display: flex;
        align-items: flex-start;
        gap: 1rem;
        padding: 0.75rem;
        background: white;
        border-radius: 8px;
        transition: all 0.3s ease;
    }
    
    .detail-item:hover {
        transform: translateX(5px);
        box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    }
    
    .detail-icon {
        width: 40px;
        height: 40px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 1.2rem;
        flex-shrink: 0;
    }
    
    .detail-content {
        display: flex;
        flex-direction: column;
        gap: 0.25rem;
        flex-grow: 1;
    }
    
    .detail-label {
        font-size: 0.8rem;
        color: #6b7280;
        font-weight: 500;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    
    .detail-value {
        font-size: 1.05rem;
        color: #1a202c;
        font-weight: 600;
    }
    
    .detail-value.salary {
        color: #10b981;
        font-size: 1.15rem;
        font-weight: 700;
    }
    
    .card-footer {
        padding: 1.5rem 1.75rem;
        border-radius: 0 0 16px 16px !important;
        background: #f8f9fa !important;
    }
    
    .btn-view-detail {
        padding: 0.75rem 2rem;
        font-size: 1rem;
        border-radius: 10px;
        font-weight: 600;
        transition: all 0.3s ease;
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }
    
    .btn-view-detail:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
    }
    
    .badge {
        padding: 0.5rem 0.9rem;
        font-weight: 600;
        border-radius: 8px;
        font-size: 0.85rem;
    }
    
    .alert {
        border-radius: 12px;
        padding: 1.25rem;
        font-size: 1rem;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
        .card-header {
            padding: 1.25rem;
        }
        
        .card-body {
            padding: 1.5rem 1.25rem;
        }
        
        .job-details {
            padding: 1.25rem;
        }
        
        .card-footer {
            padding: 1.25rem;
        }
        
        .btn-view-detail {
            width: 100%;
        }
        
        .post-icon {
            width: 45px;
            height: 45px;
        }
        
        .card-header h5 {
            font-size: 1.1rem;
        }
    }
</style>
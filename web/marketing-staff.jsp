<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="model.Admin"%>

<%
    // Check quyền role
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("MarketingStaff")) {
        response.sendRedirect("access-denied.jsp");
        return;
    }
    Admin admin = (Admin) session.getAttribute("user");

    
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Marketing Staff Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        header {
            background: rgba(255, 255, 255, 0.15);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            padding: 25px 30px;
            margin-bottom: 30px;
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
        }

        header h1 {
            color: white;
            font-size: 28px;
            font-weight: 300;
            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
            color: white;
        }

        .welcome-text {
            font-size: 16px;
            opacity: 0.9;
        }

        .welcome-text b {
            font-weight: 600;
            color: #ffd700;
            text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
        }

        .logout {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 25px;
            border: 1px solid rgba(255, 255, 255, 0.3);
            transition: all 0.3s ease;
            font-weight: 500;
        }

        .logout:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        main {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
            gap: 25px;
        }

        .card {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            border: 1px solid rgba(255, 255, 255, 0.2);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #4facfe 0%, #00f2fe 100%);
        }

        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
        }

        .card h2 {
            color: #2c3e50;
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .card p {
            color: #5a6c7d;
            line-height: 1.6;
            margin-bottom: 25px;
            font-size: 16px;
        }

        .card-link {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 12px 25px;
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .card-link:hover {
            transform: translateX(5px);
            box-shadow: 0 5px 15px rgba(79, 172, 254, 0.4);
        }

        .icon {
            font-size: 32px;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* Responsive design */
        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }

            header {
                flex-direction: column;
                gap: 15px;
                text-align: center;
            }

            header h1 {
                font-size: 24px;
            }

            main {
                grid-template-columns: 1fr;
            }

            .card {
                padding: 20px;
            }

            .user-info {
                flex-direction: column;
                gap: 10px;
            }
        }

        /* Animation */
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

        .card {
            animation: fadeInUp 0.6s ease forwards;
        }

        .card:nth-child(2) {
            animation-delay: 0.2s;
        }
        
        /* Đảm bảo card mới có animation delay */
        .card:nth-child(3) {
            animation-delay: 0.4s; 
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>📊 Marketing Staff Dashboard</h1>
        <div class="user-info">
            <span class="welcome-text">Xin chào, <b>${user.username}</b></span>
            <a class="logout" href="logout">🚪 Đăng xuất</a>
        </div>
    </header>
    
    <main>
        <div class="card">
            <h2>
                <div class="icon">📰</div>
                Đăng tin khuyến mãi
            </h2>
            <p>Tạo mới và quản lý các tin khuyến mãi để thu hút ứng viên và nhà tuyển dụng. Tăng tương tác và hiệu quả marketing cho doanh nghiệp.</p>
            <a href="postPromotion" class="card-link">
                ✨ Đăng tin khuyến mãi
            </a>
        </div>
        
        <div class="card">
            <h2>
                <div class="icon">📋</div>
                Quản lý Khuyến mãi
            </h2>
            <p>Xem, chỉnh sửa, và kiểm soát trạng thái của tất cả các chương trình khuyến mãi đã đăng. Đảm bảo thông tin luôn chính xác và cập nhật.</p>
            <a href="listPromotion" class="card-link">
                🏷️ Xem danh sách khuyến mãi
            </a>
        </div>
        
        <div class="card">
            <h2>
                <div class="icon">📊</div>
                Thống kê Marketing
            </h2>
            <p>Xem báo cáo chi tiết, phân tích dữ liệu và đánh giá hiệu quả của các chiến dịch marketing. Tối ưu hóa chiến lược dựa trên insights.</p>
            <a href="marketingStatistics" class="card-link">
                📈 Xem thống kê
            </a>
        </div>
    </main>
</div>
</body>
</html>
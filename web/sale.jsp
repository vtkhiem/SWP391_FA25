<%-- 
    Document   : sale
    Created on : Sep 23, 2025, 2:41:10 PM
    Author     : Admin
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders - Service Sales</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .header {
            background: linear-gradient(135deg, #2563eb 0%, #1e40af 100%);
            padding: 30px;
            text-align: center;
        }

        .header h2 {
            color: white;
            font-size: 2.5em;
            font-weight: 300;
            letter-spacing: 1px;
            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
        }

        .content {
            padding: 30px;
        }

        .no-orders {
            text-align: center;
            padding: 60px;
            color: #64748b;
            font-size: 1.2em;
        }

        .no-orders::before {
            content: "📦";
            display: block;
            font-size: 4em;
            margin-bottom: 20px;
        }

        .table-container {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead th {
            background: linear-gradient(135deg, #1d4ed8 0%, #1e3a8a 100%);
            color: white;
            padding: 20px 15px;
            text-align: left;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            font-size: 0.9em;
        }

        tbody tr {
            transition: all 0.3s ease;
            border-bottom: 1px solid #e2e8f0;
        }

        tbody tr:hover {
            background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        tbody tr:last-child {
            border-bottom: none;
        }

        td {
            padding: 20px 15px;
            color: #334155;
            font-size: 0.95em;
        }

        .order-id {
            font-weight: 700;
            color: #2563eb;
            font-family: 'Courier New', monospace;
        }

        .employer-name {
            font-weight: 600;
            color: #1d4ed8;
        }

        .service-name {
            color: #3730a3;
            font-weight: 500;
        }

        .price {
            font-weight: 700;
            color: #dc2626;
            font-size: 1.1em;
        }

        .date {
            color: #6b7280;
            font-size: 0.9em;
        }

        .status {
            padding: 8px 16px;
            border-radius: 25px;
            font-weight: 600;
            font-size: 0.85em;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            display: inline-block;
        }

        .status-pending {
            background: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(251, 191, 36, 0.4);
        }

        .status-success {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
        }

        .status-canceled {
            background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
            color: white;
            box-shadow: 0 4px 12px rgba(239, 68, 68, 0.4);
        }

        .stats-bar {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
            flex-wrap: wrap;
        }

        .stat-card {
            flex: 1;
            min-width: 200px;
            background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
            color: white;
            padding: 20px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 8px 25px rgba(59, 130, 246, 0.3);
        }

        .stat-number {
            font-size: 2em;
            font-weight: 700;
            display: block;
        }

        .stat-label {
            font-size: 0.9em;
            opacity: 0.9;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        @media (max-width: 768px) {
            .container {
                margin: 10px;
                border-radius: 15px;
            }
            
            .header {
                padding: 20px;
            }
            
            .header h2 {
                font-size: 2em;
            }
            
            .content {
                padding: 20px;
            }
            
            table {
                font-size: 0.85em;
            }
            
            th, td {
                padding: 12px 8px;
            }
            
            .stats-bar {
                flex-direction: column;
            }
        }

        .fade-in {
            animation: fadeIn 0.8s ease-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <div class="container fade-in">
        <div class="header">
            <h2>📋 Danh sách Order từ Employer</h2>
        </div>
        
        <div class="content">
            <c:if test="${empty orders}">
                <div class="no-orders">
                    Chưa có order nào.<br>
                    <small>Các đơn hàng sẽ xuất hiện tại đây khi có employer đặt mua dịch vụ.</small>
                </div>
            </c:if>
            
            <c:if test="${not empty orders}">
                <div class="stats-bar">
                    <div class="stat-card">
                        <span class="stat-number">${orders.size()}</span>
                        <span class="stat-label">Tổng Orders</span>
                    </div>
                    <div class="stat-card">
                        <span class="stat-number">
                            <c:set var="totalRevenue" value="0" />
                            <c:forEach var="o" items="${orders}">
                                <c:if test="${o.status eq 'success'}">
                                    <c:set var="totalRevenue" value="${totalRevenue + o.price}" />
                                </c:if>
                            </c:forEach>
                            <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </span>
                        <span class="stat-label">Doanh thu</span>
                    </div>
                </div>

                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Mã Order</th>
                                <th>Employer</th>
                                <th>Dịch vụ</th>
                                <th>Giá</th>
                                <th>Ngày mua</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orders}">
                                <tr>
                                    <td class="order-id">#${o.id}</td>
                                    <td class="employer-name">${o.employerName}</td>
                                    <td class="service-name">${o.serviceName}</td>
                                    <td class="price">
                                        <fmt:formatNumber value="${o.price}" type="currency" currencySymbol="₫" />
                                    </td>
                                    <td class="date">
                                        <fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${o.status eq 'pending'}">
                                                <span class="status status-pending">⏳ Đang chờ</span>
                                            </c:when>
                                            <c:when test="${o.status eq 'success'}">
                                                <span class="status status-success">✅ Thành công</span>
                                            </c:when>
                                            <c:when test="${o.status eq 'canceled'}">
                                                <span class="status status-canceled">❌ Đã hủy</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status">${o.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
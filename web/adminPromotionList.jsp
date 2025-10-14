<%-- 
    Document   : adminPromotionList
    Created on : Oct 9, 2025, 4:48:37 PM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý khuyến mãi - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="card shadow p-4 rounded-3">
                <h3 class="text-primary mb-3 text-center">Danh sách khuyến mãi</h3>

                <c:if test="${not empty message}">
                    <div class="alert alert-info text-center">${message}</div>
                </c:if>

                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="table-primary">
                        <tr>
                            <th>ID</th>
                            <th>Mã</th>
                            <th>Giảm (%)</th>
                            <th>Mô tả</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                            <th>Ngày tạo</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${promotionList}">
                            <tr>
                                <td>${p.promotionID}</td>
                                <td class="fw-bold">${p.code}</td>
                                <td>${p.discount}</td>
                                <td class="text-start">${p.description}</td>
                                <td>${p.dateSt}</td>
                                <td>${p.dateEn}</td>
                                <td>${p.dateCr}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.status}">
                                            <span class="badge bg-success">Đã duyệt</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Chờ duyệt</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${!p.status}">
                                        <form action="verifyPromo" method="post" class="d-inline">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">

                                            <button class="btn btn-success btn-sm">Duyệt</button>
                                        </form>

                                        <form action="verifyPromo" method="get" class="d-inline">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">

                                            <button class="btn btn-danger btn-sm">Từ chối</button>
                                        </form>
                                      
                                    </c:if>

                                    <c:if test="${p.status}">
                                        <button class="btn btn-secondary btn-sm" disabled>Đã duyệt</button>
                                        <form action="verifyPromo" method="get" class="d-inline"
                                              onsubmit="return confirm('Bạn có chắc muốn xóa khuyến mãi này không?');">
                                            <input type="hidden" name="promotionId" value="${p.promotionID}">
                                            <button class="btn btn-outline-danger btn-sm">Xóa</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <c:if test="${empty promotionList}">
                    <p class="text-center text-muted">Hiện chưa có khuyến mãi nào.</p>
                </c:if>
            </div>
        </div>

    </body>
</html>

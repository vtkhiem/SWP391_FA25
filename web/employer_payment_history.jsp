<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="model.ServiceEmployerHistory, java.time.format.DateTimeFormatter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Lịch sử giao dich Nhà tuyển dụng - Job Board</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
    </head>

    <body>
        <!-- header_start -->
        <jsp:include page="header.jsp"/>
        <!-- header_end -->

        <!-- bradcam_area  -->
        <div class="bradcam_area bradcam_bg_1">
            <div class="container">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="bradcam_text">
                            <h3>Lịch sử giao dịch</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/ bradcam_area  -->

        <!-- payment-history-area -->
        <div class="payment_history_area">
            <div class="container p-0">
                <div class="row">
                    <div class="col-12">
                        <div class="table-responsive mt-3">
                            <table class="table table-hover table-bordered">
                                <thead class="thead-light">
                                    <tr>
                                        <th style="width:60px">No</th>
                                        <th style="width:250px">Tên dịch vụ</th>
                                        <th style="width:200px">Ngày đăng ký</th>
                                        <th style="width:200px">Ngày hết hạn</th>
                                        <th style="width:180px">Trạng thái thanh toán</th>
                                        <th style="width:180px">Loại hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="payment" items="${payments}" varStatus="st">
                                        <tr>
                                            <td>${st.index + 1}</td>
                                            <td>${payment.serviceName}</td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty payment.registerDate}">
                                                        <fmt:formatDate value="${payment.registerDate}" pattern="dd/MM/yyyy, HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty payment.expirationDate}">
                                                        <fmt:formatDate value="${payment.expirationDate}" pattern="dd/MM/yyyy, HH:mm"/>
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>${payment.paymentStatus}</td>
                                            <td>${payment.actionType}</td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty payments}">
                                        <tr>
                                            <td colspan="6" class="text-center">Không có lịch sử thanh toán nào.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="pagination justify-content-center mb-4">
                            <ul class="pagination">
                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage-1}">
                                        &lt;
                                    </a>
                                </li>

                                <c:forEach var="i" begin="1" end="${noOfPages}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>

                                <li class="page-item ${currentPage == noOfPages ? 'disabled' : ''}">
                                    <a class="page-link" href="?page=${currentPage+1}">
                                        &gt;
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- payment_history_area_end -->

        <!-- footer -->
        <jsp:include page="footer.jsp"/>
        <!-- footer -->
    </body>
</html>
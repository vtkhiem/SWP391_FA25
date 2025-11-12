<%-- 
    Document   : feedback_success_guest
    Created on : Nov 7, 2025, 3:36:43 AM
    Author     : vuthienkhiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <meta charset="UTF-8">
    <title>Kết quả gửi phản hồi</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body class="text-center p-5">
    <h3 class="text-success">${message}</h3>
    <h3 class="text-danger">${error}</h3>
    <a href="index.jsp" class="btn btn-primary mt-3">Quay lại trang dịch vụ</a>
</body>
</html>

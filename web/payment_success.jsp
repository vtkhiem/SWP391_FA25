<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán thành công</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh; /* Sửa thành min-height để nội dung dài vẫn hiển thị tốt */
        }
        .container {
            background-color: #ffffff;
            padding: 2em;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08); /* Làm mờ shadow một chút */
            text-align: center;
            max-width: 500px;
            width: 100%;
        }
        .icon {
            font-size: 5em;
            color: #28a745; /* Màu xanh lá thành công */
        }
        
        /* ✅ Sửa: Style cho h1 (thay vì h2) */
        h1 {
            color: #28a745;
            margin-top: 0.5em;
            margin-bottom: 0.5em;
        }
        
        /* Style cho h3 */
        h3 {
            color: #333;
            margin-top: 1.5em;
            text-align: left;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }

        p {
            color: #555;
            line-height: 1.6;
        }
        
        /* ✅ CSS này bạn đã viết, giờ chỉ cần HTML khớp là đẹp */
        .info {
            text-align: left;
            margin: 1.5em 0;
        }
        .info p {
            display: flex;
            justify-content: space-between;
            margin: 0.8em 0; /* Thêm khoảng cách giữa các dòng */
        }
        .info span {
            font-weight: bold;
            color: #333; /* Cho chữ đậm màu hơn */
        }
        
        /* ✅ CSS này bạn đã viết, giờ chỉ cần HTML khớp là đẹp */
        .back-button {
            display: inline-block;
            margin-top: 1.5em;
            padding: 0.8em 1.5em;
            background-color: #007bff;
            color: #ffffff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            font-weight: bold;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <%
        // 1. Lấy dữ liệu từ session
        String orderId = (String) session.getAttribute("orderId");
        Long amount = (Long) session.getAttribute("amount");
        String transactionNo = (String) session.getAttribute("transactionNo");
        String bankCode = (String) session.getAttribute("bankCode");
        String payDate = (String) session.getAttribute("payDate");

        // 2. Xóa các thuộc tính khỏi session
        session.removeAttribute("orderId");
        session.removeAttribute("amount");
        session.removeAttribute("transactionNo");
        session.removeAttribute("bankCode");
        session.removeAttribute("payDate");

        // 3. Kiểm tra nếu không có data (do refresh trang)
        if (orderId == null) {
            response.sendRedirect("employerServices"); // Trang chủ của bạn
            return; // Dừng xử lý trang JSP
        }
        
        // Định dạng lại ngày tháng (nếu payDate có dạng yyyyMMddHHmmss)
        String formattedPayDate = payDate;
        if (payDate != null && payDate.length() == 14) {
            try {
                formattedPayDate = payDate.substring(6, 8) + "/" + // dd
                                 payDate.substring(4, 6) + "/" + // MM
                                 payDate.substring(0, 4) + " " + // yyyy
                                 payDate.substring(8, 10) + ":" + // HH
                                 payDate.substring(10, 12) + ":" + // mm
                                 payDate.substring(12, 14); // ss
            } catch (Exception e) {
                // Nếu lỗi thì vẫn giữ nguyên
                formattedPayDate = payDate;
            }
        }
    %>

    <div class="container">
        <i class="fas fa-check-circle icon"></i>
    
        <h1>Thanh toán thành công!</h1>
        <p>Cảm ơn bạn đã hoàn tất thanh toán.</p>
        
        <h3>Chi tiết giao dịch:</h3>
        
        <div class="info">
            <p><span>Mã đơn hàng:</span> <%= orderId %></p>
            <p><span>Số tiền:</span> <%= String.format("%,d", amount) %> VND</p>
            <p><span>Mã giao dịch VNPay:</span> <%= transactionNo %></p>
            <p><span>Ngân hàng:</span> <%= bankCode %></p>
            <p><span>Thời gian thanh toán:</span> <%= formattedPayDate %></p>
        </div>
        
        <a href="employerServices" class="back-button">Quay về trang quản lý</a>
    </div>
</body>
</html>
<%-- 
    Document   : requestSupport
    Created on : Nov 7, 2025, 2:50:40 AM
    Author     : vuthienkhiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yêu cầu Hỗ trợ</title>
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        
        <style>
            body {
                background-color: #f8f9fa;
            }
            .feedback-card {
                max-width: 650px;
                margin: 3rem auto;
                background: #fff;
                padding: 2rem;
                border-radius: 15px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            }
            .btn-submit {
                background-color: #0d6efd;
                color: white;
                border-radius: 30px;
                border: none;
                padding: 0.6rem 1.5rem;
            }
            .btn-submit:hover {
                background-color: #095bb5;
            }
        </style>
    </head>
    
    <body>
        


        <div class="container">
            <div class="feedback-card">
                
                <h4 class="text-center text-primary mb-3">
                    ✉️ Gửi Yêu cầu Hỗ trợ
                </h4>
                
                <form action="requestSupport" method="post">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Gmail của bạn</label>
                        <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn..." required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Chủ đề</label>
                        <input type="text" name="subject" class="form-control" placeholder="Nhập tiêu đề yêu cầu..." required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Nội dung</label>
                        <textarea name="content" class="form-control" rows="6" placeholder="Nhập nội dung yêu cầu..." required></textarea>
                    </div>

                    <div class="text-center">
                        <button type="submit" class="btn-submit">Gửi yêu cầu</button>
                    </div>
                </form>
                
            </div>
        </div>

  

    </body>
</html>
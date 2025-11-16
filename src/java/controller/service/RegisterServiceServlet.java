/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.service;

import dal.OrderDAO;
import dal.PromotionDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employer;
import model.Order;
import model.Promotion;
import model.Service;

@WebServlet(name = "RegisterServiceServlet", urlPatterns = {"/registerService"})
public class RegisterServiceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Employer emp = (Employer) request.getSession().getAttribute("user");
            if (emp == null) {
                response.sendRedirect("login-employer.jsp");
                return;
            }

            int employerId = emp.getEmployerId();
            int serviceId = Integer.parseInt(request.getParameter("serviceID"));
            String promoCode = request.getParameter("promoCode");
            
            // --- DEBUG 1: Kiểm tra mã khuyến mãi nhận được ---
            System.out.println("DEBUG (Start): Xử lý đăng ký dịch vụ...");
            System.out.println("DEBUG 1: Promo Code nhận được: [" + promoCode + "]");
            // ----------------------------------------------------

            ServiceDAO serviceDAO = new ServiceDAO();
            PromotionDAO promoDAO = new PromotionDAO();
            OrderDAO orderDAO = new OrderDAO();

            Service service = serviceDAO.getServiceById(serviceId);
            if (service == null) {
                request.setAttribute("error", "Không tìm thấy gói dịch vụ.");
                request.getRequestDispatcher("buy_service.jsp").forward(request, response);
                return;
            }

            BigDecimal finalPrice = service.getPrice();
            Promotion promo = null;
            
            if (promoCode != null && !promoCode.trim().isEmpty()) {
                
                // Khả năng cao lỗi nằm trong dòng này, do DB hoặc mapping
                promo = promoDAO.getPromotionByCode(promoCode.trim());
                
                // --- DEBUG 2: Kiểm tra đối tượng Promotion sau khi truy vấn DB ---
                if (promo == null) {
                    System.out.println("DEBUG 2: Đối tượng Promotion là NULL (Mã không tồn tại hoặc lỗi DB)");
                } else {
                    System.out.println("DEBUG 2: Đối tượng Promotion đã được lấy: Mã = " + promo.getCode() + ", Trạng thái = " + promo.isStatus());
                }
                // -----------------------------------------------------------------
                
                if (promo != null && promo.isStatus()) {
                    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

                    // --- DEBUG 3: Kiểm tra các trường thời gian ---
                    System.out.println("DEBUG 3: Date Start: " + promo.getDateSt());
                    System.out.println("DEBUG 3: Date End: " + promo.getDateEn());
                    
                    if (promo.getDateSt() == null || promo.getDateEn() == null) {
                        // Nếu trường ngày tháng bị null, nó sẽ gây ra NullPointerException 
                        // trong khối này nếu không được kiểm tra.
                        System.out.println("DEBUG 3 ERROR: DateSt hoặc DateEn của Promotion là NULL. KHÔNG ÁP DỤNG KM.");
                    } else if (now.after(promo.getDateSt()) && now.before(promo.getDateEn())) {
                        System.out.println("DEBUG 3: Mã KM còn hiệu lực. Bắt đầu tính giảm giá.");
                        BigDecimal discount = BigDecimal.ONE.subtract(promo.getDiscount());
                        finalPrice = finalPrice.multiply(discount);
                    } else {
                        System.out.println("DEBUG 3: Mã KM đã hết hạn hoặc chưa đến thời gian áp dụng.");
                    }
                    // ---------------------------------------------
                }
            }

            // ✅ Tạo order mới
            Order order = new Order();
            order.setEmployerID(employerId);
            order.setServiceID(serviceId);
            order.setAmount(finalPrice);
            order.setPayMethod("VNPAY");
            order.setStatus("Pending");
            order.setDate(LocalDateTime.now());
            order.setDuration(service.getDuration());
            order.setCode(promoCode);

            int orderId = orderDAO.insertOrder(order);
            if (orderId <= 0) {
                request.setAttribute("error", "Không thể tạo đơn hàng!");
                request.getRequestDispatcher("buy_service.jsp").forward(request, response);
                return;
            }
            
            // ✅ Chuyển sang PaymentServlet
            String orderInfo = "Thanh toan don hang #" + orderId + " - " + service.getServiceName();
            String amount = String.valueOf(finalPrice.intValue());
            
            System.out.println("DEBUG (End): Tạo Order thành công và chuyển hướng đến Payment Servlet.");

            response.sendRedirect(
                request.getContextPath() + "/payment"
                + "?amount=" + amount
                + "&orderInfo=" + URLEncoder.encode(orderInfo, "UTF-8")
                + "&orderId=" + orderId
            );

        } catch (Exception e) {
            // --- DEBUG Catch: In ra Stack Trace đầy đủ ---
            System.err.println("CRITICAL ERROR: Lỗi xảy ra trong RegisterServiceServlet:");
            e.printStackTrace(); 
            // ----------------------------------------------
            response.sendRedirect("error.jsp");
        }
    }



    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
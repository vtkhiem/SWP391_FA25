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
                response.sendRedirect("login.jsp");
                return;
            }

            int employerId = emp.getEmployerId();
            int serviceId = Integer.parseInt(request.getParameter("serviceID"));
            String promoCode = request.getParameter("promoCode");

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
                promo = promoDAO.getPromotionByCode(promoCode.trim());
                if (promo != null && promo.isStatus()) {
                    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                    if (now.after(promo.getDateSt()) && now.before(promo.getDateEn())) {
                        BigDecimal discount = BigDecimal.ONE.subtract(promo.getDiscount());
                        finalPrice = finalPrice.multiply(discount);
                    }
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

            int orderId = orderDAO.insertOrder(order);
            if (orderId <= 0) {
                request.setAttribute("error", "Không thể tạo đơn hàng!");
                request.getRequestDispatcher("buy_service.jsp").forward(request, response);
                return;
            }

            // ✅ Chuyển sang PaymentServlet
            String orderInfo = "Thanh toan don hang #" + orderId + " - " + service.getServiceName();
            String amount = String.valueOf(finalPrice.intValue());

            response.sendRedirect(
                request.getContextPath() + "/payment"
                + "?amount=" + amount
                + "&orderInfo=" + URLEncoder.encode(orderInfo, "UTF-8")
                + "&orderId=" + orderId
            );

        } catch (Exception e) {
            e.printStackTrace();
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

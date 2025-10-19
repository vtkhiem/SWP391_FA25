/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.service;

import dal.PromotionDAO;
import dal.ServiceDAO;
import dal.ServiceEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import model.Employer;
import model.Promotion;
import model.Service;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "RegisterServiceServlet", urlPatterns = {"/registerService"})
public class RegisterServiceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServiceServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // ✅ Lấy employer đang đăng nhập
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
            ServiceEmployerDAO seDAO = new ServiceEmployerDAO();

            // ✅ Lấy thông tin dịch vụ
            Service service = serviceDAO.getServiceById(serviceId);
            if (service == null) {
                request.setAttribute("error", "Không tìm thấy gói dịch vụ.");
                request.getRequestDispatcher("buy_service.jsp").forward(request, response);
                return;
            }

            BigDecimal finalPrice = service.getPrice();  
            Promotion promo = promoDAO.getPromotionByCode(promoCode.trim());

            if (promo != null && promo.isStatus()) {
                Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                if (now.after(promo.getDateSt()) && now.before(promo.getDateEn())) {
                    BigDecimal discountMultiplier = BigDecimal.ONE.subtract(promo.getDiscount());
                    finalPrice = finalPrice.multiply(discountMultiplier);
                    request.setAttribute("appliedPromo", promo);
                } else {
                    request.setAttribute("promoInvalid", "Mã khuyến mãi đã hết hạn hoặc chưa bắt đầu!");
                }
            } else {
                request.setAttribute("promoInvalid", "Mã khuyến mãi không hợp lệ!");
            }

            // ✅ Ghi vào bảng ServiceEmployer
            Timestamp registerDate = Timestamp.valueOf(LocalDateTime.now());
            Timestamp expirationDate = Timestamp.valueOf(LocalDateTime.now().plus(service.getDuration(), ChronoUnit.DAYS));

            boolean inserted = seDAO.registerService(
                    employerId,
                    serviceId,
                    registerDate,
                    expirationDate,
                    "Pending", // trạng thái thanh toán
                    0 // JobPostCount ban đầu = 0
            );

            if (inserted) {
                request.setAttribute("service", service);
                request.setAttribute("finalPrice", finalPrice);
                request.getRequestDispatcher("service_success.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Đăng ký dịch vụ thất bại!");
                request.getRequestDispatcher("buy_service.jsp").forward(request, response);
            }

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

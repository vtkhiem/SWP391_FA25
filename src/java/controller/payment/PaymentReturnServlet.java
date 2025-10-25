/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import dal.OrderDAO;
import dal.ServiceDAO;
import dal.ServiceEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import model.Order;
import model.Service;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "PaymentReturnServlet", urlPatterns = {"/payment-return"})
public class PaymentReturnServlet extends HttpServlet {

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
            out.println("<title>Servlet PaymentReturnServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentReturnServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // ✅ 1. Lấy toàn bộ tham số từ VNPay
            Map<String, String> fields = new HashMap<>();
            for (String key : request.getParameterMap().keySet()) {
                String value = request.getParameter(key);
                if (value != null && !value.isEmpty()) {
                    fields.put(key, value);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");

            // ✅ 2. Kiểm tra chữ ký
            String signValue = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, VNPayConfig.hashAllFields(fields));
            if (!signValue.equals(vnp_SecureHash)) {
                response.getWriter().println("<html><body><h3>Lỗi: Chữ ký không hợp lệ!</h3></body></html>");
                return;
            }

            // ✅ 3. Lấy orderId từ tham số đã gửi ban đầu
           String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
           String orderIdStr = vnp_OrderInfo.replaceAll("\\D+", "");

            if (orderIdStr == null) {
                response.getWriter().println("<html><body><h3>Lỗi: Không tìm thấy orderId!</h3></body></html>");
                return;
            }
            int orderId = Integer.parseInt(orderIdStr);

            OrderDAO orderDAO = new OrderDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            ServiceEmployerDAO serviceEmpDAO = new ServiceEmployerDAO();

            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                response.getWriter().println("<html><body><h3>Lỗi: Không tìm thấy đơn hàng!</h3></body></html>");
                return;
            }
            int employerId = order.getEmployerID();
            int serviceId = order.getServiceID();

            // ✅ 4. Nếu thanh toán thành công
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                // Cập nhật trạng thái đơn hàng
                orderDAO.updateOrderStatus(orderId, "Paid");

                // Lấy service
                Service service = serviceDAO.getServiceById(order.getServiceID());
                if (service == null) {
                    response.getWriter().println("<html><body><h3>Lỗi: Dịch vụ không tồn tại!</h3></body></html>");
                    return;
                }

                // ✅ 5. Thêm vào bảng ServiceEmployer
                Timestamp registerDate = Timestamp.valueOf(LocalDateTime.now());
                Timestamp expirationDate = Timestamp.valueOf(LocalDateTime.now().plus(service.getDuration(), ChronoUnit.DAYS));
                String paymentStatus = "Paid";
                int historyServiceId = serviceEmpDAO.getServiceIdByEmployerId(employerId);
                String actionType;
                if (historyServiceId == -1) {
                    actionType = "REGISTER";
                } else if (historyServiceId == serviceId) {
                    actionType = "RENEW";
                } else {
                    actionType = "CHANGE";
                }

                boolean inserted = serviceEmpDAO.registerService(employerId, serviceId, registerDate, expirationDate, paymentStatus, actionType);
if(inserted){
     
              response.sendRedirect("payment_success.jsp?" + request.getQueryString());

}else{
    //database 
}
             
            } else {
                // ❌ Giao dịch thất bại
                orderDAO.updateOrderStatus(orderId, "Failed");
                response.sendRedirect("payment_failure.jsp?orderId=" + orderId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<html><body><h3>Lỗi SQL: " + e.getMessage() + "</h3></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<html><body><h3>Lỗi hệ thống: " + e.getMessage() + "</h3></body></html>");
        }

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
        processRequest(request, response);
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

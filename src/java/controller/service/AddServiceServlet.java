/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.service;

import dal.ServiceDAO;
import dal.ServicePromotionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import model.Service;
import java.sql.SQLException;
import model.ServicePromotion;

/**
 *
 * @author Admin
 */
@WebServlet(name="AddServiceServlet", urlPatterns={"/addService"})
public class AddServiceServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet AddServiceServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddServiceServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
           request.setCharacterEncoding("UTF-8"); // ✅ Đảm bảo nhận tiếng Việt đúng
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 🔹 Lấy dữ liệu từ form
            String name = request.getParameter("serviceName");
            String priceStr = request.getParameter("price");
            String durationStr = request.getParameter("duration");
            String description = request.getParameter("description");
            String[] promoIds = request.getParameterValues("promotionID");
            boolean isVisible = request.getParameter("isVisible") != null;
   ServiceDAO serviceDAO = new ServiceDAO();
            // 🔹 Kiểm tra dữ liệu đầu vào
            if (name == null || name.trim().isEmpty()
                    || priceStr == null || durationStr == null ) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin dịch vụ!");
                request.getRequestDispatcher("loadAddService").forward(request, response);
                return;
            }else if(serviceDAO.isServiceNameExists(name)){
                                request.setAttribute("error", "ServiceName đã tồn tại");
                request.getRequestDispatcher("loadAddService").forward(request, response);
                return;
            }

            // 🔹 Chuyển đổi kiểu dữ liệu
            BigDecimal price = new BigDecimal(priceStr);
            int duration = Integer.parseInt(durationStr);
         

            // 🔹 Tạo đối tượng service
            Service service = new Service();
            service.setServiceName(name.trim());
            service.setPrice(price);
            service.setDescription(description != null ? description.trim() : "");
            service.setDuration(duration);
            service.setIsVisible(isVisible);

         
            ServicePromotionDAO spDAO = new ServicePromotionDAO();

            // 🔹 Thêm service và lấy ID tự sinh
            int newServiceId = serviceDAO.addServiceAndReturnId(service);

            if (newServiceId > 0) {
                // 🔹 Gắn service mới vào khuyến mãi (nếu có)
                for (String promoId : promoIds) {
                        if (promoId != null && !promoId.isEmpty()) {
                            int pid = Integer.parseInt(promoId);
                            ServicePromotion sp = new ServicePromotion(newServiceId, pid);
                            spDAO.addServicePromotion(sp);
                        }
                    }

                request.setAttribute("message", "✅ Thêm dịch vụ thành công!");
            } else {
                request.setAttribute("error", "❌ Thêm dịch vụ thất bại!");
            }

            // ✅ Chuyển hướng về danh sách
            response.sendRedirect("listService");

        } catch (NumberFormatException e) {
            throw new ServletException("Dữ liệu không hợp lệ! Vui lòng kiểm tra giá, thời lượng hoặc mã khuyến mãi.", e);
        } catch (SQLException e) {
            throw new ServletException("Lỗi cơ sở dữ liệu khi thêm dịch vụ.", e);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

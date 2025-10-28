/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.promotion;

import dal.PromotionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import model.Promotion;

/**
 *
 * @author Admin
 */
@WebServlet(name="AddPromotionServlet", urlPatterns={"/addPromotion"})
public class AddPromotionServlet extends HttpServlet {
   
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
            out.println("<title>Servlet AddPromotionServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddPromotionServlet at " + request.getContextPath () + "</h1>");
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
       try {
            request.setCharacterEncoding("UTF-8");

            String code = request.getParameter("code");
            BigDecimal discount = new BigDecimal(request.getParameter("discount"));
            String description = request.getParameter("description");

            Timestamp dateSt = Timestamp.valueOf(request.getParameter("dateSt").replace("T", " ") + ":00");
            Timestamp dateEn = Timestamp.valueOf(request.getParameter("dateEn").replace("T", " ") + ":00");

             Timestamp now = new Timestamp(System.currentTimeMillis());
        if (dateSt.before(now) || dateEn.before(now)) {
            request.setAttribute("message", "❌ Ngày bắt đầu và kết thúc không được ở quá khứ!");
            request.getRequestDispatcher("addPromotion.jsp").forward(request, response);
            return;
        }

        if (dateEn.before(dateSt)) {
            request.setAttribute("message", "❌ Ngày kết thúc phải sau ngày bắt đầu!");
            request.getRequestDispatcher("addPromotion.jsp").forward(request, response);
            return;
        }
            Promotion p = new Promotion();
            p.setCode(code);
            p.setDiscount(discount);
            p.setDateSt(dateSt);
            p.setDateEn(dateEn);
            p.setDescription(description);
            p.setStatus(false); 
            
            PromotionDAO dao = new PromotionDAO();
            boolean success = dao.addPromotion(p);

            if (success) {
                request.setAttribute("message", "✅ Thêm khuyến mãi thành công! Đang chờ Admin xác nhận.");
            } else {
                request.setAttribute("message", "❌ Thêm khuyến mãi thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Lỗi khi thêm khuyến mãi: " + e.getMessage());
        }

        request.getRequestDispatcher("addPromotion.jsp").forward(request, response);
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

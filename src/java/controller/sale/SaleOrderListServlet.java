/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.sale;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import model.OrderView;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="SaleOrderListServlet", urlPatterns={"/sale"})
public class SaleOrderListServlet extends HttpServlet {
   
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
            out.println("<title>Servlet SaleOrderListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleOrderListServlet at " + request.getContextPath () + "</h1>");
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
      private LocalDate parseDate(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return null;
            return LocalDate.parse(s); // định dạng yyyy-MM-dd
        } catch (Exception e) {
            return null;
        }
    }

    private int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException {
       String role = (String) req.getSession().getAttribute("role");
        if (role == null || !role.equalsIgnoreCase("Sale")) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }

        int page     = parseIntOrDefault(req.getParameter("page"), 1);
        int pageSize = parseIntOrDefault(req.getParameter("size"), 10);

        OrderDAO dao = new OrderDAO();

        int totalRows  = dao.countAll();
        if (pageSize <= 0) pageSize = 10;
        int totalPages = (int) Math.ceil(totalRows / (double) pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        List<OrderView> orders = dao.findPage(page, pageSize);
        BigDecimal revenue = dao.totalRevenue(); 
        req.setAttribute("orders", orders);
        req.setAttribute("revenue", revenue);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalRows", totalRows);
        req.getRequestDispatcher("/sale.jsp").forward(req, resp);
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
        processRequest(request, response);
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

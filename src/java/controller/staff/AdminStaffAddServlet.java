/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;

import dal.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="AdminStaffAddServlet", urlPatterns={"/admin/add-staff"})
public class AdminStaffAddServlet extends HttpServlet {
   
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
            out.println("<title>Servlet AdminStaffAddServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminStaffAddServlet at " + request.getContextPath () + "</h1>");
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
         request.getRequestDispatcher("/admin/staff-add.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String roleStr  = req.getParameter("roleId");

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || roleStr == null || roleStr.trim().isEmpty()) {
            req.setAttribute("error", "");
            req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
            return;
        }

        int roleId;
        try {
            roleId = Integer.parseInt(roleStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Vai trò không hợp lệ.");
            req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
            return;
        }


        if (roleId != 2 && roleId != 3) {
            req.setAttribute("error", "Chỉ được chọn Marketing Staff (2) hoặc Sale (3).");
            req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
            return;
        }

        AdminDAO dao = new AdminDAO();
    try {
        if (dao.usernameExists(username.trim())) {
            req.setAttribute("error", "Username đã tồn tại.");
            req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
            return;
        }

        boolean ok = dao.createAdminWithRole(username.trim(), password, Integer.parseInt(roleStr));
        if (ok) {
            req.getSession().setAttribute("message", "Thêm nhân sự thành công.");
            resp.sendRedirect(req.getContextPath() + "/admin/staffs");
            return;
        } else {
            req.setAttribute("error", "Không thể thêm nhân sự. Vui lòng thử lại.");
            req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
        }
    } catch (Exception e) {
        e.printStackTrace();
        req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        req.getRequestDispatcher("/admin/staff-add.jsp").forward(req, resp);
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

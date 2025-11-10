/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.auth;

import dal.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Admin;
import model.Role;
import tool.AdminRoleDefine;

/**
 *
 * @author Admin
 */
@WebServlet(name="LoginAdminServlet", urlPatterns={"/login-admin"})
public class LoginAdminServlet extends HttpServlet {
   
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
            out.println("<title>Servlet LoginAdminServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginAdminServlet at " + request.getContextPath () + "</h1>");
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
      String status = null;
    String inputValue = request.getParameter("username");
    String password = request.getParameter("password");

    try {
        AdminDAO adminDAO = new AdminDAO();
        HttpSession session = request.getSession();

        if (adminDAO.isAdmin(inputValue)) {
            Admin admin = adminDAO.loginAccountAdmin(inputValue, password);
            if (admin != null) {
                List<Role> roles = adminDAO.getRolesByAdminId(admin.getAdminId());
                session.setAttribute("roles", roles);
                session.setAttribute("username", inputValue);
                session.setAttribute("user", admin);

                for (Role role : roles) {
                    switch (role.getRoleId()) {
                        case AdminRoleDefine.ADMIN:
                            session.setAttribute("role", "Admin");
                            response.sendRedirect("admin_home.jsp");
                            return;
                        case AdminRoleDefine.SALE:
                            session.setAttribute("role", "Sale");
                            response.sendRedirect("logSale");
                            return;
                        case AdminRoleDefine.MARKETING_STAFF:
                            session.setAttribute("role", "MarketingStaff");
                            response.sendRedirect("logStaff");
                            return;
                    }
                }
                response.sendRedirect("access-denied.jsp");
                return;
            } else {
                status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
            }
        } else {
            status = "Bạn không có quyền đăng nhập hệ thống Admin";
        }
    } catch (Exception e) {
        e.printStackTrace();
        status = "Đã xảy ra lỗi, vui lòng thử lại!";
    }

    request.setAttribute("status", status);
    request.getRequestDispatcher("login-admin.jsp").forward(request, response);
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

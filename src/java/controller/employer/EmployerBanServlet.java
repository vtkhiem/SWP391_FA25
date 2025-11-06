/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.employer;

import dal.EmailBannedDAO;
import dal.EmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employer;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "EmployerBanServlet", urlPatterns = {"/employerBan"})
public class EmployerBanServlet extends HttpServlet {

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
            out.println("<title>Servlet EmployerBanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerBanServlet at " + request.getContextPath() + "</h1>");
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
          HttpSession session = request.getSession();
      EmailBannedDAO ebdao = new EmailBannedDAO();
   EmailService es = new EmailService();       
        String banReason = request.getParameter("banReason");
        int id = Integer.parseInt(request.getParameter("id"));
        EmployerDAO candao = new EmployerDAO();
       String email = candao.getEmailByID(id);
        if (email== null ||email.isEmpty() || banReason == null || banReason.isEmpty()) {
            session.setAttribute("error", "Lỗi: Email và Lý do không được để trống.");
            response.sendRedirect("admin/employers");
            return;
        }
      
    boolean success = ebdao.addBannedEmail(email,"Employer",banReason);
    if(success){
         session.setAttribute("message", "Hạn chế tài khoản có "+email+" thành công");
          es.sendWarningToUser(email, banReason, "Employer");
    }else{
         session.setAttribute("error", "Tài khoản "+email+" vai trò "+ "Employer "+"đã bị hạn chế từ trước");
    }
      response.sendRedirect("admin/employers");
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

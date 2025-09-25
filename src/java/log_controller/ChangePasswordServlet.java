/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package log_controller;

import dal.RegisterCandidateDAO;
import dal.RegisterEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.EncodePassword;
import tool.ValidationRegister;
/**
 *
 * @author Admin
 */
@WebServlet(name="ChangePasswordServlet", urlPatterns={"/changePassword"})
public class ChangePasswordServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ChangePasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordServlet at " + request.getContextPath () + "</h1>");
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
           request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        String email = request.getParameter("email");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");
           ValidationRegister validation = new ValidationRegister();
        if(!validation.checkLength(newPassword)||!validation.checkChar(newPassword)){
               response.sendRedirect("profile?error=matkhauyeucautoithieu8kituvakitudacbiet");
               return;
        }

        // Server-side validation
        if (oldPassword == null || newPassword == null || confirmNewPassword == null ||
            oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmNewPassword.trim().isEmpty()) {
            response.sendRedirect("profile?error=thongtinmatkhaukhongduocdetrong");
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            response.sendRedirect("profile?error=matkhaumoikhongkhop");
            return;
        }

        try {
            boolean success = false;
            if ("Candidate".equals(role)) {
                RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
                // Verify old password
                if (candidateDAO.verifyPassword(email, oldPassword)) {
                    // Hash new password
                    String hashedPassword = EncodePassword.encodePasswordbyHash(newPassword);
                    success = candidateDAO.changePassword(email, hashedPassword);
                } else {
                    response.sendRedirect("profile?error=matkhaucukhongdung");
                    return;
                }
            } else if ("Employer".equals(role)) {
                RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
                if (employerDAO.verifyPassword(email, oldPassword)) {
                    String hashedPassword = EncodePassword.encodePasswordbyHash(newPassword);
                    success = employerDAO.changePassword(email, hashedPassword);
                } else {
                    response.sendRedirect("profile?error=saimatkhaucu");
                    return;
                }
            } else {
                response.sendRedirect("profile?error=vaitrokhonghotrodoimatkhau");
                return;
            }

            if (success) {
                // Invalidate session để force re-login
                session.invalidate();
                response.sendRedirect("login.jsp?success=doimatkhauthanhcong");
            } else {
                response.sendRedirect("profile?error=loikhicapnhat");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("profile?error=loihethong:" + e.getMessage());
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

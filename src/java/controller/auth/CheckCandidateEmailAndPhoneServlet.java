/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.auth;

import dal.RegisterCandidateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name="CheckCandidateEmailAndPhoneServlet", urlPatterns={"/checkInput"})
public class CheckCandidateEmailAndPhoneServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CheckCandidateEmailAndPhoneServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckCandidateEmailAndPhoneServlet at " + request.getContextPath () + "</h1>");
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
    
         response.setContentType("text/plain;charset=UTF-8");
         String type = request.getParameter("type"); 
        String value = request.getParameter("value");

        RegisterCandidateDAO dao = new RegisterCandidateDAO();

        if (value == null || value.trim().isEmpty()) {
            response.getWriter().write("");
            return;
        }

        if ("email".equalsIgnoreCase(type)) {
            if (dao.isEmailCandidateExist(value)) {
                response.getWriter().write("❌ Email này đã được sử dụng.");
            } else {
                response.getWriter().write("✅ Email hợp lệ, có thể đăng ký.");
            }
        } else if ("phone".equalsIgnoreCase(type)) {
            if (dao.isPhoneCandidateExist(value)) {
                response.getWriter().write("❌ Số điện thoại này đã được sử dụng.");
            } else {
                response.getWriter().write("✅ Số điện thoại hợp lệ, có thể đăng ký.");
            }
        } else {
            response.getWriter().write("Tham số không hợp lệ!");
        }

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

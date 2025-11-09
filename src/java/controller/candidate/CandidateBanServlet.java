/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.candidate;

import dal.CandidateDAO;
import dal.EmailBannedDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Candidate;
import model.EmailBanned;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "CandidateBanServlet", urlPatterns = {"/candidateBan"})
public class CandidateBanServlet extends HttpServlet {

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
            out.println("<title>Servlet CandidateBanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CandidateBanServlet at " + request.getContextPath() + "</h1>");
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
        CandidateDAO candao = new CandidateDAO();
       Candidate can = candao.getCandidateById(id);
        if (can.getEmail() == null || can.getEmail().isEmpty() || banReason == null || banReason.isEmpty()) {
            session.setAttribute("error", "Lỗi: Email và Lý do không được để trống.");
            response.sendRedirect("admin/candidates");
            return;
        }
      
    boolean success = ebdao.addBannedEmail(can.getEmail(),"Candidate",banReason);
    if(success){
         session.setAttribute("message", "Hạn chế tài khoản có "+can.getEmail()+" thành công");
         es.sendWarningToUser(can.getEmail(), banReason, "Candidate");
    }else{
         session.setAttribute("error", "Tài khoản "+can.getEmail()+" vai trò "+ "Candidate "+"đã bị hạn chế từ trước");
    }
      response.sendRedirect("admin/candidates");
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
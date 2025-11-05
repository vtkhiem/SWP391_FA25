/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.feedback;

import dal.CandidateDAO;
import dal.EmployerDAO;
import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.Feedback;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "RespondFeedbackServlet", urlPatterns = {"/respondFeedback"})
public class RespondFeedbackServlet extends HttpServlet {

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
            out.println("<title>Servlet RespondFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RespondFeedbackServlet at " + request.getContextPath() + "</h1>");
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
        try {
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            int feedbackID = Integer.parseInt(request.getParameter("feedbackID"));
            String adminResponse = request.getParameter("adminResponse");
            String newStatus = request.getParameter("newStatus");
            String role = request.getParameter("role");
            int id=feedbackDAO.getSenderId(feedbackID);
            EmailService email = new EmailService();
            Feedback feedback = feedbackDAO.getFeedbackById(feedbackID);
            if(role.equalsIgnoreCase("employer")){
                EmployerDAO dao = new EmployerDAO();
                email.sendEmailToUser(dao.getEmailByID(id), adminResponse, feedback.getSubject());
            }else if(role.equalsIgnoreCase("candidate")){
                CandidateDAO dao = new CandidateDAO();
                email.sendEmailToUser(dao.getCandidateById(id).getEmail(), adminResponse, feedback.getSubject());
            }
            
            
         
            try {
                feedbackDAO.respondToFeedback(feedbackID, adminResponse, newStatus);
                response.sendRedirect("adminFeedbackList"); // reload list
            } catch (SQLException e) {
                throw new ServletException("Lỗi khi phản hồi feedback", e);
            }
        } catch (SQLException ex) {
            System.getLogger(RespondFeedbackServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
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

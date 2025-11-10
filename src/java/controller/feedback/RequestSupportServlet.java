/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.feedback;

import dal.CandidateDAO;
import dal.EmailBannedDAO;
import dal.EmployerDAO;
import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Feedback;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "RequestSupportServlet", urlPatterns = {"/requestSupport"})
public class RequestSupportServlet extends HttpServlet {

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
            out.println("<title>Servlet RequestSupportServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestSupportServlet at " + request.getContextPath() + "</h1>");
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
       
    String email = request.getParameter("email");
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");
    
    EmailBannedDAO ebdao = new EmailBannedDAO();
    CandidateDAO candidateDAO = new CandidateDAO();
    EmployerDAO employerDAO = new EmployerDAO();
    FeedbackDAO fdao = new FeedbackDAO();
    
    try {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        boolean isCandidate = candidateDAO.getIdByEmail(email) != -1;
        boolean isEmployer = employerDAO.getIdByEmail(email) != -1;
        
        
        Integer candidateID = null;
        Integer employerID = null;
        
        if (isCandidate) {
            candidateID = candidateDAO.getIdByEmail(email);
        }
        
        if (isEmployer) {
            employerID = employerDAO.getIdByEmail(email);
        }
        
        // Tạo feedback với đúng ID
        Feedback feedback = new Feedback(employerID, candidateID, subject, content, null, null);
        
        int feedbackId;
        if (employerID != null && candidateID != null) {
            // Cả 2 role
            feedbackId = fdao.insertFeedbackAndReturnIdGuest(feedback);
        } else if (employerID != null) {
            // Chỉ Employer
            feedbackId = fdao.insertFeedbackAndReturnId(feedback);
        } else if (candidateID != null) {
            // Chỉ Candidate
            feedbackId = fdao.insertFeedbackAndReturnIdCan(feedback);
        } else {
            // Guest (không có tài khoản)
            feedbackId = fdao.insertFeedbackAndReturnIdGuest(feedback);
        }
        
        // Thêm type feedback (4 = support request)
        if (feedbackId != -1) {
            fdao.insertFeedbackType(feedbackId, 4);
            EmailService emailService = new EmailService();
            emailService.sendFeedbackToAdmin("vuthienkhiem2005@gmail.com", email, subject, content);
            request.setAttribute("message", "Gửi phản hồi thành công! Cảm ơn bạn đã góp ý.");
        } else {
            request.setAttribute("error", "Không thể gửi phản hồi. Vui lòng thử lại.");
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
    }
    
    request.getRequestDispatcher("feedback_success_guest.jsp").forward(request, response);
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

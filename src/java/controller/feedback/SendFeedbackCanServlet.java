/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.feedback;

import dal.FeedbackDAO;
import dal.TypeFeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import model.Candidate;
import model.Feedback;
import model.TypeFeedback;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "SendFeedbackCanServlet", urlPatterns = {"/sendFeedbackCan"})
public class SendFeedbackCanServlet extends HttpServlet {

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
            out.println("<title>Servlet SendFeedbackCanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendFeedbackCanServlet at " + request.getContextPath() + "</h1>");
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
         request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
       
            Candidate can = (Candidate) request.getSession().getAttribute("user");
            if (can == null) {
                response.sendRedirect("login.jsp");
                return;
            }

       String[] typeFeedbackIDs = request.getParameterValues("typeFeedbackIDs");
 Integer canID = can.getCandidateId();
 String email = can.getEmail();
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
   

        Feedback feedback = new Feedback(null, canID, subject, content, null, null,email);

        try {
            FeedbackDAO dao = new FeedbackDAO();
             int feedbackID = dao.insertFeedbackAndReturnIdCan(feedback);
           dao.insertFeedbackTypes(feedbackID, Arrays.asList(typeFeedbackIDs));
          
          
                   EmailService tool = new EmailService();
                tool.sendFeedbackToAdmin("vuthienkhiem2005@gmail.com", can.getEmail(), subject, content);
                request.setAttribute("message", "Gửi phản hồi thành công! Cảm ơn bạn đã góp ý.");
            
                
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
        }

        request.getRequestDispatcher("feedback_success_can.jsp").forward(request, response);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.feedback;

import dal.FeedbackDAO;
import dal.PromotionDAO;
import dal.ServiceDAO;
import dal.TypeFeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import model.Employer;
import model.Feedback;
import model.Promotion;
import model.Service;
import model.TypeFeedback;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "SendFeedBackServlet", urlPatterns = {"/sendFeedback"})
public class SendFeedBackServlet extends HttpServlet {

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
            out.println("<title>Servlet SendFeedBackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendFeedBackServlet at " + request.getContextPath() + "</h1>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Employer emp = (Employer) session.getAttribute("user");
        if (emp == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Integer employerID = emp.getEmployerId();
       
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");
        String[] typeFeedbackIDs = request.getParameterValues("typeFeedbackIDs"); // nhiều checkbox
        String serviceID_raw = request.getParameter("serviceID");
        String promotionID_raw = request.getParameter("promotionID");
   
Integer serviceID = null;
        if (serviceID_raw != null && !serviceID_raw.isEmpty()) {
            serviceID = Integer.parseInt(serviceID_raw);
       }


         Integer promotionID = null;
       if (promotionID_raw != null && !promotionID_raw.isEmpty()) {
     promotionID = Integer.parseInt(promotionID_raw);
        
     }
        if (typeFeedbackIDs == null || typeFeedbackIDs.length == 0) {
            request.setAttribute("error", "Vui lòng chọn ít nhất một loại phản hồi.");
            doGet(request, response);
            return;
        }

        try {
            FeedbackDAO dao = new FeedbackDAO();

      
            Feedback feedback = new Feedback(employerID, null, subject, content, serviceID,promotionID);
           
            if(promotionID!=null && serviceID != null){
                 PromotionDAO pdao = new PromotionDAO();
                   String code = pdao.getCodeById(promotionID);
                     ServiceDAO sdao = new ServiceDAO();
            String serviceName = sdao.getServiceNameById(serviceID);
               int feedbackID = dao.insertFeedbackAndReturnId(feedback);

        
            dao.insertFeedbackTypes(feedbackID, Arrays.asList(typeFeedbackIDs));

            EmailService tool = new EmailService();
            tool.sendFeedbackToAdminEmp("vuthienkhiem2005@gmail.com", emp.getEmail(), subject, content,code,serviceName);

     
            request.setAttribute("message", "Gửi phản hồi thành công! Cảm ơn bạn đã góp ý.");
            request.getRequestDispatcher("feedback_success.jsp").forward(request, response);
            }
      
            else if(promotionID == null && serviceID==null){
               int feedbackID = dao.insertFeedbackAndReturnId(feedback);

        
            dao.insertFeedbackTypes(feedbackID, Arrays.asList(typeFeedbackIDs));

            EmailService tool = new EmailService();
            tool.sendFeedbackToAdmin("vuthienkhiem2005@gmail.com", emp.getEmail(), subject, content);

     
            request.setAttribute("message", "Gửi phản hồi thành công! Cảm ơn bạn đã góp ý.");
            request.getRequestDispatcher("feedback_success.jsp").forward(request, response);
           }else{
                 PromotionDAO pdao = new PromotionDAO();
                  ServiceDAO sdao = new ServiceDAO();
                  String serviceName = "";
                   String code = "";
                 if(promotionID!=null){
                
               
                        code = pdao.getCodeById(promotionID);
                  
                 }
                 if(serviceID!=null){
               
             
                          serviceName = sdao.getServiceNameById(serviceID);
                 }
       
               int feedbackID = dao.insertFeedbackAndReturnId(feedback);

        
            dao.insertFeedbackTypes(feedbackID, Arrays.asList(typeFeedbackIDs));

            EmailService tool = new EmailService();
            tool.sendFeedbackToAdminEmp("vuthienkhiem2005@gmail.com", emp.getEmail(), subject, content,code,serviceName);

     
            request.setAttribute("message", "Gửi phản hồi thành công! Cảm ơn bạn đã góp ý.");
            request.getRequestDispatcher("feedback_success.jsp").forward(request, response);
            }
      
            

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("feedback_form.jsp").forward(request, response);
        }

        request.getRequestDispatcher("feedback_success.jsp").forward(request, response);
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

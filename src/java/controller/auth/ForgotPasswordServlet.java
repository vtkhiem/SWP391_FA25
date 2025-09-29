/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.auth;


import dal.PasswordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.PasswordResetToken;
import tool.ResetService;

/**
 *
 * @author Admin
 */
@WebServlet(name="ForgotPasswordServlet", urlPatterns={"/forgotPassword"})
public class ForgotPasswordServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ForgotPasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath () + "</h1>");
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
           request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String role = request.getParameter("role"); // có thể null nếu mới nhập email
        PasswordDAO passwordDAO = new PasswordDAO();
        ResetService resetService = new ResetService();

        try {
            // Nếu email không tồn tại trong hệ thống
            if (!passwordDAO.isEmailCandidateExist(email) && !passwordDAO.isEmailEmployerExist(email)) {
                request.setAttribute("error", "Email không tồn tại trong hệ thống");
                request.getRequestDispatcher("forget_password.jsp").forward(request, response);
                return;
            }

            // Nếu chưa chọn role (và email tồn tại ở cả 2 vai trò) → chuyển sang choose_role_reset.jsp
            if (role == null && passwordDAO.isEmailCandidateExist(email) && passwordDAO.isEmailEmployerExist(email)) {
                request.setAttribute("email", email);
                request.getRequestDispatcher("choose_role_reset.jsp").forward(request, response);
                return;
            }

            // Nếu chưa có role → tự xác định
            if (role == null) {
                role = passwordDAO.isEmailCandidateExist(email) ? "Candidate" : "Employer";
            }

            // Sinh token + thời gian hết hạn
            String token = resetService.generateToken();
            Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
            Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));

            // Tạo đối tượng token (nếu muốn lưu tạm để debug/log)
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setEmail(email);
            resetToken.setTokenHash(token); // sẽ hash trong DAO
            resetToken.setCreatedAt(createdAt);
            resetToken.setExpiresAt(expiresAt);
            resetToken.setUsed(false);
            resetToken.setAttempts(0);
            resetToken.setRole(role);

            // Lưu DB
            passwordDAO.savePasswordResetToken(email, role, token, expiresAt);

            // Tạo link reset
            String resetLink = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/resetPassword?token=" + token + "&role=" + role;

            // Gửi email
            resetService.sendEmail(email, resetLink, role);

            // Thông báo
            request.setAttribute("message", "Link đặt lại mật khẩu đã được gửi đến email của bạn");
            request.getRequestDispatcher("forget_password.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("forget_password.jsp").forward(request, response);
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

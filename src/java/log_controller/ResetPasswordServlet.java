/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package log_controller;

import dal.PasswordDAO;
import dal.RegisterCandidateDAO;
import dal.RegisterEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PasswordResetToken;
import tool.EncodePassword;

/**
 *
 * @author Admin
 */
@WebServlet(name="ResetPasswordServlet", urlPatterns={"/resetPassword"})
public class ResetPasswordServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ResetPasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath () + "</h1>");
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
      String token = request.getParameter("token");
        String role = request.getParameter("role");

        if (token == null || role == null) {
            request.setAttribute("error", "Link không hợp lệ!");
            request.getRequestDispatcher("forget_password.jsp").forward(request, response);
            return;
        }

        PasswordDAO dao = new PasswordDAO();
        try {
            boolean valid = dao.isValidToken(token, role);
            if (valid) {
                // Token hợp lệ → show form đổi mật khẩu
                request.setAttribute("token", token);
                request.setAttribute("role", role);
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
                request.getRequestDispatcher("forget_password.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi khi xác thực token: " + e.getMessage());
            request.getRequestDispatcher("forget_password.jsp").forward(request, response);
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
       request.setCharacterEncoding("UTF-8");

        String token = request.getParameter("token");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (token == null || role == null) {
            request.setAttribute("error", "Yêu cầu không hợp lệ!");
            request.getRequestDispatcher("forget_password.jsp").forward(request, response);
            return;
        }

        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.setAttribute("token", token);
            request.setAttribute("role", role);
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        PasswordDAO passwordDAO = new PasswordDAO();

        try {
            // Lấy token từ DB
            PasswordResetToken resetToken = passwordDAO.getToken(token, role);

            if (resetToken == null || !passwordDAO.isValidToken(token, role)) {
                request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn!");
                request.getRequestDispatcher("forget_password.jsp").forward(request, response);
                return;
            }

            // Hash mật khẩu mới
            String hashedPassword = EncodePassword.encodePasswordbyHash(password);

            boolean updated = false;
            if ("Candidate".equalsIgnoreCase(role)) {
                RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
                updated = candidateDAO.changePassword(resetToken.getEmail(), hashedPassword);
            } else if ("Employer".equalsIgnoreCase(role)) {
                RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
                updated = employerDAO.changePassword(resetToken.getEmail(), hashedPassword);
            }

            if (updated) {
                // Đánh dấu token đã sử dụng
                passwordDAO.markTokenAsUsed(token, role);
                response.sendRedirect("login.jsp?success=doimatkhauthanhcong");
            } else {
                request.setAttribute("error", "Không thể cập nhật mật khẩu!");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
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

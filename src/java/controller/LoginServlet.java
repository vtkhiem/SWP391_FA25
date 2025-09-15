    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AdminDAO;
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
import model.Candidate;
import model.Employer;
import tool.ValidationRegister;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        String status = null;  // Thay đổi: Khởi tạo null để dễ check
        String inputValue = request.getParameter("email");  // Thay đổi: Lấy chung cho email/username
        String password = request.getParameter("password");

        try {
            RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
            AdminDAO adminDAO = new AdminDAO();

            HttpSession session = request.getSession();

            // Thay đổi: Kiểm tra admin trước (dùng username = inputValue), vì linh hoạt hơn
            // Giả sử bạn implement method isUsernameExist() trong AdminDAO tương tự các DAO khác
            if (adminDAO.isAdmin(inputValue)) {  // Nếu chưa có method này, implement nó (check DB bằng username)
                boolean result = adminDAO.loginAccountAdmin(inputValue, password);
                if (result) {
                    session.setAttribute("username", inputValue);
                    session.setAttribute("role", "Admin");
                    response.sendRedirect("StatictisData");  // Sửa: "StatictisData" → "StatisticsData" nếu là lỗi typo
                    return;  // Thêm: Tránh code chạy tiếp
                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                }
            } // Nếu không phải admin, kiểm tra candidate/employer (chỉ nếu có "@" để an toàn)
            else if (inputValue.contains("@")) {
                // Kiểm tra candidate trước
                if (candidateDAO.isEmailCandidateExist(inputValue)) {
                    boolean result = candidateDAO.loginCandidate(inputValue, password);
                    Candidate candidate = candidateDAO.getCandidateByEmail(inputValue);
                    if (result) {
                        session.setAttribute("email", inputValue);
                        session.setAttribute("role", "Candidate");
                        session.setAttribute("user", candidate);
                        response.sendRedirect("index.jsp");
                        return;
                    } else {
                        status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                    }
                } // Kiểm tra employer
                else if (employerDAO.isEmailEmployerExist(inputValue)) {
                    boolean result = employerDAO.loginEmployer(inputValue, password);
                    Employer employer = employerDAO.getEmployerByEmail(inputValue);
                    if (result) {
                        session.setAttribute("email", inputValue);
                        session.setAttribute("user", employer);
                        session.setAttribute("role", "Employer");
                        response.sendRedirect("Index");  // Giả sử "Index" là servlet/redirect đúng
                        return;
                    } else {
                        status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                    }
                } // Nếu email có "@" nhưng không tồn tại ở candidate/employer → lỗi chung
                else {
                    status = "Tài Khoản không tồn tại";
                }
            } // Nếu không có "@" và không phải admin → lỗi
            else {
                status = "Tài Khoản không tồn tại";
            }

            // Nếu đến đây → login thất bại, forward về login.jsp với status và giữ input
            request.setAttribute("status", status);
            request.setAttribute("username", inputValue);  // Thay đổi: Nhất quán, dùng cho cả admin/email
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {  // Thay đổi: Đổi 's' thành 'e' chuẩn
            // Thêm: Luôn set status lỗi mặc định trong catch
            status = "Có lỗi xảy ra khi đăng nhập. Vui lòng thử lại!";
            request.setAttribute("status", status);
            request.setAttribute("username", inputValue);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            e.printStackTrace();  // Thêm: Log lỗi để debug (xem console server)
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import dal.AdminDAO;
import dal.CandidateDAO;
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
import java.util.List;
import model.Candidate;
import model.Employer;
import model.Role;
import tool.ValidationRegister;
import model.Admin;
import tool.AdminRoleDefine;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = null;  // Thay đổi: Khởi tạo null để dễ check
        String inputValue = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();

            HttpSession session = request.getSession();

            if (candidateDAO.isEmailCandidateExist(inputValue)) {
                boolean result = candidateDAO.loginCandidate(inputValue, password);

                if (result) {
                    Candidate candidate = candidateDAO.getCandidateByEmail(inputValue);
                    session.setAttribute("email", inputValue);
                    session.setAttribute("role", "Candidate");
                    session.setAttribute("user", candidate);
                    session.setAttribute("candidateId", candidate.getCandidateId());
                    response.sendRedirect("index.jsp");
                    return;
                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                }
            } // Kiểm tra employer
            else if (employerDAO.isEmailEmployerExist(inputValue)) {

                boolean result = employerDAO.loginEmployer(inputValue, password);

                if (result) {
                    Employer employer = employerDAO.getEmployerByEmail(inputValue);
                    boolean passed = employerDAO.getEmployerStatusByEmail(inputValue);

                    if (passed) {
                        session.setAttribute("email", inputValue);
                        session.setAttribute("user", employer);
                        session.setAttribute("role", "Employer");
                        response.sendRedirect("employer.jsp");
                        return;
                    } else {
                        status = "Tài khoản chưa dc duyệt";
                    }

                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                }
            } else {
                status = "Tài Khoản không tồn tại";
            }

            request.setAttribute("status", status);
            request.setAttribute("username", inputValue);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

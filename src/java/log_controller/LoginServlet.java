/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package log_controller;

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
        String inputValue = request.getParameter("email");  // Thay đổi: Lấy chung cho email/username
        String password = request.getParameter("password");

        try {
            RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
            AdminDAO adminDAO = new AdminDAO();

            HttpSession session = request.getSession();

            // Thay đổi: Kiểm tra admin trước (dùng username = inputValue), vì linh hoạt hơn
            // Giả sử bạn implement method isUsernameExist() trong AdminDAO tương tự các DAO khác
            if (adminDAO.isAdmin(inputValue)) {
                Admin admin = adminDAO.loginAccountAdmin(inputValue, password);
                if (admin != null) {
                    List<Role> roles = adminDAO.getRolesByAdminId(admin.getAdminId());

                    session.setAttribute("roles", roles);

                    // Check quyền dựa trên roleId
                    for (Role role : roles) {
                        switch (role.getRoleId()) {
                            case AdminRoleDefine.ADMIN:
                                session.setAttribute("role", "Admin");
                                session.setAttribute("username", inputValue);
                              
                                response.sendRedirect("admin/candidates");
                                return;
                            case AdminRoleDefine.SALE:
                                session.setAttribute("username", inputValue);
                             
                                session.setAttribute("role", "Sale");
                                response.sendRedirect("logSale");
                                return;
                            case AdminRoleDefine.MARKETING_STAFF:
                                session.setAttribute("role", "MarketingStaff");
                                session.setAttribute("username", inputValue);
                           
                                response.sendRedirect("logStaff");
                                return;
                        }
                    }

                    // Nếu không match role nào
                    response.sendRedirect("access-denied.jsp");
                    return;
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
                        session.setAttribute("candidateId", candidate.getCandidateId());
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
                        response.sendRedirect("employer.jsp");  // Giả sử "Index" là servlet/redirect đúng
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

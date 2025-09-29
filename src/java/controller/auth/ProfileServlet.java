/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

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

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet ProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false); // Không tạo session mới
        if (session == null || session.getAttribute("role") == null) {
            request.setAttribute("status", "Vui lòng đăng nhập để xem profile!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        String error = request.getParameter("error");
        String role = (String) session.getAttribute("role");
        if ("Candidate".equals(role)) {
            // Candidate: Dữ liệu đã có trong session từ LoginServlet
            Candidate candidate = (Candidate) session.getAttribute("user");
            session.setAttribute("candidate", candidate);
            if (candidate == null) {
                // Fallback: Query DB nếu session mất
                RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
                String email = (String) session.getAttribute("email");
                candidate = candidateDAO.getCandidateByEmail(email);
                if (candidate != null) {
                    session.setAttribute("candidate", candidate);
                } else {
                    request.setAttribute("status", "Không tìm thấy thông tin ứng viên!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }
        } else if ("Employer".equals(role)) {
            RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
            Employer employer = (Employer) session.getAttribute("user");
            // Employer: Query DB để lấy thông tin (sẽ implement getEmployerByEmail)

            if (employer == null) {
                String email = (String) session.getAttribute("email");
                employer = employerDAO.getEmployerByEmail(email);

                request.setAttribute("status", "Không tìm thấy thông tin nhà tuyển dụng!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                session.setAttribute("employer", employer);
            }
        } else {
            request.setAttribute("status", "Vai trò không hợp lệ!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        request.setAttribute("error", error);

        request.getRequestDispatcher("profile.jsp").forward(request, response);

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
        processRequest(request, response);
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

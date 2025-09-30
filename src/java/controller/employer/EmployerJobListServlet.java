package controller.employer;

import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Employer;
import model.JobPost;

@WebServlet(name = "EmployerJobListServlet", urlPatterns = {"/employer_jobs"})
public class EmployerJobListServlet extends HttpServlet {
    JobPostDAO jobPostDAO = new JobPostDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployerJobListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerJobListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null
                || !"Employer".equals(session.getAttribute("role"))) {
            // Nếu chưa login hoặc không phải employer thì chặn lại
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy employer từ session
        Employer employer = (Employer) session.getAttribute("user");
        int employerId = employer.getEmployerId();

        // Gọi DAO để lấy job theo employerId
        List<JobPost> jobs = jobPostDAO.getJobsByEmployer(employerId);

        request.setAttribute("jobs", jobs);
        request.getRequestDispatcher("employer_jobs.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

package controller.job;

import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employer;
import model.JobPost;

@WebServlet(name = "EmployerJobDetailServlet", urlPatterns = {"/employer_job_details"})
public class EmployerJobDetailServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployerJobDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerJobDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        int jobId = Integer.parseInt(request.getParameter("id"));
        JobPost job = jobPostDAO.getJobPostById(jobId);

        if (job != null) {
            if (employer.getEmployerId() == job.getEmployerID()) {
                request.setAttribute("job", job);
                request.getRequestDispatcher("/employer_job_details.jsp").forward(request, response);
            } else {
                session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
                response.sendRedirect(request.getContextPath() + "/employer_jobs");
            }
        } else {
            session.setAttribute("error", "Không tìm thấy công việc.");
            response.sendRedirect(request.getContextPath() + "/employer_jobs");
        }
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

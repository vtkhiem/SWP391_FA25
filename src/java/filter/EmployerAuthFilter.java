package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.JobPostDAO;
import model.Employer;
import model.JobPost;
import java.io.IOException;

@WebFilter(urlPatterns = {"/job_post.jsp", "/job_edit", "/job_edit.jsp", "/job_delete"})
public class EmployerAuthFilter implements Filter {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Employer employer = (session != null) ? (Employer) session.getAttribute("employer") : null;

        // Chưa đăng nhập → redirect login
        if (employer == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String uri = req.getRequestURI();

        // Với edit/delete thì check quyền sở hữu job
        if (uri.contains("job_edit") || uri.contains("job_delete")) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                try {
                    int jobId = Integer.parseInt(idParam);
                    JobPost job = jobPostDAO.getJobPostById(jobId);

                    if (job == null || job.getEmployerID() != employer.getEmployerId()) {
                        // Không tồn tại hoặc không phải job của employer này
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not allowed to modify this job.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Job ID");
                    return;
                }
            }
        }

        // Cho phép tiếp tục
        chain.doFilter(request, response);
    }
}
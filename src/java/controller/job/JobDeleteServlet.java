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

@WebServlet(name = "JobDeleteServlet", urlPatterns = {"/job_delete"})
public class JobDeleteServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobDeleteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobDeleteServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        try {
            int jobId = Integer.parseInt(request.getParameter("id"));
            JobPost job = jobPostDAO.getJobPostById(jobId);

            if (job == null) {
                session.setAttribute("error", "Không tìm thấy công việc cần xoá.");
                response.sendRedirect(request.getContextPath() + "/employer_jobs");
                return;
            }

            if (employer.getEmployerId() == job.getEmployerID()) {
                session.setAttribute("error", "Bạn không có quyền xoá công việc này.");
                response.sendRedirect(request.getContextPath() + "/employer_jobs");
                return;
            }

            boolean deleted = jobPostDAO.deleteJobPost(jobId);

            if (deleted) {
                session.setAttribute("message", "Xoá công việc thành công!");
            } else {
                session.setAttribute("error", "Xoá công việc thất bại. Vui lòng thử lại.");
            }

            response.sendRedirect(request.getContextPath() + "/employer_jobs");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
            response.sendRedirect(request.getContextPath() + "/employer_jobs");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
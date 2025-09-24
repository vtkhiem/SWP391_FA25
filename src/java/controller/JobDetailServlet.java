package controller;

import dal.JobPostDAO;
import model.JobPost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class JobDetailServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int jobId = Integer.parseInt(request.getParameter("id"));
        JobPost job = jobPostDAO.getJobPostById(jobId);

        if (job != null) {
            request.setAttribute("job", job);
            request.getRequestDispatcher("/job_details.jsp").forward(request, response);
        } else {
            response.sendRedirect("jobs"); // nếu không có thì quay lại danh sách
        }
    }
}
package controller;

import dal.JobPostDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/job/delete")
public class JobDeleteServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int jobId = Integer.parseInt(request.getParameter("id"));
            boolean deleted = jobPostDAO.deleteJobPost(jobId);

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/jobs?message=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/job_detail?id=" + jobId + "&error=deleteFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/jobs?error=invalidDelete");
        }
    }
}

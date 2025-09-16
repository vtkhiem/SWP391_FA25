package controller;

import dal.JobPostDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class JobDeleteServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int jobPostId = Integer.parseInt(request.getParameter("id"));
        jobPostDAO.deleteJobPost(jobPostId);
        response.sendRedirect("joblist");
    }
}
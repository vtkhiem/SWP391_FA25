package controller;

import dal.JobPostDAO;
import model.JobPost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class JobListServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<JobPost> jobs = jobPostDAO.getAllJobPosts();
        request.setAttribute("jobs", jobs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("job_list.jsp");
        dispatcher.forward(request, response);
    }
}
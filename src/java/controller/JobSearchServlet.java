package controller;

import dal.JobPostDAO;
import model.JobPost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class JobSearchServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        String location = request.getParameter("location");
        String keyword = request.getParameter("keyword");

        // Salary filter
        double min = Double.valueOf(request.getParameter("minSalary"));
        double max = Double.valueOf(request.getParameter("maxSalary"));

        List<JobPost> jobs = jobPostDAO.searchJobs(category, location, min, max, keyword);
        request.setAttribute("jobs", jobs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("job_list.jsp");
        dispatcher.forward(request, response);
    }
}
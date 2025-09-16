package controller;

import dal.JobPostDAO;
import model.JobPost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class JobEditServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int jobPostId = Integer.parseInt(request.getParameter("jobPostId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String position = request.getParameter("position");
        String location = request.getParameter("location");
        BigDecimal offerMin = new BigDecimal(request.getParameter("offerMin"));
        BigDecimal offerMax = new BigDecimal(request.getParameter("offerMax"));
        int numberExp = Integer.parseInt(request.getParameter("numberExp"));
        String typeJob = request.getParameter("typeJob");

        JobPost job = jobPostDAO.getJobPostById(jobPostId);
        if (job != null) {
            job.setTitle(title);
            job.setDescription(description);
            job.setCategory(category);
            job.setPosition(position);
            job.setLocation(location);
            job.setOfferMin(offerMin);
            job.setOfferMax(offerMax);
            job.setNumberExp(numberExp);
            job.setTypeJob(typeJob);
            jobPostDAO.updateJobPost(job);
        }

        response.sendRedirect("joblist");
    }
}
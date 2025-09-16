package controller;

import dal.JobPostDAO;
import model.JobPost;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobAddServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employerId = Integer.parseInt(request.getParameter("employerId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String position = request.getParameter("position");
        String location = request.getParameter("location");
        BigDecimal offerMin = new BigDecimal(request.getParameter("offerMin"));
        BigDecimal offerMax = new BigDecimal(request.getParameter("offerMax"));
        int numberExp = Integer.parseInt(request.getParameter("numberExp"));
        boolean visible = true;
        String typeJob = request.getParameter("typeJob");

        JobPost job = new JobPost(0, employerId, title, description, category, position, location,
                offerMin, offerMax, numberExp, visible, typeJob, LocalDateTime.now());

        jobPostDAO.insertJobPost(job);
        response.sendRedirect("joblist");
    }
}
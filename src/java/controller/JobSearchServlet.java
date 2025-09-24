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

        // Nếu không nhập thì để rỗng
        if (category == null) {
            category = "";
        }
        if (location == null) {
            location = "";
        }
        if (keyword == null) {
            keyword = "";
        }

        // Salary filter (nếu không nhập thì set = -1 để bỏ qua)
        double min = -1;
        double max = -1;

        try {
            String minParam = request.getParameter("minSalary");
            if (minParam != null && !minParam.isEmpty()) {
                min = Double.parseDouble(minParam);
            }

            String maxParam = request.getParameter("maxSalary");
            if (maxParam != null && !maxParam.isEmpty()) {
                max = Double.parseDouble(maxParam);
            }
        } catch (NumberFormatException e) {
            // Nếu parse lỗi thì bỏ qua filter
            min = -1;
            max = -1;
        }

        List<JobPost> jobs = jobPostDAO.searchJobs(category, location, min, max, keyword);

        request.setAttribute("jobs", jobs);
        request.getRequestDispatcher("jobs.jsp").forward(request, response);
    }
}

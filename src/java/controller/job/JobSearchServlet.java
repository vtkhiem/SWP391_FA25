package controller.job;

import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.JobPost;

@WebServlet(name = "JobSearchServlet", urlPatterns = {"/search"})
public class JobSearchServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobSearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
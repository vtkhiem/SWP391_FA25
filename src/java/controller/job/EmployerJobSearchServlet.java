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
import java.text.Normalizer;
import java.util.List;
import model.Employer;
import model.JobPost;

@WebServlet(name = "EmployerJobSearchServlet", urlPatterns = {"/employer_search"})
public class EmployerJobSearchServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployerJobSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerJobSearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        String category = (request.getParameter("category"));
        String location = request.getParameter("location");
        String keyword = request.getParameter("keyword");
        String jobType = request.getParameter("jobType");

        // Nếu không nhập thì để rỗng
        if (category == null) {
            category = "";
        }
        if (location == null) {
            location = "";
        }
        if (keyword == null) {
            keyword = "";
        } else {
            keyword = keyword.trim();
            keyword = Normalizer.normalize(keyword, Normalizer.Form.NFD);
            keyword = keyword.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            keyword = keyword.replaceAll("[^\\p{L}\\p{N}\\s]", "");
            keyword = keyword.replaceAll("\\s+", " ");
        }
        if (jobType == null) {
            jobType = "";
        }

        // Salary filter (nếu không nhập thì set = -1 để bỏ qua)
        double min = -1;
        double max = -1;
        int numExp = -1;
        int page = 1;

        try {
            String minParam = request.getParameter("minSalary");
            if (minParam != null && !minParam.isEmpty()) {
                min = Double.parseDouble(minParam);
            }

            String maxParam = request.getParameter("maxSalary");
            if (maxParam != null && !maxParam.isEmpty()) {
                max = Double.parseDouble(maxParam);
            }

            if (min > max) {
                request.setAttribute("errorMessage", "Mức lương tối đa phải lớn hơn hoặc bằng mức lương tối thiểu!");
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Nếu parse lỗi thì bỏ qua filter
            min = -1;
            max = -1;
        }

        try {
            String numParam = request.getParameter("numberExp");
            if (numParam != null && !numParam.isEmpty()) {
                numExp = Integer.parseInt(numParam);
            }
        } catch (NumberFormatException e) {
            numExp = -1;
        }

        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        int recordsPerPage = 10;
        int offset = (page - 1) * recordsPerPage;
        
        int employerId = employer.getEmployerId();

        List<JobPost> jobs = jobPostDAO.searchJobsbyEmployer(employerId, category, location, min, max, keyword, numExp, jobType, offset, recordsPerPage);
        int totalRecords = jobPostDAO.countSearchedJobsbyEmployer(employerId, category, location, min, max, keyword, numExp, jobType);
        int noOfPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        
        request.setAttribute("jobs", jobs);
        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);
        request.getRequestDispatcher("employer_job_search.jsp").forward(request, response);
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
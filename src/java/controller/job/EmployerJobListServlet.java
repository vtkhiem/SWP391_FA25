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
import java.util.List;
import model.Employer;
import model.JobPost;

@WebServlet(name = "EmployerJobListServlet", urlPatterns = {"/employer_jobs"})
public class EmployerJobListServlet extends HttpServlet {
    JobPostDAO jobPostDAO = new JobPostDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EmployerJobListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerJobListServlet at " + request.getContextPath() + "</h1>");
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
        
        int page = 1;
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

        List<JobPost> jobs = jobPostDAO.getJobsByEmployer(employer.getEmployerId(), offset, recordsPerPage);
        int totalRecords = jobPostDAO.countJobsByEmployer(employer.getEmployerId());
        int noOfPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        
        String error = (String) session.getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        
        request.setAttribute("jobs", jobs);
        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);
        request.getRequestDispatcher("employer_jobs.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
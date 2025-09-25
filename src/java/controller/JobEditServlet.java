package controller;

import dal.JobPostDAO;
import model.JobPost;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebServlet(name = "JobEditServlet", urlPatterns = {"/job_edit"})
public class JobEditServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobEditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobEditServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/jobs?error=noIdProvided");
            return;
        }

        int jobId = Integer.parseInt(idParam);
        JobPost job = jobPostDAO.getJobPostById(jobId);

        if (job != null) {
            request.setAttribute("job", job);
            request.getRequestDispatcher("/job_edit.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/jobs?error=notfound");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int jobId = Integer.parseInt(request.getParameter("id"));
            int employerId = Integer.parseInt(request.getParameter("employerId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String position = request.getParameter("position");
            String location = request.getParameter("location");
            BigDecimal offerMin = new BigDecimal(request.getParameter("offerMin"));
            BigDecimal offerMax = new BigDecimal(request.getParameter("offerMax"));
            int numberExp = Integer.parseInt(request.getParameter("numberExp"));
            String typeJob = request.getParameter("typeJob");
            boolean visible = true;

            JobPost oldJob = jobPostDAO.getJobPostById(jobId);

            JobPost job = new JobPost(jobId, employerId, title, description, category,
                    position, location, offerMin, offerMax, numberExp, visible,
                    typeJob, LocalDateTime.now());

            boolean updated = jobPostDAO.updateJobPost(job);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/job_details?id=" + jobId + "&message=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/job_edit?id=" + jobId + "&error=updateFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/job_edit?error=invalidInput");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

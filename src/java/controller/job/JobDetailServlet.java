package controller.job;

import dal.JobPostDAO;
import dal.SavedJobDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Candidate;
import model.JobPost;
import model.SavedJob;

@WebServlet(name = "JobDetailServlet", urlPatterns = {"/job_details"})
public class JobDetailServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int jobId = Integer.parseInt(request.getParameter("id"));
        JobPost job = jobPostDAO.getJobPostById(jobId);

        if (job != null) {
            if (job.isVisible()) {
                HttpSession session = request.getSession();
                Candidate candidate = (Candidate) session.getAttribute("user");
                if (candidate != null) {
                    SavedJobDAO savedJobDAO = new SavedJobDAO();
                    SavedJob savedJob = savedJobDAO.getSavedJobByID(candidate.getCandidateId(), jobId);
                    request.setAttribute("isSaved", savedJob != null);
                } else {
                    request.setAttribute("isSaved", false);
                }
                request.setAttribute("job", job);
                request.getRequestDispatcher("/job_details.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/jobs");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/jobs");
        }
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

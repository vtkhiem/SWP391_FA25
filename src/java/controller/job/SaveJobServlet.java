package controller.job;

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

@WebServlet(name = "SaveJobServlet", urlPatterns = {"/save_job"})
public class SaveJobServlet extends HttpServlet {
    private SavedJobDAO savedJobDAO = new SavedJobDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaveJobServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveJobServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (candidate == null || !"Candidate".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int candidateId = ((model.Candidate) candidate).getCandidateId();
        int jobId = Integer.parseInt(request.getParameter("jobId"));

        boolean isSaved = savedJobDAO.isJobSaved(candidateId, jobId);
        if (isSaved) {
            savedJobDAO.removeSavedJob(candidateId, jobId);
        } else {
            savedJobDAO.saveJob(candidateId, jobId);
        }

        /* Quay lại trang gốc
        if (redirect != null && !redirect.isEmpty()) {
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect("job_details?id=" + jobId);
        }*/
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

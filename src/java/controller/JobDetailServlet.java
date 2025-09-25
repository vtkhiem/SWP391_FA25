package controller;

import dal.JobPostDAO;
import model.JobPost;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

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
            request.setAttribute("job", job);
            request.getRequestDispatcher("/job_details.jsp").forward(request, response);
        } else {
            response.sendRedirect("jobs"); // nếu không có thì quay lại danh sách
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
package controller.job;

import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import model.JobPost;

@WebServlet(name = "JobAddServlet", urlPatterns = {"/job_add"})
public class JobAddServlet extends HttpServlet {

    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobAddServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobAddServlet at " + request.getContextPath() + "</h1>");
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
                    try {
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

            boolean success = jobPostDAO.insertJobPost(job);

            if (success) {
                request.setAttribute("message", "Job added successfully!");
            } else {
                request.setAttribute("message", "Failed to add job. Please try again.");
            }
        } catch (Exception e) {
            request.setAttribute("message", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("job_post.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

package controller.job;

import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "JobDeleteBilkServlet", urlPatterns = {"/job_delete_bulk"})
public class JobDeleteBilkServlet extends HttpServlet {
    private JobPostDAO jobPostDAO = new JobPostDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobDeleteBilkServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobDeleteBilkServlet at " + request.getContextPath() + "</h1>");
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
        String[] ids = request.getParameterValues("jobIds");
        if (ids != null) {
            for (String idStr : ids) {
                int id = Integer.parseInt(idStr);
                jobPostDAO.deleteJobPost(id);
            }
        }
        response.sendRedirect("employer_jobs");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
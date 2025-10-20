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
import model.Employer;
import model.JobPost;

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
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        String[] ids = request.getParameterValues("jobIds");

        if (ids == null || ids.length == 0) {
            session.setAttribute("error", "Vui lòng chọn ít nhất một công việc để xoá.");
            response.sendRedirect(request.getContextPath() + "/employer_jobs");
            return;
        }

        int successCount = 0;
        int failCount = 0;
        int unauthorizedCount = 0;

        for (String idStr : ids) {
            try {
                int id = Integer.parseInt(idStr);
                JobPost job = jobPostDAO.getJobPostById(id);
                
                if (job == null) {
                    failCount++;
                    continue;
                }
                
                if(job.getEmployerID() != employer.getEmployerId()) {
                    unauthorizedCount++;
                    continue;
                }
                
                boolean deleted = jobPostDAO.deleteJobPost(id);
                if (deleted) {
                    successCount++;
                } else {
                    failCount++;
                }
            } catch (NumberFormatException e) {
                failCount++;
            }
        }
        
        String message = "";
        if (successCount > 0) {
            message += "Đã xoá thành công " + successCount + " công việc.<br>";
        }
        if (failCount > 0) {
            message += failCount + " công việc bị lỗi hoặc không tồn tại.<br>";
        }
        if (unauthorizedCount > 0) {
            message += unauthorizedCount + " công việc không thuộc quyền sở hữu của bạn.";
        }
        
        if (successCount > 0 && failCount == 0 && unauthorizedCount == 0) {
            session.setAttribute("message", message.trim());
        } else {
            session.setAttribute("error", message.trim());
        }
        
        response.sendRedirect(request.getContextPath() + "/employer_jobs");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
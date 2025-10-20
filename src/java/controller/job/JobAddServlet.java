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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Employer;
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
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/job_post.jsp");
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

        try {
            String title = request.getParameter("title");
            String desc1 = request.getParameter("description-1");
            String desc2 = request.getParameter("description-2");
            String desc3 = request.getParameter("description-3");
            String category = request.getParameter("category");
            String position = request.getParameter("position");
            String location = request.getParameter("location");
            String offerMinStr = request.getParameter("offerMin");
            String offerMaxStr = request.getParameter("offerMax");
            String numberExpStr = request.getParameter("numberExp");
            String typeJob = request.getParameter("typeJob");
            String dueDateStr = request.getParameter("dueDate");
            String employerIdStr = request.getParameter("employerId");

            if (employerIdStr == null || employerIdStr.isEmpty()
                    || Integer.parseInt(employerIdStr) != employer.getEmployerId()) {
                request.setAttribute("error", "Thêm công việc thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("job_post.jsp").forward(request, response);
                return;
            }

            if (title == null || title.trim().isEmpty()
                    || desc1 == null || desc1.trim().isEmpty()
                    || desc2 == null || desc2.trim().isEmpty()
                    || desc3 == null || desc3.trim().isEmpty()
                    || category == null || category.trim().isEmpty()
                    || position == null || position.trim().isEmpty()
                    || location == null || location.trim().isEmpty()
                    || offerMinStr == null || offerMinStr.isEmpty()
                    || offerMaxStr == null || offerMaxStr.isEmpty()
                    || numberExpStr == null || numberExpStr.isEmpty()
                    || typeJob == null || typeJob.trim().isEmpty()
                    || dueDateStr == null || dueDateStr.isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc.");
                request.getRequestDispatcher("job_post.jsp").forward(request, response);
                return;
            }

            StringBuilder descriptionBuilder = new StringBuilder();
            descriptionBuilder.append(desc1.trim()).append("<br>");
            descriptionBuilder.append("<b>Yêu cầu công việc:</b><br>").append(desc2.trim()).append("<br>");
            descriptionBuilder.append("<b>Về quyền lợi:</b><br>").append(desc3.trim());
            String description = descriptionBuilder.toString().trim();

            int employerId = Integer.parseInt(employerIdStr);
            BigDecimal offerMin = new BigDecimal(offerMinStr);
            BigDecimal offerMax = new BigDecimal(offerMaxStr);
            int numberExp = Integer.parseInt(numberExpStr);

            if (offerMax.compareTo(offerMin) < 0) {
                request.setAttribute("error", "Mức lương tối đa không được nhỏ hơn tối thiểu.");
                request.getRequestDispatcher("job_post.jsp").forward(request, response);
                return;
            }

            LocalDateTime dueDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dueDateStr, formatter);
            dueDate = localDate.atStartOfDay();

            JobPost job = new JobPost(0, employerId, title.trim(), description.trim(),
                    category.trim(), position.trim(), location.trim(),
                    offerMin, offerMax, numberExp, true, typeJob.trim(),
                    LocalDateTime.now(), dueDate);

            boolean success = jobPostDAO.insertJobPost(job);

            if (success) {
                request.setAttribute("message", "Thêm công việc thành công!");
            } else {
                request.setAttribute("error", "Thêm công việc thất bại. Vui lòng thử lại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
        }

        request.getRequestDispatcher("job_post.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

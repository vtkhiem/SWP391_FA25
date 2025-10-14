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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Employer;
import model.JobPost;

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

        int jobId;
        try {
            jobId = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            response.sendRedirect(request.getContextPath() + "/jobs?error=invalidId");
            return;
        }

        JobPost job = jobPostDAO.getJobPostById(jobId);
        if (job == null) {
            response.sendRedirect(request.getContextPath() + "/jobs?error=notfound");
            return;
        }

        // Kiểm tra session employer (chỉ employer mới được edit)
        HttpSession session = request.getSession(false);
        Employer employer = (session != null) ? (Employer) session.getAttribute("user") : null;
        if (employer == null) {
            // chưa đăng nhập hoặc không phải employer
            response.sendRedirect(request.getContextPath() + "/login?next=job_edit&id=" + jobId);
            return;
        }

        if (job.getEmployerID() != employer.getEmployerId()) {
            // Không phải chủ job -> forbidden
            response.sendRedirect(request.getContextPath() + "/jobs?error=forbidden");
            return;
        }

        request.setAttribute("job", job);
        request.getRequestDispatcher("/job_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Employer employer = (session != null) ? (Employer) session.getAttribute("user") : null;
        if (employer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int jobId = Integer.parseInt(request.getParameter("id"));

            // Lấy job cũ và kiểm tra quyền
            JobPost oldJob = jobPostDAO.getJobPostById(jobId);
            if (oldJob == null) {
                response.sendRedirect(request.getContextPath() + "/jobs?error=notfound");
                return;
            }
            if (oldJob.getEmployerID() != employer.getEmployerId()) {
                response.sendRedirect(request.getContextPath() + "/jobs?error=forbidden");
                return;
            }

            // Parsing / lấy input (nếu rỗng => giữ giá trị cũ)
            String title = request.getParameter("title");
            if (title == null) {
                title = oldJob.getTitle();
            }

            String description = request.getParameter("description");
            if (description == null) {
                description = oldJob.getDescription();
            }

            String category = request.getParameter("category");
            if (category == null) {
                category = oldJob.getCategory();
            }

            String position = request.getParameter("position");
            if (position == null) {
                position = oldJob.getPosition();
            }

            String location = request.getParameter("location");
            if (location == null) {
                location = oldJob.getLocation();
            }

            BigDecimal offerMin = oldJob.getOfferMin();
            String sMin = request.getParameter("offerMin");
            if (sMin != null && !sMin.trim().isEmpty()) {
                offerMin = new BigDecimal(sMin.trim());
            }

            BigDecimal offerMax = oldJob.getOfferMax();
            String sMax = request.getParameter("offerMax");
            if (sMax != null && !sMax.trim().isEmpty()) {
                offerMax = new BigDecimal(sMax.trim());
            }

            int numberExp = oldJob.getNumberExp();
            String sExp = request.getParameter("numberExp");
            if (sExp != null && !sExp.trim().isEmpty()) {
                numberExp = Integer.parseInt(sExp.trim());
            }

            String typeJob = request.getParameter("typeJob");
            if (typeJob == null) {
                typeJob = oldJob.getTypeJob();
            }

            // DueDate: xử lý an toàn (yyyy-MM-dd)
            LocalDateTime dueDate = oldJob.getDueDate(); // giữ giá trị cũ nếu user không nhập
            String dueParam = request.getParameter("dueDate");
            if (dueParam != null && !dueParam.trim().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(dueParam.trim(), formatter);
                    dueDate = localDate.atStartOfDay();
                } catch (DateTimeException dtEx) {
                    // invalid format -> redirect về form với error
                    response.sendRedirect(request.getContextPath() + "/job_edit?id=" + jobId + "&error=invalidDueDate");
                    return;
                }
            }

            // Giữ DayCreate cũ (không overwrite)
            LocalDateTime dayCreate = oldJob.getDayCreate();
            if (dayCreate == null) {
                dayCreate = LocalDateTime.now();
            }

            // Tạo job object để cập nhật (dùng oldJob để giữ các trường khác)
            JobPost job = oldJob;
            job.setTitle(title);
            job.setDescription(description);
            job.setCategory(category);
            job.setPosition(position);
            job.setLocation(location);
            job.setOfferMin(offerMin);
            job.setOfferMax(offerMax);
            job.setNumberExp(numberExp);
            job.setTypeJob(typeJob);
            job.setVisible(true); // hoặc giữ oldJob.isVisible()
            job.setDayCreate(dayCreate);
            job.setDueDate(dueDate);

            boolean updated = jobPostDAO.updateJobPost(job);

            if (updated) {
                // redirect sang detail (PRG) để tránh resubmit
                response.sendRedirect(request.getContextPath() + "/job_details?id=" + jobId + "&message=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/job_edit?id=" + jobId + "&error=updateFailed");
            }

        } catch (NumberFormatException nfe) {
            response.sendRedirect(request.getContextPath() + "/job_edit?error=invalidInput");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/job_edit?error=serverError");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
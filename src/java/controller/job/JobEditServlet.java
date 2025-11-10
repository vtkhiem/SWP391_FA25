package controller.job;

import dal.ApplyDAO;
import dal.JobPostDAO;
import dal.ServiceEmployerDAO;
import dal.ServiceFunctionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Employer;
import model.Function;
import model.JobPost;
import model.ServiceEmployer;

@WebServlet(name = "JobEditServlet", urlPatterns = {"/job_edit"})
public class JobEditServlet extends HttpServlet {
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
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        try {
            ServiceEmployerDAO seDAO = new ServiceEmployerDAO();
            int serviceId = seDAO.getCurrentServiceByEmployerId(employer.getEmployerId());
            if (serviceId == -1) {
                session.setAttribute("error", "Chưa đăng kí dịch vụ. Vui lòng đăng kí.");
                response.sendRedirect(request.getContextPath() + "/employerServices");
            } else {
                ServiceEmployer se = seDAO.getCurrentServiceInfoByEmployerID(employer.getEmployerId(), serviceId);
                if (!se.getExpirationDate().after(new Timestamp(System.currentTimeMillis()))) {
                    session.setAttribute("error", "Dịch vụ đã hết hạn. Vui lòng đăng kí mới.");
                    response.sendRedirect(request.getContextPath() + "/employerServices");
                } else {
                    ServiceFunctionDAO sfDAO = new ServiceFunctionDAO();
                    List<Function> funcs = sfDAO.getFunctionsByServiceId(serviceId);
                    boolean hasEditFunction = false;

                    for (Function f : funcs) {
                        if (f.getFunctionName().equalsIgnoreCase("EditJobPost")) {
                            hasEditFunction = true;
                            break;
                        }
                    }

                    if (!hasEditFunction) {
                        session.setAttribute("error", "Gói dịch vụ không có quyền được chỉnh sửa công việc.");
                        response.sendRedirect(request.getContextPath() + "/employer_jobs");
                    } else {
                        String idParam = request.getParameter("id");

                        if (idParam == null || idParam.isEmpty()) {
                            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                            return;
                        }

                        int jobId = Integer.parseInt(idParam);
                        JobPostDAO jobPostDAO = new JobPostDAO();
                        JobPost job = jobPostDAO.getJobPostById(jobId);

                        if (job == null) {
                            session.setAttribute("error", "Không tìm thấy công việc cần chỉnh sửa.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                            return;
                        }

                        if (job.getEmployerID() != employer.getEmployerId()) {
                            session.setAttribute("error", "Bạn không có quyền chỉnh sửa công việc này.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                            return;
                        }

                        ApplyDAO applyDAO = new ApplyDAO();

                        if (applyDAO.checkHasApply(jobId)) {
                            session.setAttribute("error", "Đã có ứng viên ứng tuyển. Bạn không thể chỉnh sửa công việc này.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                        } else {
                            if (job.isVisible()) {
                                session.setAttribute("error", "Công việc đang mở. Bạn không thể chỉnh sửa công việc này.");
                                response.sendRedirect(request.getContextPath() + "/employer_jobs");
                            } else {
                                String[] parts = job.getDescription().split("<b>.*?</b>");
                                String desc1 = "";
                                String desc2 = "";
                                String desc3 = "";

                                for (int i = 0; i < parts.length; i++) {
                                    String clean = parts[i].replaceAll("(?i)<br>", "\n").trim();

                                    switch (i) {
                                        case 0:
                                            desc1 = clean;
                                            break;
                                        case 1:
                                            desc2 = clean;
                                            break;
                                        case 2:
                                            desc3 = clean;
                                            break;
                                    }
                                }

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String dueDateFormatted = "";
                                if (job.getDueDate() != null) {
                                    dueDateFormatted = job.getDueDate().format(formatter);
                                }
                                request.setAttribute("dueDateFormatted", dueDateFormatted);
                                request.setAttribute("desc1", desc1);
                                request.setAttribute("desc2", desc2);
                                request.setAttribute("desc3", desc3);
                                request.setAttribute("job", job);
                                request.getRequestDispatcher("/job_edit.jsp").forward(request, response);
                            }
                        }
                    }
                }
            }
        } catch (ServletException | IOException | NumberFormatException | SQLException e) {
            session.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/employer_jobs");
        }
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
            ServiceEmployerDAO seDAO = new ServiceEmployerDAO();
            int serviceId = seDAO.getCurrentServiceByEmployerId(employer.getEmployerId());
            if (serviceId == -1) {
                session.setAttribute("error", "Chưa đăng kí dịch vụ. Vui lòng đăng kí.");
                response.sendRedirect(request.getContextPath() + "/employerServices");
            } else {
                ServiceEmployer se = seDAO.getCurrentServiceInfoByEmployerID(employer.getEmployerId(), serviceId);
                if (!se.getExpirationDate().after(new Timestamp(System.currentTimeMillis()))) {
                    session.setAttribute("error", "Dịch vụ đã hết hạn. Vui lòng đăng kí mới.");
                    response.sendRedirect(request.getContextPath() + "/employerServices");
                } else {
                    ServiceFunctionDAO sfDAO = new ServiceFunctionDAO();
                    List<Function> funcs = sfDAO.getFunctionsByServiceId(serviceId);
                    boolean hasEditFunction = false;

                    for (Function f : funcs) {
                        if (f.getFunctionName().equalsIgnoreCase("EditJobPost")) {
                            hasEditFunction = true;
                            break;
                        }
                    }

                    if (!hasEditFunction) {
                        session.setAttribute("error", "Gói dịch vụ không có quyền được chỉnh sửa công việc.");
                        response.sendRedirect(request.getContextPath() + "/employer_jobs");
                    } else {
                        int jobId = Integer.parseInt(request.getParameter("id"));
                        JobPostDAO jobPostDAO = new JobPostDAO();
                        JobPost oldJob = jobPostDAO.getJobPostById(jobId);

                        if (oldJob == null || oldJob.getEmployerID() != employer.getEmployerId()) {
                            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                            return;
                        }

                        String title = request.getParameter("title");
                        if (title == null || title.trim().isEmpty()) {
                            title = oldJob.getTitle();
                        }

                        String description = "";
                        String desc1 = request.getParameter("description-1");
                        String desc2 = request.getParameter("description-2");
                        String desc3 = request.getParameter("description-3");
                        if (desc1 == null || desc1.trim().isEmpty()
                                || desc2 == null || desc2.trim().isEmpty()
                                || desc3 == null || desc3.trim().isEmpty()) {
                            description = oldJob.getDescription();
                        } else {
                            StringBuilder descriptionBuilder = new StringBuilder();
                            descriptionBuilder.append((desc1.trim()).replaceAll("\r?\n", "<br>")).append("<br>");
                            descriptionBuilder.append("<b>Yêu cầu công việc:</b><br>").append((desc2.trim()).replaceAll("\r?\n", "<br>")).append("<br>");
                            descriptionBuilder.append("<b>Về quyền lợi:</b><br>").append((desc3.trim()).replaceAll("\r?\n", "<br>"));
                            description = descriptionBuilder.toString().trim();
                        }

                        String category = request.getParameter("category");
                        if (category == null || category.trim().isEmpty()) {
                            category = oldJob.getCategory();
                        }

                        String position = request.getParameter("position");
                        if (position == null || position.trim().isEmpty()) {
                            position = oldJob.getPosition();
                        }

                        String location = request.getParameter("location");
                        if (location == null || location.trim().isEmpty()) {
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
                        if (typeJob == null || typeJob.trim().isEmpty()) {
                            typeJob = oldJob.getTypeJob();
                        }

                        LocalDateTime dueDate = oldJob.getDueDate();
                        String dueParam = request.getParameter("dueDate");
                        if (dueParam != null && !dueParam.trim().isEmpty()) {
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate localDate = LocalDate.parse(dueParam.trim(), formatter);
                                dueDate = localDate.atStartOfDay();
                            } catch (DateTimeException dtEx) {
                                request.setAttribute("error", "Chỉnh sửa công việc thất bại. Vui lòng thử lại.");
                                request.getRequestDispatcher(request.getContextPath() + "/job_edit?id=" + jobId).forward(request, response);
                                return;
                            }
                        }

                        LocalDateTime dayCreate = oldJob.getDayCreate();
                        if (dayCreate == null) {
                            dayCreate = LocalDateTime.now();
                        }

                        if (offerMax.compareTo(offerMin) < 0) {
                            request.setAttribute("error", "Mức lương tối đa không được nhỏ hơn tối thiểu.");
                            request.getRequestDispatcher(request.getContextPath() + "/job_edit?id=" + jobId).forward(request, response);
                            return;
                        }

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
                        job.setVisible(oldJob.isVisible());
                        job.setDayCreate(dayCreate);
                        job.setDueDate(dueDate);

                        boolean updated = jobPostDAO.updateJobPost(job);

                        if (updated) {
                            session.setAttribute("message", "Chỉnh sửa công việc thành công!");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                        } else {
                            request.setAttribute("error", "Chỉnh sửa công việc thất bại. Vui lòng thử lại.");
                            request.getRequestDispatcher("/job_edit").forward(request, response);
                        }
                    }
                }
            }
        } catch (ServletException | IOException | NumberFormatException | SQLException e) {
            session.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/employer_jobs");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
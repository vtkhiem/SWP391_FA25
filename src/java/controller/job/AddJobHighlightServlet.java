package controller.job;

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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import model.Employer;
import model.Function;
import model.ServiceEmployer;

@WebServlet(name = "AddJobHighlightServlet", urlPatterns = {"/highlight-job"})
public class AddJobHighlightServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddJobHighlightServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddJobHighlightServlet at " + request.getContextPath() + "</h1>");
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
                    boolean hasHighlightPostFunction = false;

                    for (Function f : funcs) {
                        if (f.getFunctionName().equalsIgnoreCase("HighlightPost")) {
                            hasHighlightPostFunction = true;
                            break;
                        }
                    }

                    if (!hasHighlightPostFunction) {
                        session.setAttribute("error", "Gói dịch vụ không có quyền được thêm công việc nổi bật.");
                        response.sendRedirect(request.getContextPath() + "/employerServices");
                    } else {
                        try {
                            JobPostDAO jpDAO = new JobPostDAO();
                            String jobPostId = request.getParameter("jobId");
                            boolean success = jpDAO.setHighlightJob(employer.getEmployerId(), Integer.parseInt(jobPostId));

                            if (success) {
                                session.setAttribute("message", "Đã thêm công việc nổi bật thành công.");
                            } else {
                                session.setAttribute("error", "Thêm công việc nổi bật thất bại.");
                            }

                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                        } catch (NumberFormatException e) {
                            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện. Vui lòng thử lại.");
                            response.sendRedirect(request.getContextPath() + "/employer_jobs");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            session.setAttribute("error", "Dịch vụ đã hết hạn. Vui lòng đăng kí mới.");
            response.sendRedirect(request.getContextPath() + "/employerServices");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
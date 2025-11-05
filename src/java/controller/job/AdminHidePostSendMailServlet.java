package controller.job;

import dal.EmployerDAO;
import dal.JobPostDAO;
import model.JobPost; // Cần import model JobPost
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Cần import HttpSession
import tool.EmailService;

/**
 * Servlet này xử lý việc "" (Lock) một JobPost và gửi email
 * thông báo cho Employer.
 */
@WebServlet(name = "AdminHidePostSendMailServlet", urlPatterns = {"/adminHideJob"})
public class AdminHidePostSendMailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        // --- 1. Bảo mật: Kiểm tra quyền Admin ---
        if (role == null || !role.equals("Admin")) {
            response.sendRedirect("access-denied.jsp"); // Chuyển hướng nếu không phải Admin
            return;
        }

        String idStr = request.getParameter("jobPostID");
        int jobPostID = 0;

        // --- 2. Lấy và Xác thực ID ---
        try {
            jobPostID = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.err.println("AdminHideJob: Lỗi parse jobPostID: " + idStr);
            session.setAttribute("error", "ID bài đăng không hợp lệ.");
            response.sendRedirect("adminJobpostList"); // Quay về trang list
            return;
        }

        JobPostDAO jobDAO = new JobPostDAO();

        // --- 3. Lấy thông tin bài đăng (Tối ưu hóa) ---
        // Lấy 1 lần để có cả Title và EmployerID, thay vì gọi DAO 3 lần
        JobPost jobToLock = jobDAO.getJobPostById(jobPostID);

        if (jobToLock == null) {
            session.setAttribute("error", "Không tìm thấy bài đăng với ID: " + jobPostID);
            response.sendRedirect("adminJobpostList");
            return;
        }

        // --- 4. Thực hiện "" (Lock) bài đăng ---
        // Sử dụng adminLockJobPost để Employer không thể tự bật lại
        boolean lockSuccess = jobDAO.hideJobPost(jobPostID);

        if (!lockSuccess) {
            session.setAttribute("error", "Ẩn bài đăng thất bại. Vui lòng thử lại.");
            response.sendRedirect("adminJobpostList");
            return;
        }

        // --- 5. Gửi Email thông báo ---
        try {
            EmployerDAO empDAO = new EmployerDAO();
            EmailService emailService = new EmailService();

            // Lấy thông tin từ đối tượng jobToLock đã lấy ở trên
            int employerId = jobToLock.getEmployerID();
            String jobTitle = jobToLock.getTitle();
            
            // Lấy email của Employer
            String employerEmail = empDAO.getEmailByID(employerId);

            if (employerEmail != null && !employerEmail.isEmpty()) {
                String subject = "Thông báo: Bài đăng của bạn đã bị ẩn";
                // Câu response của bạn đã được sửa
                String responses = "Bài đăng của bạn không phù hợp, cụ thể là bài đăng có nội dung '" + jobTitle + "'. "
                                 + "Bài đăng này đã bị ẩn. Vui lòng liên hệ Ban quản trị để biết thêm chi tiết.";

                emailService.sendEmailToUser(employerEmail, responses, subject);
                
                session.setAttribute("message", "Đã ẩn bài đăng " + jobPostID + " và gửi email.");
            } else {
                System.err.println("AdminHideJob: Không tìm thấy email cho EmployerID: " + employerId);
                session.setAttribute("message", "Đã ẩn bài đăng. (Lỗi: Không tìm thấy email NTD để gửi thông báo).");
            }

        } catch (Exception e) {
            System.err.println("AdminHideJob: Lỗi khi gửi email: " + e.getMessage());
            // Bài đăng ĐÃ BỊ , nhưng email lỗi. Vẫn thông báo cho Admin
            session.setAttribute("message", "Đã ẩn bài đăng. (Lỗi: Gửi email thất bại).");
        }

        // --- 6. Quay về trang Admin List ---
        response.sendRedirect("adminJobpostList");
    }

    /**
     * Chặn các yêu cầu GET.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chỉ cho phép POST
        response.sendRedirect("adminJobpostList");
    }

    @Override
    public String getServletInfo() {
        return "Admin action to lock a job post and send a notification email to the employer.";
    }
}
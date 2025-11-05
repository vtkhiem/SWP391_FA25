package controller.job;

import dal.JobPostDAO;
import model.JobPost;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Servlet này xử lý việc hiển thị và lọc danh sách bài đăng (JobPost)
 * cho trang quản trị (Admin).
 */
// Đảm bảo URL pattern này khớp với file JSP của bạn
@WebServlet(name = "AdminJobListServlet", urlPatterns = {"/adminJobpostList"})
public class AdminJobListServlet extends HttpServlet {

    // Đặt số lượng bản ghi trên mỗi trang
    private static final int RECORDS_PER_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        JobPostDAO jobDAO = new JobPostDAO();
        
        // --- 1. Xử lý Phân trang ---
        int currentPage = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi parse 'page': " + e.getMessage());
                currentPage = 1; // Quay về trang 1 nếu lỗi
            }
        }
        int offset = (currentPage - 1) * RECORDS_PER_PAGE;

        // --- 2. Lấy tham số Filter ---
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String location = request.getParameter("location");
        String minSalaryStr = request.getParameter("minSalary");
        String maxSalaryStr = request.getParameter("maxSalary");
        String numberExpStr = request.getParameter("numberExp");
        String jobType = request.getParameter("jobType");
        String employerId_raw = request.getParameter("employerId");

        // --- 3. Chuyển đổi kiểu dữ liệu cho tham số ---
        Double minSalary = null;
        Double maxSalary = null;
        int numberExp = -1; // Mặc định là -1 (bỏ qua filter này)
        int employerId= -1;

        try {
            if (minSalaryStr != null && !minSalaryStr.isEmpty()) {
                minSalary = Double.parseDouble(minSalaryStr);
            }
            if (maxSalaryStr != null && !maxSalaryStr.isEmpty()) {
                maxSalary = Double.parseDouble(maxSalaryStr);
            }
            if (numberExpStr != null && !numberExpStr.isEmpty()) {
                numberExp = Integer.parseInt(numberExpStr);
            }
            if (employerId_raw != null && !employerId_raw.isEmpty()) {
                employerId = Integer.parseInt(employerId_raw);
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse filter: " + e.getMessage());
            // Có thể set lỗi ra request nếu cần, ở đây ta bỏ qua
        }

        // --- 4. Kiểm tra xem có đang filter hay không ---
        boolean isFiltering = (keyword != null && !keyword.isEmpty())
                || (category != null && !category.isEmpty())
                || (location != null && !location.isEmpty())
                || (minSalary != null)
                || (maxSalary != null)
                || (numberExp >= 0) // 0 (Không yêu cầu KN) cũng là 1 filter
                || (jobType != null && !jobType.isEmpty())
        || (employerId >= 0);

        List<JobPost> jobs;
        int totalJobs;

        // --- 5. Lấy dữ liệu từ DAO ---
        if (isFiltering) {
            // Có filter: Gọi hàm search
            jobs = jobDAO.adminSearchJobs(category, location, minSalary, maxSalary, keyword, numberExp, jobType, offset, RECORDS_PER_PAGE,employerId);
            totalJobs = jobDAO.adminCountJobsSearched(category, location, minSalary, maxSalary, keyword, numberExp, jobType,employerId);
        } else {
            // Không filter: Lấy tất cả
            jobs = jobDAO.adminGetJobPosts(offset, RECORDS_PER_PAGE);
            totalJobs = jobDAO.adminCountAllJobs();
        }

        // --- 6. Tính toán tổng số trang ---
        int noOfPages = (int) Math.ceil((double) totalJobs / RECORDS_PER_PAGE);
        if (noOfPages == 0) noOfPages = 1; // Đảm bảo luôn có ít nhất 1 trang

        // --- 7. Set Attributes và Forward ---
        request.setAttribute("jobs", jobs);
        request.setAttribute("totalJobs", totalJobs);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", currentPage);

        // Đổi "adminJobPost.jsp" thành tên file JSP của bạn
        request.getRequestDispatcher("jobs_manage_by_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu form filter bị ai đó set method="post", ta vẫn xử lý như GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing (listing, filtering) admin job posts.";
    }
}
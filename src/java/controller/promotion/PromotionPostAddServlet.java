package controller.promotion;

import dal.PromotionPostDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Admin;
import model.PromotionPost;
import model.Service;

@WebServlet(name = "PromotionPostAddServlet", urlPatterns = {"/post_promotion"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class PromotionPostAddServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PromotionPostAddServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PromotionPostAddServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (admin == null || !"MarketingStaff".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-admin.jsp");
            return;
        }

        ServiceDAO serviceDAO = new ServiceDAO();
        try {
            List<Service> list = serviceDAO.getAllVisibleServices();
            request.setAttribute("services", list);
            request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
            response.sendRedirect(request.getContextPath() + "/logStaff");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (admin == null || !"MarketingStaff".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-admin.jsp");
            return;
        }

        try {
            String title = request.getParameter("title");
            String service = request.getParameter("service");
            String content = request.getParameter("content");
            String createBy = request.getParameter("createBy");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            if (createBy == null || createBy.isEmpty()
                    || Integer.parseInt(createBy) != admin.getAdminId()) {
                request.setAttribute("error", "Thêm tin khuyến mãi thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
                return;
            }

            if (title == null || title.trim().isEmpty()
                    || service == null || service.trim().isEmpty()
                    || content == null || content.trim().isEmpty()
                    || startDateStr == null || startDateStr.trim().isEmpty()
                    || endDateStr == null || endDateStr.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc.");
                request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
                return;
            }

            if (!title.matches("^[\\p{L}\\p{N}\\s]+$")) {
                request.setAttribute("error", "Tiêu đề không được chứa ký tự đặc biệt.");
                request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
                return;
            }
            
            content = (content.trim()).replaceAll("\n?\n", "<br>");

            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate sDate = LocalDate.parse(startDateStr, formatter);
            LocalDate eDate = LocalDate.parse(endDateStr, formatter);
            startDate = sDate.atStartOfDay();
            endDate = eDate.atStartOfDay();

            if (startDate.isBefore(LocalDate.now().atStartOfDay())) {
                request.setAttribute("error", "Ngày bắt đầu phải từ hôm nay trở đi.");
                request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
                return;
            }
            
            if (startDate.isAfter(endDate)) {
                request.setAttribute("error", "Ngày bắt đầu không thể sau ngày kết thúc.");
            } else {
                // --- Upload file ---
                Part filePart = request.getPart("bannerImage");
                if (filePart == null || filePart.getSubmittedFileName().isEmpty()) {
                    request.setAttribute("error", "Vui lòng chọn ảnh bìa hợp lệ.");
                    request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
                    return;
                }
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                String uploadPath = getServletContext().getRealPath("/uploads/banner");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);

                String bannerUrl = "uploads/banner/" + fileName;

                PromotionPost proPost = new PromotionPost(0, Integer.parseInt(service), title, content, bannerUrl, startDate, endDate, true, 0, LocalDateTime.now(), null, Integer.parseInt(createBy));

                PromotionPostDAO ppDAO = new PromotionPostDAO();
                boolean success = ppDAO.insertPromotionPost(proPost);

                if (success) {
                    request.setAttribute("message", "Thêm tin khuyến mãi thành công!");
                } else {
                    request.setAttribute("error", "Thêm tin khuyến mãi thất bại. Vui lòng thử lại.");
                }
                
                request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
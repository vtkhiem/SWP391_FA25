package controller.promotion;

import dal.PromotionPostDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.PromotionPost;

@WebServlet(name = "ViewPromotionPosts", urlPatterns = {"/viewPromotionPosts"})
public class ViewPromotionPosts extends HttpServlet {

    private final int PAGE_SIZE = 6; // số card mỗi trang
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        PromotionPostDAO ppDAO = new PromotionPostDAO();
        int totalPosts = ppDAO.countPromotionPosts();
        int totalPages = (int) Math.ceil((double) totalPosts / PAGE_SIZE);

        // Lấy danh sách promotion theo trang
        List<PromotionPost> promotions = ppDAO.getByPage(currentPage, PAGE_SIZE);

        request.setAttribute("promotionPosts", promotions);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("now", new Date());
        request.getRequestDispatcher("promotion-post.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // POST cũng xử lý giống GET
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách PromotionPost";
    }
}

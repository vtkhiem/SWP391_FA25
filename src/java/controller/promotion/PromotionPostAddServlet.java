package controller.promotion;

import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import model.Admin;
import model.Service;

@WebServlet(name = "PromotionPostAddServlet", urlPatterns = {"/post_promotion"})
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

        if (admin == null || !"Admin".equals(role) || !"MarketingStaff".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-admin.jsp");
            return;
        }

        ServiceDAO serviceDAO = new ServiceDAO();
        try {
            List<Service> list = serviceDAO.getAllVisibleServices();
            request.setAttribute("services", list);
            request.getRequestDispatcher("promotion_post.jsp").forward(request, response);
        } catch (SQLException ex) {
            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
            response.sendRedirect(request.getContextPath() + "/logStaff");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
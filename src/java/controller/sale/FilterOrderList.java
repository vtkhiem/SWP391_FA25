/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import dal.OrderDAO;
import dal.PromotionDAO;
import dal.ServiceDAO;
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
import java.util.ArrayList;
import java.util.List;
import model.OrderView;
import model.Promotion;
import model.Service;
import tool.Validation;

/**
 *
 * @author shiro
 */
@WebServlet(name = "FilterOrderList", urlPatterns = {"/filterOrderList"})
public class FilterOrderList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FilterOrderList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterOrderList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private int parseIntOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String txt = Validation.searchKey(req.getParameter("txt"));
        String service = req.getParameter("service");
        String promotion = req.getParameter("promotion");
        String status = req.getParameter("status");

        OrderDAO dao = new OrderDAO();
        ServiceDAO sdao = new ServiceDAO();
        PromotionDAO pdao = new PromotionDAO();

        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int size = parseIntOrDefault(req.getParameter("size"), 10);
        int serviceID = Validation.getId(service);
        int offSet = (page - 1) * size;

        int totalRows = dao.countFilteredOrders(txt, serviceID, promotion, status);
        int totalPages = (int) Math.ceil(totalRows / (double) size);
        if (totalPages == 0) {
            totalPages = 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        List<OrderView> filteredOrder = dao.filterOrders(txt, serviceID, promotion, status, offSet, size);

        List<Service> services = new ArrayList<>();
        List<Promotion> promotions = new ArrayList<>();
        try {
            services = sdao.getAllServices();
            promotions = pdao.getAllPromotions();
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể tải danh sách khuyến mãi!");
        }

        BigDecimal revenue = dao.totalRevenue();

        req.setAttribute("orders", filteredOrder);
        req.setAttribute("services", services);
        req.setAttribute("promotions", promotions);
        req.setAttribute("revenue", revenue);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalRows", totalRows);
        req.setAttribute("txt", txt);
        req.setAttribute("selectedService", service);
        req.setAttribute("selectedPromotion", promotion);
        req.setAttribute("selectedStatus", status);

        req.getRequestDispatcher("/sale.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

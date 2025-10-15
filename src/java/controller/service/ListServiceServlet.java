/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.service;

import dal.ServiceDAO;
import dal.ServiceFunctionDAO;
import dal.ServicePromotionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Function;
import model.Promotion;
import model.Service;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListServiceServlet", urlPatterns = {"/listService"})
public class ListServiceServlet extends HttpServlet {

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
            out.println("<title>Servlet ListServiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListServiceServlet at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            ServicePromotionDAO spDAO = new ServicePromotionDAO();
            ServiceFunctionDAO sfDAO = new ServiceFunctionDAO();


            // 🔹 Lấy tất cả dịch vụ
            List<Service> serviceList = serviceDAO.getAllServices();

            // 🔹 Gắn thêm promotion + function cho từng service
            for (Service s : serviceList) {
                List<Promotion> promotions = spDAO.getPromotionsByServiceId(s.getServiceID());
                s.setPromotions(promotions);

                List<Function> functions = sfDAO.getFunctionsByServiceId(s.getServiceID());
                s.setFunctions(functions);
            }

            // 🔹 Lấy message nếu có (từ session hoặc query param)
            String message = (String) request.getSession().getAttribute("message");
            if (message == null) {
                message = request.getParameter("message");
            }
            request.getSession().removeAttribute("message"); // Xóa để tránh hiện lại sau reload

            // 🔹 Gửi dữ liệu sang JSP
            request.setAttribute("serviceList", serviceList);
            request.setAttribute("message", message);
            request.getRequestDispatcher("serviceList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "❌ Lỗi khi tải danh sách dịch vụ: " + e.getMessage());
            request.getRequestDispatcher("serviceList.jsp").forward(request, response);
        }
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

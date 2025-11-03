/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.wall;

import dal.WallDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "HideAndShowJobpostFromWallServlet", urlPatterns = {"/hideAndShow"})
public class HideAndShowJobpostFromWallServlet extends HttpServlet {

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
            out.println("<title>Servlet HideAndShowJobpostFromWallServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HideAndShowJobpostFromWallServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int employerId = Integer.parseInt(request.getParameter("employerId"));
            int jobpostId = Integer.parseInt(request.getParameter("jobpostId"));
            boolean isActive = Boolean.parseBoolean(request.getParameter("active"));
            WallDAO wallDAO = new WallDAO();
             boolean success = wallDAO.toggleActiveStatus(employerId, jobpostId, isActive);

            if (success) {
                request.getSession().setAttribute("message",
                        isActive ? "Đã hiển thị công việc lên tường!" : "Đã ẩn công việc khỏi tường!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật trạng thái thất bại!");
            }

          response.sendRedirect("employer_jobs");
                  
            
        } catch (Exception e) {
              e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi cập nhật trạng thái!");
            response.sendRedirect("employer_job_list");
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

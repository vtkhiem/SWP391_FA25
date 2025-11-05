/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.wall;

import dal.ServiceEmployerDAO;
import dal.ServiceFunctionDAO;
import dal.WallDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Function;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "PinJobOnWallServlet", urlPatterns = {"/pinJob"})
public class PinJobOnWallServlet extends HttpServlet {

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
            out.println("<title>Servlet PinJobOnWallServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PinJobOnWallServlet at " + request.getContextPath() + "</h1>");
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
            int jobPostId = Integer.parseInt(request.getParameter("jobpostId"));
            String action = request.getParameter("action"); // "pin" hoặc "unpin"

            WallDAO dao = new WallDAO();
            boolean success = false;
                ServiceEmployerDAO sedao= new ServiceEmployerDAO();
                    ServiceFunctionDAO sfdao = new ServiceFunctionDAO();
            int serviceId = sedao.getCurrentServiceByEmployerId(employerId);
            List<Function> list = sfdao.getFunctionsByServiceId(serviceId);
              boolean hasWallFunction = false;
            for(Function f : list){
                 if (f.getFunctionName().equalsIgnoreCase("PinPost")) {
                        hasWallFunction = true;
                        break;
                    }
            }
            
              if(hasWallFunction){
                   if ("pin".equalsIgnoreCase(action)) {
                success = dao.pinJob(employerId, jobPostId);
            } else if ("unpin".equalsIgnoreCase(action)) {
                success = dao.unpinJob(employerId, jobPostId);
            }

            if (success) {
                request.getSession().setAttribute("message",
                        "pin".equalsIgnoreCase(action)
                                ? "📌 Đã ghim bài tuyển dụng lên đầu tường!"
                                : "📍 Đã bỏ ghim bài tuyển dụng!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật ghim thất bại!");
            }
              }else{
                   request.getSession().setAttribute("error", "Dịch vụ hiện tại của bạn không hỗ trợ chức năng ghim bài viết!");
              }

           

            response.sendRedirect("employerWall");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi cập nhật trạng thái ghim!");
            response.sendRedirect("employerWall");
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

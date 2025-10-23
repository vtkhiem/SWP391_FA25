/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;

import dal.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.StaffView;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="StaffListServlet", urlPatterns={"/admin/staffs"})
public class StaffListServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet StaffListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffListServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session != null) {
        Object msg = session.getAttribute("message");
        if (msg != null) {
            req.setAttribute("message", msg.toString());
            session.removeAttribute("message");
        }
    }
    String q = req.getParameter("q");
    String pageStr = req.getParameter("page");
    String roleParam = req.getParameter("role"); 
    int pageSize = 10;
    int page = 1;
    if (pageStr != null) {
        try { page = Math.max(1, Integer.parseInt(pageStr)); } catch (NumberFormatException ignore) {}
    }

    AdminDAO dao = new AdminDAO();
    List<StaffView> all = new ArrayList<>();
    try {
        all = dao.findAllMarketingSale(); 
        if (q != null && !q.trim().isEmpty()) {
            String kw = q.trim().toLowerCase();
            List<StaffView> filtered = new ArrayList<>();
            for (StaffView s : all) {
                String uname = s.getUsername() == null ? "" : s.getUsername().toLowerCase();
                String roles = s.getRoles() == null ? "" : s.getRoles().toLowerCase();
                String pass  = (s.getPassword() == null ? "" : s.getPassword().toLowerCase()); // nếu có
                if (uname.contains(kw) || roles.contains(kw) || pass.contains(kw)) {
                    filtered.add(s);
                }
            }
            all = filtered;
        }
        if (roleParam != null && !roleParam.isEmpty()) {
            if ("2".equals(roleParam) || "3".equals(roleParam)) {
                List<StaffView> filtered = new ArrayList<>();
                for (StaffView s : all) {
                    String roles = s.getRoles() == null ? "" : s.getRoles();
                    boolean match = ("2".equals(roleParam) && roles.contains("Marketing"))
                                 || ("3".equals(roleParam) && roles.contains("Sale"));
                    if (match) filtered.add(s);
                }
                all = filtered;
            }
        }

        int total = all.size();
        int totalPages = (int) Math.ceil(total / (double) pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int from = (page - 1) * pageSize;
        int to = Math.min(total, from + pageSize);
        List<StaffView> pageList = (from < to) ? all.subList(from, to) : new ArrayList<>();
        req.setAttribute("staffs", pageList);
        req.setAttribute("total", total);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("page", page);
        req.setAttribute("q", q == null ? "" : q.trim());
        req.setAttribute("role", roleParam == null ? "" : roleParam);
    } catch (Exception e) {
        e.printStackTrace();
        req.setAttribute("error", "Không tải được danh sách: " + e.getMessage());
        req.setAttribute("staffs", new ArrayList<StaffView>());
        req.setAttribute("total", 0);
        req.setAttribute("totalPages", 1);
        req.setAttribute("page", 1);
        req.setAttribute("q", q == null ? "" : q.trim());
        req.setAttribute("role", roleParam == null ? "" : roleParam);
    }

    req.getRequestDispatcher("/admin/staff-list.jsp").forward(req, resp);
}

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

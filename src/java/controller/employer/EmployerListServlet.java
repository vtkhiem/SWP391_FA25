/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.employer;

import dal.BanDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dal.EmployerDAO;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import model.Employer;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "EmployerListServlet", urlPatterns = {"/admin/employers"})
public class EmployerListServlet extends HttpServlet {

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
            out.println("<title>Servlet EmployerListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerListServlet at " + request.getContextPath() + "</h1>");
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
    private String param(HttpServletRequest req, String name, String def) {
        String v = req.getParameter(name);
        return v == null ? def : v;
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Set<Integer> bannedEmployerIds;
        try {
            BanDAO bdao = new BanDAO();
            bannedEmployerIds = bdao.getActiveBannedEmployerIds();
        } catch (Exception e) {
            bannedEmployerIds = Collections.emptySet();
        }
        req.setAttribute("bannedEmployerIds", bannedEmployerIds);
        String qParam = param(req, "q", "").trim();
        String statusParam = param(req, "status", "").trim();
        int page = parseInt(param(req, "page", "1"), 1);
        final int size = 10;
        if (!qParam.isEmpty()) {
            qParam = qParam.replaceAll("\\s+", " ").trim();
        }
        String likeKeyword = null;
        if (!qParam.isEmpty()) {
            likeKeyword = qParam.replace(" ", "%");
        }

        req.setAttribute("qNormalized", qParam);

        Boolean statusFilter = null;
        if (!statusParam.isEmpty()) {
            String v = statusParam.toLowerCase();
            if (v.equals("true") || v.equals("1") || v.equals("verified")) {
                statusFilter = Boolean.TRUE;
                statusParam = "true";
            } else if (v.equals("false") || v.equals("0") || v.equals("not")) {
                statusFilter = Boolean.FALSE;
                statusParam = "false";
            } else {
                statusFilter = null;
                statusParam = "";
            }
        }

        EmployerDAO dao = new EmployerDAO();

        int total = dao.countAll(likeKeyword, statusFilter);

        int totalPages = (int) Math.ceil(total / (double) size);
        if (totalPages <= 0) {
            totalPages = 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        List<Employer> employers = dao.findPage(page, size, likeKeyword, statusFilter);

        req.setAttribute("q", qParam);
        req.setAttribute("status", statusParam);
        req.setAttribute("page", page);
        req.setAttribute("total", total);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("employers", employers);

        req.getRequestDispatcher("/admin/employer-list.jsp").forward(req, resp);
        
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

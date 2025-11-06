/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.employer;

import dal.BanDAO;
import dal.EmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import model.Ban;
import model.Employer;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="EmployerBanServlet", urlPatterns={"/admin/employers/ban"})
public class EmployerBanServlet extends HttpServlet {
   
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
            out.println("<title>Servlet EmployerBanServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerBanServlet at " + request.getContextPath () + "</h1>");
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

        HttpSession s = req.getSession(false);
        String role = s == null ? null : (String) s.getAttribute("role");
        if (!"Admin".equals(role)) {
            resp.sendRedirect(req.getContextPath()+"/access-denied.jsp");
            return;
        }

        int id;
        try { id = Integer.parseInt(req.getParameter("id")); }
        catch (Exception e) { resp.sendError(400, "Invalid employer id"); return; }

        EmployerDAO edao = new EmployerDAO();
        Employer emp = edao.findById(id); 
        if (emp == null) { resp.sendError(404, "Employer not found"); return; }

        try {
            BanDAO bdao = new BanDAO();
            Ban activeBan = bdao.getActiveBanForEmployer(id);
            req.setAttribute("employer", emp);
            req.setAttribute("activeBan", activeBan);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/admin/employer-ban.jsp").forward(req, resp);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

        HttpSession s = req.getSession(false);
        String role = s == null ? null : (String) s.getAttribute("role");
        if (!"Admin".equals(role)) {
            resp.sendRedirect(req.getContextPath()+"/access-denied.jsp");
            return;
        }

        int employerId;
        try { employerId = Integer.parseInt(req.getParameter("employerId")); }
        catch (Exception e) { resp.sendError(400, "Invalid employer id"); return; }

        String banType = req.getParameter("banType");    
        String untilStr = req.getParameter("bannedUntil");

        Instant untilUTC = null;
        if ("temp".equals(banType)) {
            untilUTC = BanDAO.parseBangkokLocalToUTC(untilStr);
            if (untilUTC == null) {
                req.setAttribute("error", "Vui lòng chọn thời điểm kết thúc cấm.");
                doGet(req, resp);
                return;
            }
        }

        try {
            BanDAO bdao = new BanDAO();
            bdao.insertForEmployer(employerId, untilUTC);
            String back = req.getParameter("backTo");
            if ("list".equals(back)) {
                resp.sendRedirect(req.getContextPath()+"/admin/employers?msg=ban_ok");
            } else {
                resp.sendRedirect(req.getContextPath()+"/admin/employers/ban?id="+employerId+"&msg=ban_ok");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
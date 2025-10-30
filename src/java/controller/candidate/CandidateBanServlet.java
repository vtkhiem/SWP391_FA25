/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.candidate;

import dal.BanDAO;
import dal.CandidateDAO;
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
import model.Candidate;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="CandidateBanServlet", urlPatterns={"/admin/candidates/ban"})
public class CandidateBanServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CandidateBanServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CandidateBanServlet at " + request.getContextPath () + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session == null) ? null : (String) session.getAttribute("role");
        if (role == null || !"Admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        String idRaw = request.getParameter("id");
        if (idRaw == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing candidate id");
            return;
        }

        int candidateId;
        try {
            candidateId = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid candidate id");
            return;
        }

        Candidate candidate = null;
        try {
            CandidateDAO cdao = new CandidateDAO();

            candidate = cdao.findById(candidateId);
        } catch (Exception ignore) {
            
        }

        if (candidate == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidate not found");
            return;
        }

        try {
            BanDAO bdao = new BanDAO();
            Ban activeBan = bdao.getActiveBanForCandidate(candidateId);
            request.setAttribute("candidate", candidate);
            request.setAttribute("activeBan", activeBan);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        request.getRequestDispatcher("/admin/candidate-ban.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);
        String role = (session == null) ? null : (String) session.getAttribute("role");
        if (role == null || !"Admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        String idRaw = request.getParameter("candidateId");
        String banType = request.getParameter("banType"); 
        String untilStr = request.getParameter("bannedUntil"); 

        int candidateId;
        try {
            candidateId = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid candidate id");
            return;
        }

        Instant untilUTC = null;
        if ("temp".equals(banType)) {
            untilUTC = BanDAO.parseBangkokLocalToUTC(untilStr);
            if (untilUTC == null) {
                request.setAttribute("error", "Vui lòng chọn thời điểm kết thúc cấm.");
                doGet(request, response);
                return;
            }
        } else {
            untilUTC = null;
        }

        try {
            BanDAO bdao = new BanDAO();

            bdao.insertForCandidate(candidateId, untilUTC);
            String back = request.getParameter("backTo");
            if (back != null && back.equals("list")) {
                response.sendRedirect(request.getContextPath() + "/admin/candidates?msg=ban_ok");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/candidates/ban?id=" + candidateId + "&msg=ban_ok");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Không thể tạo lệnh cấm: " + e.getMessage());
            doGet(request, response);
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

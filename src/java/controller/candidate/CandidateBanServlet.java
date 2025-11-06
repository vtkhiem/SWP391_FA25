/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<< HEAD

package controller.candidate;

import dal.BanDAO;
import dal.CandidateDAO;
=======
package controller.candidate;

import dal.CandidateDAO;
import dal.EmailBannedDAO;
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
<<<<<<< HEAD
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
=======
import model.Candidate;
import model.EmailBanned;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "CandidateBanServlet", urlPatterns = {"/candidateBan"})
public class CandidateBanServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD
    throws ServletException, IOException {
=======
            throws ServletException, IOException {
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
<<<<<<< HEAD
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
=======
            out.println("<title>Servlet CandidateBanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CandidateBanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD
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
=======
            throws ServletException, IOException {
   
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD
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
=======
            throws ServletException, IOException {
        HttpSession session = request.getSession();
      EmailBannedDAO ebdao = new EmailBannedDAO();
        EmailService es = new EmailService();
      
        String banReason = request.getParameter("banReason");
        int id = Integer.parseInt(request.getParameter("id"));
        CandidateDAO candao = new CandidateDAO();
       Candidate can = candao.getCandidateById(id);
        if (can.getEmail() == null || can.getEmail().isEmpty() || banReason == null || banReason.isEmpty()) {
            session.setAttribute("error", "Lỗi: Email và Lý do không được để trống.");
            response.sendRedirect("admin/candidates");
            return;
        }
      
    boolean success = ebdao.addBannedEmail(can.getEmail(),"Candidate",banReason);
    if(success){
         session.setAttribute("message", "Hạn chế tài khoản có "+can.getEmail()+" thành công");
         es.sendWarningToUser(can.getEmail(), banReason, "Candidate");
    }else{
         session.setAttribute("error", "Tài khoản "+can.getEmail()+" vai trò "+ "Candidate "+"đã bị hạn chế từ trước");
    }
      response.sendRedirect("admin/candidates");
    }

    /**
     * Returns a short description of the servlet.
     *
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

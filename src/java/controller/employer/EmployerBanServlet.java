/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<< HEAD

package controller.employer;

import dal.BanDAO;
=======
package controller.employer;

import dal.EmailBannedDAO;
>>>>>>> 4fcbea66ddbfcb5f56b0db6d0262b72ebd57ef46
import dal.EmployerDAO;
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
import model.Employer;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="EmployerBanServlet", urlPatterns={"/admin/employers/ban"})
public class EmployerBanServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
=======
import model.Employer;
import tool.EmailService;

/**
 *
 * @author vuthienkhiem
 */
@WebServlet(name = "EmployerBanServlet", urlPatterns = {"/employerBan"})
public class EmployerBanServlet extends HttpServlet {

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
=======
            out.println("<title>Servlet EmployerBanServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerBanServlet at " + request.getContextPath() + "</h1>");
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
<<<<<<< HEAD
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
=======
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
<<<<<<< HEAD
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
=======
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          HttpSession session = request.getSession();
      EmailBannedDAO ebdao = new EmailBannedDAO();
   EmailService es = new EmailService();       
        String banReason = request.getParameter("banReason");
        int id = Integer.parseInt(request.getParameter("id"));
        EmployerDAO candao = new EmployerDAO();
       String email = candao.getEmailByID(id);
        if (email== null ||email.isEmpty() || banReason == null || banReason.isEmpty()) {
            session.setAttribute("error", "Lỗi: Email và Lý do không được để trống.");
            response.sendRedirect("admin/employers");
            return;
        }
      
    boolean success = ebdao.addBannedEmail(email,"Employer",banReason);
    if(success){
         session.setAttribute("message", "Hạn chế tài khoản có "+email+" thành công");
          es.sendWarningToUser(email, banReason, "Employer");
    }else{
         session.setAttribute("error", "Tài khoản "+email+" vai trò "+ "Employer "+"đã bị hạn chế từ trước");
    }
      response.sendRedirect("admin/employers");
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

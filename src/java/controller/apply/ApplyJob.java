/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.apply;

import dal.ApplyDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.Apply;
import model.Candidate;
import tool.Validation;

/**
 *
 * @author shiro
 */
@WebServlet(name = "ApplyJob", urlPatterns = {"/applyJob"})
public class ApplyJob extends HttpServlet {

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
            out.println("<title>Servlet ApplyJob</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApplyJob at " + request.getContextPath() + "</h1>");
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
        doPost(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        
        int jobId = Validation.getId(request.getParameter("jobId"));
        int CVID = Validation.getId(request.getParameter("CVID"));
        Candidate candidate = (Candidate) session.getAttribute("user");
        
        if (jobId == 0 || CVID == 0) {
            session.setAttribute("error", "Lỗi không mong muốn đã xảy ra");
            response.sendRedirect(request.getContextPath() + "/jobs");
            return; // avoid “response already committed”
        }

        //tao apply 
        ApplyDAO dao = new ApplyDAO();
        LocalDateTime dayCreate = LocalDateTime.now();
        dao.insertApply(jobId, candidate.getCandidateId(), CVID, dayCreate, "Pending", "");
        session.setAttribute("message", "Ứng tuyển vào công việc thành công");
        response.sendRedirect(request.getContextPath() + "/jobs");
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

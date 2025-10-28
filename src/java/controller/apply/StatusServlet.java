/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.apply;

import dal.ApplyDAO;
import dal.JobPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Apply;
import model.ApplyDetail;
import model.CV;
import model.Candidate;
import model.JobPost;

/**
 *
 * @author shiro
 */
@WebServlet(name = "StatusServlet", urlPatterns = {"/status"})
public class StatusServlet extends HttpServlet {

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
            out.println("<title>Servlet StatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatusServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        
        response.setContentType("text/plain;charset=UTF-8");

        String applyIdStr = request.getParameter("applyId");
        String newStatus = request.getParameter("status");
        
        System.out.println("applyIdStr=" + applyIdStr + ", newStatus=" + newStatus);

        if (applyIdStr != null && !applyIdStr.isEmpty() && newStatus != null) {
            try {
                int applyId = Integer.parseInt(applyIdStr);
                ApplyDAO dao = new ApplyDAO();

                Apply apply = dao.getApplyById(applyId);
                if (apply != null) {
                    dao.updateApplyStatus(applyId, newStatus);

                    response.getWriter().write("SUCCESS: Status updated to " + newStatus);
                } else {
                    response.getWriter().write("ERROR: Apply not found");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("ERROR: Invalid applyId");
            }
        } else {
            response.getWriter().write("ERROR: Missing parameters");
        }
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

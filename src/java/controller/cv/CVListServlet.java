/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.cv;

import dal.CVDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import model.CV;
import model.Candidate;

/**
 *
 * @author Admin
 */
@WebServlet(name="CVListServlet", urlPatterns={"/cv-list"})
public class CVListServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CVListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CVListServlet at " + request.getContextPath () + "</h1>");
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
        try {
            HttpSession session = request.getSession();
            Object userObj = session.getAttribute("user");

            // Kiểm tra user đăng nhập và phải là Candidate
            if (userObj == null || !(userObj instanceof Candidate)) {
                response.sendRedirect("login.jsp");
                return;
            }

            Candidate candidate = (Candidate) userObj;
            int candidateId = candidate.getCandidateId();
   
            // Debug log
            System.out.println("Loading CVs for Candidate ID: " + candidateId);
            
            CVDAO cvDao = new CVDAO();
            // Chỉ lấy CV của candidate hiện tại
            List<CV> cvList = cvDao.getCVsByCandidate(candidateId);

            // Debug logs
       
            
            // Add list to request
            request.setAttribute("cvList", cvList);
            request.setAttribute("candidateId", candidateId); // Thêm candidateId vào request

            request.getRequestDispatcher("cv-management.jsp").forward(request, response);
            
            
      

        } catch (Exception e) {
            System.out.println("Error in CVListServlet: " + e.getMessage());
            e.printStackTrace();

        }
    
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

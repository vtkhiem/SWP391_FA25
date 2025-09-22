/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
 * @author ADMIN
 */
@WebServlet(name = "CVListServlet", urlPatterns = {"/cv-list"})
public class CVListServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
            System.out.println("Found " + (cvList != null ? cvList.size() : 0) + " CVs for candidate");
            
            // Add list to request
            request.setAttribute("cvList", cvList);
            request.setAttribute("candidateId", candidateId); // Thêm candidateId vào request

            request.getRequestDispatcher("cv-management.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error in CVListServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}

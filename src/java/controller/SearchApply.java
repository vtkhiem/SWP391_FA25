/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
@WebServlet(name = "SearchApply", urlPatterns = {"/searchApply"})
public class SearchApply extends HttpServlet {

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
            out.println("<title>Servlet SearchApply</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchApply at " + request.getContextPath() + "</h1>");
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
        //cho phep search Tieng Viet
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String txtSearch = request.getParameter("txt");
        ApplyDAO dao = new ApplyDAO();
        List<Apply> foundList = dao.searchApplyByCandidateName(txtSearch);
        List<ApplyDetail> details = new ArrayList<>();

        JobPostDAO jdao = new JobPostDAO();
        List<JobPost> jobs = jdao.getAllJobPosts();

        for (Apply apply : foundList) {
            Candidate can = dao.getCandidateById(apply.getCandidateId());
            CV cv = dao.getCVById(apply.getCvId());
            JobPost job = dao.getJobPostById(apply.getJobPostId());
            details.add(new ApplyDetail(apply, can, cv, job));
        }

        request.setAttribute("jobList", jobs);
        request.setAttribute("sValue", txtSearch);
        request.setAttribute("applyDetails", details);

        request.getRequestDispatcher("apply.jsp").forward(request, response);
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

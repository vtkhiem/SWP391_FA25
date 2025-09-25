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
        String applyIdStr = request.getParameter("applyId");
        String newStatus = request.getParameter("status");

        if (applyIdStr != null && !applyIdStr.isEmpty() && newStatus != null) {
            try {
                int applyId = Integer.parseInt(applyIdStr);
                ApplyDAO dao = new ApplyDAO();

                // Lấy apply hiện tại
                model.Apply apply = dao.getApplyById(applyId);
                if (apply != null) {
                    apply.setStatus(newStatus);  // Cập nhật status mới
                    dao.updateApply(apply);      // Gọi DAO để update DB
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String jobIdStr = request.getParameter("jobId");

        if (jobIdStr != null && !jobIdStr.isEmpty()) {
            int jobId = Integer.parseInt(jobIdStr);
            ApplyDAO dao = new ApplyDAO();
            JobPostDAO jdao = new JobPostDAO();

            List<Apply> applies = dao.getApplyByJobPost(jobId);
            List<ApplyDetail> details = new ArrayList<>();

            for (Apply apply : applies) {
                Candidate can = dao.getCandidateById(apply.getCandidateId());
                CV cv = dao.getCVById(apply.getCvId());
                JobPost job = dao.getJobPostById(apply.getJobPostId());
                details.add(new ApplyDetail(apply, can, cv, job));
            }

            List<JobPost> jobs = jdao.getAllJobPosts();
            request.setAttribute("jobList", jobs);
            request.setAttribute("applyDetails", details);
            request.getRequestDispatcher("apply.jsp").forward(request, response);
        } else {
            // Nếu không có jobId, redirect về trang job list
            response.sendRedirect("viewApply");
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

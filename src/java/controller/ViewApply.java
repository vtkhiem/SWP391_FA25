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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Apply;
import model.ApplyDetail;
import model.CV;
import model.Candidate;
import model.Employer;
import model.JobPost;

/**
 *
 * @author shiro
 */
@WebServlet(name = "ViewApply", urlPatterns = {"/viewApply"})
public class ViewApply extends HttpServlet {

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
            out.println("<title>Servlet ViewApply</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewApply at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null
                || !"Employer".equals(session.getAttribute("role"))) {
            // Nếu chưa login hoặc không phải employer thì chặn lại
            response.sendRedirect("login.jsp");
            return;
        }
        // Lấy employer từ session
        Employer employer = (Employer) session.getAttribute("user");
        int employerId = employer.getEmployerId();

        String jobIdStr = request.getParameter("id");

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

            request.setAttribute("details", details);
            request.getRequestDispatcher("apply.jsp").forward(request, response);

        } else {
            // Nếu không có jobId, redirect về trang job list
            response.sendRedirect("employer_jobs");
        }

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
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("user") == null
                    || !"Employer".equals(session.getAttribute("role"))) {
                // Nếu chưa login hoặc không phải employer thì chặn lại
                response.sendRedirect("login.jsp");
                return;
            }
            // Lấy employer từ session
            Employer employer = (Employer) session.getAttribute("user");
            int employerId = employer.getEmployerId();

            // Gọi DAO để lấy job theo employerId
            List<JobPost> jobs = jdao.getJobsByEmployer(employerId);

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

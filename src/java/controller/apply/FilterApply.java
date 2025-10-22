/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.apply;

import dal.ApplyDAO;
import dal.CVDAO;
import dal.CandidateDAO;
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
import tool.Validation;

/**
 *
 * @author shiro
 */
@WebServlet(name = "FilterApply", urlPatterns = {"/filterApply"})
public class FilterApply extends HttpServlet {

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
            out.println("<title>Servlet FilterApply</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterApply at " + request.getContextPath() + "</h1>");
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
        String txt = Validation.searchKey(request.getParameter("txt"));
        String exp = request.getParameter("exp");
        String status = request.getParameter("status");

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);

        int jobId = Validation.getId(request.getParameter("jobId"));
        Employer employer = (Employer) session.getAttribute("user");
        int page = Validation.getPage(request.getParameter("page"));

        if (jobId == 0) {
            response.sendRedirect("employer_jobs");
            return; // avoid “response already committed”
        }

        ApplyDAO dao = new ApplyDAO();
        JobPostDAO jdao = new JobPostDAO();
        CandidateDAO cdao = new CandidateDAO();
        CVDAO cvdao = new CVDAO();

        int recordsPerPage = 10;
        int offSet = (page - 1) * recordsPerPage;

        List<Apply> applies = dao.filterApply(jobId, employer.getEmployerId(), Validation.searchKey(txt), status, exp, offSet, recordsPerPage);
        List<ApplyDetail> details = new ArrayList<>();

        for (Apply apply : applies) {
            Candidate can = cdao.getCandidateById(apply.getCandidateId());
            CV cv = cvdao.getCVById(apply.getCvId());
            JobPost job = jdao.getJobPostById(apply.getJobPostId());
            details.add(new ApplyDetail(apply, can, cv, job));
        }
        int totalRecords = details.size();
        int totalPage = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        
        request.setAttribute("txt", txt);
        request.setAttribute("exp", exp);
        request.setAttribute("status", status);
        request.setAttribute("details", details);
        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", totalPage);
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

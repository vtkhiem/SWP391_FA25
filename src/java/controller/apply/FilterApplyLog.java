/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.apply;

import dal.ApplyDAO;
import dal.CVDAO;
import dal.CandidateDAO;
import dal.EmployerDAO;
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
@WebServlet(name = "FilterApplyLog", urlPatterns = {"/filterApplyLog"})
public class FilterApplyLog extends HttpServlet {

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
            out.println("<title>Servlet FilterApplyLog</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterApplyLog at " + request.getContextPath() + "</h1>");
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
        String txt = Validation.searchKey(request.getParameter("txt"));
        String category = request.getParameter("catergory");
        String offerMin= request.getParameter("min");
        String offerMax = request.getParameter("max");
        String status = request.getParameter("status");
        String dayOrder = request.getParameter("dayOrder");

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();


        Candidate candidate = (Candidate) session.getAttribute("user");
        int candidateId = candidate.getCandidateId();
        int page = Validation.getPage(request.getParameter("page"));


        ApplyDAO dao = new ApplyDAO();
        JobPostDAO jdao = new JobPostDAO();
        CandidateDAO cdao = new CandidateDAO();
        CVDAO cvdao = new CVDAO();
        EmployerDAO edao = new EmployerDAO();

        int recordsPerPage = 10;
        int offSet = (page - 1) * recordsPerPage;

        List<Apply> applies = dao.filterApplyLog(candidateId, Validation.searchKey(txt),Validation.getId(category), status, dayOrder, offSet, recordsPerPage);
        List<ApplyDetail> details = new ArrayList<>();

        for (Apply apply : applies) {
            Candidate can = cdao.getCandidateById(apply.getCandidateId());
            CV cv = cvdao.getCVById(apply.getCvId());
            JobPost job = jdao.getJobPostById(apply.getJobPostId());
            Employer employer = edao.findById(job.getEmployerID());
            details.add(new ApplyDetail(apply, can, cv, job, employer));
        }
        int totalRecords = dao.countFilteredApplyLog(candidateId, txt, Validation.getId(category), status);
        int noOfPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);


        request.setAttribute("txt", txt);
        request.setAttribute("category", category);
        request.setAttribute("status", status);
        request.setAttribute("dayOrder", dayOrder);
        request.setAttribute("details", details);
        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);
        request.getRequestDispatcher("candidate-status-log.jsp").forward(request, response);
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
        processRequest(request, response);
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

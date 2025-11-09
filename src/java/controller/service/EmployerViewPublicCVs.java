/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.service;

import dal.CVDAO;
import dal.CandidateDAO;
import dal.ServiceEmployerDAO;
import dal.ServiceFunctionDAO;
import dal.WallDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import model.CV;
import model.Candidate;
import model.Employer;
import model.Function;

/**
 *
 * @author shiro
 */
@WebServlet(name = "EmployerViewPublicCVs", urlPatterns = {"/viewPublicCVs"})
public class EmployerViewPublicCVs extends HttpServlet {

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
            out.println("<title>Servlet employerCandidates</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet employerCandidates at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");

        Employer employer = (Employer) session.getAttribute("user");

        try {
            ServiceEmployerDAO sedao = new ServiceEmployerDAO();
            ServiceFunctionDAO sfdao = new ServiceFunctionDAO();

            int serviceId = sedao.getCurrentServiceByEmployerId(employer.getEmployerId());
            List<Function> list = sfdao.getFunctionsByServiceId(serviceId);

            boolean hasSearchCandidateFunction = false;
            for (Function f : list) {
                if (f.getFunctionName().equalsIgnoreCase("SearchCandidate")) {
                    hasSearchCandidateFunction = true;
                    break;
                }
            }

            if (hasSearchCandidateFunction) {

                int pageSize = 10;
                int page = 1;

                try {
                    String p = request.getParameter("page");
                    if (p != null) {
                        page = Math.max(1, Integer.parseInt(p));
                    }
                } catch (NumberFormatException ignored) {
                }

                CVDAO cvdao = new CVDAO();

                int total = cvdao.countPublicCVs();
                int totalPages = (int) Math.ceil(total / (double) pageSize);
                if (totalPages == 0) {
                    totalPages = 1;
                }
                if (page > totalPages) {
                    page = totalPages;
                }

                int recordsPerPage = 10;
                int offset = (page - 1) * recordsPerPage;

                List<CV> cvs = cvdao.getPublicCVs(offset, recordsPerPage);

                request.setAttribute("cvs", cvs);
                request.setAttribute("page", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("total", total);

                request.getRequestDispatcher("/employer-view-candidate.jsp")
                        .forward(request, response);

                return; // ✅ CHẶN KHÔNG CHO XUỐNG redirect
            }

            // Không có quyền
            request.getSession().setAttribute("error",
                    "Dịch vụ hiện tại của bạn không hỗ trợ tìm ứng viên");
            response.sendRedirect("employer_jobs");
            return;

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi không mong muốn đã xảy ra");
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

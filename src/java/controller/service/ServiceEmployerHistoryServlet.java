package controller.service;

import dal.ServiceEmployerHistoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Employer;
import model.ServiceEmployerHistory;

@WebServlet(urlPatterns={"/payments_history"})
public class ServiceEmployerHistoryServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceEmployerHistoryServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceEmployerHistoryServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employer employer = (Employer) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employer == null || !"Employer".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login-employer.jsp");
            return;
        }

        ServiceEmployerHistoryDAO serviceEmployerHistoryDAO = new ServiceEmployerHistoryDAO();
        int page = 1;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }
        int recordsPerPage = 10;
        int offset = (page - 1) * recordsPerPage;
        
        List<ServiceEmployerHistory> payments = serviceEmployerHistoryDAO.getServiceEmployerHistoryByEmployer(employer.getEmployerId(), offset, recordsPerPage);
        int totalRecords = serviceEmployerHistoryDAO.countServiceEmployerHistoryByEmployer(employer.getEmployerId());
        int noOfPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        
        request.setAttribute("payments", payments);
        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);
        request.getRequestDispatcher("employer_payment_history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
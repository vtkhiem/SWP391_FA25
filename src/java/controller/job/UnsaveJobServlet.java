package controller.job;

import dal.SavedJobDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Candidate;
import model.SavedJob;

@WebServlet(name = "UnsaveJobServlet", urlPatterns = {"/unsave_job"})
public class UnsaveJobServlet extends HttpServlet {
    private SavedJobDAO savedJobDAO = new SavedJobDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UnsaveJobServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UnsaveJobServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");
        String role = (String) session.getAttribute("role");
        
        if (candidate == null || !"Candidate".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        try {
            int jobId = Integer.parseInt(request.getParameter("jobId"));
            SavedJob savedJob = savedJobDAO.getSavedJobByID(candidate.getCandidateId(), jobId);
            
            if (savedJob == null) {
                session.setAttribute("error", "Không tìm thấy công việc cần xoá.");
                response.sendRedirect(request.getContextPath() + "/saved_jobs");
                return;
            }
            
            if (candidate.getCandidateId() != savedJob.getCandidateID()) {
                session.setAttribute("error", "Bạn không có quyền xoá việc làm khỏi danh sách yêu thích.");
                response.sendRedirect(request.getContextPath() + "/saved_jobs");
                return;
            }
            
            boolean deleted = savedJobDAO.removeSavedJob(candidate.getCandidateId(), jobId);
        
            if (deleted) {
                session.setAttribute("message", "Xoá công việc khỏi danh sách yêu thích thành công!");
            } else {
                session.setAttribute("error", "Xoá công việc thất bại. Vui lòng thử lại.");
            }
        
            response.sendRedirect(request.getContextPath() + "/saved_jobs");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Có lỗi xảy ra khi thực hiện.");
            response.sendRedirect(request.getContextPath() + "/saved_jobs");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
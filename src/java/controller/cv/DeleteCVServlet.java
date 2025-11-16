package controller.cv;

import dal.CVDAO;
import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CV;
import model.Candidate;

@WebServlet(name="DeleteCVServlet", urlPatterns={"/delete-cv"})
public class DeleteCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");

        if (userObj == null || !(userObj instanceof Candidate)) {
            session.setAttribute("error", "You must log in as a candidate to delete a CV.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        Candidate candidate = (Candidate) userObj;

        int cvId;
        try {
            cvId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid CV ID.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        CVDAO cvDao = new CVDAO();
        CV cv = cvDao.getCVById(cvId);

        if (cv == null || cv.getCandidateID() != candidate.getCandidateId()) {
            session.setAttribute("error", "Access denied or CV not found.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        if (cvDao.hasApplied(cvId)) {
            session.setAttribute("error", "Cannot delete CV that has already been applied.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        // Xóa file CV
        if (cv.getFileData() != null) {
            String realPath = getServletContext().getRealPath("") + File.separator + cv.getFileData();
            File file = new File(realPath);
            if (file.exists()) file.delete();
        }

        // Xóa record trong database
        boolean deleted = cvDao.deleteCV(cvId);
        if (deleted) {
            session.setAttribute("message", "CV deleted successfully.");
        } else {
            session.setAttribute("error", "Failed to delete CV.");
        }

        response.sendRedirect(request.getContextPath() + "/list-cv");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response); // Xử lý giống GET
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting CV with applied check";
    }
}

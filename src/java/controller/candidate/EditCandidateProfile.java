package controller.candidate;

import dal.CandidateDAO;
import dal.RegisterCandidateDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Candidate;
import tool.Validation;

@WebServlet(name = "EditCandidateProfile", urlPatterns = {"/editCandidateProfile"})
public class EditCandidateProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Candidate candidate = (Candidate) session.getAttribute("user");


        // Lấy dữ liệu từ form
        String candidateName = request.getParameter("candidateName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String nationality = request.getParameter("nationality");
        boolean isPublic = request.getParameter("isPublic") != null;

        // Kiểm tra dữ liệu đầu vào
        if (candidateName == null || candidateName.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || nationality == null || nationality.trim().isEmpty()) {
            session.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            response.sendRedirect("candidateProfile");
            return;
        }

        if (!Validation.isValidPhone(phoneNumber)) {
            session.setAttribute("error", "Số điện thoại không hợp lệ.");
            response.sendRedirect("candidateProfile");
            return;
        }

        // Cập nhật đối tượng candidate hiện tại
        candidate.setCandidateName(candidateName.trim());
        candidate.setPhoneNumber(phoneNumber.trim());
        candidate.setAddress(address.trim());
        candidate.setNationality(nationality.trim());
        candidate.setIsPublic(isPublic);

        try {
            RegisterCandidateDAO dao = new RegisterCandidateDAO();
            CandidateDAO canDAO = new CandidateDAO();
            boolean updated = canDAO.updateCandidateProfile(candidate);

            if (updated) {
                // Cập nhật lại thông tin trong session
                session.setAttribute("user", candidate);
                session.setAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                session.setAttribute("error", "Không thể cập nhật thông tin. Vui lòng thử lại.");
            }

            // Forward về profile.jsp (thông qua ProfileServlet)
            response.sendRedirect("candidateProfile");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi trong quá trình cập nhật.");
            response.sendRedirect("candidateProfile");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet dùng để chỉnh sửa thông tin hồ sơ ứng viên";
    }
}

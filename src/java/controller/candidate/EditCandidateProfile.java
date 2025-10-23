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
        HttpSession session = request.getSession(false);

        request.getRequestDispatcher("/profile").forward(request, response);

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

        // Kiểm tra dữ liệu đầu vào
        if (candidateName == null || candidateName.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || nationality == null || nationality.trim().isEmpty()) {

            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("/profile").forward(request, response);
            return;
        }

        if (!Validation.isValidPhone(phoneNumber)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ.");
            request.getRequestDispatcher("/profile").forward(request, response);
            return;
        }

        // Cập nhật đối tượng candidate hiện tại
        candidate.setCandidateName(candidateName.trim());
        candidate.setPhoneNumber(phoneNumber.trim());
        candidate.setAddress(address.trim());
        candidate.setNationality(nationality.trim());

        try {
            RegisterCandidateDAO dao = new RegisterCandidateDAO();
            CandidateDAO canDAO = new CandidateDAO();
            boolean updated = canDAO.updateCandidateProfile(candidate);

            if (updated) {
                // Cập nhật lại thông tin trong session
                session.setAttribute("user", candidate);
                session.setAttribute("candidate", candidate);
                request.setAttribute("success", "Cập nhật thông tin thành công!");
            } else {
                request.setAttribute("error", "Không thể cập nhật thông tin. Vui lòng thử lại.");
            }

            // Forward về profile.jsp (thông qua ProfileServlet)
            request.getRequestDispatcher("/profile").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình cập nhật.");
            request.getRequestDispatcher("/profile").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet dùng để chỉnh sửa thông tin hồ sơ ứng viên";
    }
}

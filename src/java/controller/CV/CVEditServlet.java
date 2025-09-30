package controller.CV;

import dal.CVDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import model.CV;
import model.Candidate;
import jakarta.servlet.annotation.MultipartConfig;

@WebServlet(name = "CVEditServlet", urlPatterns = {"/edit-cv"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB: lưu file tạm trong RAM
        maxFileSize = 1024 * 1024 * 10, // 10MB: giới hạn dung lượng file
        maxRequestSize = 1024 * 1024 * 50 // 50MB: tổng dung lượng request
)
public class CVEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cvid = Integer.parseInt(request.getParameter("id"));
            CVDAO cvDao = new CVDAO();
            CV cv = cvDao.getCVById(cvid);

            if (cv != null) {
                HttpSession session = request.getSession();
                Candidate candidate = (Candidate) session.getAttribute("user");

                if (candidate != null && cv.getCandidateID() == candidate.getCandidateId()) {
                    request.setAttribute("cv", cv);
                    request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CV ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
    CVDAO cvDao = new CVDAO();
    try {
        request.setCharacterEncoding("UTF-8");
        int cvid = Integer.parseInt(request.getParameter("id"));

        CV existingCV = cvDao.getCVById(cvid);

        // Kiểm tra quyền
        HttpSession session = request.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");

        if (existingCV == null || candidate == null
                || existingCV.getCandidateID() != candidate.getCandidateId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        // ================= Lấy dữ liệu từ form =================
        existingCV.setFullName(request.getParameter("fullName"));
        existingCV.setEmail(request.getParameter("email"));
        existingCV.setAddress(request.getParameter("address"));
        existingCV.setPosition(request.getParameter("position"));
        existingCV.setEducation(request.getParameter("education"));
        existingCV.setField(request.getParameter("field"));
        existingCV.setNationality(request.getParameter("nationality"));
        existingCV.setGender(request.getParameter("gender"));

        String numberExpStr = request.getParameter("numberExp");
        if (numberExpStr != null && !numberExpStr.isEmpty()) {
            existingCV.setNumberExp(Integer.parseInt(numberExpStr));
        } else {
            existingCV.setNumberExp(0);
        }

        String currentSalaryStr = request.getParameter("currentSalary");
        if (currentSalaryStr != null && !currentSalaryStr.isEmpty()) {
            existingCV.setCurrentSalary(new java.math.BigDecimal(currentSalaryStr));
        } else {
            existingCV.setCurrentSalary(null);
        }

        String birthdayStr = request.getParameter("birthday");
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            existingCV.setBirthday(java.sql.Date.valueOf(birthdayStr));
        }

        // ================= Xử lý file upload =================
        Part filePart = request.getPart("cvFile");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_"
                    + filePart.getSubmittedFileName().replaceAll("\\s+", "_");

            String uploadPath = getServletContext().getRealPath("")
                    + File.separator + "uploads/cv_files";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = "uploads/cv_files" + File.separator + fileName;

            try (InputStream input = filePart.getInputStream();
                 OutputStream output = new FileOutputStream(uploadPath + File.separator + fileName)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }

            // Xóa file cũ (nếu có)
            if (existingCV.getFileData() != null) {
                File oldFile = new File(getServletContext().getRealPath("")
                        + File.separator + existingCV.getFileData());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            existingCV.setFileData(filePath);
        }

        // ================= Update DB =================
        boolean updated = cvDao.updateFullCV(existingCV);
        if (updated) {
            response.sendRedirect("cv-list");
        } else {
            request.setAttribute("error", "Failed to update CV");
            request.setAttribute("cv", existingCV);
            request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
        }

    } catch (NumberFormatException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CV ID");
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "An error occurred while updating CV.");
        request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
    }
    }

    @Override
    public String getServletInfo() {
        return "Edit only Full Name of CV";
    }
}

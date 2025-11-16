package controller.cv;

import dal.CVDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.CV;
import model.Candidate;
import tool.Validation;

@WebServlet(name = "EditCVServlet", urlPatterns = {"/edit-cv"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB lưu tạm
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB tổng
)
public class EditCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cvid = Validation.getId(request.getParameter("id"));
        if (cvid <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CV ID");
            return;
        }

        CVDAO cvDao = new CVDAO();
        CV cv = cvDao.getCVById(cvid);
        HttpSession session = request.getSession();

        if (cv == null) {
            session.setAttribute("error", "CV not found");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        Candidate candidate = (Candidate) session.getAttribute("user");
        if (candidate == null || cv.getCandidateID() != candidate.getCandidateId()) {
            session.setAttribute("error", "Access denied");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        if (cvDao.hasApplied(cvid)) {
            session.setAttribute("error", "Cannot edit CV that has already been applied.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        request.setAttribute("cv", cv);
        request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        int cvid = Validation.getId(request.getParameter("cvid"));
        HttpSession session = request.getSession();
        CVDAO cvDao = new CVDAO();
        CV existingCV = cvDao.getCVById(cvid);

        Candidate candidate = (Candidate) session.getAttribute("user");
        if (existingCV == null || candidate == null || existingCV.getCandidateID() != candidate.getCandidateId()) {
            session.setAttribute("error", "Access denied");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        if (cvDao.hasApplied(cvid)) {
            session.setAttribute("error", "Cannot edit CV that has already been applied.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
            return;
        }

        try {
            existingCV.setFullName(request.getParameter("fullName"));
            existingCV.setEmail(request.getParameter("email"));
            existingCV.setAddress(request.getParameter("address"));
            existingCV.setPosition(request.getParameter("position"));
            existingCV.setEducation(request.getParameter("education"));
            existingCV.setField(request.getParameter("field"));
            existingCV.setNationality(request.getParameter("nationality"));
            existingCV.setGender(request.getParameter("gender"));

            String expStr = request.getParameter("numberExp");
            existingCV.setNumberExp((expStr != null && !expStr.isEmpty()) ? Integer.parseInt(expStr) : 0);

            String salaryStr = request.getParameter("currentSalary");
            existingCV.setCurrentSalary((salaryStr != null && !salaryStr.isEmpty()) ? new BigDecimal(salaryStr) : null);

            String birthdayStr = request.getParameter("birthday");
            existingCV.setBirthday((birthdayStr != null && !birthdayStr.isEmpty()) ? Date.valueOf(birthdayStr) : null);

            // File upload
            Part filePart = request.getPart("cvFile");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName().replaceAll("\\s+", "_");
                String uploadDirPath = getServletContext().getRealPath("") + File.separator + "uploads/cv_files";
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String filePath = "uploads/cv_files" + File.separator + fileName;

                try (InputStream input = filePart.getInputStream();
                     OutputStream output = new FileOutputStream(uploadDirPath + File.separator + fileName)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }

                // Xóa file cũ
                if (existingCV.getFileData() != null) {
                    File oldFile = new File(getServletContext().getRealPath("") + File.separator + existingCV.getFileData());
                    if (oldFile.exists()) oldFile.delete();
                }

                existingCV.setFileData(filePath);
            }

            boolean updated = cvDao.updateFullCV(existingCV);
            if (updated) {
                session.setAttribute("message", "CV updated successfully.");
                response.sendRedirect(request.getContextPath() + "/list-cv");
            } else {
                session.setAttribute("error", "Failed to update CV.");
                response.sendRedirect(request.getContextPath() + "/list-cv");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred while updating CV.");
            response.sendRedirect(request.getContextPath() + "/list-cv");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing CV with applied check";
    }
}

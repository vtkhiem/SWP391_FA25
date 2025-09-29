/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.cv;

import dal.CVDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.math.BigDecimal;
import model.CV;
import model.Candidate;

@WebServlet(name = "CVEditServlet", urlPatterns = {"/edit-cv"})
public class CVEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cvId = Integer.parseInt(request.getParameter("id"));
            if(cvId>22){
                response.sendRedirect("access-denied.jsp");
                return;
            }
            CVDAO cvDao = new CVDAO();
            CV cv = cvDao.getCVById(cvId);

            if (cv != null) {
                // Kiểm tra quyền truy cập
                HttpSession session = request.getSession();
                Candidate candidate = (Candidate) session.getAttribute("user");
                if (candidate != null && cv.getCandidateId() == candidate.getCandidateId()) {
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
        try {
            request.setCharacterEncoding("UTF-8");

            // Get CV ID and validate
            int cvId = Integer.parseInt(request.getParameter("CVID"));
            CVDAO cvDao = new CVDAO();
            CV existingCV = cvDao.getCVById(cvId);

            // Security check - ensure user owns this CV
            HttpSession session = request.getSession();
            Candidate candidate = (Candidate) session.getAttribute("user");
            if (existingCV == null || candidate == null
                    || existingCV.getCandidateId() != candidate.getCandidateId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            // Create updated CV object
            CV updatedCV = new CV();
            updatedCV.setCvId(cvId);
            updatedCV.setCandidateId(existingCV.getCandidateId());
            updatedCV.setFullName(request.getParameter("fullName"));
            updatedCV.setAddress(request.getParameter("address"));
            updatedCV.setEmail(request.getParameter("email"));
            updatedCV.setPosition(request.getParameter("position"));
            updatedCV.setNumberExp(Integer.parseInt(request.getParameter("numberExp")));
            updatedCV.setEducation(request.getParameter("education"));
            updatedCV.setField(request.getParameter("field"));
            updatedCV.setCurrentSalary(new BigDecimal(request.getParameter("currentSalary")));

            String birthdayStr = request.getParameter("birthday");
            if (birthdayStr != null && !birthdayStr.isEmpty()) {
                updatedCV.setBirthday(java.sql.Date.valueOf(birthdayStr));
            }

            updatedCV.setNationality(request.getParameter("nationality"));
            updatedCV.setGender(request.getParameter("gender"));

            // Keep existing file data if no new file uploaded
            updatedCV.setFileData(existingCV.getFileData());
            

            // Handle file upload if new file is provided
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

                // Save new file
                try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(uploadPath + File.separator + fileName)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }

                    // Delete old file if exists
                    if (existingCV.getFileData() != null) {
                        File oldFile = new File(getServletContext().getRealPath("")
                                + File.separator + existingCV.getFileData());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }

                    updatedCV.setFileData(filePath);
                    
                }
            }

            // Update CV in database
            boolean updated = cvDao.updateCV(updatedCV);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/cv-list");
            } else {
                request.setAttribute("error", "Failed to update CV");
                request.setAttribute("cv", updatedCV);
                request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println("Error updating CV: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the CV");
            request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
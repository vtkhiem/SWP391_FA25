/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.CV;

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
import jakarta.servlet.annotation.MultipartConfig;

@WebServlet(name = "CVEditServlet", urlPatterns = {"/edit-cv"})
public class CVEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cvid = Integer.parseInt(request.getParameter("id"));

            CVDAO cvDao = new CVDAO();
            CV cv = cvDao.getCVById(cvid);

            if (cv != null) {
                // Kiểm tra quyền truy cập
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

        // Khai báo ngoài scope để khối catch có thể truy cập
        CV existingCV = null;
        CV updatedCV = new CV();
        CVDAO cvDao = new CVDAO(); // Khởi tạo DAO ở đây cho tiện dùng

        try {
            request.setCharacterEncoding("UTF-8");

                int cvid = 0;
            try {
                System.out.println("--- Bắt đầu Khối 1: Lấy ID và Security Check ---");

                // 1. Xử lý CVID an toàn
                String cvidStr = request.getParameter("id");

                if (cvidStr == null || cvidStr.isEmpty()) {
                    // Nếu thiếu CVID, ném lỗi để khối catch chính bắt
                    throw new NumberFormatException("id parameter is missing or empty.");
                }

                // 2. Parse CVID và fetch existingCV
                cvid = Integer.parseInt(cvidStr);
                existingCV = cvDao.getCVById(cvid);

                // 3. Security check
                HttpSession session = request.getSession();
                Candidate candidate = (Candidate) session.getAttribute("user");

                if (existingCV == null || candidate == null
                        || existingCV.getCandidateID() != candidate.getCandidateId()) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;
                }

                // 4. Gán các giá trị ban đầu cho updatedCV
                updatedCV.setCVID(cvid);
                updatedCV.setCandidateID(existingCV.getCandidateID());
                updatedCV.setFileData(existingCV.getFileData()); // Giữ file cũ

                System.out.println("--- Khối 1 hoàn thành thành công ---");
            } catch (Exception e) {
                // Chỉ cần một catch block để xử lý lỗi trong Khối 1 và ném ra
                System.err.println("❌ LỖI KHỐI 1 (CVID/Security): " + e.getMessage());
                throw e;
            }

            // =================================================================
            // KHỐI 2: Lấy dữ liệu String & Number/Date (Dễ lỗi NumberFormatException)
            // =================================================================
            try {
                System.out.println("--- Bắt đầu Khối 2: Lấy dữ liệu form ---");
                updatedCV.setFullName(request.getParameter("fullName"));
                updatedCV.setAddress(request.getParameter("address"));
                updatedCV.setEmail(request.getParameter("email"));
                updatedCV.setPosition(request.getParameter("position"));
                updatedCV.setEducation(request.getParameter("education"));
                updatedCV.setField(request.getParameter("field"));
                updatedCV.setNationality(request.getParameter("nationality"));
                updatedCV.setGender(request.getParameter("gender"));

                // NumberExp (Integer)
                String numberExpStr = request.getParameter("numberExp");
                if (numberExpStr != null && !numberExpStr.isEmpty()) {
                    updatedCV.setNumberExp(Integer.parseInt(numberExpStr));
                } else {
                    updatedCV.setNumberExp(0);
                }

                // CurrentSalary (BigDecimal)
                String currentSalaryStr = request.getParameter("currentSalary");
                if (currentSalaryStr != null && !currentSalaryStr.isEmpty()) {
                    updatedCV.setCurrentSalary(new BigDecimal(currentSalaryStr));
                } else {
                    updatedCV.setCurrentSalary(null);
                } // Nên là null nếu rỗng/không nhập

                // Birthday (Date)
                String birthdayStr = request.getParameter("birthday");
                if (birthdayStr != null && !birthdayStr.isEmpty()) {
                    updatedCV.setBirthday(java.sql.Date.valueOf(birthdayStr));
                }
                System.out.println("--- Khối 2 hoàn thành thành công ---");
            } catch (Exception e) {
                System.err.println("❌ LỖI KHỐI 2 (Number/Date/String Parsing): " + e.getMessage());
                throw e;
            }

            // =================================================================
            // KHỐI 3: Xử lý File Upload (Dễ lỗi IOException/IllegalStateException)
            // =================================================================
            try {
                System.out.println("--- Bắt đầu Khối 3: Xử lý File Upload ---");
                Part filePart = request.getPart("cvFile");
                if (filePart != null && filePart.getSize() > 0) {

                    // Logic xử lý file...
                    String fileName = System.currentTimeMillis() + "_"
                            + filePart.getSubmittedFileName().replaceAll("\\s+", "_");
                    String uploadPath = getServletContext().getRealPath("")
                            + File.separator + "uploads/cv_files";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String filePath = "uploads/cv_files" + File.separator + fileName;

                    try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(uploadPath + File.separator + fileName)) {

                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }

                        // Xóa file cũ
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
                System.out.println("--- Khối 3 hoàn thành thành công ---");
            } catch (Exception e) {
                System.err.println("❌ LỖI KHỐI 3 (File Upload/IO): " + e.getMessage());
                throw e;
            }

            // =================================================================
            // KHỐI 4: Cập nhật Database (Lỗi SQL/DB)
            // =================================================================
            try {
                System.out.println("--- Bắt đầu Khối 4: Update DB ---");
                boolean updated = cvDao.updateCV(updatedCV);

                if (updated) {
                    System.out.println("--- Khối 4: Update thành công. Chuyển hướng. ---");
                    response.sendRedirect("cv-list");
                    return; // Thoát ngay sau khi redirect
                } else {
                    System.err.println("❌ LỖI KHỐI 4: Update DB thất bại (DAO trả về false)");
                    request.setAttribute("error", "Failed to update CV (Database error)");
                    request.setAttribute("cv", updatedCV);
                    request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
                    return; // Thoát ngay sau khi forward
                }
            } catch (Exception e) {
                System.err.println("❌ LỖI KHỐI 4 (SQL/DB Connection): " + e.getMessage());
                throw e;
            }

        } catch (Exception e) {
            // =================================================================
            // KHỐI CATCH CHUNG: Xử lý lỗi cuối cùng và Forward về JSP
            // =================================================================
            System.err.println("⚠️ Lỗi Chung (Forwarding): " + e.getMessage());
            e.printStackTrace(); // In toàn bộ stack trace

            request.setAttribute("error", "An error occurred while updating the CV. Please check your inputs.");

            // Logic đảm bảo đối tượng 'cv' không null
            if (updatedCV.getCVID() != 0) {
                request.setAttribute("cv", updatedCV);
            } else if (existingCV != null) {
                request.setAttribute("cv", existingCV);
            } else {
                request.setAttribute("cv", updatedCV); // Sử dụng đối tượng rỗng (non-null)
            }

            request.getRequestDispatcher("cv-edit.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


package controller.CV;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.CVDAO;
import model.CV;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import model.Candidate;

@WebServlet(name = "CreateCVServlet", urlPatterns = {"/create-CV"})
@MultipartConfig( //        fileSizeThreshold = 1024 * 1024, // 1 MB
        //        maxFileSize = 1024 * 1024 * 10, // 10 MB
        //        maxRequestSize = 1024 * 1024 * 15 // 15 MB
        )
public class CreateCVServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads/cv_files";

    @Override
    public void init() throws ServletException {
        // Tạo thư mục uploads nếu chưa tồn tại
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== CV CREATE SERVLET DEBUG ===");

        try {
            // Get session and check login status
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println("ERROR: No session found");
                response.sendRedirect("login.jsp");
                return;
            }

            Part filePart = request.getPart("cvFile");
            String fileName = "";
            String filePath = "";

            if (filePart != null && filePart.getSize() > 0) {
                // Tạo tên file unique bằng timestamp
                fileName = System.currentTimeMillis() + "_"
                        + filePart.getSubmittedFileName().replaceAll("\\s+", "_");

                // Tạo đường dẫn lưu file
                String uploadPath = getServletContext().getRealPath("")
                        + File.separator + UPLOAD_DIRECTORY;
                filePath = UPLOAD_DIRECTORY + File.separator + fileName;

                // Lưu file vào thư mục
                try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(uploadPath + File.separator + fileName)) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }

                    System.out.println("File saved successfully: " + filePath);
                } catch (IOException e) {
                    System.out.println("Error saving file: " + e.getMessage());
                    throw e;
                }
            }

            // Debug session info
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session attributes: ");
            java.util.Enumeration<String> attrs = session.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String attr = attrs.nextElement();
                System.out.println("  " + attr + " = " + session.getAttribute(attr));
            }

            // Try multiple ways to get candidateId
            int candidateId = -1;

            // Method 1: Direct candidateId from session
            Object candidateIdObj = session.getAttribute("candidateId");
            if (candidateIdObj != null) {
                candidateId = (int) candidateIdObj;
                System.out.println("Found candidateId directly: " + candidateId);
            } // Method 2: Get from user object
            else {
                Object userObj = session.getAttribute("user");
                if (userObj != null && userObj instanceof Candidate) {
                    Candidate candidate = (Candidate) userObj;
                    candidateId = candidate.getCandidateId();
                    System.out.println("Found candidateId from user object: " + candidateId);
                } else {
                    System.out.println("ERROR: No valid candidateId found in session");
                    System.out.println("user object type: " + (userObj != null ? userObj.getClass().getName() : "null"));

                    request.setAttribute("error", "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }

            // Validate candidateId
            if (candidateId <= 0) {
                System.out.println("ERROR: Invalid candidateId: " + candidateId);
                request.setAttribute("error", "ID người dùng không hợp lệ.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Check if candidate exists in database
            CVDAO cvDAO = new CVDAO();
            if (!cvDAO.isCandidateExists(candidateId)) {
                System.out.println("ERROR: CandidateID " + candidateId + " not found in database");
                request.setAttribute("error", "Candidate không tồn tại trong hệ thống.");
                request.getRequestDispatcher("cv-create.jsp").forward(request, response);
                return;
            }
            System.out.println("SUCCESS: CandidateID " + candidateId + " exists in database");

            // Get form data
            System.out.println("Getting form parameters...");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String position = request.getParameter("position");
            String numberExpStr = request.getParameter("numberExp");
            String education = request.getParameter("education");
            String field = request.getParameter("field");
            String currentSalaryStr = request.getParameter("currentSalary");
            String birthdayStr = request.getParameter("birthday");
            String nationality = request.getParameter("nationality");
            String gender = request.getParameter("gender");

            // Debug form data
            System.out.println("Form data received:");
            System.out.println("  fullName: " + fullName);
            System.out.println("  email: " + email);
            System.out.println("  position: " + position);
            System.out.println("  numberExp: " + numberExpStr);
            System.out.println("  currentSalary: " + currentSalaryStr);
            System.out.println("  birthday: " + birthdayStr);

            // Validate required fields
            if (fullName == null || fullName.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || position == null || position.trim().isEmpty()
                    || numberExpStr == null || numberExpStr.trim().isEmpty()
                    || currentSalaryStr == null || currentSalaryStr.trim().isEmpty()
                    || birthdayStr == null || birthdayStr.trim().isEmpty()) {

                System.out.println("ERROR: Missing required fields");
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin bắt buộc.");
                request.getRequestDispatcher("cv-create.jsp").forward(request, response);
                return;
            }

            // Parse numeric fields
            int numberExp;
            BigDecimal currentSalary;
            Date birthday;

            try {
                numberExp = Integer.parseInt(numberExpStr);
                currentSalary = new BigDecimal(currentSalaryStr);
                birthday = Date.valueOf(birthdayStr);
                System.out.println("Parsed numeric fields successfully");
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: Invalid number format - " + e.getMessage());
                request.setAttribute("error", "Định dạng số hoặc ngày không hợp lệ.");
                request.getRequestDispatcher("cv-create.jsp").forward(request, response);
                return;
            }

            // Handle file upload
            // Create CV object
            CV cv = new CV();
            cv.setCandidateId(candidateId);
            cv.setFullName(fullName);
            cv.setEmail(email);
            cv.setAddress(address);
            cv.setPosition(position);
            cv.setNumberExp(numberExp);
            cv.setEducation(education);
            cv.setField(field);
            cv.setCurrentSalary(currentSalary);
            cv.setBirthday(birthday);
            cv.setNationality(nationality);
            cv.setGender(gender);
            cv.setFileData(filePath); // Lưu đường dẫn thay vì nội dung file


            System.out.println("CV object created, attempting to save to database...");

            // Save to database
            boolean success = cvDAO.createCV(cv);

            if (success) {
                System.out.println("SUCCESS: CV created successfully");
                session.setAttribute("message", "CV created successfully!");
                response.sendRedirect("cv-management.jsp");
            } else {
                System.out.println("ERROR: Failed to create CV in database");
                request.setAttribute("error", "Failed to create CV! Please check server logs.");
                request.getRequestDispatcher("cv-create.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION in CVCreateServlet:");
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("cv-create.jsp").forward(request, response);
        }

        System.out.println("=== END CV CREATE SERVLET ===");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
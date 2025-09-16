package controller;
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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;


// @WebServlet(name = "CVCreateServlet", urlPatterns = {"/create-CV"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class CVCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Get form data
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String position = request.getParameter("position");
            int numberExp = Integer.parseInt(request.getParameter("numberExp"));
            String education = request.getParameter("education");
            String field = request.getParameter("field");
            double currentSalary = Double.parseDouble(request.getParameter("currentSalary"));
            Date birthday = Date.valueOf(request.getParameter("birthday"));
            String nationality = request.getParameter("nationality");
            String gender = request.getParameter("gender");
            
            // Handle file upload with resource management
            Part filePart = request.getPart("cvFile");
            byte[] fileData;
            try (InputStream fileContent = filePart.getInputStream()) {
                fileData = fileContent.readAllBytes();
            }
            String mimeType = filePart.getContentType();
            
            // Create CV object
            CV cv = new CV();
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
            cv.setFileData(fileData);
            cv.setMimeType(mimeType);
            
            // Save to database
            CVDAO cvDAO = new CVDAO();
            boolean success = cvDAO.createCV(cv);
            
            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "CV created successfully!");
                response.sendRedirect("cv-management.jsp");
            } else {
                request.setAttribute("error", "Failed to create CV!");
                request.getRequestDispatcher("cv-create.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("cv-create.jsp").forward(request, response);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

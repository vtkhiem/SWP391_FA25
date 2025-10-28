/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.candidate;

import dal.CandidateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import model.Candidate;

/**
 *
 * @author shiro
 */
@WebServlet(name = "UploadCandidateImage", urlPatterns = {"/uploadCandidateImage"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class UploadCandidateImage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadCandidateImage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadCandidateImage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private static final String UPLOAD_DIRECTORY = "uploads/candidate_images";

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Candidate candidate = (Candidate) session.getAttribute("user");

        // Lấy file
        Part filePart = request.getPart("candidateImage");
        if (filePart == null || filePart.getSize() == 0) {
            session.setAttribute("error", "Vui lòng chọn ảnh để tải lên!");
            response.sendRedirect("candidateProfile");
            return;
        }

        // Lấy tên file
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Kiểm tra định dạng
        if (!fileName.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")) {
            session.setAttribute("error", "Chỉ được tải lên file ảnh (.jpg, .jpeg, .png)");
            System.out.println(session.getAttribute("error"));

            response.sendRedirect("candidateProfile");
            return;
        }

        // Tạo thư mục uploads
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Tạo tên file duy nhất
        String newFileName = "candidate_" + candidate.getCandidateId() + "_" + System.currentTimeMillis() + "_" + fileName;

        // Ghi file vào thư mục server
        String filePath = uploadPath + File.separator + newFileName;
        filePart.write(filePath);

        // Đường dẫn tương đối để lưu vào DB
        String imageURL = UPLOAD_DIRECTORY + "/" + newFileName;

        // Cập nhật DB
        CandidateDAO dao = new CandidateDAO();
        boolean updated = dao.updateAvatar(candidate.getCandidateId(), imageURL);

        if (updated) {
            candidate.setAvatar(imageURL); // cập nhật lại session
            session.setAttribute("user", candidate);
            session.setAttribute("message", "Cập nhật ảnh đại diện thành công!");
        } else {
            session.setAttribute("error", "Lỗi không mong muốn khi lưu ảnh!");
        }

        // Điều hướng lại trang profile (redirect để tránh re-submit form)
        response.sendRedirect("candidateProfile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

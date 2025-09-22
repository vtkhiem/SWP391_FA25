package controller;

import dal.CVDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "CVDownloadServlet", urlPatterns = {"/download-cv"})
public class CVDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int cvId = Integer.parseInt(request.getParameter("id"));
            CVDAO cvDao = new CVDAO();
            
            String filePath = cvDao.getCVFilePath(cvId);
            if (filePath == null || filePath.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file not found");
                return;
            }

            // Lấy đường dẫn thực của file
            String realPath = getServletContext().getRealPath("") + File.separator + filePath;
            File file = new File(realPath);
            
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "CV file not found on server");
                return;
            }

            // Set response headers
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + 
                              file.getName() + "\"");
            response.setContentLength((int) file.length());

            // Stream file to response
            try (FileInputStream in = new FileInputStream(file);
                 OutputStream out = response.getOutputStream()) {
                
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CV ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error downloading CV: " + e.getMessage());
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CVDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import model.CV;

/**
 *
 * @author shiro
 */
@WebServlet(name = "DownloadCVServlet", urlPatterns = {"/downloadCV"})
public class DownloadCVServlet extends HttpServlet {

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
            out.println("<title>Servlet DownloadCVServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadCVServlet at " + request.getContextPath() + "</h1>");
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
        String idsParam = request.getParameter("ids");
        if (idsParam == null || idsParam.trim().isEmpty()) {
            response.getWriter().write("No CV selected");
            return;
        }

        String[] idStrs = idsParam.split(",");
        CVDAO cvDAO = new CVDAO();

        if (idStrs.length == 1) {
            // === Case 1: chỉ có 1 CV ===
            int cvId = Integer.parseInt(idStrs[0].trim());
            CV cv = cvDAO.getCVById(cvId);
            if (cv != null && cv.getFileData() != null) {
                File file = new File(getServletContext().getRealPath("") + File.separator + cv.getFileData());
                if (file.exists()) {
                    response.setContentType(getServletContext().getMimeType(file.getName()));
                    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

                    try (FileInputStream fis = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = fis.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                    }
                    return;
                }
            }
            response.getWriter().write("File not found");
        } else {
            // === Case 2: nhiều CV => tạo file ZIP ===
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=CVs.zip");

            try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
                for (String idStr : idStrs) {
                    try {
                        int cvId = Integer.parseInt(idStr.trim());
                        CV cv = cvDAO.getCVById(cvId);
                        if (cv != null && cv.getFileData() != null) {
                            String filePath = getServletContext().getRealPath("") + File.separator + cv.getFileData();
                            File file = new File(filePath);
                            if (file.exists()) {
                                try (FileInputStream fis = new FileInputStream(file)) {
                                    zos.putNextEntry(new ZipEntry(file.getName()));
                                    byte[] buffer = new byte[4096];
                                    int len;
                                    while ((len = fis.read(buffer)) != -1) {
                                        zos.write(buffer, 0, len);
                                    }
                                    zos.closeEntry();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.candidate;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import dal.CandidateDAO;
import model.Candidate;

/**
 *
 * @author shiro
 */
@WebServlet(name = "ExportCandidateList", urlPatterns = {"/admin/exportCandidateList"})
public class ExportCandidateList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        try {
            // Lấy toàn bộ danh sách Candidate từ DB
            CandidateDAO dao = new CandidateDAO();
            List<Candidate> list = dao.getAllCandidates();

            // Tạo workbook và sheet
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Candidates");

            // Tạo header
            String[] headers = {"ID", "Họ tên", "Địa chỉ", "Email", "Số điện thoại", "Quốc tịch", "Công khai"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Ghi dữ liệu từng dòng
            int rowNum = 1;
            for (Candidate c : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(c.getCandidateId());
                row.createCell(1).setCellValue(c.getCandidateName());
                row.createCell(2).setCellValue(c.getAddress());
                row.createCell(3).setCellValue(c.getEmail());
                row.createCell(4).setCellValue(c.getPhoneNumber());
                row.createCell(5).setCellValue(c.getNationality());
                row.createCell(6).setCellValue(c.isIsPublic() ? "Yes" : "No");
            }

            // Tự động căn cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Cấu hình phản hồi tải file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=candidates.xlsx");

            // Ghi workbook ra output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi xuất file: " + e.getMessage());
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
        doGet(request, response);
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

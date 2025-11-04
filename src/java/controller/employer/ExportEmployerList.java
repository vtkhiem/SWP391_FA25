/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.employer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;
import model.Employer;
import dal.EmployerDAO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author shiro
 */
@WebServlet(name = "ExportEmployerList", urlPatterns = {"/admin/exportEmployerList"})
public class ExportEmployerList extends HttpServlet {

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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employers.xlsx");

        EmployerDAO dao = new EmployerDAO();
        List<Employer> employers = dao.getAllEmployers();  // lấy toàn bộ employer

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employers");

            // Tạo tiêu đề cột
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Tên", "Công ty", "Email", "SĐT", "Địa điểm", "Website", "TaxCode", "Trạng thái"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Ghi dữ liệu
            int rowNum = 1;
            for (Employer e : employers) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(e.getEmployerId());
                row.createCell(1).setCellValue(e.getEmployerName());
                row.createCell(2).setCellValue(e.getCompanyName());
                row.createCell(3).setCellValue(e.getEmail());
                row.createCell(4).setCellValue(e.getPhoneNumber());
                row.createCell(5).setCellValue(e.getLocation());
                row.createCell(6).setCellValue(e.getUrlWebsite());
                row.createCell(7).setCellValue(e.getTaxCode());
                row.createCell(8).setCellValue(e.isStatus() ? "Verified" : "Not Verified");
            }

            // Tự động chỉnh độ rộng cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi file ra response
            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
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
        doGet(request,response);
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

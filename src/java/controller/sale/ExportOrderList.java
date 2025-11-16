package controller.order;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "ExportOrderList", urlPatterns = {"/exportOrderList"})
public class ExportOrderList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        OrderDAO dao = new OrderDAO();
        List<OrderView> list = dao.findPage(1, 9999);  // xuất tất cả

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Orders");

        // --- Header style ---
        CellStyle headerStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        // --- Header row ---
        String[] headers = {
                "Order ID", "Ngày đặt", "Nhà tuyển dụng", "Email",
                "Gói", "Mã KM", "Giảm (%)",
                "Tổng tiền", "Phương thức", "Thời lượng", "Ngày kết thúc", "Trạng thái"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // --- Data rows ---
        int rowIndex = 1;
        for (OrderView o : list) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(o.getOrderId());
            row.createCell(1).setCellValue(o.getDate() != null ? o.getDate().toString() : "");
            row.createCell(2).setCellValue(o.getEmployerName());
            row.createCell(3).setCellValue(o.getEmployerEmail());
            row.createCell(4).setCellValue(o.getServiceName());
            row.createCell(5).setCellValue(o.getPromotionCode() != null ? o.getPromotionCode() : "");
            row.createCell(6).setCellValue(o.getDiscountPercent() != null ? o.getDiscountPercent().toString() : "");
            row.createCell(7).setCellValue(o.getFinalAmount().doubleValue());
            row.createCell(8).setCellValue(o.getPayMethod());

            row.createCell(9).setCellValue(o.getDuration() != null ? o.getDuration() : 0);

            if (o.getEndDate() != null) {
                row.createCell(10).setCellValue(o.getEndDate().toString());
            } else {
                row.createCell(10).setCellValue("");
            }

            row.createCell(11).setCellValue(o.getStatus());
        }

        // Auto-size
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        OutputStream out = resp.getOutputStream();
        wb.write(out);
        wb.close();
        out.flush();
        out.close();
    }
}

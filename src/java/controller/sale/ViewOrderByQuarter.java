package controller.sale;

import dal.OrderDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet(name = "ViewOrderByQuarter", urlPatterns = {"/viewOrderByQuarter"})
public class ViewOrderByQuarter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO dao = new OrderDAO();
        List<Order> orders = dao.getAllOrders();

        // Map<Year, Map<Quarter, Map<"count"/"revenue", Object>>>
        Map<Integer, Map<Integer, Map<String, Object>>> revenueByQuarter = new LinkedHashMap<>();

        for (Order o : orders) {
            if (o.getDate() == null) {
                continue;
            }

            LocalDateTime date = o.getDate();
            int year = date.getYear();
            int month = date.getMonthValue();
            int quarter = (month - 1) / 3 + 1;

            revenueByQuarter.putIfAbsent(year, new LinkedHashMap<>());
            Map<Integer, Map<String, Object>> yearMap = revenueByQuarter.get(year);

            yearMap.putIfAbsent(quarter, new HashMap<>());
            Map<String, Object> qMap = yearMap.get(quarter);

            // Số đơn
            Integer countObj = (Integer) qMap.get("count");
            int count = (countObj != null) ? countObj : 0;
            qMap.put("count", count + 1);

            // Doanh thu
            BigDecimal revenue = (BigDecimal) qMap.get("revenue");
            if (revenue == null) {
                revenue = BigDecimal.ZERO;
            }
            BigDecimal amount = o.getFinalAmount(o.getOrderID());
            if (amount == null) {
                amount = BigDecimal.ZERO;
            }
            qMap.put("revenue", revenue.add(amount));

            yearMap.put(quarter, qMap);
        }

        request.setAttribute("revenueByQuarter", revenueByQuarter);
        request.getRequestDispatcher("sale-by-quarter.jsp").forward(request, response);
    }
}

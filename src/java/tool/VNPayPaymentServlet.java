package tool;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "VNPayPaymentServlet", urlPatterns = {"/vnpay"})
public class VNPayPaymentServlet extends HttpServlet {

    // ⚠️ Nhớ dùng đúng thông tin từ tài khoản sandbox của bạn
    private static final String vnp_TmnCode = "0DJ2W01T";
    private static final String vnp_HashSecret = "W5NFOUG2O1WHM8G65TGRU42L9OG62R2Q";
    private static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String vnp_ReturnUrl = "http://localhost:8080/SWP391_FA25/vnpay-return";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long amount = 10000; // VND
        String orderId = String.valueOf(System.currentTimeMillis());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // nhân 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", orderId);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang #" + orderId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String createDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", createDate);
        cld.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", expireDate);

        // ⚠️ Địa chỉ IP phải là IPv4 hợp lệ
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "127.0.0.1";
        }
        vnp_Params.put("vnp_IpAddr", ipAddress);

        // ---- Build hashData và query ----
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String name : fieldNames) {
            String value = vnp_Params.get(name);
            if (value != null && !value.isEmpty()) {
                // build hashData (raw, not URL-encoded)
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                hashData.append(name).append('=').append(value);

                // build query (URL-encoded)
                if (query.length() > 0) {
                    query.append('&');
                }
                query.append(URLEncoder.encode(name, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
            }
        }

        // ---- Tạo secure hash ----
        String vnp_SecureHash = VNPaySecurityUtils.hmacSHA512(vnp_HashSecret, hashData.toString()).toUpperCase();
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String paymentUrl = vnp_Url + "?" + query;

        // ---- Log kiểm tra ----
        System.out.println("VNPAY DEBUG >>> hashData = " + hashData);
        System.out.println("VNPAY DEBUG >>> vnp_SecureHash = " + vnp_SecureHash);
        System.out.println("VNPAY DEBUG >>> paymentUrl = " + paymentUrl);

        response.sendRedirect(paymentUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

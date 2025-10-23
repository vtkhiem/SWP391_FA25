package filter;

import dal.BlogPostDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class SlugRouterFilter implements Filter {

    private static final Set<String> SKIP_PREFIXES = Set.of(
        "/css/", "/js/", "/img/", "/assets/", "/vendors/",
        "/WEB-INF/", "/META-INF/",
        "/blogs", "/jobs", "/login", "/logout", "/profile",
        "/employer", "/employerServices", "/list-cv", "/cv-create.jsp",
        "/index.jsp", "/contact.html"
    );

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String ctx = req.getContextPath();                 // /SWP391_FA25
        String uri = req.getRequestURI();                  // /SWP391_FA25/<something>
        String path = uri.substring(ctx.length());         // /<something>

        // Bỏ qua trang chủ, tài nguyên tĩnh, đường dẫn đã biết, và mọi path có dấu chấm (file tĩnh)
        if (path.isEmpty() || "/".equals(path) || path.contains(".")) {
            chain.doFilter(request, response);
            return;
        }
        for (String prefix : SKIP_PREFIXES) {
            if (path.startsWith(prefix)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Chỉ nhận slug dạng /[a-z0-9-]+ (1 segment). Nếu muốn hỗ trợ nhiều segment, nới regex.
        if (path.matches("^/[a-z0-9-]+/?$")) {
            try {
                BlogPostDAO dao = new BlogPostDAO();
                boolean exists = dao.existsByUrl(path.replaceAll("/+$", "")); // chuẩn hóa bỏ '/' cuối
                if (exists) {
                    // Forward nội bộ đến servlet cũ /blogs/* mà không đổi URL trên trình duyệt
                    String target = "/blogs" + path;
                    req.getRequestDispatcher(target).forward(request, response);
                    return;
                }
            } catch (Exception ignore) { /* fallback chain */ }
        }

        // Không match => để tiếp tục chuỗi filter/servlet bình thường
        chain.doFilter(request, response);
    }
}

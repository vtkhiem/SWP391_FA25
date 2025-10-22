package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/create-cv", "/delete-cv", "/edit-cv", "/list-cv", "/profile", "/selectCV"})
public class AuthCandidateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        // nếu chưa login hoặc role khác Employer ->  redirect tới login
        if (session == null || session.getAttribute("role") == null
                || !"Candidate".equals(session.getAttribute("role"))) {
            // bạn có thể thêm thông báo ?next= để redirect sau khi login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // nếu ok → tiếp tục chain
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

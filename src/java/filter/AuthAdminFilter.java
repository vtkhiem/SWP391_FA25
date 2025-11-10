/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author shiro
 */
@WebFilter(filterName = "AuthAdminFilter", urlPatterns = {"/filterOrderList"})
public class AuthAdminFilter implements Filter {

   @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        // nếu chưa login hoặc role khác Employer ->  redirect tới login
        if (session == null || session.getAttribute("role") == null
                || (!"Admin".equals(session.getAttribute("role"))
                && !"Sale".equals(session.getAttribute("role"))
                && !"MarketingStaff".equals(session.getAttribute("role")))
                ) {
            // bạn có thể thêm thông báo ?next= để redirect sau khi login
            response.sendRedirect("login-admin.jsp");
            return;
        }

        // nếu ok → tiếp tục chain
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.EmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import model.Employer;
import tool.EncodePassword;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="EmployerAddServlet", urlPatterns={"/admin/employer/add"})
public class EmployerAddServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet EmployerAddServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployerAddServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
        request.getRequestDispatcher("/admin/employer-add.jsp").forward(request, response);
    }
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String employerName = s(req.getParameter("employerName"));
        String email        = s(req.getParameter("email"));
        String phoneNumber  = s(req.getParameter("phoneNumber"));
        String companyName  = s(req.getParameter("companyName"));
        String description  = s(req.getParameter("description"));
        String location     = s(req.getParameter("location"));
        String urlWebsite   = s(req.getParameter("urlWebsite"));

        String password     = s(req.getParameter("password")); // hiển thị & bắt buộc

        String err = validate(employerName, email, phoneNumber, password, urlWebsite);
        if (err != null) {
            req.setAttribute("error", err);
            req.getRequestDispatcher("/admin/employer-add.jsp").forward(req, resp);
            return;
        }

        String passwordHash = EncodePassword.encodePasswordbyHash(password);

        Employer e = new Employer();
        e.setEmployerName(employerName);
        e.setEmail(email);
        e.setPhoneNumber(phoneNumber);
        e.setPasswordHash(passwordHash);
        e.setCompanyName(companyName);
        e.setDescription(description);
        e.setLocation(location);
        e.setUrlWebsite(urlWebsite);


        EmployerDAO dao = new EmployerDAO();
        int newId = dao.insert(e); 
        if (newId > 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/employers?added=1");
        } else {
            req.setAttribute("error", "Không thể thêm employer. Có thể trùng EmployerName/Email/Phone/TaxCode hoặc dữ liệu không hợp lệ.");
            req.getRequestDispatcher("/admin/employer-add.jsp").forward(req, resp);
        }
    }

    private String s(String v) { return v == null ? "" : v.trim(); }

    private String validate(String name, String email, String phone, String pass, String website) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty())
            return "Vui lòng nhập đủ Tên employer, Email, Số điện thoại và Mật khẩu.";

        if (name.length() > 100) return "Tên employer tối đa 100 ký tự.";
        if (!EMAIL.matcher(email).matches() || email.length() > 100) return "Email không hợp lệ hoặc quá dài (≤100).";
        if (phone.length() > 15) return "Số điện thoại tối đa 15 ký tự.";
        if (pass.length() < 6) return "Mật khẩu tối thiểu 6 ký tự.";
        if (website != null && !website.isEmpty() && website.length() > 100) return "URL website tối đa 100 ký tự.";
        return null;
    }

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

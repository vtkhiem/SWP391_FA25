/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Candidate;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Candidate;
import dal.CandidateDAO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import tool.EncodePassword;
/**
 *
 * @author ADMIN
 */
@WebServlet(name="CandidateAddServlet", urlPatterns={"/admin/candidate/add"})
public class CandidateAddServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CandidateAddServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CandidateAddServlet at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("/admin/candidate-add.jsp").forward(request, response);
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

        String name        = s(req.getParameter("name"));
        String email       = s(req.getParameter("email"));
        String phone       = s(req.getParameter("phone"));
        String address     = s(req.getParameter("address"));
        String nationality = s(req.getParameter("nationality"));
        String password    = s(req.getParameter("password"));

       
        String err = validate(name, email, phone, password);
        if (err != null) {
            req.setAttribute("error", err);
            req.getRequestDispatcher("/admin/candidate-add.jsp").forward(req, resp);
            return;
        }

       
        String passwordHash = EncodePassword.encodePasswordbyHash(password);

        Candidate cd = new Candidate();
        cd.setCandidateName(name);
        cd.setEmail(email);
        cd.setPhoneNumber(phone);
        cd.setAddress(address);
        cd.setNationality(nationality);
        cd.setPasswordHash(passwordHash);
        cd.setAvatar(null);

        int newId = new CandidateDAO().insert(cd); // DAO nên trả về ID mới (>0) nếu OK
        if (newId > 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/candidates?added=1");
        } else {
            req.setAttribute("error", "Không thể thêm ứng viên. Có thể trùng Tên/Email/SĐT hoặc dữ liệu không hợp lệ.");
            req.getRequestDispatcher("/admin/candidate-add.jsp").forward(req, resp);
        }
    }

    private String s(String v) { return v == null ? "" : v.trim(); }

    private String validate(String name, String email, String phone, String pass) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty())
            return "Vui lòng nhập đủ Tên, Email, Số điện thoại và Mật khẩu.";
        if (!EMAIL.matcher(email).matches()) return "Email không hợp lệ.";
        if (phone.length() > 15) return "Số điện thoại tối đa 15 ký tự.";
        if (pass.length() < 6) return "Mật khẩu tối thiểu 6 ký tự.";
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

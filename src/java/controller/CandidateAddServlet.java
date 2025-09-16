/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Candidate;
import dal.CandidateDAO;
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

        String name  = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String addr  = req.getParameter("address");
        String nat   = req.getParameter("nationality");

        // Upload avatar (tùy chọn)
        
        // Tạo đối tượng
        Candidate cd = new Candidate();
        cd.setCandidateName(name);
        cd.setEmail(email);
        cd.setPhoneNumber(phone);
        cd.setAddress(addr);
        cd.setNationality(nat);
        cd.setPasswordHash("hashed_default");
      
        CandidateDAO dao = new CandidateDAO();
        int newId = dao.insert(cd);
        if (newId > 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/candidates?added=1");
        } else {
            req.setAttribute("error", "Không thể thêm ứng viên. Kiểm tra trùng Email/Phone/Name hoặc dữ liệu không hợp lệ.");
            req.getRequestDispatcher("/admin/candidate-add.jsp").forward(req, resp);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

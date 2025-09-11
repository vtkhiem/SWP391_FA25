/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AdminDAO;
import dal.RegisterCandidateDAO;
import dal.RegisterEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.ValidationRegister;

/**
 *
 * @author Admin
 */
@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
   
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
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      try {
           RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employerDAO = new RegisterEmployerDAO();
            AdminDAO adminDAO = new AdminDAO();

            HttpSession session = request.getSession();

            String status = " ";
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if(email.contains("@")){
            // kiểm tra xem có tồn tại trong Candidate trước          
            if (candidateDAO.isEmailCandidateExist(email)) {

                boolean result = candidateDAO.loginCandidate(email, password);
                String idCandidate = candidateDAO.getIDByEmail(email);
                if (result) {
                    session.setAttribute("email", email);   
                    session.setAttribute("role", "Candidate");       // lưu role 
                    session.setAttribute("idUser", idCandidate);     // lưu id
                
                  
                    response.sendRedirect("index.jsp");
                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                    request.setAttribute("status", status);
                    request.setAttribute("username", email);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                // Kiểm tra xem trong employerr
            } else if (employerDAO.isEmailEmployerExist(email)) {

                boolean result = employerDAO.loginEmployer(email, password);
                String idEmployer = employerDAO.getIDByEmail(email);
                if (result) {
                    session.setAttribute("email", email);
                    session.setAttribute("idUser", idEmployer);
                    session.setAttribute("role", "Employer");
                
                    response.sendRedirect("Index");
                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                    request.setAttribute("username",email );
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
            }else{
                String username = request.getParameter("email");
                 

                boolean result = adminDAO.loginAccountAdmin(username, password);
                if (result) {
                    session.setAttribute("username", username);
                    session.setAttribute("role", "Admin");

                    response.sendRedirect("StatictisData"); 

                } else {
                    status = "Tài Khoản hoặc Mật khẩu của bạn không chính xác";
                    request.setAttribute("username", username);
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
        
            }

        } catch (Exception s) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Auth;

import dal.RegisterCandidateDAO;
import dal.RegisterEmployerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.ValidationRegister;

/**
 *
 * @author Admin
 */
@WebServlet(name="RegisterServlet", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {
   
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
            out.println("<title>Servlet RegisterServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath () + "</h1>");
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
            String status = " ";
            String name = request.getParameter("name");
            String email = request.getParameter("email");
             String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("confirmPassword");
            String role = request.getParameter("role");

        

            ValidationRegister validation = new ValidationRegister();
            RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employersDAO = new RegisterEmployerDAO();

            // `1.xử lý nếu user chọn Candidate  (đã test)
            if (role.equals("candidate")) {

                // kiểm tra xem email đã tồn tại chưa 
                if (candidateDAO.isEmailCandidateExist(email) &&!employersDAO.isEmailEmployerExist(email)) {
                    status = "Email của bạn đã được đăng ký với một tài khoản khác";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                    // kiểm tra tài khoản 
                } else if (!candidateDAO.isEmailCandidateExist(email) &&employersDAO.isEmailEmployerExist(email)) {
                    status = "Email của bạn đã được đăng ký ở nhà tuyển dụng";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                    // kiểm tra tài khoản 
                }
                else if (!validation.checkLength(password)) {
                    status = "Mật khẩu yêu cầu tối thiểu là 8 ký tự !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (!password.equals(passwordConfirm)) {
                    status = "Mật khẩu xác nhận không khớp với mật khẩu ban đầu bạn nhập !" + password + " và " + passwordConfirm;
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (!validation.checkChar(password)) {

                    status = "Mật khẩu cần 8 ký tự và các ký tự đặc biệt";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                } else {
                   
                    boolean result = candidateDAO.registerCandidate(name, email,phone, password);

                    if (result) {
                        status = "Đăng ký thành công,mời bạn quay lại để đăng nhập !";
                        request.setAttribute("status", status);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {
                        status = "Không đăng ký thành công";
                        request.setAttribute("status", status);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }

                }
                
            } else if (role.equals("employer")) {

             
                if (employersDAO.isEmailEmployerExist(email)) {
                    status = "Tài khoản Email này đã được đăng ký với 1 tài khoản khác !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
       
                }  else if (!employersDAO.isEmailEmployerExist(email)&&candidateDAO.isEmailCandidateExist(email)) {
                    status = "Tài khoản Email này đã được đăng ký ở ứng viên !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
       
                }    else if (!validation.checkLength(password)) {
                    status = "Mật khẩu yêu cầu tối thiểu là 8 ký tự !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (!password.equals(passwordConfirm)) {
                    status = "Mật khẩu xác nhận không khớp với mật khẩu đầu tiên bạn nhập !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (!validation.checkChar(password)) {

                    status = "Mật khẩu cần 8 ký tự và các ký tự đặc biệt";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                } else if (phone.length() != 10) {
                    status = "Số điện thoại chỉ 10 chữ số ";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (employersDAO.isPhoneEmployerExist(phone)) {
                    status = "Số điện thoại đã được đăng ký ";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    // đăng ký 

                    boolean result = employersDAO.registerEmployer(name, email, phone, password);
                    System.out.println("Thằng result " + result);
                             System.out.println("Đăng kÝ tài khoản thành công");
                    if (result) {
                        status = "Đăng ký thành công,mời bạn quay lại để đăng nhập !";
                         response.sendRedirect("login.jsp");
                         return ;
                    } else {
                        status = "ối rồi ồi , đã có rác rồi nên không đăng ký thành công";
                        request.setAttribute("status", status);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return ;
                    }

                }
                status = "Không đăng ký thành công";
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
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

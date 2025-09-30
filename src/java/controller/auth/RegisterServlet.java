package controller.auth;

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

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
    
    /** * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
=======
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
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
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
    /** * Handles the HTTP <code>GET</code> method.
=======
    /**
     * Handles the HTTP <code>GET</code> method.
     *
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
    throws ServletException, IOException {
        // Chuyển hướng đến trang đăng nhập/đăng ký nếu truy cập bằng GET
        request.getRequestDispatcher("login.jsp").forward(request, response);
    } 

    /** * Handles the HTTP <code>POST</code> method.
=======
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
    throws ServletException, IOException {
        // Thiết lập mã hóa ký tự để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        
        String status = "";
        
        try {
            // Lấy tham số từ form
=======
            throws ServletException, IOException {
        try {
            String status = " ";
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("confirmPassword");
            String role = request.getParameter("role");

            ValidationRegister validation = new ValidationRegister();
            RegisterCandidateDAO candidateDAO = new RegisterCandidateDAO();
            RegisterEmployerDAO employersDAO = new RegisterEmployerDAO();

<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
            // --- KIỂM TRA TÍNH HỢP LỆ CHUNG (EMAIL & MẬT KHẨU) ---
=======
            // `1.xử lý nếu user chọn Candidate  (đã test)
            if (role.equals("candidate")) {

                // kiểm tra xem email đã tồn tại chưa 
                if (candidateDAO.isEmailCandidateExist(email) && !employersDAO.isEmailEmployerExist(email)) {
                    status = "Email của bạn đã được đăng ký với một tài khoản khác";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                    // kiểm tra tài khoản 
                } else if (!candidateDAO.isEmailCandidateExist(email) && employersDAO.isEmailEmployerExist(email)) {
                    status = "Email của bạn đã được đăng ký ở nhà tuyển dụng";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                    // kiểm tra tài khoản 
                } else if (!validation.checkLength(password)) {
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
>>>>>>> main:src/java/controller/auth/RegisterServlet.java

            // 1. Kiểm tra Email đã tồn tại (ở bất kỳ vai trò nào)
            if (candidateDAO.isEmailCandidateExist(email) || employersDAO.isEmailEmployerExist(email)) {
                
                // Cung cấp thông báo cụ thể hơn cho người dùng
                if ("candidate".equals(role) && employersDAO.isEmailEmployerExist(email)) {
                    status = "Email này đã được đăng ký với tài khoản nhà tuyển dụng. Vui lòng sử dụng email khác.";
                } else if ("employer".equals(role) && candidateDAO.isEmailCandidateExist(email)) {
                    status = "Email này đã được đăng ký với tài khoản ứng viên. Vui lòng sử dụng email khác.";
                } else {
<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
                    status = "Email của bạn đã được đăng ký với một tài khoản khác.";
                }
                
=======

                    boolean result = candidateDAO.registerCandidate(name, email, phone, password);

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

                } else if (!employersDAO.isEmailEmployerExist(email) && candidateDAO.isEmailCandidateExist(email)) {
                    status = "Tài khoản Email này đã được đăng ký ở ứng viên !";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                } else if (!validation.checkLength(password)) {
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
                        return;
                    } else {
                        status = "ối rồi ồi , đã có rác rồi nên không đăng ký thành công";
                        request.setAttribute("status", status);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    }

                }
                status = "Không đăng ký thành công";
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return; // QUAN TRỌNG: Dừng thực thi
            }

            // 2. Kiểm tra độ dài mật khẩu (tối thiểu 8 ký tự)
            if (!validation.checkLength(password)) {
                status = "Mật khẩu yêu cầu tối thiểu là 8 ký tự!";
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return; // QUAN TRỌNG: Dừng thực thi
            }
            
            // 3. Kiểm tra mật khẩu xác nhận
            if (!password.equals(passwordConfirm)) {
                status = "Mật khẩu xác nhận không khớp với mật khẩu ban đầu bạn nhập!"; 
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return; // QUAN TRỌNG: Dừng thực thi
            }
            
            // 4. Kiểm tra độ phức tạp mật khẩu
            if (!validation.checkChar(password)) {
                status = "Mật khẩu cần 8 ký tự và bao gồm các ký tự đặc biệt (chữ hoa, chữ thường, số, ký tự đặc biệt).";
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return; // QUAN TRỌNG: Dừng thực thi
            }


            // --- KIỂM TRA RIÊNG VÀ THỰC HIỆN ĐĂNG KÝ ---

            boolean registrationResult = false;

            if ("candidate".equals(role)) {
                // Đăng ký Ứng viên (Candidate)
                registrationResult = candidateDAO.registerCandidate(name, email, phone, password);

            } else if ("employer".equals(role)) {
                
                // 5. Kiểm tra số điện thoại của Nhà tuyển dụng
                if (phone == null || phone.length() != 10) {
                    status = "Số điện thoại phải có đúng 10 chữ số!";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                } else if (employersDAO.isPhoneEmployerExist(phone)) {
                    status = "Số điện thoại đã được đăng ký với một tài khoản nhà tuyển dụng khác.";
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                // Đăng ký Nhà tuyển dụng (Employer)
                registrationResult = employersDAO.registerEmployer(name, email, phone, password);

            } else {
                // Xử lý vai trò không hợp lệ
                status = "Lỗi: Vai trò đăng ký không hợp lệ.";
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            
            // --- XỬ LÝ KẾT QUẢ ĐĂNG KÝ CUỐI CÙNG ---

            if (registrationResult) {
                status = "Đăng ký thành công, mời bạn quay lại để đăng nhập!";
                request.setAttribute("status", status);
                // Forward tới login.jsp để hiển thị thông báo thành công
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
                // Lỗi khi chèn vào DB (DAO thất bại)
                status = "Đăng ký thất bại do lỗi hệ thống (DAO failure). Vui lòng thử lại.";
                request.setAttribute("status", status);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }


        } catch (Exception e) {
            // Log lỗi để debug
            System.err.println("Exception during registration process: " + e.getMessage());
            e.printStackTrace();

            // Chuyển hướng với thông báo lỗi hệ thống
            request.setAttribute("status", "Lỗi hệ thống nghiêm trọng. Vui lòng liên hệ hỗ trợ.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
    }

<<<<<<< HEAD:src/java/log_controller/RegisterServlet.java
    /** * Returns a short description of the servlet.
=======
    /**
     * Returns a short description of the servlet.
     *
>>>>>>> main:src/java/controller/auth/RegisterServlet.java
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles user registration for both candidates and employers.";
    }// </editor-fold>
}
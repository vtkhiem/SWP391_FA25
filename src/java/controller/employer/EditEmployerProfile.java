/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.employer;
import dal.CandidateDAO;
import dal.EmployerDAO;
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
import model.Candidate;
import model.Employer;
import tool.Validation;
/**
*
* @author shiro
*/
@WebServlet(name="EditEmployerProfile", urlPatterns={"/editEmployerProfile"})
public class EditEmployerProfile extends HttpServlet {
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
out.println("<title>Servlet EditEmployerProfile</title>");
out.println("</head>");
out.println("<body>");
out.println("<h1>Servlet EditEmployerProfile at " + request.getContextPath () + "</h1>");
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
request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Employer employer = (Employer) session.getAttribute("user");


        // Lấy dữ liệu từ form
        String companyName = request.getParameter("companyName");
        String phoneNumber = request.getParameter("phoneNumber");
        String location = request.getParameter("location");
        String urlWebsite = request.getParameter("urlWebsite");
        String description = request.getParameter("description");

        // Kiểm tra dữ liệu đầu vào
        if (companyName == null || companyName.trim().isEmpty()
                || location == null || location.trim().isEmpty()
                || urlWebsite == null || urlWebsite.trim().isEmpty()
                ||description == null||description.trim().isEmpty()) {

            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("/employerProfile").forward(request, response);
            return;
        }

        if (!Validation.isValidPhone(phoneNumber)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ.");
            request.getRequestDispatcher("/employerProfile").forward(request, response);
            return;
        }

        // Cập nhật đối tượng hiện tại
        employer.setCompanyName(companyName);
        employer.setPhoneNumber(phoneNumber);
        employer.setLocation(location);
        employer.setUrlWebsite(urlWebsite);
        employer.setDescription(description);

        try {
            RegisterEmployerDAO dao = new RegisterEmployerDAO();
            EmployerDAO empDAO = new EmployerDAO();
            boolean updated = empDAO.updateEmployerProfile(employer);

            if (updated) {
                // Cập nhật lại thông tin trong session
                session.setAttribute("user", employer);
                request.setAttribute("success", "Cập nhật thông tin thành công!");
            } else {
                request.setAttribute("error", "Không thể cập nhật thông tin. Vui lòng thử lại.");
            }

            // Forward về profile.jsp (thông qua ProfileServlet)
            request.getRequestDispatcher("/employerProfile").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình cập nhật.");
            request.getRequestDispatcher("/employerProfile").forward(request, response);
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
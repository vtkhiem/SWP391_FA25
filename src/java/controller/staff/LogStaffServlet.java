/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staff;
import dal.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LogStaff", urlPatterns = {"/logStaff"})
public class LogStaffServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet LogStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LogStaff at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      HttpSession session = request.getSession();
    String username = (String) session.getAttribute("username");

        // Check if username is null or empty
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Error: Username parameter is null or empty");
            // Optionally, set an error message for the client
            request.setAttribute("error", "Username is required");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Debug: Log the username
        System.out.println("Username received: " + username);

        // Proceed with AdminDAO
        AdminDAO adminDAO = new AdminDAO();
        Admin admin = adminDAO.getAdminByUsername(username);

        // Check if admin is null
        if (admin == null) {
            System.out.println("Error: No Admin found for username: " + username);
            request.setAttribute("errorMessage", "Invalid username");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Debug: Log the admin username
        System.out.println("Admin found: " + admin.getUsername());

        // Set attributes and forward to JSP
        request.setAttribute("user", admin);
        request.setAttribute("role", "MarketingStaff");
        request.getRequestDispatcher("marketing-staff.jsp").forward(request, response);
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
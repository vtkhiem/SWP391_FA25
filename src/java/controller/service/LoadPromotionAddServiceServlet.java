/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.service;

import dal.FunctionDAO;
import dal.PromotionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import model.Function;
import model.Promotion;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoadPromotionAddServiceServlet", urlPatterns = {"/loadAddService"})
public class LoadPromotionAddServiceServlet extends HttpServlet {

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
            out.println("<title>Servlet LoadPromotionAddServiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadPromotionAddServiceServlet at " + request.getContextPath() + "</h1>");
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
        PromotionDAO dao = new PromotionDAO();
        FunctionDAO daoo = new FunctionDAO();
        try {
            List<Function> functionList = daoo.getAllFunctions();
            List<Promotion> promotionList = dao.getAllPromotions();
            request.setAttribute("functionList", functionList);
            request.setAttribute("promotionList", promotionList);
            request.getRequestDispatcher("addService.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
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
       PromotionDAO dao = new PromotionDAO();
        try {
            String err=request.getParameter("error");
            
            List<Promotion> promotionList = dao.getAllPromotions(); 
            request.setAttribute("promotionList", promotionList);
            request.setAttribute("error", err);
            request.getRequestDispatcher("addService.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
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

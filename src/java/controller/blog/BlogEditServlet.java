/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.blog;

import dal.BlogPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogPost;
import model.BlogPostDetail;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="BlogEditServlet", urlPatterns={"/blog-list/edit"})
public class BlogEditServlet extends HttpServlet {
   
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
            out.println("<title>Servlet BlogEditServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogEditServlet at " + request.getContextPath () + "</h1>");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
        String sid = req.getParameter("id");
        if (sid == null) { resp.sendError(400, "Missing id"); return; }
        try {
            int id = Integer.parseInt(sid);
            BlogPostDAO dao = new BlogPostDAO();
            BlogPost post = dao.getById(id);
            if (post == null) { resp.sendError(404); return; }
            BlogPostDetail detail = dao.getDetailByPostId(id);
            req.setAttribute("post", post);
            req.setAttribute("detail", detail);
            req.getRequestDispatcher("/blog-edit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
        try {
            int id = Integer.parseInt(req.getParameter("postID"));
            String title = req.getParameter("title");
            String cate  = req.getParameter("categoryName");
            boolean status = "on".equals(req.getParameter("status"));

            String contentHtml = req.getParameter("contentHtml");
            if (contentHtml == null) contentHtml = "";

            BlogPostDAO dao = new BlogPostDAO();
            dao.updateAdmin(id, title, cate, status);    // cập nhật BlogPost
            dao.upsertDetail(id, contentHtml, true);     // lưu BlogPostDetail (status detail tùy bạn, để true)

            resp.sendRedirect(req.getContextPath()+"/blog-list/edit?id="+id+"&saved=1");
        } catch (Exception e) {
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

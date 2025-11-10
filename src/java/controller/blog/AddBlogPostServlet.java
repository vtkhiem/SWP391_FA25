/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.blog;

import dal.BlogPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.UUID;
import model.BlogPost;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="AddBlogPostServlet", urlPatterns={"/blog-list/add"})
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) 
public class AddBlogPostServlet extends HttpServlet {
       private String slugify(String input) {
        if (input == null) return null;
        String nowhitespace = input.trim().replaceAll("[\\s]+", "-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String slug = accentRemoved.toLowerCase();
        slug = slug.replaceAll("[^a-z0-9\\-]", "");
        slug = slug.replaceAll("-{2,}", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }
           private static final String BASE_UPLOAD_DIR = 
        "C:\\Users\\ADMIN\\Desktop\\ASM\\SWP391_FA25\\web\\img\\blog";
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
            out.println("<title>Servlet AddBlogPostServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBlogPostServlet at " + request.getContextPath () + "</h1>");
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
         request.getRequestDispatcher("/add-post.jsp").forward(request, response);
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

        String title = request.getParameter("title");
        String url    = request.getParameter("url");
        String category = request.getParameter("category");
        String excerpt  = request.getParameter("excerpt");
        String contentHtml = request.getParameter("contentHtml");

        if (title == null || title.isBlank() || category == null || category.isBlank()) {
            request.setAttribute("error", "Tiêu đề và Category là bắt buộc.");
            request.getRequestDispatcher("/add-post.jsp").forward(request, response);
            return;
        }
        if (url == null || url.isBlank()) {
            url = slugify(title);
        } else {
            url = slugify(url);
        }
        if (url == null || url.isBlank()) {
            request.setAttribute("error", "Không tạo được slug URL. Vui lòng nhập URL hợp lệ.");
            request.getRequestDispatcher("/add-post.jsp").forward(request, response);
            return;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }

        url = url.replaceAll("/{2,}", "/");

        String finalCoverImageUrl = null;
        Part filePart = request.getPart("coverFile");
        if (filePart != null && filePart.getSize() > 0) {
            File uploadDir = new File(BASE_UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String submitted = filePart.getSubmittedFileName();
            String ext = "";
            if (submitted != null && submitted.lastIndexOf(".") >= 0) {
                ext = submitted.substring(submitted.lastIndexOf(".")).toLowerCase();
            }
            String fileName = "cover_" + UUID.randomUUID() + ext;
            File saved = new File(uploadDir, fileName);

            Files.copy(filePart.getInputStream(), saved.toPath(), StandardCopyOption.REPLACE_EXISTING);

            finalCoverImageUrl = "/img/blog/" + fileName;
        }

        BlogPost post = new BlogPost();
        post.setTitle(title);
        post.setUrl(url); 
        post.setCategoryName(category);
        post.setAuthorName("JobCV");
        post.setPublishedDate(java.sql.Date.valueOf(LocalDate.now()));
        post.setCoverImageUrl(finalCoverImageUrl);
        post.setExcerpt(excerpt);
        post.setFeatured(false);
        post.setStatus(true);
        post.setViewCount(0);

        BlogPostDAO dao = new BlogPostDAO();
        try {
            int newPostId = dao.insertPostWithDetail(post, contentHtml);
            response.sendRedirect(request.getContextPath() + "/blog-list?created=1&postId=" + newPostId);
        } catch (Exception ex) {
            request.setAttribute("error", "Không thể lưu bài viết: " + ex.getMessage());
            request.getRequestDispatcher("/add-post.jsp").forward(request, response);
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

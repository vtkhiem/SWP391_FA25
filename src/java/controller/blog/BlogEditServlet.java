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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.BlogPost;
import model.BlogPostDetail;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="BlogEditServlet", urlPatterns={"/blog-list/edit"})
@MultipartConfig( 
    fileSizeThreshold = 1024 * 1024,     
    maxFileSize = 5L * 1024 * 1024,       
    maxRequestSize = 20L * 1024 * 1024   
)
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
   private static final int EXCERPT_MAX = 300;
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
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String sid = req.getParameter("id");
        if (sid == null || sid.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
            return;
        }

        try {
            int id = Integer.parseInt(sid);
            BlogPostDAO dao = new BlogPostDAO();
            BlogPost post = dao.getById(id);
            if (post == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
                return;
            }
            BlogPostDetail detail = dao.getDetailByPostId(id);
            req.setAttribute("post", post);
            req.setAttribute("detail", detail);
            req.getRequestDispatcher("/blog-edit.jsp").forward(req, resp);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
        } catch (Exception e) {
            throw new ServletException("Error loading blog post", e);
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
        resp.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(req.getParameter("postID"));
            String title = trimOrEmpty(req.getParameter("title"));
            String cate  = trimOrEmpty(req.getParameter("categoryName"));
            boolean status = "on".equals(req.getParameter("status"));
            String contentHtml = req.getParameter("contentHtml");
            if (contentHtml == null) contentHtml = "";

            String excerpt = trimOrEmpty(req.getParameter("excerpt"));
            if (excerpt.length() > EXCERPT_MAX) {
                excerpt = excerpt.substring(0, EXCERPT_MAX);
            }
            String oldCoverUrl = trimOrEmpty(req.getParameter("oldCoverUrl"));

            BlogPostDAO dao = new BlogPostDAO();
            dao.updateAdmin(id, title, cate, status);
            dao.updateExcerpt(id, excerpt);

            Part coverPart = null;
            try {
                coverPart = req.getPart("coverFile");
            } catch (IllegalStateException tooLarge) {
                resp.sendRedirect(req.getContextPath() + "/blog-list/edit?id=" + id + "&error=file_too_large");
                return;
            }

            String finalCoverUrl = oldCoverUrl;

            if (coverPart != null && coverPart.getSize() > 0) {
                String ct = coverPart.getContentType();
                if (ct == null || !(ct.equals("image/jpeg") || ct.equals("image/png") || ct.equals("image/webp"))) {
                    resp.sendRedirect(req.getContextPath() + "/blog-list/edit?id=" + id + "&error=invalid_image_type");
                    return;
                }
                String ext = mimeToExt(ct);
                String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
                String fileName = "post_" + id + "_" + stamp + ext;
                String relativeDir = "/img/blog";
                String realDir = getServletContext().getRealPath(relativeDir);
                if (realDir == null) {
                    realDir = System.getProperty("java.io.tmpdir") + File.separator + "img_blog";
                }

                File dir = new File(realDir);
                if (!dir.exists()) dir.mkdirs();

                Path savePath = dir.toPath().resolve(fileName);
                try (InputStream in = coverPart.getInputStream()) {
                    Files.copy(in, savePath);
                }
                finalCoverUrl = relativeDir + "/" + fileName;  
                dao.updateCoverImage(id, finalCoverUrl);

            }


            dao.upsertDetail(id, contentHtml, true);

            resp.sendRedirect(req.getContextPath() + "/blog-list/edit?id=" + id + "&saved=1");
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
        } catch (Exception e) {
            throw new ServletException("Error saving blog post", e);
        }
    }

    private static String trimOrEmpty(String s) { return s == null ? "" : s.trim(); }

    private static String mimeToExt(String ct) {
        switch (ct) {
            case "image/jpeg": return ".jpg";
            case "image/png":  return ".png";
            case "image/webp": return ".webp";
            default:           return "";
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

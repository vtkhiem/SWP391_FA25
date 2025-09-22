/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.CVDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import model.CV;
import model.Candidate;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="CVDeleteServlet", urlPatterns={"/delete-cv"})
public class CVDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int cvId = Integer.parseInt(request.getParameter("id"));
        CVDAO cvDao = new CVDAO();
        
        // Kiểm tra quyền xóa CV
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof Candidate) {
            Candidate candidate = (Candidate) userObj;
            CV cv = cvDao.getCVById(cvId);
            
            if (cv != null && cv.getCandidateID() == candidate.getCandidateId()) {
                // Xóa file CV
                String realPath = getServletContext().getRealPath("") + File.separator + cv.getFileData();
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
                
                // Xóa record trong database
                cvDao.deleteCV(cvId);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/cv-management");
    }

}

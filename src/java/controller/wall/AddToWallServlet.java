    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
     */
    package controller.wall;

    import dal.ServiceEmployerDAO;
    import dal.ServiceFunctionDAO;
    import dal.WallDAO;
    import java.io.IOException;
    import java.io.PrintWriter;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import java.util.List;
    import model.Function;

    /**
     *
     * @author vuthienkhiem
     */
    @WebServlet(name = "AddToWallServlet", urlPatterns = {"/addToWall"})
    public class AddToWallServlet extends HttpServlet {

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
                out.println("<title>Servlet AddToWallServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet AddToWallServlet at " + request.getContextPath() + "</h1>");
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
           try {
                String id_raw = request.getParameter("employerId");
                String job_raw = request.getParameter("jobpostId");
                if(id_raw==null || job_raw==null){
                    request.getRequestDispatcher("error.jsp");
                }
                int employerId = Integer.parseInt(id_raw);
                int jobpostId = Integer.parseInt(job_raw);
                ServiceEmployerDAO sedao= new ServiceEmployerDAO();
                        ServiceFunctionDAO sfdao = new ServiceFunctionDAO();
                int serviceId = sedao.getCurrentServiceByEmployerId(employerId);
                List<Function> list = sfdao.getFunctionsByServiceId(serviceId);
                  boolean hasWallFunction = false;
                 for (Function f : list) {
                    if (f.getFunctionName().equalsIgnoreCase("EmployerWall")) {
                        hasWallFunction = true;
                        break;
                    }
                }  if (hasWallFunction) {


                    WallDAO dao = new WallDAO();
                    if(!dao.isJobOnWall(employerId, jobpostId)){
                         boolean success = dao.addJobToWall(employerId, jobpostId);

                    if (success) {
                        request.getSession().setAttribute("message", "Đã thêm công việc lên tường!");
                    } else {
                        request.getSession().setAttribute("error", "Thêm công việc lên tường thất bại!");
                    }
                } else {
                   request.getSession().setAttribute("message", "Công việc đã có trên tường!");
                }
                    }else{
                     request.getSession().setAttribute("error", "Dịch vụ hiện tại của bạn không hỗ trợ đăng lên tường!");
                    
                }

                    response.sendRedirect("employer_jobs");

            } catch (Exception e) {
                   e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi thêm công việc lên tường!");
            response.sendRedirect("employer_jobs");
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
    
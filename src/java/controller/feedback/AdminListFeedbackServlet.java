package controller.feedback;

import dal.FeedbackDAO;
import dal.PromotionDAO;
import dal.ServiceDAO;
import dal.TypeFeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Feedback;
import model.TypeFeedback;

@WebServlet(name = "AdminListFeedbackServlet", urlPatterns = {"/adminFeedbackList"})
public class AdminListFeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ServiceDAO svDAO = new ServiceDAO();
        PromotionDAO pDAO = new PromotionDAO();
        TypeFeedbackDAO tfDAO = new TypeFeedbackDAO();
        
        try {
            // ✅ Lấy danh sách feedback
            List<Feedback> listAll = feedbackDAO.getAllFeedback();
            List<Feedback> listEmployers = feedbackDAO.getAllFeedbackFromEmployers();
            List<Feedback> listCandidates = feedbackDAO.getAllFeedbackFromCandidates();
            List<Feedback> listFromBoth = feedbackDAO.getAllFromBoth();
            int numF = listFromBoth.size();
int numE =listEmployers.size()-numF;
int numC = listCandidates.size()-numF;
int numA = listAll.size();

            // 🔍 DEBUG: Kiểm tra list có null không
            System.out.println("=== DEBUG FEEDBACK LISTS ===");
            System.out.println("listAll: " + (listAll == null ? "NULL" : "Size = " + listAll.size()));
            System.out.println("listEmployers: " + (listEmployers == null ? "NULL" : "Size = " + numE));
            System.out.println("listCandidates: " + (listCandidates == null ? "NULL" : "Size = " + numC));

            // ✅ Khởi tạo Map
            Map<Integer, String> serviceNameMap = new HashMap<>();
            Map<Integer, String> promotionNameMap = new HashMap<>();
            Map<Integer, List<TypeFeedback>> feedbackTypeMap = new HashMap<>();

            // ✅ GỘP TẤT CẢ FEEDBACK ĐỂ XỬ LÝ
            List<Feedback> allFeedbacksToProcess = new ArrayList<>();
            
            if (listAll != null) {
                allFeedbacksToProcess.addAll(listAll);
            }
            
            // Thêm feedback từ listEmployers nếu chưa có
            if (listEmployers != null) {
                for (Feedback f : listEmployers) {
                    if (!containsFeedback(allFeedbacksToProcess, f.getFeedbackID())) {
                        allFeedbacksToProcess.add(f);
                    }
                }
            }
            
            // Thêm feedback từ listCandidates nếu chưa có
            if (listCandidates != null) {
                for (Feedback f : listCandidates) {
                    if (!containsFeedback(allFeedbacksToProcess, f.getFeedbackID())) {
                        allFeedbacksToProcess.add(f);
                    }
                }
            }

            System.out.println("Total unique feedbacks to process: " + allFeedbacksToProcess.size());

            // ✅ Lấy thông tin chi tiết cho TẤT CẢ feedback
            for (Feedback f : allFeedbacksToProcess) {
                System.out.println("\n--- Processing Feedback ID: " + f.getFeedbackID() + " ---");
                
                // Lấy service name
                if (f.getServiceID() != null) {
                    try {
                        String serviceName = svDAO.getServiceNameById(f.getServiceID());
                        serviceNameMap.put(f.getFeedbackID(), serviceName);
                        System.out.println("  ServiceID: " + f.getServiceID() + " -> Name: " + serviceName);
                    } catch (Exception e) {
                        System.err.println("  ERROR getting service name: " + e.getMessage());
                    }
                } else {
                    System.out.println("  ServiceID: NULL");
                }
                
                // Lấy promotion name
                if (f.getPromotionID() != null) {
                    try {
                        String promotionName = pDAO.getCodeById(f.getPromotionID());
                        promotionNameMap.put(f.getFeedbackID(), promotionName);
                        System.out.println("  PromotionID: " + f.getPromotionID() + " -> Code: " + promotionName);
                    } catch (Exception e) {
                        System.err.println("  ERROR getting promotion name: " + e.getMessage());
                    }
                } else {
                    System.out.println("  PromotionID: NULL");
                }
                
                // Lấy feedback types
                try {
                    List<TypeFeedback> types = tfDAO.getTypesByFeedbackId(f.getFeedbackID());
                    feedbackTypeMap.put(f.getFeedbackID(), types);
                    System.out.println("  Types: " + (types == null ? "NULL" : types.size() + " types"));
                    if (types != null) {
                        for (TypeFeedback t : types) {
                            System.out.println("    - " + t.getTypeFeedbackName());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("  ERROR getting types: " + e.getMessage());
                }
            }

            // 🔍 DEBUG: Kiểm tra Map có dữ liệu không
            System.out.println("\n=== DEBUG MAPS ===");
            System.out.println("serviceNameMap size: " + serviceNameMap.size());
            System.out.println("promotionNameMap size: " + promotionNameMap.size());
            System.out.println("feedbackTypeMap size: " + feedbackTypeMap.size());
            
            // In chi tiết các key trong map
            System.out.println("\nserviceNameMap keys: " + serviceNameMap.keySet());
            System.out.println("promotionNameMap keys: " + promotionNameMap.keySet());
            System.out.println("feedbackTypeMap keys: " + feedbackTypeMap.keySet());

            // ✅ Gửi sang JSP
            request.setAttribute("listAll", listAll != null ? listAll : new ArrayList<>());
            request.setAttribute("listEmployers", listEmployers != null ? listEmployers : new ArrayList<>());
            request.setAttribute("listCandidates", listCandidates != null ? listCandidates : new ArrayList<>());
            request.setAttribute("feedbackTypeMap", feedbackTypeMap);
            request.setAttribute("serviceNameMap", serviceNameMap);
            request.setAttribute("promotionNameMap", promotionNameMap);
            
            System.out.println("\n✅ Forwarding to adminFeedback.jsp");
            request.getRequestDispatcher("adminFeedback.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.err.println("❌ SQLException: " + e.getMessage());
            e.printStackTrace();
            response.sendError(500, "Lỗi khi tải danh sách feedback: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
            response.sendError(500, "Lỗi không xác định: " + e.getMessage());
        }
    }

    /**
     * Helper method để kiểm tra feedback đã tồn tại trong list chưa
     */
    private boolean containsFeedback(List<Feedback> list, int feedbackID) {
        for (Feedback f : list) {
            if (f.getFeedbackID() == feedbackID) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin Feedback List Servlet with Debug";
    }
}
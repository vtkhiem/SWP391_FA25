/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.ServiceFunction;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import model.Function;

/**
 *
 * @author vuthienkhiem
 */
public class ServiceFunctionDAO extends DBContext{
    // ✅ Thêm liên kết giữa Service và Function
    public boolean addServiceFunction(ServiceFunction sf) throws SQLException {
        String sql = "INSERT INTO ServiceFunction (ServiceID, FunctionID) VALUES (?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, sf.getServiceID());
            ps.setInt(2, sf.getFunctionID());
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Xóa liên kết cụ thể giữa Service và Function
    public boolean deleteServiceFunction(int serviceID, int functionID) throws SQLException {
        String sql = "DELETE FROM ServiceFunction WHERE ServiceID = ? AND FunctionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            ps.setInt(2, functionID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Xóa toàn bộ function của 1 service
    public boolean deleteFunctionsByService(int serviceID) throws SQLException {
        String sql = "DELETE FROM ServiceFunction WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Lấy danh sách FunctionID theo ServiceID
    public List<Integer> getFunctionIdsByService(int serviceID) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT FunctionID FROM ServiceFunction WHERE ServiceID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("FunctionID"));
                }
            }
        }
        return list;
    }

    // ✅ Lấy danh sách ServiceID theo FunctionID
    public List<Integer> getServiceIdsByFunction(int functionID) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT ServiceID FROM ServiceFunction WHERE FunctionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, functionID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ServiceID"));
                }
            }
        }
        return list;
    }

    // ✅ Kiểm tra xem Service đã có Function cụ thể chưa
    public boolean exists(int serviceID, int functionID) throws SQLException {
        String sql = "SELECT 1 FROM ServiceFunction WHERE ServiceID = ? AND FunctionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            ps.setInt(2, functionID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // ✅ Lấy danh sách Function (đối tượng) của 1 Service
    public List<Function> getFunctionsByServiceId(int serviceID) throws SQLException {
        List<Function> functions = new ArrayList<>();
        String sql = """
            SELECT f.FunctionID, f.FunctionName
            FROM ServiceFunction sf
            JOIN Functions f ON sf.FunctionID = f.FunctionID
            WHERE sf.ServiceID = ?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Function func = new Function();
                    func.setFunctionID(rs.getInt("FunctionID"));
                    func.setFunctionName(rs.getString("FunctionName"));
                    functions.add(func);
                }
            }
        }
        return functions;
    }

}

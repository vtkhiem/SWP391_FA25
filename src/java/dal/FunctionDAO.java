/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Function;
import java.sql.*;

/**
 *
 * @author vuthienkhiem
 */
public class FunctionDAO extends DBContext{
    
     public List<Function> getAllFunctions() throws SQLException {
        List<Function> list = new ArrayList<>();
        String sql = "SELECT FunctionID, FunctionName FROM Functions ORDER BY FunctionID ASC";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Function f = new Function();
                f.setFunctionID(rs.getInt("FunctionID"));
                f.setFunctionName(rs.getString("FunctionName"));
             
                list.add(f);
            }
        }
        return list;
    }

    // ✅ Lấy function theo ID
    public Function getFunctionById(int id) throws SQLException {
        String sql = "SELECT FunctionID, FunctionName FROM Functions WHERE FunctionID = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Function(
                        rs.getInt("FunctionID"),
                        rs.getString("FunctionName")
                
                    );
                }
            }
        }
        return null;
    }
}

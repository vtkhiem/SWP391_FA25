/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.TypeFeedback;

/**
 *
 * @author vuthienkhiem
 */
public class TypeFeedbackDAO extends DBContext{
     public List<TypeFeedback> getTypeFeedbackByRole(String role) throws SQLException {
    String sql = "SELECT * FROM TypeFeedback WHERE RoleScope = ? OR RoleScope = 'Both'";
    PreparedStatement ps = c.prepareStatement(sql);
    ps.setString(1, role);
    ResultSet rs = ps.executeQuery();

    List<TypeFeedback> list = new ArrayList<>();
    while (rs.next()) {
        TypeFeedback tf = new TypeFeedback();
        tf.setTypeFeedbackID(rs.getInt("TypeFeedbackID"));
        tf.setTypeFeedbackName(rs.getString("TypeFeedbackName"));
        tf.setRoleScope(rs.getString("RoleScope"));
        list.add(tf);
    }
    return list;
}
 
public List<TypeFeedback> getTypesByFeedbackId(int feedbackId) throws SQLException {
    List<TypeFeedback> list = new ArrayList<>();
    String sql = """
        SELECT tf.TypeFeedbackID, tf.TypeFeedbackName, tf.RoleScope
        FROM FeedbackTypes ft
        JOIN TypeFeedback tf ON ft.TypeFeedbackID = tf.TypeFeedbackID
        WHERE ft.FeedbackID = ?
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, feedbackId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TypeFeedback t = new TypeFeedback();
                t.setTypeFeedbackID(rs.getInt("TypeFeedbackID"));
                t.setTypeFeedbackName(rs.getString("TypeFeedbackName"));
                t.setRoleScope(rs.getString("RoleScope"));
                list.add(t);
            }
        }
    }

    return list;
}
public List<TypeFeedback> getAllTypes() throws SQLException {
    List<TypeFeedback> list = new ArrayList<>();
    String sql = """
        SELECT TypeFeedbackID, TypeFeedbackName,RoleScope from TypeFeedback
       
    """;

    try (PreparedStatement ps = c.prepareStatement(sql)) {
     
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TypeFeedback t = new TypeFeedback();
                t.setTypeFeedbackID(rs.getInt("TypeFeedbackID"));
                t.setTypeFeedbackName(rs.getString("TypeFeedbackName"));
                t.setRoleScope(rs.getString("RoleScope"));
                list.add(t);
            }
        }
    }

    return list;
}


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Notification;
import java.sql.*;

/**
 *
 * @author vuthienkhiem
 */
public class NotificationDAO extends DBContext{
    public List<Notification> getNotifications(String receiverRole, int receiverID) {
        List<Notification> list = new ArrayList<>();
        String sql = """
                     SELECT NotificationID, SenderRole, ReceiverRole, ReceiverID, Message, SentDate, IsRead
                     FROM Notification
                     WHERE ReceiverRole = ? AND ReceiverID = ?
                     ORDER BY SentDate DESC
                     """;
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, receiverRole);
            st.setInt(2, receiverID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationID(rs.getInt("NotificationID"));
                n.setSenderRole(rs.getString("SenderRole"));
                n.setReceiverRole(rs.getString("ReceiverRole"));
                n.setReceiverID(rs.getInt("ReceiverID"));
                n.setMessage(rs.getString("Message"));
                n.setSentDate(rs.getTimestamp("SentDate"));
                n.setIsRead(rs.getBoolean("IsRead"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm số thông báo chưa đọc
    public int countUnread(String receiverRole, int receiverID) {
        String sql = "SELECT COUNT(*) FROM Notification WHERE ReceiverRole = ? AND ReceiverID = ? AND IsRead = 0";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, receiverRole);
            st.setInt(2, receiverID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Đánh dấu đã đọc
    public void markAsRead(int notificationID) {
        String sql = "UPDATE Notification SET IsRead = 1 WHERE NotificationID = ?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, notificationID);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Thêm thông báo mới
    public void addNotification(String senderRole, String receiverRole, int receiverID, String message) {
        String sql = """
                     INSERT INTO Notification (SenderRole, ReceiverRole, ReceiverID, Message)
                     VALUES (?, ?, ?, ?)
                     """;
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, senderRole);
            st.setString(2, receiverRole);
            st.setInt(3, receiverID);
            st.setString(4, message);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

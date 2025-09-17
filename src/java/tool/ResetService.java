package tool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

public class ResetService {
    private final int LIMIT_MINUTES = 10; // Thời hạn token
    private final String from = "vuthienkhiem2005@gmail.com";
    private final String password = "wgdj tgxv azcf ktxu"; // app password của Gmail (16 ký tự)

    // Sinh token random
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Thời gian hết hạn
    public LocalDateTime expireDateTime() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUTES);
    }

    // Kiểm tra hết hạn
    public boolean isExpireTime(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }

    // Gửi email reset mật khẩu
    public void sendEmail(String to, String link, String name) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); // sửa lỗi chính tả ở đây

            // Authenticator
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            };

            Session session = Session.getInstance(props, auth);

            // Soạn mail
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, "Support Team")); // hiển thị tên người gửi
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("Yêu cầu đặt lại mật khẩu");
            
            String htmlContent = "<h3>Xin chào " + name + ",</h3>"
                    + "<p>Bạn vừa yêu cầu đặt lại mật khẩu.</p>"
                    + "<p>Nhấn vào link bên dưới để đổi mật khẩu (hiệu lực 10 phút):</p>"
                    + "<a href='" + link + "'>Đổi mật khẩu</a>"
                    + "<br><br><p>Nếu không phải bạn yêu cầu, vui lòng bỏ qua email này.</p>";
            
            msg.setContent(htmlContent, "text/html; charset=UTF-8");

            // Gửi mail
            Transport.send(msg);

            System.out.println("Email đã được gửi thành công tới: " + to);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

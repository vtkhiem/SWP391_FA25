/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class Validation {

    public static String getTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(formatter);
    }

    public boolean checkLength(String check) {
        return check.length() >= 8;
    }

    public boolean checkTaxcode(String check) {
        return (check.length() == 10 || check.length() == 13);
    }

    public static boolean checkChar(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$");
    }

    public static String convertDateTimeToDate(String dateTimeStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);
        LocalDate date = parsedDateTime.toLocalDate();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(outputFormatter);
    }

    public static String searchKey(String key) {
        if(key==null) return "";
        return key.trim().replaceAll("\\s+", " ");
    }

    public static Integer getId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid id: " + input);
            return 0;
        }
    }

    public static Integer getPage(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 1;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid id: " + input);
            return 1;
        }
    }

    // Regex kiểm tra số điện thoại Việt Nam: cho phép +84 hoặc 0 ở đầu, theo sau là 9 hoặc 10 chữ số
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+84|0)(\\d{9})$");

    // Regex kiểm tra email (chuẩn cơ bản, không quá khắt khe)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");

    // Kiểm tra định dạng số điện thoại
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    // Kiểm tra định dạng email
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static void main(String[] args) {
        System.out.println(searchKey("  khiem vu     thien "));
    }
}

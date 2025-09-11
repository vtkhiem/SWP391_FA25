/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import java.security.MessageDigest;
import java.util.Base64;

/**
 *
 * @author Admin
 */
public class EncodePassword {
     public static String encodePasswordbyHash(String password) {
        String result = null;

        String salt = "vuthienkhiemhahahahahaqwertyuiop123456";

        password += salt;

        try {

            byte[] databyte = password.getBytes("UTF-8");    // chuyển về thành mảng byte 

            MessageDigest md = MessageDigest.getInstance("SHA-1"); //   MessageDigest 1 lớp để sử dụng các thuật toán  băm

            result = Base64.getEncoder().encodeToString(md.digest(databyte));  // Base64 giúp mã hóa dữ liệu nhị phân (byte/số) thành chuỗi ký tự dễ đọc, dễ lưu trữ.

//            byte[] t = md.digest(databyte);
//
//            for (byte k : t) {
//                System.out.println(k);
//            }
        } catch (Exception s) {
            System.out.println(s.getMessage());
        }

        return result;
    }

    public static void main(String[] args)  {

        System.out.println(encodePasswordbyHash("1234"));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Admin
 */
public class ValidationRegister {
   
    
    public static String getTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return now.format(formatter);
    }

  
    public boolean checkLength(String check) {
        return check.length() >= 8;
    }
    public boolean checkTaxcode(String check) {
        return (check.length() ==10 ||check.length()==13);
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


    
    
     public static String searchKey(String key){
         return key.trim().replaceAll("\\s+", " ");
     }
     
     
     
     
     
     public static void main(String[] args) {
         System.out.println(searchKey("  khiem vu     thien "));
    }
}

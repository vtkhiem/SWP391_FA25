/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.math.BigDecimal;
import java.sql.Date;
/**
 *
 * @author ADMIN
 */
public class CV {

    private int CVID;
    private int candidateID;
    private String fullName;
    private String address;
    private String email;
    private String position;
    private int numberExp;
    private String education;
    private String field;
    private BigDecimal currentSalary;
    private Date birthday;
    private String nationality;
    private String gender;
    private String fileData;

    private Date dayCreate;

    public CV() {
    }

        public CV(int CVID, int candidateID, String fullName, String address, String email, String position, int numberExp, String education, String field, BigDecimal currentSalary, Date birthday, String nationality, String gender, String fileData, Date dayCreate) {
        this.CVID = CVID;
        this.candidateID = candidateID;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.position = position;
        this.numberExp = numberExp;
        this.education = education;
        this.field = field;
        this.currentSalary = currentSalary;
        this.birthday = birthday;
        this.nationality = nationality;
        this.gender = gender;
        this.fileData = fileData;
        this.dayCreate = dayCreate;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setNumberExp(int numberExp) {
        this.numberExp = numberExp;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }



    public void setDayCreate(Date dayCreate) {
        this.dayCreate = dayCreate;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public int getNumberExp() {
        return numberExp;
    }

    public String getEducation() {
        return education;
    }

    public String getField() {
        return field;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public String getGender() {
        return gender;
    }

    public String getFileData() {
        return fileData;
    }


    public Date getDayCreate() {
        return dayCreate;
    }
    
    public int getCVID() {
        return CVID;
    }

    public void setCVID(int CVID) {
        this.CVID = CVID;
    }
    
}

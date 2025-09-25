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

    private int cvId;
    private int candidateId;

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

    public CV(int cvId, int candidateId, String fullName, String address, String email, String position, int numberExp, String education, String field, BigDecimal currentSalary, Date birthday, String nationality, String gender, String fileData, Date dayCreate) {
        this.cvId = cvId;
        this.candidateId = candidateId;
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

    public int getCvId() {
        return cvId;
    }

    public void setCvId(int cvId) {
        this.cvId = cvId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumberExp() {
        return numberExp;
    }

    public void setNumberExp(int numberExp) {
        this.numberExp = numberExp;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public BigDecimal getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(BigDecimal currentSalary) {
        this.currentSalary = currentSalary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public Date getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(Date dayCreate) {
        this.dayCreate = dayCreate;
    }



    
    


    
}
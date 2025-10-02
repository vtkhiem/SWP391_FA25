package model;

public class Employer {
    private int employerId;
    private String employerName;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String companyName;
    private String taxCode;
    private String description;
    private String location;
    private String urlWebsite;
    private String imgLogo;
    private boolean status;

    public Employer() {
    }
    

    public Employer(int employerId, String employerName, String email, String phoneNumber, String passwordHash, String companyName, String description, String location, String urlWebsite, String imgLogo) {
        this.employerId = employerId;
        this.employerName = employerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.companyName = companyName;
        this.description = description;
        this.location = location;
        this.urlWebsite = urlWebsite;
        this.imgLogo = imgLogo;
    }

    public Employer(int employerId, String employerName, String email, String phoneNumber, String passwordHash, String companyName, String taxCode, String description, String location, String urlWebsite, String imgLogo, boolean status) {
        this.employerId = employerId;
        this.employerName = employerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.companyName = companyName;
        this.taxCode = taxCode;
        this.description = description;
        this.location = location;
        this.urlWebsite = urlWebsite;
        this.imgLogo = imgLogo;
        this.status = status;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrlWebsite() {
        return urlWebsite;
    }

    public void setUrlWebsite(String urlWebsite) {
        this.urlWebsite = urlWebsite;
    }

    public String getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(String imgLogo) {
        this.imgLogo = imgLogo;
    }
}


package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JobPost {
    private int jobPostID;
    private int employerID;
    private String title;
    private String description;
    private String category;
    private String position;
    private String location;
    private BigDecimal offerMin;
    private BigDecimal offerMax;
    private int numberExp;
    private boolean visible;
    private String typeJob;
    private LocalDateTime dayCreate;

    // --- Constructors ---
    public JobPost() {
    }

    public JobPost(int jobPostID, int employerID, String title, String description,
            String category, String position, String location,
            BigDecimal offerMin, BigDecimal offerMax, int numberExp,
            boolean visible, String typeJob, LocalDateTime dayCreate) {
        this.jobPostID = jobPostID;
        this.employerID = employerID;
        this.title = title;
        this.description = description;
        this.category = category;
        this.position = position;
        this.location = location;
        this.offerMin = offerMin;
        this.offerMax = offerMax;
        this.numberExp = numberExp;
        this.visible = visible;
        this.typeJob = typeJob;
        this.dayCreate = dayCreate;
    }

    // --- Getters & Setters ---
    public int getJobPostID() {
        return jobPostID;
    }

    public void setJobPostID(int jobPostID) {
        this.jobPostID = jobPostID;
    }

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getOfferMin() {
        return offerMin;
    }

    public void setOfferMin(BigDecimal offerMin) {
        this.offerMin = offerMin;
    }

    public BigDecimal getOfferMax() {
        return offerMax;
    }

    public void setOfferMax(BigDecimal offerMax) {
        this.offerMax = offerMax;
    }

    public int getNumberExp() {
        return numberExp;
    }

    public void setNumberExp(int numberExp) {
        this.numberExp = numberExp;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTypeJob() {
        return typeJob;
    }

    public void setTypeJob(String typeJob) {
        this.typeJob = typeJob;
    }

    public LocalDateTime getDayCreate() {
        return dayCreate;
    }

    public void setDayCreate(LocalDateTime dayCreate) {
        this.dayCreate = dayCreate;
    }

    // --- toString() ---
    @Override
    public String toString() {
        return "JobPost{"
                + "jobPostID=" + jobPostID
                + ", employerID=" + employerID
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", category='" + category + '\''
                + ", position='" + position + '\''
                + ", location='" + location + '\''
                + ", offerMin=" + offerMin
                + ", offerMax=" + offerMax
                + ", numberExp=" + numberExp
                + ", visible=" + visible
                + ", typeJob='" + typeJob + '\''
                + ", dayCreate=" + dayCreate
                + '}';
    }

}


package ir.redmind.paasho.service.dto.mock;


import ir.redmind.paasho.domain.enumeration.GenderType;
import ir.redmind.paasho.domain.enumeration.PriceType;

public class CreateEventDTO {
    private String code;
    private String title;
    private String customTitle;
    private String description;
    private PriceType pricing;
    private float score;
    private String date;
    private String time;
    private double latitude;
    private double longitude;
    private int categoryId;
    private int ageLimitFrom;
    private int ageLimitTo;
    private GenderType gender;
    private String address;
    private String tel;
    private Long id;
    private Long capacity;

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PriceType getPricing() {
        return pricing;
    }

    public void setPricing(PriceType pricing) {
        this.pricing = pricing;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public int getAgeLimitFrom() {
        return ageLimitFrom;
    }

    public void setAgeLimitFrom(int ageLimitFrom) {
        this.ageLimitFrom = ageLimitFrom;
    }

    public int getAgeLimitTo() {
        return ageLimitTo;
    }

    public void setAgeLimitTo(int ageLimitTo) {
        this.ageLimitTo = ageLimitTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}

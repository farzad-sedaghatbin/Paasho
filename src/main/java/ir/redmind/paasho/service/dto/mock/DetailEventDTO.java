package ir.redmind.paasho.service.dto.mock;


import ir.redmind.paasho.domain.enumeration.GenderType;
import ir.redmind.paasho.domain.enumeration.PriceType;

import java.util.ArrayList;
import java.util.List;

public class DetailEventDTO {

    private String code;
    private String title;
    private String category;
    private List<String> pic=new ArrayList<>();
    private String date;
    private String time;
    private String description;
    private String address;
    private PriceType pricing;
    private float score;
    private String telegram;
    private String instagram;
    private String tel;
    private int participantNumber;
    private int ageLimitFrom;
    private int ageLimitTo;
    private GenderType gender;
    private double latitude;
    private double longitude;
    private int view;
    private JoinStatus joinStatus;
    private String creator;
    private int capacity;
    private Long id;
    private Long categoryId;


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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setParticipantNumber(int participantNumber) {
        this.participantNumber = participantNumber;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public int getParticipantNumber() {
        return participantNumber;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getView() {
        return view;
    }

    public JoinStatus getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
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

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

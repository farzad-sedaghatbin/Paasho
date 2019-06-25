package ir.redmind.paasho.service.dto.mock;

import ir.redmind.paasho.domain.enumeration.PriceType;

public class MapEventDTO {
    private String code;
    private String title;
    private PriceType pricing;
    private float score;
    private Long pic;
    private String date;
    private String time;
    private double latitude;
    private double longitude;
    private int categoryId;
    private Long view;
    private boolean editable;


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

    public Long getPic() {
        return pic;
    }

    public void setPic(Long pic) {
        this.pic = pic;
    }

    public boolean isEditable() {
        return editable;
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

    public void setView(Long view) {
        this.view = view;
    }

    public Long getView() {
        return view;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean getEditable() {
        return editable;
    }
}

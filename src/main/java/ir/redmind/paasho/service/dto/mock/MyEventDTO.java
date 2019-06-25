package ir.redmind.paasho.service.dto.mock;

public class MyEventDTO {
    private String code;
    private String title;
    private float score;
    private Long pic;
    private Long id;
    private boolean editable;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean getEditable() {
        return editable;
    }
}

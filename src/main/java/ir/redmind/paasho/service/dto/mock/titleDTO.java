package ir.redmind.paasho.service.dto.mock;

public class titleDTO {
    private String title;
    private Long id;

    public titleDTO(String s, Long i) {
        title=s;
        id=i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

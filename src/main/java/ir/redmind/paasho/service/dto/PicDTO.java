package ir.redmind.paasho.service.dto;

public class PicDTO {
    String url;
    Long id;

    public PicDTO(String s) {
        url=s;
    }

    public PicDTO() {
    }

    public PicDTO(String url, Long id) {
        this.url = url;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

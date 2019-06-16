package ir.redmind.paasho.service.dto;

public class PicDTO {
    String url;

    public PicDTO(String s) {
        url=s;
    }

    public PicDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

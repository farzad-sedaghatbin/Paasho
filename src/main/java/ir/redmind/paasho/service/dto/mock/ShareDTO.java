package ir.redmind.paasho.service.dto.mock;

public class ShareDTO {

    private String user;
    private String content;
    private String androidMarketURL;
    private String iosMarketURL;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAndroidMarketURL() {
        return androidMarketURL;
    }

    public void setAndroidMarketURL(String androidMarketURL) {
        this.androidMarketURL = androidMarketURL;
    }

    public String getIosMarketURL() {
        return iosMarketURL;
    }

    public void setIosMarketURL(String iosMarketURL) {
        this.iosMarketURL = iosMarketURL;
    }
}

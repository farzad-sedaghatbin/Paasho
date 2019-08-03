package ir.redmind.paasho.service.dto.mock;

public class NotificationDTO {
    private String requestId;
    private String relatedUserId;
    private String relatedUserTelegram;
    private String relatedUserInstagram;
    private String relatedUserName;
    private String relatedEventcode;
    private boolean pending;
    private String text;
    private String avatar;


    public String getRelatedUserTelegram() {
        return relatedUserTelegram;
    }

    public void setRelatedUserTelegram(String relatedUserTelegram) {
        this.relatedUserTelegram = relatedUserTelegram;
    }

    public String getRelatedUserInstagram() {
        return relatedUserInstagram;
    }

    public void setRelatedUserInstagram(String relatedUserInstagram) {
        this.relatedUserInstagram = relatedUserInstagram;
    }

    public String getRelatedUserName() {
        return relatedUserName;
    }

    public void setRelatedUserName(String relatedUserName) {
        this.relatedUserName = relatedUserName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(String relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public String getRelatedEventcode() {
        return relatedEventcode;
    }

    public void setRelatedEventcode(String relatedEventcode) {
        this.relatedEventcode = relatedEventcode;
    }

    public void setAvatar(String avatar) {

        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }
}

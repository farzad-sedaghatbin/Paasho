package ir.redmind.paasho.service.dto;
import ir.redmind.paasho.domain.User;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Comment entity.
 */
public class ChatDTO implements Serializable {

    private Long id;
    private Long firstId;
    private String firstAvatar;
    private String secondAvatar;
    private Long secondId;

    private String text;

    private boolean firstRead;
    private boolean secondRead;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstId() {
        return firstId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public Long getSecondId() {
        return secondId;
    }

    public void setSecondId(Long secondId) {
        this.secondId = secondId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFirstRead() {
        return firstRead;
    }

    public void setFirstRead(boolean firstRead) {
        this.firstRead = firstRead;
    }

    public boolean isSecondRead() {
        return secondRead;
    }

    public void setSecondRead(boolean secondRead) {
        this.secondRead = secondRead;
    }

    public String getSecondAvatar() {
        return secondAvatar;
    }

    public void setSecondAvatar(String secondAvatar) {
        this.secondAvatar = secondAvatar;
    }

    public String getFirstAvatar() {
        return firstAvatar;
    }

    public void setFirstAvatar(String firstAvatar) {
        this.firstAvatar = firstAvatar;
    }
}

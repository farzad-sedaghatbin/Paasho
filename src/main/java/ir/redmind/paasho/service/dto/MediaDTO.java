package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.Objects;
import ir.redmind.paasho.domain.enumeration.MediaType;

/**
 * A DTO for the Media entity.
 */
public class MediaDTO implements Serializable {

    private Long id;

    private String path;

    private MediaType type;


    private Long mediaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long eventId) {
        this.mediaId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (mediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", type='" + getType() + "'" +
            ", media=" + getMediaId() +
            "}";
    }
}

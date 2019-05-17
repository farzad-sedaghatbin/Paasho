package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ir.redmind.paasho.domain.enumeration.NotificationType;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;

/**
 * A DTO for the Notification entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    private String description;

    private NotificationType type;

    private NotificationStatus status;


    private Set<UserDTO> users = new HashSet<>();

    private Long fromId;

    private String fromLogin;

    private Long relatedEventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(Long userId) {
        this.fromId = userId;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String userLogin) {
        this.fromLogin = userLogin;
    }

    public Long getRelatedEventId() {
        return relatedEventId;
    }

    public void setRelatedEventId(Long eventId) {
        this.relatedEventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", from=" + getFromId() +
            ", from='" + getFromLogin() + "'" +
            ", relatedEvent=" + getRelatedEventId() +
            "}";
    }
}

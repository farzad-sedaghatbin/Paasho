package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ir.redmind.paasho.domain.enumeration.GenderType;

/**
 * A DTO for the User1 entity.
 */
public class User1DTO implements Serializable {

    private Long id;

    private String name;

    private String lastName;

    private GenderType gender;

    private String birthDate;


    private Set<CategoryDTO> favorits = new HashSet<>();

    private Long eventId;

    private Long notificationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Set<CategoryDTO> getFavorits() {
        return favorits;
    }

    public void setFavorits(Set<CategoryDTO> categories) {
        this.favorits = categories;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User1DTO user1DTO = (User1DTO) o;
        if (user1DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), user1DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User1DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", event=" + getEventId() +
            ", notification=" + getNotificationId() +
            "}";
    }
}

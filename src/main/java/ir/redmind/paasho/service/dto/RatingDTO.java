package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rating entity.
 */
public class RatingDTO implements Serializable {

    private Long id;

    private Double rate;


    private Long eventId;

    private Long idId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getIdId() {
        return idId;
    }

    public void setIdId(Long user1Id) {
        this.idId = user1Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (ratingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", rate=" + getRate() +
            ", event=" + getEventId() +
            ", id=" + getIdId() +
            "}";
    }
}

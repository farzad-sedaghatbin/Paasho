package ir.redmind.paasho.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import ir.redmind.paasho.domain.enumeration.FactorStatus;

/**
 * A DTO for the Factor entity.
 */
public class FactorDTO implements Serializable {

    private Long id;

    private BigDecimal price;

    private String code;

    private FactorStatus status;

    private ZonedDateTime completeDate;

    private ZonedDateTime issueDate;


    private Long eventId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FactorStatus getStatus() {
        return status;
    }

    public void setStatus(FactorStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(ZonedDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactorDTO factorDTO = (FactorDTO) o;
        if (factorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactorDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", code='" + getCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", completeDate='" + getCompleteDate() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", event=" + getEventId() +
            "}";
    }
}

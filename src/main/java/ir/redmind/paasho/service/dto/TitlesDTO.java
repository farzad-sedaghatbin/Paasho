package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Titles entity.
 */
public class TitlesDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TitlesDTO titlesDTO = (TitlesDTO) o;
        if (titlesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), titlesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TitlesDTO{" +
            "id=" + getId() +
            "}";
    }
}

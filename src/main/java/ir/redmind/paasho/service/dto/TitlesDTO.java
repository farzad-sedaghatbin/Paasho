package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Titles entity.
 */
public class TitlesDTO implements Serializable {

    private Long id;

    private String title;


    private Long categoryId;
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
            ", title='" + getTitle() + "'" +
            ", category=" + getCategoryId() +
            "}";
    }
}

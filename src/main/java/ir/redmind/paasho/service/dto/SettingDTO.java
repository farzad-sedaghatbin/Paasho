package ir.redmind.paasho.service.dto;
import java.io.Serializable;
import java.util.Objects;
import ir.redmind.paasho.domain.enumeration.SettingKey;

/**
 * A DTO for the Setting entity.
 */
public class SettingDTO implements Serializable {

    private Long id;

    private SettingKey key;

    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SettingKey getKey() {
        return key;
    }

    public void setKey(SettingKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SettingDTO settingDTO = (SettingDTO) o;
        if (settingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettingDTO{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}

package ir.redmind.paasho.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import ir.redmind.paasho.domain.enumeration.PriceType;
import ir.redmind.paasho.domain.enumeration.EventStatus;

/**
 * A DTO for the Event entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    private ZonedDateTime eventTime;

    private String description;

    private String code;

    private String title;

    private Integer maxAge;

    private Integer minAge;

    private PriceType priceType;

    private EventStatus status;

    private String address;

    private Integer visitCount;

    private Double latitude;

    private Double longitude;

    private Integer likes;

    @Lob
    private byte[] files;

    private String filesContentType;
    private String tel;

    private String instagram;

    private String telegram;

    private Long capacity;

    private String customTitle;

    private String dateString;

    private String timeString;


    private Set<CategoryDTO> categories = new HashSet<>();

    private Long titlesId;

    private Set<UserDTO> participants = new HashSet<>();

    private Set<MediaDTO> medias = new HashSet<>();

    private Long creatorId;

    private String creatorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public byte[] getFiles() {
        return files;
    }

    public void setFiles(byte[] files) {
        this.files = files;
    }

    public String getFilesContentType() {
        return filesContentType;
    }

    public void setFilesContentType(String filesContentType) {
        this.filesContentType = filesContentType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Long getTitlesId() {
        return titlesId;
    }

    public void setTitlesId(Long titlesId) {
        this.titlesId = titlesId;
    }

    public Set<UserDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserDTO> users) {
        this.participants = users;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long userId) {
        this.creatorId = userId;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String userLogin) {
        this.creatorLogin = userLogin;
    }

    public Set<MediaDTO> getMedias() {
        return medias;
    }

    public void setMedias(Set<MediaDTO> medias) {
        this.medias = medias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", eventTime='" + getEventTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", maxAge=" + getMaxAge() +
            ", minAge=" + getMinAge() +
            ", priceType='" + getPriceType() + "'" +
            ", status='" + getStatus() + "'" +
            ", address='" + getAddress() + "'" +
            ", visitCount=" + getVisitCount() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", likes=" + getLikes() +
            ", files='" + getFiles() + "'" +
            ", tel='" + getTel() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", telegram='" + getTelegram() + "'" +
            ", capacity=" + getCapacity() +
            ", customTitle='" + getCustomTitle() + "'" +
            ", dateString='" + getDateString() + "'" +
            ", timeString='" + getTimeString() + "'" +
            ", titles=" + getTitlesId() +
            ", creator=" + getCreatorId() +
            ", creator='" + getCreatorLogin() + "'" +
            "}";
    }
}

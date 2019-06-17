package ir.redmind.paasho.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ir.redmind.paasho.domain.enumeration.PriceType;

import ir.redmind.paasho.domain.enumeration.EventStatus;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "event_time")
    private ZonedDateTime eventTime;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "min_age")
    private Integer minAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type")
    private PriceType priceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    @Column(name = "address")
    private String address;

    @Column(name = "visit_count")
    private Integer visitCount=0;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "likes")
    private Integer likes;

    @Lob
    @Column(name = "files")
    private byte[] files;

    @Column(name = "files_content_type")
    private String filesContentType;

    @Column(name = "tel")
    private String tel;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "custom_title")
    private String customTitle;

    @Column(name = "date_string")
    private String dateString;

    @Column(name = "time_string")
    private String timeString;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Media> medias = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_categories",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> rates = new HashSet<>();
    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Factor> factors = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("events")
    private Titles titles;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_participants",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"))
    private Set<User> participants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("events")
    private User creator;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public Event eventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public void setEventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public Event code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public Event title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public Event maxAge(Integer maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public Event minAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public Event priceType(PriceType priceType) {
        this.priceType = priceType;
        return this;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public EventStatus getStatus() {
        return status;
    }

    public Event status(EventStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public Event address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVisitCount() {
        if(visitCount==null)
            return 0;
        return visitCount;
    }

    public Event visitCount(Integer visitCount) {
        this.visitCount = visitCount;
        return this;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Event latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Event longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getLikes() {
        return likes;
    }

    public Event likes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public byte[] getFiles() {
        return files;
    }

    public Event files(byte[] files) {
        this.files = files;
        return this;
    }

    public void setFiles(byte[] files) {
        this.files = files;
    }

    public String getFilesContentType() {
        return filesContentType;
    }

    public Event filesContentType(String filesContentType) {
        this.filesContentType = filesContentType;
        return this;
    }

    public void setFilesContentType(String filesContentType) {
        this.filesContentType = filesContentType;
    }

    public String getTel() {
        return tel;
    }

    public Event tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInstagram() {
        return instagram;
    }

    public Event instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTelegram() {
        return telegram;
    }

    public Event telegram(String telegram) {
        this.telegram = telegram;
        return this;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public Long getCapacity() {
        return capacity;
    }

    public Event capacity(Long capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getCustomTitle() {
        return customTitle;
    }

    public Event customTitle(String customTitle) {
        this.customTitle = customTitle;
        return this;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public String getDateString() {
        return dateString;
    }

    public Event dateString(String dateString) {
        this.dateString = dateString;
        return this;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public Event timeString(String timeString) {
        this.timeString = timeString;
        return this;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public Event medias(Set<Media> media) {
        this.medias = media;
        return this;
    }

    public Event addMedias(Media media) {
        this.medias.add(media);
        media.setEvent(this);
        return this;
    }

    public Event removeMedias(Media media) {
        this.medias.remove(media);
        media.setEvent(null);
        return this;
    }

    public void setMedias(Set<Media> media) {
        this.medias = media;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Event categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Event addCategories(Category category) {
        this.categories.add(category);
        category.getEvents().add(this);
        return this;
    }

    public Event removeCategories(Category category) {
        this.categories.remove(category);
        category.getEvents().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Rating> getRates() {
        return rates;
    }

    public Event rates(Set<Rating> ratings) {
        this.rates = ratings;
        return this;
    }

    public Event addRates(Rating rating) {
        this.rates.add(rating);
        rating.setEvent(this);
        return this;
    }

    public Event removeRates(Rating rating) {
        this.rates.remove(rating);
        rating.setEvent(null);
        return this;
    }

    public void setRates(Set<Rating> ratings) {
        this.rates = ratings;
    }

    public Set<Factor> getFactors() {
        return factors;
    }

    public Event factors(Set<Factor> factors) {
        this.factors = factors;
        return this;
    }

    public Event addFactors(Factor factor) {
        this.factors.add(factor);
        factor.setEvent(this);
        return this;
    }

    public Event removeFactors(Factor factor) {
        this.factors.remove(factor);
        factor.setEvent(null);
        return this;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
    }

    public Titles getTitles() {
        return titles;
    }

    public Event titles(Titles titles) {
        this.titles = titles;
        return this;
    }

    public void setTitles(Titles titles) {
        this.titles = titles;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public Event participants(Set<User> users) {
        this.participants = users;
        return this;
    }

    public Event addParticipants(User user) {
        this.participants.add(user);
        return this;
    }

    public Event removeParticipants(User user) {
        this.participants.remove(user);
        return this;
    }

    public void setParticipants(Set<User> users) {
        this.participants = users;
    }

    public User getCreator() {
        return creator;
    }

    public Event creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Event comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Event addComments(Comment comment) {
        this.comments.add(comment);
        comment.setEvent(this);
        return this;
    }

    public Event removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setEvent(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
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
            ", filesContentType='" + getFilesContentType() + "'" +
            ", tel='" + getTel() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", telegram='" + getTelegram() + "'" +
            ", capacity=" + getCapacity() +
            ", customTitle='" + getCustomTitle() + "'" +
            ", dateString='" + getDateString() + "'" +
            ", timeString='" + getTimeString() + "'" +
            "}";
    }
}

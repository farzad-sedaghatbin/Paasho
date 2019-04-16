package ir.redmind.paasho.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
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
@Document(indexName = "event")
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
    private Integer visitCount;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "likes")
    private Integer likes;

    @OneToOne
    @JoinColumn(unique = true)
    private User1 creator;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Media> medias = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_participants",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"))
    private Set<User1> participants = new HashSet<>();

    @ManyToMany
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

    public User1 getCreator() {
        return creator;
    }

    public Event creator(User1 user1) {
        this.creator = user1;
        return this;
    }

    public void setCreator(User1 user1) {
        this.creator = user1;
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

    public Set<User1> getParticipants() {
        return participants;
    }

    public Event participants(Set<User1> user1S) {
        this.participants = user1S;
        return this;
    }

    public Event addParticipants(User1 user1) {
        this.participants.add(user1);
        user1.getEvents().add(this);
        return this;
    }

    public Event removeParticipants(User1 user1) {
        this.participants.remove(user1);
        user1.getEvents().remove(this);
        return this;
    }

    public void setParticipants(Set<User1> user1S) {
        this.participants = user1S;
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
        category.getCategories().add(this);
        return this;
    }

    public Event removeCategories(Category category) {
        this.categories.remove(category);
        category.getCategories().remove(this);
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

    public Event addRate(Rating rating) {
        this.rates.add(rating);
        rating.setEvent(this);
        return this;
    }

    public Event removeRate(Rating rating) {
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

    public Event addFactor(Factor factor) {
        this.factors.add(factor);
        factor.setEvent(this);
        return this;
    }

    public Event removeFactor(Factor factor) {
        this.factors.remove(factor);
        factor.setEvent(null);
        return this;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
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
            "}";
    }
}

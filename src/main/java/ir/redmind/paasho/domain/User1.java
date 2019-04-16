package ir.redmind.paasho.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ir.redmind.paasho.domain.enumeration.GenderType;

/**
 * A User1.
 */
@Entity
@Table(name = "user_1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "user1")
public class User1 implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "birth_date")
    private String birthDate;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contacts = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> rates = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_1_favorits",
               joinColumns = @JoinColumn(name = "user1_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "favorits_id", referencedColumnName = "id"))
    private Set<Category> favorits = new HashSet<>();

    @OneToOne(mappedBy = "creator")
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties("users")
    private Notification notification;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Factor> factors = new HashSet<>();
    @ManyToMany(mappedBy = "participants")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User1 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public User1 lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public User1 gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public User1 birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public User1 contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public User1 addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setUser(this);
        return this;
    }

    public User1 removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setUser(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Rating> getRates() {
        return rates;
    }

    public User1 rates(Set<Rating> ratings) {
        this.rates = ratings;
        return this;
    }

    public User1 addRates(Rating rating) {
        this.rates.add(rating);
        rating.setUser(this);
        return this;
    }

    public User1 removeRates(Rating rating) {
        this.rates.remove(rating);
        rating.setUser(null);
        return this;
    }

    public void setRates(Set<Rating> ratings) {
        this.rates = ratings;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public User1 comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public User1 addComments(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
        return this;
    }

    public User1 removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Category> getFavorits() {
        return favorits;
    }

    public User1 favorits(Set<Category> categories) {
        this.favorits = categories;
        return this;
    }

    public User1 addFavorits(Category category) {
        this.favorits.add(category);
        category.getUsers().add(this);
        return this;
    }

    public User1 removeFavorits(Category category) {
        this.favorits.remove(category);
        category.getUsers().remove(this);
        return this;
    }

    public void setFavorits(Set<Category> categories) {
        this.favorits = categories;
    }

    public Event getEvent() {
        return event;
    }

    public User1 event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Notification getNotification() {
        return notification;
    }

    public User1 notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Set<Factor> getFactors() {
        return factors;
    }

    public User1 factors(Set<Factor> factors) {
        this.factors = factors;
        return this;
    }

    public User1 addFactors(Factor factor) {
        this.factors.add(factor);
        factor.setUser(this);
        return this;
    }

    public User1 removeFactors(Factor factor) {
        this.factors.remove(factor);
        factor.setUser(null);
        return this;
    }

    public void setFactors(Set<Factor> factors) {
        this.factors = factors;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public User1 events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public User1 addEvents(Event event) {
        this.events.add(event);
        event.getParticipants().add(this);
        return this;
    }

    public User1 removeEvents(Event event) {
        this.events.remove(event);
        event.getParticipants().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
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
        User1 user1 = (User1) o;
        if (user1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), user1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User1{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}

package ir.redmind.paasho.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "icon")
    private String icon;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToMany(mappedBy = "favorits")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<User1> ids = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Event> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public Category icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Category code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<User1> getIds() {
        return ids;
    }

    public Category ids(Set<User1> user1S) {
        this.ids = user1S;
        return this;
    }

    public Category addId(User1 user1) {
        this.ids.add(user1);
        user1.getFavorits().add(this);
        return this;
    }

    public Category removeId(User1 user1) {
        this.ids.remove(user1);
        user1.getFavorits().remove(this);
        return this;
    }

    public void setIds(Set<User1> user1S) {
        this.ids = user1S;
    }

    public Set<Event> getIds() {
        return ids;
    }

    public Category ids(Set<Event> events) {
        this.ids = events;
        return this;
    }

    public Category addId(Event event) {
        this.ids.add(event);
        event.getCategories().add(this);
        return this;
    }

    public Category removeId(Event event) {
        this.ids.remove(event);
        event.getCategories().remove(this);
        return this;
    }

    public void setIds(Set<Event> events) {
        this.ids = events;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}

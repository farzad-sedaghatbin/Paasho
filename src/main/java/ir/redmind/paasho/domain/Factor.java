package ir.redmind.paasho.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import ir.redmind.paasho.domain.enumeration.FactorStatus;

/**
 * A Factor.
 */
@Entity
@Table(name = "factor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "factor")
public class Factor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FactorStatus status;

    @Column(name = "complete_date")
    private ZonedDateTime completeDate;

    @Column(name = "issue_date")
    private ZonedDateTime issueDate;

    @ManyToOne
    @JsonIgnoreProperties("factors")
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Factor price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public Factor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FactorStatus getStatus() {
        return status;
    }

    public Factor status(FactorStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FactorStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCompleteDate() {
        return completeDate;
    }

    public Factor completeDate(ZonedDateTime completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public void setCompleteDate(ZonedDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public Factor issueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Event getEvent() {
        return event;
    }

    public Factor event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        Factor factor = (Factor) o;
        if (factor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Factor{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", code='" + getCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", completeDate='" + getCompleteDate() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            "}";
    }
}

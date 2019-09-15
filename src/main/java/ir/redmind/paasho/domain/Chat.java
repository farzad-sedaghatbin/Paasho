package ir.redmind.paasho.domain;

import ir.redmind.paasho.web.rest.yeka.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Chat implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User first;
    @ManyToOne
    private User second;

    @Lob
    @Type(type = "text")
    private String text;

    private boolean firstRead;
    private boolean secondRead;
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirst() {
        return first;
    }

    public void setFirst(User first) {
        this.first = first;
    }

    public User getSecond() {
        return second;
    }

    public void setSecond(User second) {
        this.second = second;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFirstRead() {
        return firstRead;
    }

    public void setFirstRead(boolean firstRead) {
        this.firstRead = firstRead;
    }

    public boolean isSecondRead() {
        return secondRead;
    }

    public void setSecondRead(boolean secondRead) {
        this.secondRead = secondRead;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

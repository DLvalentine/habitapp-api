package io.github.dlvalentine.habitappapi.models;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "habit_activity")
public class HabitActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = true)
    @Column(name = "comment")
    private String comment;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "hid")
    private Integer hid;

    @Column(name = "uid")
    private Integer uid;

    public Integer getId() {
        return this.id;
    }

    public String getComment() {
        return  this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreated() {
        return this.created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Integer getHid() {
        return this.hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}

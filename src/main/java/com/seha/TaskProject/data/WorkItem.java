package com.seha.TaskProject.data;

import com.seha.TaskProject.data.workitemenum.Status;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public final class WorkItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date_updated")
    @UpdateTimestamp
    private LocalDate updateDate;

    @ManyToOne
    @XmlTransient
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> helpers;

    @OneToOne(orphanRemoval = true)
    @XmlTransient
    private Issue issue;

    protected WorkItem() {
        this.name = null;
        this.description = null;
        this.helpers = new ArrayList<>();
    }

    public WorkItem(String name, String description) {
        this.name = name;
        this.description = description;
        this.helpers = new ArrayList<>();
        status = Status.UNSTARTED;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public boolean addHelper(User helper) {
        return this.helpers.add(helper);
    }

    public boolean removeHelper(User helper) {
        return this.helpers.remove(helper);
    }
}

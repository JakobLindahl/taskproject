package com.seha.TaskProject.data;

import com.seha.TaskProject.data.workitemenum.Status;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;

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

    @OneToOne(orphanRemoval = true)
    @XmlTransient
    private Issue issue;

    protected WorkItem() {
        this.name = null;
        this.description = null;
    }

    public WorkItem(String name, String description) {
        this.name = name;
        this.description = description;
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
}

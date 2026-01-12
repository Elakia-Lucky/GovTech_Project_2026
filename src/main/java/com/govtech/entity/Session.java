package com.govtech.entity;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "initiated_by")
    private User initiatedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    public enum Status {
        OPEN,
        CLOSED
    }


    public Session() {
    }

    public Session(User initiatedBy) {
        this.initiatedBy = initiatedBy;
        this.status = Status.OPEN;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(User initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

package com.govtech.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "RESTAURANT_OPTION")
public class RestaurantOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String submittedBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private Session session;


    public RestaurantOption() {
    }

    public RestaurantOption(String restaurantName, String submittedBy, Session session) {
        this.restaurantName = restaurantName;
        this.submittedBy = submittedBy;
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

package com.MealTable.meal_table.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String userEmail;

    public Review() {}

    public Review(int rating, String comment, String userEmail) {
        this.rating = rating;
        this.comment = comment;
        this.userEmail = userEmail;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

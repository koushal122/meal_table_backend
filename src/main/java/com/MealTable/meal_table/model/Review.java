package com.MealTable.meal_table.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}

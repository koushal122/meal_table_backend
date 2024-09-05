package com.MealTable.meal_table.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "phone_number",nullable = false)
    private long phoneNumber;

    @Column(name = "building_name",nullable = false)
    private String buildingName;

    @Column(name = "town_name",nullable = false)
    private String townName;

    @Column(name = "district_name",nullable = false)
    private String districtName;

    @Column(name = "pincode",nullable = false)
    private int pincode;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "state_name",nullable = false)
    private String stateName;

}

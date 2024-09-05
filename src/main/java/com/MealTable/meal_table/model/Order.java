package com.MealTable.meal_table.model;


import com.MealTable.meal_table.helper.ProductWithQuantity;
import com.MealTable.meal_table.util.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //foreign key point to id column of table user
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "orderStatus")
    private OrderStatus orderStatus;

    @ManyToOne()
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "deliveryNotes")
    private String deliveryNotes;

    @Column(name = "totalPrice")
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductQuantity> orderProductQuantityList = new ArrayList<>();

    @Column(name = "requestedTime")
    private long requestedTime;

    @Column(name = "orderConfirmed")
    private long orderConfirmed;

    @Column(name = "orderDispatched")
    private long orderDispatched;

    @Column(name = "cancelRequested")
    private long cancelRequested;

    @Column(name = "cancelRejected")
    private long cancelRejected;

    @Column(name = "cancelApproved")
    private long cancelApproved;

    @Column(name = "returnRequested")
    private long returnRequested;

    @Column(name = "returnRejected")
    private long returnRejected;

    @Column(name = "returnApproved")
    private long returnApproved;

    @Column(name = "delivered")
    private long delivered;

    @Column(name = "outForPickUp")
    private long outForPickUp;

    @Column(name = "returned")
    private long returned;

}


package com.MealTable.meal_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int numberOfItems;

    //here user is another table and user_id is foreign key referencing
    //primary key of user table. This we need because we have specified relationship
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //here because relationship is many to many that's why
    //we have created another table whose name will be cart_product and there will be
    //two foreign key in table one is cart_id point to current cart table
    //and another is product_id point to primary key of product table.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    //here we used element collection because jpa can handle only basic datatype such has int,string etc..
    //but for complex data type such as collection we need to create another table like below we have created.
    //custom table named cart_product_entity and there will be foreign key cart_id and product_id and also there will
    //another column quantity.
    @ElementCollection
    @CollectionTable(name = "cart_product_quantity", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Integer, Integer> productQuantityWithId = new HashMap<>();

    @Column(nullable = false)
    private double totalPrice;

}

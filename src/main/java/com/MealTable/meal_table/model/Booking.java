package com.MealTable.meal_table.model;


import com.MealTable.meal_table.util.BookingStatus;
import com.MealTable.meal_table.util.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int seats;

    @Column(nullable = false)
    private long startTime;

    @Column(nullable = false)
    private long endTime;

    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private double amountPaid;

    @Column(nullable = false)
    private double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private List<Seat> seatsBooked;

    @Column(name = "paymentStatus")
    private PaymentStatus paymentStatus;

    @Column(name = "phoneNumber")
    private long phoneNumber;

}

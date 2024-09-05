package com.MealTable.meal_table.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private int requiredSeats;
    private long startTime;
    private long endTime;
    private double amountPaid;
    private double totalPrice;
    private long phoneNumber;
}

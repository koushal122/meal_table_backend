package com.MealTable.meal_table.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private int id;
    private int seatsBooked;
    private long startTime;
    private long endTime;
    private long mobileNumber;
    Map<Integer,Integer> tableIDWithSeats;
}

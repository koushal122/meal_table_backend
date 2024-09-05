package com.MealTable.meal_table.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {
    private String message;
    private String subject;
    private long phoneNumber;
}

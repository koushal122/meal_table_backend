package com.MealTable.meal_table.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignUpRequest {
    private String adminName;
    private String newAdminEmail;
    private String password;
    private boolean canCreateAdmin;
}

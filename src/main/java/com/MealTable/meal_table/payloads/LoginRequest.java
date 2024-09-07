package com.MealTable.meal_table.payloads;


import lombok.Data;

@Data
public class LoginRequest {
    private String userEmail;
    private String password;


    public LoginRequest(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }
}

package com.MealTable.meal_table.response;

import java.util.List;

public class LoginResponse {
    private String jwtToken;

    private String userEmail;

    private String userName;

    public LoginResponse(String userEmail, String jwtToken, String userName) {
        this.userEmail = userEmail;
        this.jwtToken = jwtToken;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}




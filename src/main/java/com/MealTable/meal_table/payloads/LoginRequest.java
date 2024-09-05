package com.MealTable.meal_table.payloads;



public class LoginRequest {
    private String userEmail;
    private String password;
    private boolean isAdmin;


    public LoginRequest(String userEmail, String password,boolean isAdmin) {
        this.userEmail = userEmail;
        this.password = password;
        this.isAdmin=isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

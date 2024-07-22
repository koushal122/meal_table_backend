package com.MealTable.meal_table.payloads;

public class AdminSignUpRequest {
    private String userName;
    private String userEmail;
    private String password;
    private String adminEmail;

    public AdminSignUpRequest() {
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public AdminSignUpRequest(String userName, String userEmail, String password, String adminEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.adminEmail = adminEmail;
    }

    public AdminSignUpRequest(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

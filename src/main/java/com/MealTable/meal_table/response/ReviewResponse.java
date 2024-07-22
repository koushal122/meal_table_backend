package com.MealTable.meal_table.response;

public class ReviewResponse {
    private String userEmail;
    private String userName;
    private String comment;
    private double rating;

    public ReviewResponse(String userEmail, String userName, String comment, double rating) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

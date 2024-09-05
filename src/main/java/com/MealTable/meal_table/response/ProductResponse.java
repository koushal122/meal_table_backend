package com.MealTable.meal_table.response;

import org.springframework.core.io.Resource;

public class ProductResponse {
    private String name;
    private String type;
    private String description;
    private double price;
    private String imageUrl;

    public ProductResponse(String name, String type, String description, double price, Resource resource) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;

    }

    public ProductResponse() {
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductResponse(String name, String type, String description, double price, String imageUrl) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}

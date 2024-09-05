package com.MealTable.meal_table.payloads;

import org.springframework.web.multipart.MultipartFile;

public class AddProductRequest {
    private String type;
    private String name;
    private String description;
    private double price;
    private MultipartFile image;

    public AddProductRequest(String type, String name, String description, double price, MultipartFile image) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public AddProductRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "AddProductRequest{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image=" + image +
                '}';
    }
}

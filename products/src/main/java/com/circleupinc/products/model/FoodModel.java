package com.circleupinc.products.model;

public class FoodModel {

    private String name;
    private String price;
    private float rating;
    private String image="default";
    private int quantity;


    public FoodModel() {
    }

    public FoodModel(String name, String price, float rating, String image) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

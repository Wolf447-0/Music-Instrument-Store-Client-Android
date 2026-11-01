package com.musicinstrumentstore.adapter;

public class ProductCard {
    private String imageUrl;
    private String name;
    private String price;
    private String categoryName;

    public ProductCard(String imageUrl, String name, String price, String categoryName) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

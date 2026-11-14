package com.musicinstrumentstore.network_service.dto.response;

public class ProductCardResponseDto {
    private String imageUrl;
    private String name;
    private String price;
    private String categoryName;
    private String description;

    public ProductCardResponseDto(String imageUrl, String name, String price, String categoryName, String description) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

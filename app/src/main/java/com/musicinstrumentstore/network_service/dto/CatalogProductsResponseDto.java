package com.musicinstrumentstore.network_service.dto;

public class CatalogProductsResponseDto {
    private String ImageUrl;
    private String productName;
    private String productCategory;
    private String productPrice;

    public CatalogProductsResponseDto(String imageUrl, String productName, String productPrice, String productCategory) {
        ImageUrl = imageUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }
}

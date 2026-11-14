package com.musicinstrumentstore.network_service.dto.response;

public class CatalogProductsResponseDto {
    private int id;
    private String imageUrl;
    private String productName;
    private String productCategory;
    private String productPrice;

    public CatalogProductsResponseDto(int id,String imageUrl, String productName, String productPrice, String productCategory) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public int getId() {
        return id;
    }
    public String getImageUrl() {
        return imageUrl;
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

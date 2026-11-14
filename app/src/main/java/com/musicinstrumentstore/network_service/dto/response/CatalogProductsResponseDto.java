package com.musicinstrumentstore.network_service.dto.response;

public class CatalogProductsResponseDto {
    private int id;
    private String ImageUrl;
    private String productName;
    private String productCategory;
    private String productPrice;

    public CatalogProductsResponseDto(int id,String imageUrl, String productName, String productPrice, String productCategory) {
        this.id = id;
        ImageUrl = imageUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public int getId() {
        return id;
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

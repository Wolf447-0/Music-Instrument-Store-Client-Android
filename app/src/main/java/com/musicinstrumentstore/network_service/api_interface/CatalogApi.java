package com.musicinstrumentstore.network_service.api_interface;

import com.musicinstrumentstore.network_service.dto.response.ProductCardResponseDto;
import com.musicinstrumentstore.network_service.dto.response.CatalogProductsResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CatalogApi {

    @GET("/catalog/all")
    Call<List<CatalogProductsResponseDto>> getAllProduct(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/catalog/product/{id}")
    Call<ProductCardResponseDto> getProduct(@Path("id") int id);
}

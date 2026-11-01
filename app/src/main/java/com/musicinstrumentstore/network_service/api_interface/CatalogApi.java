package com.musicinstrumentstore.network_service.api_interface;

import com.musicinstrumentstore.network_service.dto.CatalogProductsResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatalogApi {

    @GET("/catalog/all")
    Call<List<CatalogProductsResponseDto>> getAllProduct();
}

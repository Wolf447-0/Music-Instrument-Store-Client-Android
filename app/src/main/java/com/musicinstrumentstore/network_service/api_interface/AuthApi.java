package com.musicinstrumentstore.network_service.api_interface;

import com.musicinstrumentstore.network_service.dto.request.LogInRequestDto;
import com.musicinstrumentstore.network_service.dto.request.RegistrationRequestDto;
import com.musicinstrumentstore.network_service.dto.response.TokenResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/registration")
    Call<TokenResponseDto> registerUser(@Body RegistrationRequestDto request);

    @POST("/login")
    Call<TokenResponseDto> loginUser(@Body LogInRequestDto request);

    @POST("/refresh_token")
    Call<TokenResponseDto> refreshToken(@Header("Authorization") String refreshToken);
}

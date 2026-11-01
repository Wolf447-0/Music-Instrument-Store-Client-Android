package com.musicinstrumentstore.network_service;

import android.content.Context;

import com.musicinstrumentstore.network_service.api_interface.AuthApi;
import com.musicinstrumentstore.network_service.dto.TokenResponseDto;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {

    private TokenManager tokenManager;

    public TokenAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (responseCount(response) >= 3) {
            return null;
        }

        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            return null;
        }

        Retrofit cleanRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.212:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthApi refreshApi = cleanRetrofit.create(AuthApi.class);

        retrofit2.Response<TokenResponseDto> refreshResponse =
                refreshApi.refreshToken("Bearer " + refreshToken).execute();

        if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {

            String newAccessToken = refreshResponse.body().getMyAccessToken();
            String newRefreshToken = refreshResponse.body().getMyRefreshToken();

            tokenManager.saveTokens(newAccessToken, newRefreshToken);

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .build();

        } else {
            tokenManager.clearTokens();
            return null;
        }
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}

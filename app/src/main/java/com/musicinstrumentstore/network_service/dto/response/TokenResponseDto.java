package com.musicinstrumentstore.network_service.dto.response;

import com.google.gson.annotations.SerializedName;


public class TokenResponseDto {
    @SerializedName("accessToken")
    private String myAccessToken; // В Java переменную можем назвать как угодно

    @SerializedName("refreshToken")
    private String myRefreshToken;

    public TokenResponseDto() {
    }

    public TokenResponseDto(String myAccessToken, String myRefreshToken) {
        this.myAccessToken = myAccessToken;
        this.myRefreshToken = myRefreshToken;
    }

    public String getMyAccessToken() {
        return myAccessToken;
    }

    public void setMyAccessToken(String myAccessToken) {
        this.myAccessToken = myAccessToken;
    }

    public String getMyRefreshToken() {
        return myRefreshToken;
    }

    public void setMyRefreshToken(String myRefreshToken) {
        this.myRefreshToken = myRefreshToken;
    }
}

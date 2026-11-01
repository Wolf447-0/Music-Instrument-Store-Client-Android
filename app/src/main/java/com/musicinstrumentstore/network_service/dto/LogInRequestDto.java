package com.musicinstrumentstore.network_service.dto;

public class LogInRequestDto {
    private String username;
    private String password;

    public LogInRequestDto() {
    }

    public LogInRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String email) {
        this.password = email;
    }
}

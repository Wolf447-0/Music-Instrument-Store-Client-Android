package com.musicinstrumentstore.network_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
    private String address;
    private int flat;
    private String phone;

    public RegistrationRequestDto() {
    }

    public RegistrationRequestDto(String username, String email, String password, String address, int flat, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.flat = flat;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

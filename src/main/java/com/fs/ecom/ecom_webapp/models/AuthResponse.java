package com.fs.ecom.ecom_webapp.models;

import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;

public class AuthResponse {

    private UserDetailsDTO userDetailsDTO;
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(UserDetailsDTO userDetailsDTO, String token) {
        this.userDetailsDTO = userDetailsDTO;
        this.token = token;
    }

    public UserDetailsDTO getUserDetailsDTO() {
        return userDetailsDTO;
    }

    public void setUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
        this.userDetailsDTO = userDetailsDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

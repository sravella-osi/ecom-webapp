package com.fs.ecom.ecom_webapp.models;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
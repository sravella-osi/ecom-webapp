package com.fs.ecom.ecom_webapp.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;
    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

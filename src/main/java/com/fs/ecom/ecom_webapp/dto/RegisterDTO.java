package com.fs.ecom.ecom_webapp.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;

    public RegisterDTO() {
    }

    public RegisterDTO(String firstName, String lastName, String email, String password, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public String defaultUserName(){
        return this.firstName.toLowerCase().charAt(0) + this.lastName.toLowerCase();
    }
}

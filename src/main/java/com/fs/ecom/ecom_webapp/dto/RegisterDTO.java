package com.fs.ecom.ecom_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;

    public String defaultUserName(){
        return this.firstName.toLowerCase().charAt(0) + this.lastName.toLowerCase();
    }
}

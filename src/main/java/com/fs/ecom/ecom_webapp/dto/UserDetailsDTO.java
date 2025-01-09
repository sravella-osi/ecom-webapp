package com.fs.ecom.ecom_webapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String userName;


}

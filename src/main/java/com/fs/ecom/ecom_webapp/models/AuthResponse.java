package com.fs.ecom.ecom_webapp.models;

import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private UserDetailsDTO userDetailsDTO;
    private String token;

}

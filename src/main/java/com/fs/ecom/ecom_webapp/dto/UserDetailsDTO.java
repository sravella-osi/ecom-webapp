package com.fs.ecom.ecom_webapp.dto;


import com.fs.ecom.ecom_webapp.models.User;
import lombok.Data;

@Data
public class UserDetailsDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String userName;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(String firstName, String lastName, String email, String mobile, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.userName = userName;
    }

    public UserDetailsDTO(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.userName = user.getUserName();
    }

}

package com.fs.ecom.ecom_webapp.dto;

import com.fs.ecom.ecom_webapp.models.User;
import lombok.Data;

@Data
public class UpdateDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String userName;

    public UpdateDTO(Long id, String firstName, String lastName, String email, String mobile, String userName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.userName = userName;
    }

    public UpdateDTO(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.userName = user.getUserName();
    }

    public UpdateDTO() {
    }

    public UpdateDTO(String firstName, String lastName, String email, String mobile, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.userName = userName;
    }

}

package com.fs.ecom.ecom_webapp.dto;

import com.fs.ecom.ecom_webapp.models.User;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

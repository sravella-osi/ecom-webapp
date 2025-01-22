package com.fs.ecom.ecom_webapp.dto;

import com.fs.ecom.ecom_webapp.models.AddressBook;
import com.fs.ecom.ecom_webapp.models.User;

public class AddressDTO {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private Long userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private int zipcode;
    private String state;
    private String country;
    private boolean isDefault;
    private String contact;
    private String countryCode;


    public AddressDTO(User user, String addressLine1, String addressLine2, String city, int zipcode, String state, String country, boolean isDefault, String contact, String countryCode) {
        this.userId = user.getId();
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        this.country = country;
        this.isDefault = isDefault;
        this.contact = contact;
        this.countryCode = countryCode;
    }

    public AddressDTO(AddressBook address) {
        this.id = address.getId();
        this.userId = address.getUser().getId();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.city = address.getCity();
        this.zipcode = address.getZipcode();
        this.state = address.getState();
        this.country = address.getCountry();
        this.isDefault = address.isDefault();
        this.contact = address.getContact();
        this.countryCode = address.getCountryCode();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public AddressDTO() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user.getId();
    }
}

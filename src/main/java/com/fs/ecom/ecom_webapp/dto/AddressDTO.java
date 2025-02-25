package com.fs.ecom.ecom_webapp.dto;

import com.fs.ecom.ecom_webapp.models.AddressBook;
import com.fs.ecom.ecom_webapp.models.User;
import lombok.Data;

@Data
public class AddressDTO {

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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public AddressDTO() {
    }

}

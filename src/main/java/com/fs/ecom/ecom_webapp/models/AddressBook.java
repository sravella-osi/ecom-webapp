package com.fs.ecom.ecom_webapp.models;

import com.fs.ecom.ecom_webapp.dto.AddressDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "address_book")
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipcode", nullable = false)
    private int zipcode;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name= "country", nullable = false)
    private String country;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name= "contact")
    private String contact;

    @Column(name= "country_code")
    private String countryCode;

    public AddressBook(Long id, User user, String addressLine1, String addressLine2, String city, int zipcode, String state, String country, String contact, String countryCode, boolean isDefault) {
        this.id = id;
        this.user = user;
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

    public AddressBook(User user, String addressLine1, String addressLine2, String city, int zipcode, String state, String country, String contact, String countryCode, boolean isDefault) {
        this.user = user;
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

    public AddressBook (AddressDTO addressDto) {
        this.addressLine1 = addressDto.getAddressLine1();
        this.addressLine2 = addressDto.getAddressLine2();
        this.city = addressDto.getCity();
        this.zipcode = addressDto.getZipcode();
        this.state = addressDto.getState();
        this.country = addressDto.getCountry();
        this.isDefault = addressDto.isDefault();
        this.contact = addressDto.getContact();
        this.countryCode = addressDto.getCountryCode();
    }

    public AddressBook (AddressDTO addressDto, User user) {
        this.id = addressDto.getId();
        this.user = user;
        this.addressLine1 = addressDto.getAddressLine1();
        this.addressLine2 = addressDto.getAddressLine2();
        this.city = addressDto.getCity();
        this.zipcode = addressDto.getZipcode();
        this.state = addressDto.getState();
        this.country = addressDto.getCountry();
        this.isDefault = addressDto.isDefault();
        this.contact = addressDto.getContact();
        this.countryCode = addressDto.getCountryCode();
    }

    public AddressBook() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

}

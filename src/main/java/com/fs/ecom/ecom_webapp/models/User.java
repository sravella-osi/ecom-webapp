package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
public class User {

    public User(String firstName, String lastName, String userName, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.mobile = mobile;
    }


    public User(Long id, String firstName, String lastName, String userName, String email, String password, String mobile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public User() {
    }

    @Id
    @Column(name= "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name= "mobile")
    private String mobile;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<UserPrivilege> userPrivileges;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<AddressBook> addressBooks;

    public User(String firstName, String lastName, String userName, String email, String password, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public List<String> getPrivilegeNames() {
        return userPrivileges.stream()
                .map(userPrivilege -> userPrivilege.getPrivilegeName())
                .collect(Collectors.toList());
    }

    public List<AddressBook> getAddresses() {
        return addressBooks.stream().collect(Collectors.toList());
    }

}

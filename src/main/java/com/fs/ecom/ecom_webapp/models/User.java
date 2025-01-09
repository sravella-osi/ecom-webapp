package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
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

    public User(String firstName, String lastName, String userName, String email, String password, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPrivilege> userPrivileges = new HashSet<>();

    public List<String> getPrivilegeNames() {
        return userPrivileges.stream()
                .map(userPrivilege -> userPrivilege.getPrivilege().getName())
                .collect(Collectors.toList());
    }

    public List<Privilege> getPrivilegesAsList() {
        return userPrivileges.stream()
                .map(UserPrivilege::getPrivilege)
                .collect(Collectors.toList());
    }

}

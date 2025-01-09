package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "privileges")
public class Privilege {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserPrivilege> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(Set<UserPrivilege> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public Privilege(int id, String name, Set<UserPrivilege> userPrivileges) {
        this.id = id;
        this.name = name;
        this.userPrivileges = userPrivileges;
    }

    public Privilege() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "privilege_name",nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<UserPrivilege> userPrivileges = new HashSet<>();

}

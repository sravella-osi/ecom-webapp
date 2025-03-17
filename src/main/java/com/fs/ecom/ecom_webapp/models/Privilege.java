package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "privileges")
@Data
public class Privilege {

    public Privilege(int id, String name, List<UserPrivilege> userPrivileges) {
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

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.REMOVE)
    private List<UserPrivilege> userPrivileges = new ArrayList<>();

}

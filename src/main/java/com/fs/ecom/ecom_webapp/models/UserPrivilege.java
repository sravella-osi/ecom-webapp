package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_privilege")
public class UserPrivilege {

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

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public UserPrivilege() {
    }

    public UserPrivilege(Long id, User user, Privilege privilege) {
        this.id = id;
        this.user = user;
        this.privilege = privilege;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link to User entity

    @ManyToOne
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privilege privilege; // Link to Privilege entity

    public String getPrivilegeName() {
        return privilege.getName();
    }
}

package com.fs.ecom.ecom_webapp.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_privilege")
@Data
public class UserPrivilege {

    public UserPrivilege() {
    }

    public UserPrivilege(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privilege privilege;

    public String getPrivilegeName() {
        return privilege.getName();
    }
}

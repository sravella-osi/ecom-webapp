package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.models.Privilege;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.models.UserPrivilege;
import com.fs.ecom.ecom_webapp.repositories.PrivilegeRepository;
import com.fs.ecom.ecom_webapp.repositories.UserPrivilegeRepository;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPrivilegeService {

    @Autowired
    UserPrivilegeRepository repository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    public void addUserPriv(User user) {
        Privilege privilege = new Privilege();
        privilege = privilegeRepository.getReferenceById((long) 2);
        UserPrivilege userPrivilege = new UserPrivilege( user, privilege );
        repository.save(userPrivilege);
    }
}

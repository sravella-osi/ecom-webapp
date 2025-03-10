package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.mapper.UserMapper;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.models.UserPrivilege;
import com.fs.ecom.ecom_webapp.repositories.PrivilegeRepository;
import com.fs.ecom.ecom_webapp.repositories.UserPrivilegeRepository;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    UserMapper userMapper;


    public List<UserDetailsDTO> getAllUsers() {
        return userMapper.getUserDetailsList(userRepository.findAll());
    }

    public void addAdminPrivilege(UserDetailsDTO userDetailsDTO){
        UserPrivilege userPrivilege = new UserPrivilege(
                userRepository.getReferenceById(userDetailsDTO.getId()),
                privilegeRepository.getReferenceById((long) 1));
        userPrivilegeRepository.save(userPrivilege);
    }
}

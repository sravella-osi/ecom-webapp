package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.LoginDTO;
import com.fs.ecom.ecom_webapp.dto.UserDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserDetails (Long id) {
        return userRepository.getReferenceById(id);
    }

    public void registerUser (UserDTO userDTO) {
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        User user = new User(firstName, lastName, userDTO.defaultUserName(), userDTO.getEmail(), userDTO.getPassword(), null);
        userRepository.save(user);
    }

    // write login service
}

package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import com.fs.ecom.ecom_webapp.security.service.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(email);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public UserDetailsDTO addUser(RegisterDTO registerDTO) {
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        UserDetailsDTO userDto = new UserDetailsDTO();
        userDto = registerUser(registerDTO);
        userPrivilegeService.addUserPriv(repository.findByEmail(registerDTO.getEmail()).get());
        return userDto;
    }

    private UserDetailsDTO registerUser (RegisterDTO registerDTO) {
        String firstName = registerDTO.getFirstName();
        String lastName = registerDTO.getLastName();
        User user = new User(firstName, lastName, registerDTO.defaultUserName(), registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getMobile());
        User registeredUser = repository.save(user);
        return new UserDetailsDTO(registeredUser);
    }

}
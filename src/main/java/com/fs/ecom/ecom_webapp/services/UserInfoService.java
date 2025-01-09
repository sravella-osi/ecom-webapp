package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import com.fs.ecom.ecom_webapp.security.service.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByUserName(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public UserDetailsDTO loadUserByToken(String token) {
        String userName = jwtService.extractUsername(token);
        Optional<User> user = repository.findByUserName(userName);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO( );

        try{
            if (user.isPresent()) {
                User temp = user.get();
                userDetailsDTO.setUserName(temp.getUserName());
                userDetailsDTO.setMobile(temp.getMobile());
                userDetailsDTO.setEmail(temp.getEmail());
                userDetailsDTO.setFirstName(temp.getFirstName());
                userDetailsDTO.setLastName(temp.getLastName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetailsDTO;
    }

    public String addUser(RegisterDTO registerDTO) {
        // Encode password before saving the user
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        userService.registerUser(registerDTO);
        return "User Added Successfully";
    }
}
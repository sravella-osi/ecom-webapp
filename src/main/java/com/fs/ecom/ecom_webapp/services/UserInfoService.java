package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UpdateDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.models.UserPrivilege;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import com.fs.ecom.ecom_webapp.security.service.UserInfoDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    private UserService userService;

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByEmail(email);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public UserDetailsDTO loadUserByToken(String token) {
        String subject = jwtService.extractSubject(token);

        Optional<User> user = repository.findByEmail(subject);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

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
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        userService.registerUser(registerDTO);
        userPrivilegeService.addUserPriv(repository.findByEmail(registerDTO.getEmail()).get());
        return "User Added Successfully";
    }

    public UserDetailsDTO updateUserProfile(UserDetailsDTO userDetailsDTO, String token) {
        Optional<User> user = repository.findByEmail(jwtService.extractSubject(token));
        UpdateDTO updateDTO = null;
        if (user.isPresent()) {
            updateDTO = new UpdateDTO(user.get());
            updateDTO.setEmail(userDetailsDTO.getEmail());
            updateDTO.setFirstName(userDetailsDTO.getFirstName());
            updateDTO.setLastName(userDetailsDTO.getLastName());
            updateDTO.setUserName(userDetailsDTO.getUserName());
            updateDTO.setMobile(userDetailsDTO.getMobile());
        }
        return userService.updateUser(updateDTO);
    }

    public String getJWTfromCookie(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    return token;
                }
            }
        }
        return "No Token";
    }
}
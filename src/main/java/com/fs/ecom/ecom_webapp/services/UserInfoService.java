package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.AddressDTO;
import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UpdateDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.exceptions.AddressNotFoundException;
import com.fs.ecom.ecom_webapp.exceptions.UserNotFoundException;
import com.fs.ecom.ecom_webapp.models.AddressBook;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.models.UserPrivilege;
import com.fs.ecom.ecom_webapp.repositories.AddressRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AddressRepository addressRepository;

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
                userDetailsDTO.setId(temp.getId());
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

    public UserDetailsDTO addUser(RegisterDTO registerDTO) {
        registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
        UserDetailsDTO userDto = new UserDetailsDTO();
        userDto = userService.registerUser(registerDTO);
        userPrivilegeService.addUserPriv(repository.findByEmail(registerDTO.getEmail()).get());
        return userDto;
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

    public User getUserFromRequest(HttpServletRequest request) throws UserNotFoundException {
        String token = getJWTfromCookie(request);
        String subject = jwtService.extractSubject(token);
        Optional<User> user = repository.findByEmail(subject);
        if(user.isPresent()){
            return user.get();
        }
        else {
            throw new UserNotFoundException("No User found from token");
        }
    }


    public AddressDTO addAddress(AddressDTO addressDto, HttpServletRequest request) {
        User user = null;
        try {
            user = getUserFromRequest(request);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if (addressRepository.findByUserId(user.getId()).isEmpty()){
            addressDto.setDefault(true);
        }

        AddressBook address = new AddressBook(addressDto);
        address.setUser(user);

        AddressBook savedAddress = addressRepository.save(address);

        AddressDTO addressDto1 = new AddressDTO(savedAddress);
        return addressDto1;
    }

    public List<AddressDTO> getAddresses(HttpServletRequest request) {
        User user = null;
        try {
            user = getUserFromRequest(request);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        List<AddressBook> addressBookList = addressRepository.findByUserId(user.getId());
        List<AddressDTO> addresses = new ArrayList<>();
        for(AddressBook address : addressBookList){
            AddressDTO addressDto = new AddressDTO(address);
            addresses.add(addressDto);
        }

        return addresses;
    }

    public void deleteAddress(AddressDTO addressDto, HttpServletRequest request) throws AddressNotFoundException {
        User user = null;
        try {
            user = getUserFromRequest(request);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(addressDto.getUserId() != user.getId()){
            throw new AddressNotFoundException("Address not found");
        }
        else {
            addressRepository.deleteById(addressDto.getId());
        }

    }
    public AddressDTO updateAddress(AddressDTO addressDto, HttpServletRequest request) throws AddressNotFoundException {
        User user = null;
        try {
            user = getUserFromRequest(request);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(addressDto.getUserId() != user.getId()){
            throw new AddressNotFoundException("Address not found");
        }
        else {
            addressDto = new AddressDTO(addressRepository.save(new AddressBook(addressDto, user)));
        }
        return addressDto;
    }
}
package com.fs.ecom.ecom_webapp.services;

import com.fs.ecom.ecom_webapp.dto.*;
import com.fs.ecom.ecom_webapp.exceptions.AddressNotFoundException;
import com.fs.ecom.ecom_webapp.exceptions.UserNotFoundException;
import com.fs.ecom.ecom_webapp.mapper.UserMapper;
import com.fs.ecom.ecom_webapp.models.AddressBook;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.repositories.AddressRepository;
import com.fs.ecom.ecom_webapp.repositories.UserRepository;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    public User getUser (Long id) {
        return repository.getReferenceById(id);
    }

    public void postUser (User user) {
        repository.save(user);
    }

    private UserDetailsDTO updateUser (UpdateDTO updateDTO) throws NullPointerException {
        User existingUser = repository.getReferenceById(updateDTO.getId());
        existingUser.setFirstName(updateDTO.getFirstName());
        existingUser.setLastName(updateDTO.getLastName());
        existingUser.setUserName(updateDTO.getUserName());
        existingUser.setEmail(updateDTO.getEmail());
        existingUser.setMobile(updateDTO.getMobile());
        return userMapper.getUserDetailsDTO(repository.save(existingUser));
    }

    public void deleteUser (Long id){
        repository.deleteById(id);
    }

    public void deleteAll (){
        repository.deleteAll();
    }

    public UserDetailsDTO loadUserByToken(String token) {
        String subject = jwtService.extractSubject(token);

        Optional<User> user = repository.findByEmail(subject);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        User temp = new User();
        try{
            if (user.isPresent()) {
                temp = user.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDetailsDTO = userMapper.getUserDetailsDTO(temp);
        return userDetailsDTO;
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
        return updateUser(updateDTO);
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

    public User getUserFromRequest(HttpServletRequest request) {
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
        user = getUserFromRequest(request);
        boolean addressListIsEmpty = addressRepository.findByUserId(user.getId()).isEmpty();

        if(addressDto.isDefault() && !addressListIsEmpty){
            updateDefault(user);
        } else if (addressListIsEmpty){
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
            if(addressDto.isDefault()){
                addresses.addFirst(addressDto);
            }
            else{
                addresses.add(addressDto);
            }
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
            if(addressDto.isDefault()){
                if(addressRepository.findByUserId(user.getId()).size()>1){
                    setDefault(user);
                }
            }
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
            if(addressDto.isDefault()){
                updateDefault(user);
            }
            addressDto = new AddressDTO(addressRepository.save(new AddressBook(addressDto, user)));
        }
        return addressDto;
    }

    private void updateDefault(User user){
        for(AddressBook address : user.getAddresses()){
            if(address.isDefault()){
                address.setDefault(false);
            }
        }
    }

    private void setDefault(User user){
        addressRepository.findByUserId(user.getId()).getFirst().setDefault(true);
    }

}

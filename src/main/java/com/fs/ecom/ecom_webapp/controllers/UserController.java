package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.AddressDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.exceptions.AddressNotFoundException;
import com.fs.ecom.ecom_webapp.services.UserInfoService;
import com.fs.ecom.ecom_webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> userProfile(HttpServletRequest httpServletRequest) {
        String jwt = userService.getJWTfromCookie(httpServletRequest);
        if(jwt == "No Token"){
            return new ResponseEntity<String>("no Content",HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userService.loadUserByToken(jwt));
    }

    @PutMapping("/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDetailsDTO userDetailsDTO, HttpServletRequest httpServletRequest) {
        UserDetailsDTO userDetails = new UserDetailsDTO();
        String jwt = userService.getJWTfromCookie(httpServletRequest);
        if(jwt == "No Token"){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Not Found");
        }
        userDetails = userService.updateUserProfile(userDetailsDTO, jwt);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @PostMapping("/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){

        AddressDTO savedAddress = userService.addAddress(addressDto, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAddresses (HttpServletRequest request){

        List<AddressDTO> addresses = userService.getAddresses(request);

        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @DeleteMapping("/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){
        try {
            userService.deleteAddress(addressDto, request);
        } catch (AddressNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted address");
    }

    @PutMapping("/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){
        AddressDTO addressDTO = null;
        try {
            addressDTO = userService.updateAddress(addressDto, request);
        } catch (AddressNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }
}

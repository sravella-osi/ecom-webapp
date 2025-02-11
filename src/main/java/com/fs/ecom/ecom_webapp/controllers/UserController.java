package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.AddressDTO;
import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.exceptions.AddressNotFoundException;
import com.fs.ecom.ecom_webapp.models.AuthRequest;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import com.fs.ecom.ecom_webapp.services.UserInfoService;
import com.fs.ecom.ecom_webapp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // below two should be in different controller classes I think.
//    @GetMapping("/{id}")
//    public User getUserDetails(@PathVariable("id") Long id){
//        return userService.getUser(id);
//    }
//
//    @PostMapping("")
//    public void postUser(User user){
//        userService.postUser(user);
//    }
//
//    @PutMapping("/{id}")
//    public void updateUserById(@PathVariable("id") Long id, User user){
//        userService.updateUser(id,user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable("id") Long id){
//        userService.deleteUser(id);
//    }
//
//    @DeleteMapping("/users")
//    public void deleteAll(){
//        userService.deleteAll();
//    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        UserDetailsDTO userDetailsDTO = userInfoService.addUser(registerDTO);
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED).body(userDetailsDTO);
        return response;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("JWT", null);
        response.addHeader("Set-Cookie", String.format("%s=%s; Max-Age=0; Path=/; Domain = localhost; HttpOnly; Secure; SameSite=None",
                cookie.getName(), cookie.getValue()));
        ResponseEntity<?> responseEntity = new ResponseEntity<>("Logout Successful",HttpStatus.OK);
        return responseEntity;
    }


    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> userProfile(HttpServletRequest httpServletRequest) {
        String jwt = userInfoService.getJWTfromCookie(httpServletRequest);
        if(jwt == "No Token"){
            return new ResponseEntity<String>("no Content",HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userInfoService.loadUserByToken(jwt));
    }

    @PutMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDetailsDTO userDetailsDTO, HttpServletRequest httpServletRequest) {
        UserDetailsDTO userDetails = new UserDetailsDTO();
        String jwt = userInfoService.getJWTfromCookie(httpServletRequest);
        if(jwt == "No Token"){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Not Found");
        }
        userDetails = userInfoService.updateUserProfile(userDetailsDTO, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetails);
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            Cookie cookie = new Cookie("JWT", token);

            response.addHeader("Set-Cookie", String.format("%s=%s; Max-Age=3600; Path=/; Domain = localhost; HttpOnly; Secure; SameSite=None",
                    cookie.getName(), cookie.getValue()));
            UserDetailsDTO userDetailsDTO = userInfoService.loadUserByToken(token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDetailsDTO);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/user/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){

        AddressDTO savedAddress = userInfoService.addAddress(addressDto, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @GetMapping("/user/addresses")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAddresses (HttpServletRequest request){

        List<AddressDTO> addresses = userInfoService.getAddresses(request);

        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @DeleteMapping("/user/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){
        try {
            userInfoService.deleteAddress(addressDto, request);
        } catch (AddressNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted address");
    }

    @PutMapping("/user/address")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateAddress (@RequestBody AddressDTO addressDto, HttpServletRequest request){
        AddressDTO addressDTO = null;
        try {
            addressDTO = userInfoService.updateAddress(addressDto, request);
        } catch (AddressNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(addressDTO);
    }

}

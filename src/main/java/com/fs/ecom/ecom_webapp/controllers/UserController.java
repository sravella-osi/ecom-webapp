package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.LoginDTO;
import com.fs.ecom.ecom_webapp.dto.UserDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    // below two should be in different controller classes I think.
    @GetMapping("/profile")
    public User getUserDetails(Long id){
        return userService.getUserDetails(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        userService.registerUser(userDTO);
        ResponseEntity<?> response = new ResponseEntity<User>(HttpStatus.OK);
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
//        userService.login(loginDTO);
//        ResponseEntity<?> response = new ResponseEntity<>("Login Successful",HttpStatus.OK);
//        return response;
//    }

    // write login api

    // Need to add CRUD operations for users
}

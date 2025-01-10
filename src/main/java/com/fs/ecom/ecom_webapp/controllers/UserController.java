package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.AuthRequest;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import com.fs.ecom.ecom_webapp.services.UserInfoService;
import com.fs.ecom.ecom_webapp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
        String msg = userInfoService.addUser(registerDTO);
        ResponseEntity<?> response = ResponseEntity.ok(msg);
        return response;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
//        userService.login(loginDTO);
//        ResponseEntity<?> response = new ResponseEntity<>("Login Successful",HttpStatus.OK);
//        return response;
//    }


    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserDetailsDTO> userProfile(@RequestHeader("Authorization") String auth_token) {
        String token = auth_token.startsWith("Bearer ") ? auth_token.substring(7) : null;
        return ResponseEntity.ok(userInfoService.loadUserByToken(token));
    }

    @PostMapping("/user/update/userProfile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserDetailsDTO userDetailsDTO,@RequestHeader("Authorization") String auth_token) {
        String token = auth_token.startsWith("Bearer ") ? auth_token.substring(7) : null;
        String msg = userInfoService.updateUserProfile(userDetailsDTO, token);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);

            return ResponseEntity.ok("Token sent as cookie");
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}

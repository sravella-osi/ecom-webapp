package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.AddressDTO;
import com.fs.ecom.ecom_webapp.dto.RegisterDTO;
import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.exceptions.AddressNotFoundException;
import com.fs.ecom.ecom_webapp.models.AuthRequest;
import com.fs.ecom.ecom_webapp.security.service.JwtService;
import com.fs.ecom.ecom_webapp.services.UserInfoService;
import com.fs.ecom.ecom_webapp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        UserDetailsDTO userDetailsDTO = userInfoService.addUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request ,HttpServletResponse response){
        if(userService.getJWTfromCookie(request).equals("No Token")){
            return new ResponseEntity<>("User is not logged in. Logout action cannot be performed.",HttpStatus.UNAUTHORIZED);
        }
        Cookie cookie = new Cookie("JWT", null);
        response.addHeader("Set-Cookie", String.format("%s=%s; Max-Age=0; Path=/; Domain = localhost; HttpOnly; Secure; SameSite=None",
                cookie.getName(), cookie.getValue()));
        return new ResponseEntity<>("Logout Successful",HttpStatus.OK);
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
            UserDetailsDTO userDetailsDTO = userService.loadUserByToken(token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDetailsDTO);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}

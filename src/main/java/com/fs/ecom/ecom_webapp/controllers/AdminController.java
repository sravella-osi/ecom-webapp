package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.dto.UserDetailsDTO;
import com.fs.ecom.ecom_webapp.models.User;
import com.fs.ecom.ecom_webapp.services.AdminService;
import com.fs.ecom.ecom_webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(originPatterns = "*")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> adminProfile(HttpServletRequest httpServletRequest) {
        String jwt = userService.getJWTfromCookie(httpServletRequest);
        if(jwt == "No Token"){
            return new ResponseEntity<String>("no Content", HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userService.loadUserByToken(jwt));
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        List<UserDetailsDTO> users = adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/admin/addPriv")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addPriv(@RequestBody UserDetailsDTO userDetailsDTO){
        adminService.addAdminPrivilege(userDetailsDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Added Admin Privileges to User");
    }

}

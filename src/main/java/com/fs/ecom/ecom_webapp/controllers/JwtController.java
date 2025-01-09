package com.fs.ecom.ecom_webapp.controllers;

import com.fs.ecom.ecom_webapp.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;



}

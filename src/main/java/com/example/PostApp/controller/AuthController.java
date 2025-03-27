package com.example.PostApp.controller;

import com.example.PostApp.model.SignupRequest;
import com.example.PostApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/adduser")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody SignupRequest signupRequest){

        return userService.registerUser(signupRequest);
    }

}

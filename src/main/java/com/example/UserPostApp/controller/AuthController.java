package com.example.UserPostApp.controller;

import com.example.UserPostApp.model.SignupRequest;
import com.example.UserPostApp.model.User;
import com.example.UserPostApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/adduser")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody SignupRequest signupRequest){
        Map<String,Object> response = new HashMap<>();
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        User savedUser = userService.addUser(user);
        response.put("success",true);
        response.put("message","user registered successfully");
        return ResponseEntity.ok().body(response);
    }

}

package com.example.PostApp.controller;

import com.example.PostApp.model.*;
import com.example.PostApp.service.JwtService;
import com.example.PostApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody SignupRequest signupRequest){

        return userService.registerUser(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest){
        //set the Authentication to the username and password...
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserPrincipal userDetails = (UserPrincipal) auth.getPrincipal();

        Set<String> userRoles = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet()); // converting from List<GrantedAuthority> to Set<String>
        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),userRoles,userDetails.isAccountNonExpired(),userDetails.getCreatedAt(),userDetails.getModifiedAt());

        if(auth.isAuthenticated()){
            String jwt = jwtService.getToken(userDetails.getUsername());
            return ResponseEntity.ok().header("JWT Token",jwt).body(response);
        }else{
            return ResponseEntity.internalServerError().body("user login failed");
        }

    }

    @PostMapping("/edituser")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest editUserRequest, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            if(username.isEmpty() || username == null){
                return ResponseEntity.badRequest().body("Invalid Token");
            }
        }else{
            return ResponseEntity.badRequest().body("Token not found");
        }
        return userService.editUser(editUserRequest, username);
    }

}

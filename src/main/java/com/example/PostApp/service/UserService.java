package com.example.PostApp.service;

import com.example.PostApp.model.*;
import com.example.PostApp.repo.RoleRepository;
import com.example.PostApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<Map<String, Object>> registerUser(SignupRequest signupRequest) {
        Map<String,Object> response = new HashMap<>();
        if(signupRequest.getUsername() == "" || signupRequest.getEmail() == "" || signupRequest.getPassword() == ""){
            response.put("success",false);
            response.put("message","Please provide username/email/password");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> existingUser = userRepository.findByUsername(signupRequest.getUsername());
        Optional<String> existingEmail = userRepository.findByEmail(signupRequest.getEmail());

        if(existingUser.isPresent()){
            response.put("success",false);
            response.put("message","Username already exists");
            return ResponseEntity.badRequest().body(response);
        }

        if(existingEmail.isPresent()){
            response.put("success",false);
            response.put("message","Email already taken");
            return ResponseEntity.badRequest().body(response);
        }


        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
//        user.setPassword(signupRequest.getPassword());
//        boolean userRolesExist = signupRequest.getUserRoles().stream().allMatch(Objects::nonNull);
        Set<Role> roles = getRole(signupRequest.getUserRoles());
        user.setUserRoles(roles);
        System.out.println(signupRequest.isActive());
        user.setActive(signupRequest.isActive());
        userRepository.save(user);
        response.put("success",true);
        response.put("message","user registered successfully");
        return ResponseEntity.ok().body(response);


    }

    private Set<Role> getRole(Set<String> userRoles) {
        Set<Role> roleSet = new HashSet<>();

        if(userRoles == null){
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roleSet.add(userRole);
        }else{
            userRoles.forEach(role -> {
                Role userRole = roleRepository.findByRoleName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                roleSet.add(userRole);
            });
        }
        return roleSet;
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Map<String,Object> response = new HashMap<>();
        if(loginRequest.getUsername() == "" || loginRequest.getPassword() == ""){
            response.put("success",false);
            response.put("message","Please provide username and password");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<User> existingUser = userRepository.findByUsername(loginRequest.getUsername());
        if(!existingUser.isPresent()){
            response.put("success",false);
            response.put("message","User does not exist");
            return ResponseEntity.badRequest().body(response);
        }

        SignupResponse userResponse = new SignupResponse();
        userResponse.setId(existingUser.get().getId());
        userResponse.setEmail(existingUser.get().getEmail());
        userResponse.setUsername(existingUser.get().getUsername());
        Set<String> userRoles = new HashSet<>();
        existingUser.get().getUserRoles().forEach(role -> {
            userRoles.add(role.getRoleName());
        });
        userResponse.setUser_roles(userRoles);
        userResponse.setActive(existingUser.get().isActive());
        userResponse.setCreatedAt(existingUser.get().getCreatedAt());
//        System.out.println(existingUser.get().getUserRoles());
//        userResponse.setUserRoles(existingUser.get().getUserRoles());

        return ResponseEntity.ok().body(userResponse);
    }
}

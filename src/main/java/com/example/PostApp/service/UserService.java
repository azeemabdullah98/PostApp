package com.example.PostApp.service;

import com.example.PostApp.model.*;
import com.example.PostApp.repo.RoleRepository;
import com.example.PostApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        // check to handle null value from the user...
        if(signupRequest.getUsername().equals("") || signupRequest.getEmail().equals("") || signupRequest.getPassword().equals("")){
            response.put("success",false);
            response.put("message","Please provide username/email/password");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> existingUser = userRepository.findByUsername(signupRequest.getUsername());
        Optional<String> existingEmail = userRepository.findByEmail(signupRequest.getEmail());

        // check if the username is already existing in the DB...
        if(existingUser.isPresent()){
            response.put("success",false);
            response.put("message","Username already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // check if the email is already existing in the DB...
        if(existingEmail.isPresent()){
            response.put("success",false);
            response.put("message","Email already taken");
            return ResponseEntity.badRequest().body(response);
        }


        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        if(signupRequest.getUserRoles().isEmpty()){
            response.put("message","Please select at least one role");
            return ResponseEntity.badRequest().body(response);
        }
        Set<Role> roles = getRole(signupRequest.getUserRoles());
        user.setUserRoles(roles);
        System.out.println(signupRequest.isActive());
        user.setActive(signupRequest.isActive());
        userRepository.save(user);
        response.put("success",true);
        response.put("message","user registered successfully");
        return ResponseEntity.ok().body(response);


    }
    //converting role datatype from Set<String> to Set<Role>...
    private Set<Role> getRole(Set<String> userRoles) {
        Set<Role> roleSet = new HashSet<>();

        // assign default role ROLE_USER if the user does not provide a role...
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

    public ResponseEntity<?> editUser(EditUserRequest editUserRequest, String email) {
        User existingUser = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if(editUserRequest.getUsername() == null || editUserRequest.getUsername().isEmpty()){
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }
        if(editUserRequest.getEmail() == null || editUserRequest.getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("Email cannot be empty");
        }

        if(editUserRequest.getUserRoles().isEmpty()){
            return ResponseEntity.badRequest().body("User must have atleast one role assigned");
        }
        existingUser.setUsername(editUserRequest.getUsername());
        existingUser.setEmail(editUserRequest.getEmail());
        Set<Role> user_roles = getRole(editUserRequest.getUserRoles());
        existingUser.setUserRoles(user_roles);
        userRepository.save(existingUser);
        UserInfoResponse response = new UserInfoResponse();
        response.setEmail(existingUser.getEmail());
        response.setUsername(existingUser.getUsername());
        response.setId(existingUser.getId());
        response.setUser_roles(editUserRequest.getUserRoles());
        response.setActive(existingUser.isActive());
        response.setCreatedAt(existingUser.getCreatedAt());
        response.setModifiedAt(existingUser.getModifiedAt());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> getRoles() {
        List<Role> userRoles = roleRepository.findAll();
        return ResponseEntity.ok().body(userRoles);
    }
}

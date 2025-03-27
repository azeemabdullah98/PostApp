package com.example.PostApp.service;

import com.example.PostApp.model.Role;
import com.example.PostApp.model.SignupRequest;
import com.example.PostApp.model.User;
import com.example.PostApp.repo.RoleRepository;
import com.example.PostApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public ResponseEntity<Map<String, Object>> registerUser(SignupRequest signupRequest) {
        Map<String,Object> response = new HashMap<>();
        if(signupRequest.getUsername() == "" || signupRequest.getEmail() == "" || signupRequest.getPassword() == ""){
            response.put("success",false);
            response.put("message","Please provide username/email/password");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<User> existingUser = userRepository.findByUsername(signupRequest.getUsername());
        if(existingUser.isPresent()){
            response.put("success",false);
            response.put("message","User already exists");
            return ResponseEntity.badRequest().body(response);
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        boolean userRolesExist = signupRequest.getUserRoles().stream().allMatch(Objects::nonNull);
        Set<Role> roles = getRole(signupRequest.getUserRoles());
        user.setUserRoles(roles);
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
}

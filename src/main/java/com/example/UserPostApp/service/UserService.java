package com.example.UserPostApp.service;

import com.example.UserPostApp.model.Role;
import com.example.UserPostApp.model.SignupRequest;
import com.example.UserPostApp.model.User;
import com.example.UserPostApp.repo.RoleRepository;
import com.example.UserPostApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
//        System.out.println(signupRequest.getUserRoles());
        boolean userRolesExist = signupRequest.getUserRoles().stream().allMatch(Objects::nonNull);
//        boolean user_roles_exist = signupRequest.getUserRoles().isEmpty();
//        System.out.println(user_roles_exist);
        if(userRolesExist){
            user.setUserRoles(signupRequest.getUserRoles());
//           for(Role role: signupRequest.getUserRoles()) {
//               Role newRole = new Role();
//               System.out.println(role.getRoleName());
//               newRole.setRoleName(role.getRoleName());
//               roleRepository.save(newRole);
//           }
        }
        userRepository.save(user);
        response.put("success",true);
        response.put("message","user registered successfully");
        return ResponseEntity.ok().body(response);
    }
}

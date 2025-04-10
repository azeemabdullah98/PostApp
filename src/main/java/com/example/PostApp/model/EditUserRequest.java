package com.example.PostApp.model;

import java.util.Set;

public class EditUserRequest {

    private String username;
    private String email;
    private Set<String> userRoles;

    public EditUserRequest(String username, String email, Set<String> userRoles) {
        this.username = username;
        this.email = email;
        this.userRoles = userRoles;
    }

    public EditUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }
}

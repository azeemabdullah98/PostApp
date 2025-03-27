package com.example.PostApp.model;

import java.util.Set;

public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private Set<String> userRoles;

    public SignupRequest(String username, String email, String password, Set<String> userRoles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    public SignupRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }
}

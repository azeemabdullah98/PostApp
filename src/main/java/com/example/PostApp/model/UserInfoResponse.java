package com.example.PostApp.model;

import java.time.LocalDateTime;
import java.util.Set;

public class UserInfoResponse {

    private int id;

    private String username;

    private String email;

    private Set<String> user_roles;

    private boolean active;

    private LocalDateTime createdAt;

    public UserInfoResponse() {
    }

    public UserInfoResponse(int id, String username, String email, Set<String> user_roles, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.user_roles = user_roles;
        this.active = active;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<String> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(Set<String> user_roles) {
        this.user_roles = user_roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

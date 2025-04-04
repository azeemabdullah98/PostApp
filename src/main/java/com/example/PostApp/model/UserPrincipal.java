package com.example.PostApp.model;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Integer id;

    private String username;

    private String email;

    private String password;

    private boolean active;

    private Collection<? extends GrantedAuthority> authorities;

    private LocalDateTime createdAt;

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public UserPrincipal(Integer id, String username, String email, String password, boolean active, Collection<? extends GrantedAuthority> authorities,LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.authorities = authorities;
        this.createdAt = createdAt;
    }
    public static UserPrincipal build(User user){
        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

        return new UserPrincipal(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),user.isActive(),authorities,user.getCreatedAt());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public Integer getId(){
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
}

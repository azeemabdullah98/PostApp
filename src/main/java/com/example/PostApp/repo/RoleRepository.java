package com.example.PostApp.repo;

import com.example.PostApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByRoleName(String roleUser);
}

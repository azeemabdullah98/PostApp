package com.example.PostApp.repo;


import com.example.PostApp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u WHERE u.username =:username")
    Optional<User> findByUsername(String username);

    @Query("select u.email from User u WHERE u.email =:email")
    Optional<String> findByEmail(String email);

}

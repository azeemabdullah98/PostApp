package com.example.PostApp.repo;

import com.example.PostApp.model.UserProduct;
import com.example.PostApp.model.UserProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, UserProductId> {

    Optional<UserProduct> findById_UserIdAndId_ProductId(Integer userId, UUID productId);

    Optional<UserProduct> findById_ProductId(UUID productId);
}

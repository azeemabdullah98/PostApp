package com.example.PostApp.repo;

import com.example.PostApp.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByProductName(String productName);

    Optional<Product> findByProductId(UUID productId);

    @Transactional
    void deleteByProductId(UUID productId);
}

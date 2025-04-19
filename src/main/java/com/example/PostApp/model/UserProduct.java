package com.example.PostApp.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_products")
@AllArgsConstructor
@NoArgsConstructor
public class UserProduct {

    @EmbeddedId
    private UserProductId id;

    public UserProductId getId() {
        return id;
    }

    public void setId(UserProductId id) {
        this.id = id;
    }
}

package com.example.PostApp.service;


import com.example.PostApp.model.Product;
import com.example.PostApp.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String IMG_DIR = "/Users/azeemabdullah/Documents/GitHub/PostApp/src/main/resources/static/images/";
    public ResponseEntity<Map<String,Object>> addProduct(String productName,String productDescription,Integer productPrice, MultipartFile imageFile) throws IOException {
        Map<String,Object> response = new HashMap<>();
        File dir = new File(IMG_DIR);
        if (!dir.exists()) dir.mkdirs();

        String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String imagePath = IMG_DIR + imageName;
        File dest = new File(imagePath);
        imageFile.transferTo(dest);
        Product productDetail = Product.builder().productName(productName).productPrice(productPrice)
                .productDescription(productDescription).imagePath(imagePath).build();
        productRepository.save(productDetail);
        response.put("status","success");
        response.put("message","Product successfully added!");
        return ResponseEntity.ok().body(response);
    }
}

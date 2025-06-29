package com.example.PostApp.controller;

import com.example.PostApp.model.Product;
import com.example.PostApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Map<String,Object>> addProduct(@RequestParam MultipartFile imageFile,
                                                         @RequestParam String productName,
                                                         @RequestParam String productDescription,
                                                         @RequestParam Integer productPrice) throws IOException {
        return productService.addProduct(productName,productDescription,productPrice,imageFile);
    }

    @GetMapping("/product")
    public List<?> getProducts(@RequestParam(required = false) UUID productId){
        return productService.getAllProducts(productId);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable UUID productId){
        return productService.deleteProduct(productId);
    }
}

package com.example.PostApp.service;


import com.example.PostApp.model.Product;
import com.example.PostApp.model.UserProduct;
import com.example.PostApp.repo.ProductRepository;
import com.example.PostApp.repo.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductRepository userProductRepository;

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

    public List<?> getAllProducts(UUID productId) {
        List<Product> response = new ArrayList<>();
        if(productId != null){
            Optional<Product> productOptional = productRepository.findByProductId(productId);
            if(productOptional.isPresent()){
                response.add(productOptional.get());
            }
            return response;
        }
        return productRepository.findAll();
    }

    public ResponseEntity<Map<String, Object>> deleteProduct(UUID productId) {
        Map<String,Object> response = new HashMap<>();
        Optional<Product> productOptional = productRepository.findByProductId(productId);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            if(product.getImagePath() != null){
                File imageFile = new File(product.getImagePath());
                if(imageFile.exists()){
                    imageFile.delete();
                }
            }else{
                response.put("status","failure");
                response.put("message","Product not found!");
                return ResponseEntity.badRequest().body(response);
            }
            Optional<UserProduct> userProductMapping = userProductRepository.findById_ProductId(productId);
            if(userProductMapping.isPresent()){
                response.put("status","failure");
                response.put("message","User is mapped to the product!");
                return ResponseEntity.badRequest().body(response);
            }

            productRepository.deleteByProductId(productId);
            response.put("status","success");
            response.put("message","Product with product Id " + productId + " and product name " + product.getProductName() + " deleted successfully");
            return ResponseEntity.ok().body(response);
        }else{
            response.put("status","failure");
            response.put("message","Product not found");
            return ResponseEntity.badRequest().body(response);
        }
    }
}

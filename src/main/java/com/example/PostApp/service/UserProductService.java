package com.example.PostApp.service;

import com.example.PostApp.model.Product;
import com.example.PostApp.model.User;
import com.example.PostApp.model.UserProduct;
import com.example.PostApp.model.UserProductId;
import com.example.PostApp.repo.ProductRepository;
import com.example.PostApp.repo.UserProductRepository;
import com.example.PostApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProductService {

    @Autowired
    private UserProductRepository userProductRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    public ResponseEntity<Map<String, Object>> addMapping(Integer userId, UUID productId) {
        Map<String,Object> response = new HashMap<>();
        Optional<User> userDetail = userRepo.findById(userId);
        if(!userDetail.isPresent()){
            response.put("status","failure");
            response.put("message","User with user id " + userId + " does not exist");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<Product> productDetail = productRepo.findByProductId(productId);
        if(!productDetail.isPresent()){
            response.put("status","failure");
            response.put("message","Product with product id " + productId + " does not exist");
            return ResponseEntity.badRequest().body(response);
        }

        if(userId != null && productId != null){
            Optional<UserProduct> userProductMapping = userProductRepo.findById_UserIdAndId_ProductId(userId,productId);
            if(userProductMapping.isPresent()){
                response.put("status","failure");
                response.put("message","User-Product mapping already exists");
                return ResponseEntity.badRequest().body(response);
            }
            UserProductId userProductId = new UserProductId(userId,productId);
            UserProduct userProduct = new UserProduct(userProductId);
            userProductRepo.save(userProduct);
            response.put("status","success");
            response.put("message","User-Product mapping added successfully!");

            return ResponseEntity.ok().body(response);
        }else{
            response.put("status","failure");
            response.put("message","user id and product id should be provided");
            return ResponseEntity.badRequest().body(response);
        }

    }

    public List<?> getMapping(Integer userId, UUID productId) {
        List<UserProduct> response = new ArrayList<>();
        if(userId != null && productId != null){
            Optional<UserProduct> userProductOptional = userProductRepo.findById_UserIdAndId_ProductId(userId,productId);
            if(userProductOptional.isPresent()){
                response.add(userProductOptional.get());
            }
            return response;
        }
        if(userId != null){
            Optional<UserProduct> userProductOptional = userProductRepo.findById_UserId(userId);
            if(userProductOptional.isPresent()){
                response.add(userProductOptional.get());
            }
            return response;
        }
        if(productId != null){
            Optional<UserProduct> userProductOptional = userProductRepo.findById_ProductId(productId);
            if(userProductOptional.isPresent()){
                response.add(userProductOptional.get());
            }
            return response;
        }
        return userProductRepo.findAll();
    }

    public ResponseEntity<Map<String, Object>> deleteMapping(Integer userId, UUID productId) {
        Map<String,Object> response = new HashMap<>();
        if(userId != null && productId != null){
            Optional<User> userDetail = userRepo.findById(userId);
            if(!userDetail.isPresent()){
                response.put("status","failure");
                response.put("message","User with user id " + userId + " found");
                return ResponseEntity.badRequest().body(response);
            }
            Optional<Product>productDetail = productRepo.findByProductId(productId);
            if(!productDetail.isPresent()){
                response.put("status","failure");
                response.put("message","Product with product id " + productId + " does not exist");
                return ResponseEntity.badRequest().body(response);
            }
            Optional<UserProduct> userProductMapping = userProductRepo.findById_UserIdAndId_ProductId(userId,productId);
            if(!userProductMapping.isPresent()){
                response.put("status","failure");
                response.put("message","User-Product mapping not found");
                return ResponseEntity.badRequest().body(response);
            }

            UserProductId userProductId = new UserProductId(userId,productId);
            userProductRepo.deleteById(userProductId);
            response.put("status","success");
            response.put("message","User-Product mapping deleted successfully!");
            return ResponseEntity.ok().body(response);
        }else{
            response.put("status","failure");
            response.put("message","userId and productId must be provided");
            return ResponseEntity.badRequest().body(response);
        }


    }
}

package com.example.PostApp.controller;

import com.example.PostApp.model.UserPrincipal;
import com.example.PostApp.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("userproducts")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;

    @PostMapping("/userproduct")
    public ResponseEntity<Map<String,Object>> addUserProductMapping(@RequestParam(required = false) Integer userId, @RequestParam(required = false) UUID productId){
        return userProductService.addMapping(userId, productId);
    }

    @GetMapping("/userproduct")
    public List<?> getUserProductMapping(@RequestParam(required = false) Integer userId,@RequestParam(required = false) UUID productId){
        return userProductService.getMapping(userId, productId);
    }

    @DeleteMapping("/userproduct")
    public ResponseEntity<Map<String,Object>> deleteUserProductMapping(@RequestParam(required = false) Integer userId, @RequestParam(required = false) UUID productId){
        return userProductService.deleteMapping(userId, productId);
    }
}

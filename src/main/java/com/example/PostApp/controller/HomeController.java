package com.example.PostApp.controller;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String greeting(){
        return "Hello world";
    }

    @GetMapping("/session")
    public String getSession(HttpServletRequest request){
        return "Session ID : " + request.getSession().getId();
    }
}

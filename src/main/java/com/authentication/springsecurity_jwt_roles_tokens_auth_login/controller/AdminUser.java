package com.authentication.springsecurity_jwt_roles_tokens_auth_login.controller;

import com.authentication.springsecurity_jwt_roles_tokens_auth_login.dto.RequestResponse;
import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.Product;
import com.authentication.springsecurity_jwt_roles_tokens_auth_login.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class AdminUser {
    private ProductRepository productRepository;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/admin/saveproduct")
    public ResponseEntity<Object> signUp(@RequestBody RequestResponse productRequest){
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepository.save(productToSave));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("User alone can access this Api only");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminAndUsersApi(){
        return ResponseEntity.ok("Both Admin and User can access the api");
    }

    //You can use this to get the details(name,email,role,ip, e.t.c) of user accessing the service
    @GetMapping("/public/email")
    public String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication); //get all details (name, email, password, roles ..) of the user
        System.out.println(authentication.getDetails()); //get remote ip
        System.out.println(authentication.getName()); //returns the email because the email is the unique identifier
        return authentication.getName(); //return the email
    }
}

package com.authentication.springsecurity_jwt_roles_tokens_auth_login.repository;

import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

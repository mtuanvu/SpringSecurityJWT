package com.authentication.springsecurity_jwt_roles_tokens_auth_login.repository;

import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OurUserRepository extends JpaRepository<OurUser, Integer> {
    Optional<OurUser> findByEmail(String username);
}

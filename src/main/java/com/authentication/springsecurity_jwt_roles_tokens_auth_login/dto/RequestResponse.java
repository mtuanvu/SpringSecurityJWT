package com.authentication.springsecurity_jwt_roles_tokens_auth_login.dto;

import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.OurUser;
import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResponse  {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private List<Product> products;
    private OurUser ourUsers;
}

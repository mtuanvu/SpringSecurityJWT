package com.authentication.springsecurity_jwt_roles_tokens_auth_login.service;

import com.authentication.springsecurity_jwt_roles_tokens_auth_login.dto.RequestResponse;
import com.authentication.springsecurity_jwt_roles_tokens_auth_login.entities.OurUser;
import com.authentication.springsecurity_jwt_roles_tokens_auth_login.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AuthService {

    @Autowired
    private OurUserRepository ourUserRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RequestResponse signUp(RequestResponse registrationRequest){

        RequestResponse requestResponse = new RequestResponse();

        try {
            OurUser ourUsers = new OurUser();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUser ourUserResult = ourUserRepository.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId() > 0){
                requestResponse.setOurUsers(ourUserResult);
                requestResponse.setMessage("User saved successfully!");
                requestResponse.setStatusCode(200);
            }
        } catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setError(e.getMessage());
        }

        return requestResponse;
    }


    public RequestResponse signIn(RequestResponse signRequest){
        RequestResponse requestResponse = new RequestResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signRequest.getEmail(), signRequest.getPassword()));
            var user = ourUserRepository.findByEmail(signRequest.getEmail()).orElseThrow();

            System.out.println("USER IS: " + user);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            requestResponse.setStatusCode(200);
            requestResponse.setToken(jwt);
            requestResponse.setRefreshToken(refreshToken);
            requestResponse.setExpirationTime("24Hr");
            requestResponse.setMessage("Successfully Signed In");
        } catch (AuthenticationException e) {
            requestResponse.setStatusCode(500);
            requestResponse.setError(e.getMessage());
        }

        return requestResponse;
    }

    public RequestResponse refreshToken(RequestResponse refreshTokenReqiest){
        RequestResponse requestResponse = new RequestResponse();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUser users = ourUserRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            requestResponse.setStatusCode(200);
            requestResponse.setToken(jwt);
            requestResponse.setRefreshToken(refreshTokenReqiest.getToken());
            requestResponse.setExpirationTime("24Hr");
            requestResponse.setMessage("Successfully Refreshed Token");
        }
        requestResponse.setStatusCode(500);
        return requestResponse;
    }
}

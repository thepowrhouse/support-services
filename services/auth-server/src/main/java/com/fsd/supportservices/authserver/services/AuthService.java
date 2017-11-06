package com.fsd.supportservices.authserver.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    public boolean isValidUser(String name, String password) {
        return true;
    }

    public String getToken() {
        return "Token";
    }

    public Map getUserRole() {
        return null;
    }
}

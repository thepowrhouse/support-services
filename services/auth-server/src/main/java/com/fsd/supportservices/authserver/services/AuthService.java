package com.fsd.supportservices.authserver.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean isValidUser(String name, String password) {
        return true;
    }

    public String getToken() {
        return "Token";
    }
}

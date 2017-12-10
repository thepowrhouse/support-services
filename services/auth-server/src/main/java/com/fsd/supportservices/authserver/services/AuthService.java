package com.fsd.supportservices.authserver.services;

import com.fsd.supportservices.authserver.model.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public boolean isValidUser(String name, String password);

    public UserDTO findUserByName(String userName);

    public String getToken();
}

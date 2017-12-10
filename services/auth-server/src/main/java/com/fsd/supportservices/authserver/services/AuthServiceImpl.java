package com.fsd.supportservices.authserver.services;

import com.fsd.supportservices.authserver.model.dto.UserDTO;
import com.fsd.supportservices.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.fsd.supportservices.authserver.transformers.UserTransformer.toUserDTO;

public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValidUser(String name, String password) {
        UserDTO userDTO = findUserByName(name);
        return password.equals(userDTO.getPassword());
    }

    @Override
    public UserDTO findUserByName(String userName)  {
        return toUserDTO(userRepository.findByUsername(userName));
    }

    @Override
    public String getToken() {
        return "Token";
    }
}

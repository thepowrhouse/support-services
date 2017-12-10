package com.fsd.supportservices.authserver.transformers;

import com.fsd.supportservices.authserver.exception.ResourceNotFoundException;
import com.fsd.supportservices.authserver.model.UserEntity;
import com.fsd.supportservices.authserver.model.dto.UserDTO;

public class UserTransformer {
    public static UserDTO toUserDTO(UserEntity userEntity) {

        if (userEntity == null) {
            throw new ResourceNotFoundException(1,"No Users Found");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setUseremail(userEntity.getUseremail());
        userDTO.setRole(userEntity.getRole());
        userDTO.setCreatedAt(userEntity.getCreatedAt());
        userDTO.setUpdatedAt(userEntity.getUpdatedAt());
        return userDTO;
    }
}

package com.fsd.supportservices.authserver.repository;

import com.fsd.supportservices.authserver.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity findByUsername(String username);

}

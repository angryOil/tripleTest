package com.example.tripletest.user.repository;

import com.example.tripletest.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity , UUID> {
    UserEntity findById(String id);
    List<UserEntity> findAll();

}

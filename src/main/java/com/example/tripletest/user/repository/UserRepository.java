package com.example.tripletest.user.repository;

import com.example.tripletest.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity , UUID> {
    UserEntity findById(String id);
    List<UserEntity> findAll();
}

package com.example.tripletest.user.service;

import com.example.tripletest.user.dto.UserDto;
import com.example.tripletest.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
     UserEntity register(UserDto userDto);
     List<UserEntity> findUsers();
     UserDto login(UserDto userDto) throws Exception;
     Optional<UserEntity> searchByUserId(UUID userId);
}

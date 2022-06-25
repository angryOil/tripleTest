package com.example.tripletest.user.service;

import com.example.tripletest.user.dto.UserDto;
import com.example.tripletest.user.entity.UserEntity;

import java.util.List;

public interface UserService {
     boolean register(UserDto userDto);
     List<UserEntity> findUsers();
     UserDto login(UserDto userDto);
     UserEntity searchByUserId(String userId);
}

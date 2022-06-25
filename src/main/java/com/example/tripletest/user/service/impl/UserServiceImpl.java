package com.example.tripletest.user.service.impl;

import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.point.service.PointService;
import com.example.tripletest.user.dto.UserDto;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.repository.UserRepository;
import com.example.tripletest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PointService pointService;

    @Override
    @Transactional
    public boolean register(UserDto userDto) {
        if (userRepository.findById(userDto.getId()) != null) {
            return false;
        }
        UserEntity userEntity = userRepository.save(
                UserEntity.builder()
                        .id(userDto.getId())
                        .pw(userDto.getPw())
                        .build());
        pointService.create(userEntity);
        return true;
    }

    @Override
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto login(UserDto userDto) {
        UserEntity findUser = userRepository.findById(userDto.getId());
        return findUser.getPw().equals(userDto.getPw()) ? userDto : null;
    }

}

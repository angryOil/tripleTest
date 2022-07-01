package com.example.tripletest.user.service.impl;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.user.dto.UserDto;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.repository.UserRepository;
import com.example.tripletest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Override
    @Transactional
    public UserEntity register(UserDto userDto) {
        if (userRepository.findById(userDto.getId()).isPresent()) {
            throw new IllegalStateException("이미존재하는 회원입니다");
        }
        UserEntity userEntity = userRepository.save(
                UserEntity.builder()
                        .id(userDto.getId())
                        .pw(userDto.getPw())
                        .build());
        pointRepository.save(PointEntity.builder()
                .uuid(userEntity.getUuid())
                .userId(userEntity.getId())
                .mileage(0)
                .build());
        return userEntity;
    }

    @Override
    public List<UserEntity> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto login(UserDto userDto) throws Exception {
        UserEntity findUser = userRepository.findById(userDto.getId()).orElseThrow(()-> new Exception("로그인 실패"));
        return findUser.getPw().equals(userDto.getPw()) ? userDto : null;
    }

    @Override
    public Optional<UserEntity> searchByUserId(UUID userId) {
        return userRepository.findById(userId);
    }


}

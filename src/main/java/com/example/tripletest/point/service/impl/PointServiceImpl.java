package com.example.tripletest.point.service.impl;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.point.service.PointService;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Override
    public PointEntity searchByUuid(UUID uuid) {
        return pointRepository.findByUuid(uuid);
    }

    @Override
    public PointEntity searchByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new Exception("존재하지않는 회원입니다"));
        return pointRepository.findByUuid(userEntity.getUuid());
    }

    @Override
    public PointEntity save(PointEntity point) {
        return pointRepository.save(point);
    }


}

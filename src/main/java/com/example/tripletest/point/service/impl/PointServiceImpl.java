package com.example.tripletest.point.service.impl;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.point.service.PointService;
import com.example.tripletest.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public PointEntity create(UserEntity userEntity) {
        PointEntity pointEntity = PointEntity.builder()
                .uuid(userEntity.getUuid())
                .mileage(0)
                .build();
        return pointRepository.save(pointEntity);
    }
}

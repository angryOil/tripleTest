package com.example.tripletest.point.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.user.entity.UserEntity;

import java.util.UUID;

public interface PointService {
    PointEntity create(UserEntity userEntity);
    PointEntity searchByUuid(UUID uuid);
    PointEntity searchByUserId(String userId);
}

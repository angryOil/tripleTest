package com.example.tripletest.point.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.user.entity.UserEntity;

public interface PointService {
    PointEntity create(UserEntity userEntity);
}

package com.example.tripletest.point.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;

import java.util.List;
import java.util.UUID;

public interface PointLogService {
    void save(PointLogEntity logEntity) throws Exception;

    List<PointLogEntity> getAll();
    List<PointLogEntity> getByUserId(UUID uuid);
}

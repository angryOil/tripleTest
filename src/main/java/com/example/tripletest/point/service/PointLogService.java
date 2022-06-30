package com.example.tripletest.point.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;

public interface PointLogService {
    void save(PointLogEntity logEntity) throws Exception;
}

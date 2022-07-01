package com.example.tripletest.point.service.impl;

import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.repository.PointLogRepository;
import com.example.tripletest.point.service.PointLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    private final PointLogRepository pointLogRepository;

    @Override
    public void save(PointLogEntity logEntity) throws Exception {
        try {
            pointLogRepository.save(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("포인트 로그 저장실패");
        }
    }

    @Override
    public List<PointLogEntity> getByUserId(UUID uuid) {
        return pointLogRepository.findByPointId(uuid);
    }

    @Override
    public List<PointLogEntity> getAll() {
        return pointLogRepository.findAll();
    }
}

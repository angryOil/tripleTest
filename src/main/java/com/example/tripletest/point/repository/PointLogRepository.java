package com.example.tripletest.point.repository;

import com.example.tripletest.point.entity.PointLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointLogRepository extends JpaRepository<PointLogEntity , UUID> {
    List<PointLogEntity> findAll();
    List<PointLogEntity> findByPointId(UUID uuid);
}

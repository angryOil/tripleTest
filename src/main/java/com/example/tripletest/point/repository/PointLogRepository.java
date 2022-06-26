package com.example.tripletest.point.repository;

import com.example.tripletest.point.entity.PointLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointLogRepository extends JpaRepository<PointLogEntity , UUID> {

}

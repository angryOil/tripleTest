package com.example.tripletest.point.repository;

import com.example.tripletest.point.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointRepository extends JpaRepository<PointEntity , UUID> {

}

package com.example.tripletest.place.repository;

import com.example.tripletest.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<PlaceEntity , UUID> {

}

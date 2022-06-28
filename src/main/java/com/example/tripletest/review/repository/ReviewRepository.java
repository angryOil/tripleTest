package com.example.tripletest.review.repository;

import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity , UUID> {
    Optional<ReviewEntity> findByUserIdAndPlaceId(UUID userId, UUID placeId);

    List<ReviewEntity> findAllByPlaceId(UUID uuid);
}

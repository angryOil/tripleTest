package com.example.tripletest.review.repository;

import com.example.tripletest.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity , UUID> {
    ReviewEntity findByUserIdAndPlaceId(UUID userId, UUID placeId);
}

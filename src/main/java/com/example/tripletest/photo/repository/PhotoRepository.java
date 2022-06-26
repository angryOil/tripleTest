package com.example.tripletest.photo.repository;

import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<PhotoEntity , UUID> {
    List<PhotoEntity> findAllByReviewEntity(ReviewEntity reviewEntity);
}

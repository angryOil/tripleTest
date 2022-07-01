package com.example.tripletest.review.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.event.dto.EventDto;
import com.example.tripletest.review.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {

    List<ReviewEntity> getReviews();

    EventDto modifyReview(EventDto eventDto) throws Exception;


    List<ReviewEntity> findAllByPlaceId(UUID uuid);

    ReviewEntity save(ReviewEntity entity);

    Optional<ReviewEntity> findByUserIdAndPlaceId(UUID userUuid, UUID placeUuid);

    Optional<ReviewEntity> findById(UUID uuid);

    void deleteReview(UUID uuid);
}



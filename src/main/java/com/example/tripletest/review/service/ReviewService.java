package com.example.tripletest.review.service;

import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.user.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewEntity> getReviews();

    ReviewDto modifyReview(ReviewDto reviewDto) throws Exception;
    ReviewEntity addReview(ReviewDto reviewDto) throws Exception;

    ReviewDto getReview(UUID uuid) throws Exception;

    PointEntity deleteReview(ReviewDto reviewDto) throws Exception;
}


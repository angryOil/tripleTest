package com.example.tripletest.review.service;

import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.user.entity.UserEntity;

import java.util.List;

public interface ReviewService {

    List<ReviewEntity> getReviews();

    ReviewEntity ModifyReview(ReviewDto reviewDto);
    ReviewEntity addReview(ReviewDto reviewDto);

}

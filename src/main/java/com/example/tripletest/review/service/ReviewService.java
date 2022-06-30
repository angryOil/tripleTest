package com.example.tripletest.review.service;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.event.dto.EventDto;
import com.example.tripletest.review.entity.ReviewEntity;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewEntity> getReviews();

    EventDto modifyReview(EventDto eventDto) throws Exception;
    ReviewEntity addReview(EventDto eventDto) throws Exception;

    EventDto getReview(UUID uuid) throws Exception;

    PointEntity deleteReview(EventDto eventDto) throws Exception;
}


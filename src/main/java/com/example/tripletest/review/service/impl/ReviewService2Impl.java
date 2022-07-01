package com.example.tripletest.review.service.impl;

import com.example.tripletest.event.dto.EventDto;
import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.service.PhotoService;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.repository.ReviewRepository;
import com.example.tripletest.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReviewService2Impl implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public EventDto modifyReview(EventDto eventDto) throws Exception {
        return null;
    }


    @Override
    public void deleteReview(UUID uuid) {
        //리뷰삭제
        reviewRepository.deleteById(uuid);
    }

    @Override
    public Optional<ReviewEntity> findByUserIdAndPlaceId(UUID userUuid, UUID placeUuid) {
        return reviewRepository.findByUserIdAndPlaceId(userUuid, placeUuid);
    }

    @Override
    public List<ReviewEntity> findAllByPlaceId(UUID uuid) {
        return reviewRepository.findAllByPlaceId(uuid);
    }

    @Override
    public ReviewEntity save(ReviewEntity entity) {
        return reviewRepository.save(entity);
    }

    @Override
    public Optional<ReviewEntity> findById(UUID uuid) {
        return reviewRepository.findById(uuid);
    }


}

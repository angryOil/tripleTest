package com.example.tripletest.review.service.impl;

import com.example.tripletest.place.repository.PlaceRepository;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.repository.ReviewRepository;
import com.example.tripletest.review.service.ReviewService;
import com.example.tripletest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PointRepository pointRepository;
    @Override
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public ReviewEntity addReview(ReviewDto reviewDto) {
        // 유저 또는 장소가 존재하지않을경우
        if ((userRepository.findById(reviewDto.getUserId()).isEmpty()) || (placeRepository.findById(reviewDto.getPlaceId()).isEmpty())) {
            return null;
        } else {
            ReviewEntity findReview = reviewRepository
                    .findByUserIdAndPlaceId(reviewDto.getUserId(), reviewDto.getPlaceId());
            // 리뷰가 존재할경우
            if (findReview != null && (!findReview.isDeleteFlag())) return null;

            PointEntity point = pointRepository.findByUuid(reviewDto.getUserId());
            pointRepository.save(PointEntity.builder()
                    .uuid(point.getUuid())
                    .mileage(point.getMileage() + ((reviewDto.getPhotoNames() !=  null) ? 2 : 1))
                    .userId(point.getUserId())
                    .build());

            return reviewRepository.save(ReviewEntity.builder()
                    .userId(reviewDto.getUserId())
                    .placeId(reviewDto.getPlaceId())
                    .content(reviewDto.getContent())
                    .deleteFlag(false)
                    .build()
            );
        }
    }
}

package com.example.tripletest.review.service.impl;

import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.photo.repository.PhotoRepository;
import com.example.tripletest.place.repository.PlaceRepository;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.repository.PointLogRepository;
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
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PointRepository pointRepository;
    private final PointLogRepository pointLogRepository;
    private final PhotoRepository photoRepository;

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
            // 리뷰가 존재하고 삭제하지 않은 경우
            if (findReview != null && (!findReview.isDeleteFlag())) return null;
            //포인트 저장
            PointEntity pointEntity = pointRepository.findByUuid(reviewDto.getUserId());
            int plusMile = (reviewDto.getPhotoNames() != null) ? 2 : 1;
            pointRepository.save(PointEntity.builder()
                    .uuid(pointEntity.getUuid())
                    .mileage(pointEntity.getMileage() + plusMile)
                    .userId(pointEntity.getUserId())
                    .build());
            //리뷰 저장
            ReviewEntity reviewEntity = reviewRepository.save(ReviewEntity.builder()
                    .userId(reviewDto.getUserId())
                    .placeId(reviewDto.getPlaceId())
                    .content(reviewDto.getContent())
                    .deleteFlag(false)
                    .build()
            );

            //포토있을경우 포토 저장
            if (reviewDto.getPhotoNames().size() != 0) {
                reviewDto
                        .getPhotoNames()
                        .forEach(name -> {
                            photoRepository.save(
                                    PhotoEntity.builder()
                                            .reviewEntity(reviewEntity)
                                            .name(name)
                                            .build());
                        });
            }
            //포인트 로그
            pointLogRepository.save(PointLogEntity.builder()
                    .pointId(pointEntity.getUuid())
                    .reviewId(reviewEntity.getUuid())
                    .placeId(reviewDto.getPlaceId())
                    .action("ADD")
                    .pointApply(plusMile)
                    .build());

            //작성된리뷰의 엔티티를  리턴
            return reviewEntity;
        }
    }


    @Override
    @Transactional
    public ReviewEntity ModifyReview(ReviewDto reviewDto) {
        //이전 리뷰와 비교 후 점수 증감
        ReviewEntity originReview = reviewRepository.findByUserIdAndPlaceId(reviewDto.getUserId(), reviewDto.getPlaceId());
        List<PhotoEntity> originPhotos = photoRepository.findAllByReviewEntity(originReview);



        return null;
    }

}

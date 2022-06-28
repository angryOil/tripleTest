package com.example.tripletest.review.service.impl;

import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.photo.repository.PhotoRepository;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.repository.PlaceRepository;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.repository.PointLogRepository;
import com.example.tripletest.point.repository.PointRepository;
import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.repository.ReviewRepository;
import com.example.tripletest.review.service.ReviewService;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public ReviewEntity addReview(ReviewDto reviewDto) throws Exception {
        // 유저 또는 장소가 존재하지않을경우
        PlaceEntity placeEntity = placeRepository.findById(reviewDto.getPlaceId()).orElseThrow(() -> new BadCredentialsException("장소를 찾을수없습니다"));
        UserEntity userEntity = userRepository.findById(reviewDto.getUserId()).orElseThrow(() -> new BadCredentialsException("유저를 찾을수없습니다"));

        reviewRepository
                .findByUserIdAndPlaceId(reviewDto.getUserId(), reviewDto.getPlaceId()).ifPresent(review -> {
                    throw new IllegalStateException("이미 작성한 리뷰가 있습니다.");
                });

        //특정장소에 첫리뷰인지확인 첫리뷰라면 보너스 1점
        boolean isSpecialFirst = reviewRepository.findAllByPlaceId(placeEntity.getUuid()).isEmpty() && placeEntity.isSpecialFlag();
        boolean havePhotos = reviewDto.getPhotoDtos() != null;
        int plusMile;
        //포인트 저장
        PointEntity pointEntity = pointRepository.findByUuid(reviewDto.getUserId());
        if (isSpecialFirst) {
            plusMile = havePhotos ? +3 : +2;
        } else {
            plusMile = havePhotos ? +2 : +1;
        }
        pointRepository.save(PointEntity.builder()
                .uuid(pointEntity.getUuid())
                .mileage(pointEntity.getMileage() + plusMile)
                .userId(pointEntity.getUserId())
                .build());
        ReviewEntity reviewEntity;
        //리뷰 저장
        reviewEntity = reviewRepository.save(ReviewEntity.builder()
                .userId(reviewDto.getUserId())
                .placeId(reviewDto.getPlaceId())
                .content(reviewDto.getContent())
                .rewordScore(plusMile)
                .build()
        );


        //포토있을경우 포토 저장
        if (havePhotos) {
            reviewDto
                    .getPhotoDtos()
                    .forEach(photoDto -> {
                        photoRepository.save(
                                PhotoEntity.builder()
                                        .reviewEntity(reviewEntity)
                                        .name(photoDto.getPhotoName())
                                        .build());
                    });
        }
        String reviewKind = isSpecialFirst ? "special" : "";
        reviewKind += havePhotos ? " photo " : "";
        //포인트 로그
        pointLogRepository.save(PointLogEntity.builder()
                .pointId(pointEntity.getUuid())
                .reviewId(reviewEntity.getUuid())
                .placeId(reviewDto.getPlaceId())
                .action("ADD")
                .reviewKind(reviewKind)
                .pointApply(plusMile)
                .build());

        //작성된리뷰의 엔티티를  리턴
        return reviewEntity;

    }

    @Override
    public ReviewDto getReview(UUID uuid) {
        ReviewEntity reviewEntity = reviewRepository.findById(uuid).get();
        List<PhotoDto> photoDtos = photoRepository.findAllByReviewEntity(reviewEntity).stream().map(entity -> PhotoDto.builder()
                        .photoID(entity.getUuid())
                        .photoName(entity.getName())
                        .userId(reviewEntity.getUserId())
                        .build())
                .collect(Collectors.toList());
        return ReviewDto.builder()
                .uuid(reviewEntity.getUuid())
                .userId(reviewEntity.getUserId())
                .placeId(reviewEntity.getPlaceId())
                .content(reviewEntity.getContent())
                .photoDtos(photoDtos)
                .build();
    }


    @Override
    @Transactional
    public ReviewDto modifyReview(ReviewDto reviewDto) throws Exception {
        //이전 리뷰와 비교 후 점수 증감
        ReviewEntity originReview = reviewRepository.findByUserIdAndPlaceId(reviewDto.getUserId(), reviewDto.getPlaceId()).get();
        List<PhotoEntity> originPhotos = photoRepository.findAllByReviewEntity(originReview);

        PointEntity pointEntity = pointRepository.findByUuid(originReview.getUserId());
        //증감할 점수
        List<PhotoDto> updatePhotos = reviewDto.getPhotoDtos();
        int changeMile;
        String reviewKind = "";
        if (originPhotos.size() == 0) { //전리뷰에 사진이없었을경우 업데이트리뷰에 사진있으면 1점 없으면 0점추가
            changeMile = reviewDto.getPhotoDtos().size() == 0 ? 0 : 1;
            reviewKind += "add  photo";
        } else { //전리뷰는 사진이있었지만 업로드 리뷰에 사진이없다면 -1 있다면 0점
            changeMile = reviewDto.getPhotoDtos().size() == 0 ? -1 : 0;

        }

        //포인트 로그 포인트는 실제 점수가 변경될경우에만 수정
        if (changeMile != 0) {
            pointRepository.save(
                    PointEntity.builder()
                            .uuid(pointEntity.getUuid())
                            .userId(pointEntity.getUserId())
                            .mileage(pointEntity.getMileage() + changeMile)
                            .build());
            pointLogRepository.save(PointLogEntity.builder()
                    .action("MOD")
                    .reviewKind(reviewKind)
                    .reviewId(originReview.getUuid())
                    .placeId(originReview.getPlaceId())
                    .pointId(pointEntity.getUuid())
                    .pointApply(changeMile)
                    .build());
        }

        //사진 삭제후 다시 업로드
        photoRepository.deleteAllByReviewEntity(originReview);
        List<PhotoEntity> photoEntities = updatePhotos.stream().map(dto -> PhotoEntity.builder()
                .reviewEntity(originReview)
                .name(dto.getPhotoName())
                .build()).collect(Collectors.toList());
        photoRepository.saveAll(photoEntities);

        // 해당리뷰로 바뀐점수 적용 , 리뷰 수정
        reviewRepository.save(ReviewEntity.builder()
                .rewordScore(originReview.getRewordScore() + changeMile)
                .uuid(originReview.getUuid())
                .content(reviewDto.getContent())
                .placeId(originReview.getPlaceId())
                .userId(originReview.getUserId())
                .build());

        return reviewDto;
    }


    @Transactional(rollbackFor = SQLException.class)
    public PointEntity deleteReview(ReviewDto reviewDto) throws Exception {
        try {
            ReviewEntity reviewEntity = reviewRepository.findById(reviewDto.getUuid()).orElseThrow(() -> new RuntimeException("no review"));
            UserEntity userEntity = userRepository.findById(reviewEntity.getUserId()).orElseThrow(() -> new RuntimeException("no user"));
            PointEntity pointEntity = pointRepository.findByUuid(userEntity.getUuid());
            //점수 회수
            PointEntity point = pointRepository.save(PointEntity.builder()
                    .uuid(pointEntity.getUuid())
                    .userId(pointEntity.getUserId())
                    .mileage(pointEntity.getMileage() - reviewEntity.getRewordScore())
                    .build());
            //사진 삭제
            photoRepository.deleteAllByReviewEntity(reviewEntity);

            //리뷰삭제
            reviewRepository.deleteById(reviewEntity.getUuid());
            return point;
        } catch (Exception e) {
            throw new Exception("삭제에 실패했습니다");
        }
    }


}

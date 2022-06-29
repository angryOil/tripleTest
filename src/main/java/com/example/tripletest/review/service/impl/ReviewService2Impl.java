package com.example.tripletest.review.service.impl;

import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.photo.service.PhotoService;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.service.PlaceService;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.service.PointLogService;
import com.example.tripletest.point.service.PointService;
import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.repository.ReviewRepository;
import com.example.tripletest.review.service.ReviewService;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ReviewService2Impl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final PointService pointService;
    private final PointLogService pointLogService;
    private final PhotoService photoService;


    @Override
    public List<ReviewEntity> getReviews() {
        return reviewRepository.findAll();
    }


    @Override
    @Transactional
    public ReviewEntity addReview(ReviewDto reviewDto) throws Exception {
        System.out.println("리뷰 작성 impl2");
        // 유저 또는 장소가 존재하지않을경우
        PlaceEntity placeEntity = placeService.findById(reviewDto.getPlaceId()).orElseThrow(() -> new BadCredentialsException("장소를 찾을수없습니다"));
        UserEntity userEntity = userService.searchByUserId(reviewDto.getUserId()).orElseThrow(() -> new BadCredentialsException("유저를 찾을수없습니다"));
        reviewRepository.findByUserIdAndPlaceId(reviewDto.getUserId(), reviewDto.getPlaceId()).ifPresent(review -> {
            throw new IllegalStateException("이미 작성한 리뷰가 있습니다.");
        });


        //특정장소에 첫리뷰인지확인 첫리뷰라면 보너스 1점
        boolean isSpecialFirst = reviewRepository.findAllByPlaceId(placeEntity.getUuid()).isEmpty() && placeEntity.isSpecialFlag();
        boolean havePhotos = (reviewDto.getPhotoDtos() != null && reviewDto.getPhotoDtos().size() != 0);
        //포인트 저장
        PointEntity pointEntity = pointService.searchByUuid(reviewDto.getUserId());
        int plusMile = reviewDto.getContent() != null ? +1 : +0;
        if (isSpecialFirst) {
            plusMile += havePhotos ? 2 : 1;
        } else {
            plusMile += havePhotos ? 1 : 0;
        }
        pointService.save(PointEntity.builder()
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
            photoService.saveAllByEntity(reviewEntity, reviewDto.getPhotoDtos());
            //포인트 로그
            pointLogService.save(PointLogEntity.builder()
                    .pointId(pointEntity.getUuid())
                    .reviewId(reviewEntity.getUuid())
                    .placeId(reviewDto.getPlaceId())
                    .action("ADD")
                    .pointApply(plusMile)
                    .build());
        }  //작성된리뷰의 엔티티를  리턴
        return reviewEntity;
    }


    @Override
    public ReviewDto getReview(UUID uuid) throws Exception {
        ReviewEntity reviewEntity = reviewRepository.findById(uuid).orElseThrow(() -> new Exception("해당 아이디 리뷰가 없습니다"));
        List<PhotoDto> photoDtos = photoService.findAllByReviewEntity(reviewEntity).stream().map(entity -> PhotoDto.builder()
                        .photoID(entity.getUuid())
                        .photoName(entity.getName())
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
        List<PhotoEntity> originPhotos = photoService.findAllByReviewEntity(originReview);

        PointEntity pointEntity = pointService.searchByUuid(originReview.getUserId());
        List<PhotoDto> updatePhotos = reviewDto.getPhotoDtos();
        //증감할 점수
        int changeMile = 0;

        // 글 관련
        if (originReview.getContent() == null || originReview.getContent().length() == 0) {  //이전 리뷰에 글이없었는데 이번에 있다면 +1 아직도없다면 0
            changeMile += (reviewDto.getContent() == null || reviewDto.getContent().length() == 0) ? 0 : 1;
        } else {   //이전에 글이있는데 지금은 삭제했다면 -1 지금도 있으면 0
            changeMile += (reviewDto.getContent() == null || reviewDto.getContent().length() == 0) ? -1 : 0;
        }

        if (originPhotos == null || originPhotos.size() == 0) { // 이전 리뷰 사진이없었다면  이번에 있으면 +1 없으면 0
            changeMile += (reviewDto.getPhotoDtos() == null || reviewDto.getPhotoDtos().size() == 0) ? 0 : 1;
        } else {  //이전 리뷰에 사진있있었는데 지금은 지웠다면 -1 지금도 사진이 있다면 0
            changeMile += (reviewDto.getPhotoDtos() == null || reviewDto.getPhotoDtos().size() == 0) ? -1 : 0;
        }


        //포인트 로그 포인트는 실제 점수가 변경될경우에만 수정
        if (changeMile != 0) {
            pointService.save(
                    PointEntity.builder()
                            .uuid(pointEntity.getUuid())
                            .userId(pointEntity.getUserId())
                            .mileage(pointEntity.getMileage() + changeMile)
                            .build());
            pointLogService.save(PointLogEntity.builder()
                    .action("MOD")
                    .reviewId(originReview.getUuid())
                    .placeId(originReview.getPlaceId())
                    .pointId(pointEntity.getUuid())
                    .pointApply(changeMile)
                    .build());
        }

        //사진 삭제후 다시 업로드
        if (photoService.findAllByReviewEntity(originReview).size() > 0) {
            photoService.deleteAll(originReview);
            List<PhotoEntity> photoEntities = updatePhotos != null ? updatePhotos.stream().map(dto -> PhotoEntity.builder()
                    .reviewEntity(originReview)
                    .name(dto.getPhotoName())
                    .build()).collect(Collectors.toList()) : null;
            if (photoEntities != null) {
                photoService.saveAllByEntity(photoEntities);
            }
        } else {
            if (updatePhotos != null) {
                List<PhotoEntity> photoEntities = updatePhotos.stream().map(dto -> PhotoEntity.builder()
                        .reviewEntity(originReview)
                        .name(dto.getPhotoName())
                        .build()).collect(Collectors.toList());
                photoService.saveAllByEntity(photoEntities);
            }
        }

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

        ReviewEntity reviewEntity = reviewRepository.findById(reviewDto.getUuid()).orElseThrow(() -> new RuntimeException("없는 리뷰입니다"));
        UserEntity userEntity = userService.searchByUserId(reviewEntity.getUserId()).orElseThrow(() -> new RuntimeException("없는 사용자입니다"));
        PointEntity pointEntity = pointService.searchByUuid(userEntity.getUuid());
        //점수 회수
        PointEntity point = pointService.save(PointEntity.builder()
                .uuid(pointEntity.getUuid())
                .userId(pointEntity.getUserId())
                .mileage(pointEntity.getMileage() - reviewEntity.getRewordScore())
                .build());
        //사진 삭제
        photoService.deleteAll(reviewEntity);

        //리뷰삭제
        reviewRepository.deleteById(reviewEntity.getUuid());
        return point;

    }


}

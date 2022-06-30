package com.example.tripletest.review.service.impl;

import com.example.tripletest.customEnum.Action;
import com.example.tripletest.customEnum.Type;
import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.photo.service.PhotoService;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.service.PlaceService;
import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.service.PointLogService;
import com.example.tripletest.point.service.PointService;
import com.example.tripletest.event.dto.EventDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.repository.ReviewRepository;
import com.example.tripletest.review.service.ReviewService;
import com.example.tripletest.user.entity.UserEntity;
import com.example.tripletest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ReviewEntity addReview(EventDto eventDto) throws Exception {
        // 유저 또는 장소가 존재하지않을경우
        PlaceEntity placeEntity = placeService.findById(eventDto.getPlaceId()).orElseThrow(() -> new BadCredentialsException("장소를 찾을수없습니다"));
        UserEntity userEntity = userService.searchByUserId(eventDto.getUserId()).orElseThrow(() -> new BadCredentialsException("유저를 찾을수없습니다"));
        reviewRepository.findByUserIdAndPlaceId(eventDto.getUserId(), eventDto.getPlaceId()).ifPresent(review -> {
            throw new IllegalStateException("이미 작성한 리뷰가 있습니다.");
        });


        //특정장소에 첫리뷰인지확인 첫리뷰라면 보너스 1점
        boolean isSpecialFirst = reviewRepository.findAllByPlaceId(placeEntity.getUuid()).isEmpty() && placeEntity.isSpecialFlag();
        boolean havePhotos = (eventDto.getPhotoDtos() != null && eventDto.getPhotoDtos().size() != 0);
        //포인트 저장
        PointEntity pointEntity = pointService.searchByUuid(eventDto.getUserId());
        int plusMile = eventDto.getContent() != null ? +1 : +0;
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
                .userId(eventDto.getUserId())
                .placeId(eventDto.getPlaceId())
                .content(eventDto.getContent())
                .rewordScore(plusMile)
                .build()
        );


        //포토있을경우 포토 저장
        if (havePhotos) {
            photoService.saveAllByEntity(reviewEntity, eventDto.getPhotoDtos());
            //포인트 로그
            pointLogService.save(PointLogEntity.builder()
                    .pointId(pointEntity.getUuid())
                    .reviewId(reviewEntity.getUuid())
                    .placeId(eventDto.getPlaceId())
                    .action(Action.ADD.name())
                    .type(Type.REVIEW.name())
                    .pointApply(plusMile)
                    .build());
        }  //작성된리뷰의 엔티티를  리턴
        return reviewEntity;
    }


    @Override
    public EventDto getReview(UUID uuid) throws Exception {
        ReviewEntity reviewEntity = reviewRepository.findById(uuid).orElseThrow(() -> new Exception("해당 아이디 리뷰가 없습니다"));
        List<PhotoDto> photoDtos = photoService.findAllByReviewEntity(reviewEntity).stream().map(entity -> PhotoDto.builder()
                        .photoID(entity.getUuid())
                        .photoName(entity.getName())
                        .build())
                .collect(Collectors.toList());
        return EventDto.builder()
                .reviewId(reviewEntity.getUuid())
                .userId(reviewEntity.getUserId())
                .placeId(reviewEntity.getPlaceId())
                .content(reviewEntity.getContent())
                .photoDtos(photoDtos)
                .build();
    }


    @Override
    @Transactional
    public EventDto modifyReview(EventDto eventDto) throws Exception {
        //이전 리뷰와 비교 후 점수 증감

        ReviewEntity originReview = reviewRepository.findByUserIdAndPlaceId(eventDto.getUserId(), eventDto.getPlaceId()).orElseThrow(()->new Exception("아이디 혹은 장소가 유효하지 않습니다"));

        List<PhotoEntity> originPhotos = photoService.findAllByReviewEntity(originReview);

        PointEntity pointEntity = pointService.searchByUuid(originReview.getUserId());
        List<PhotoDto> updatePhotos = eventDto.getPhotoDtos();
        //증감할 점수
        int changeMile = 0;

        // 글 관련
        if (originReview.getContent() == null || originReview.getContent().length() == 0) {  //이전 리뷰에 글이없었는데 이번에 있다면 +1 아직도없다면 0
            changeMile += (eventDto.getContent() == null || eventDto.getContent().length() == 0) ? 0 : 1;
        } else {   //이전에 글이있는데 지금은 삭제했다면 -1 지금도 있으면 0
            changeMile += (eventDto.getContent() == null || eventDto.getContent().length() == 0) ? -1 : 0;
        }

        if (originPhotos == null || originPhotos.size() == 0) { // 이전 리뷰 사진이없었다면  이번에 있으면 +1 없으면 0
            changeMile += (eventDto.getPhotoDtos() == null || eventDto.getPhotoDtos().size() == 0) ? 0 : 1;
        } else {  //이전 리뷰에 사진있있었는데 지금은 지웠다면 -1 지금도 사진이 있다면 0
            changeMile += (eventDto.getPhotoDtos() == null || eventDto.getPhotoDtos().size() == 0) ? -1 : 0;
        }


        // 로그저장
        pointService.save(
                PointEntity.builder()
                        .uuid(pointEntity.getUuid())
                        .userId(pointEntity.getUserId())
                        .mileage(pointEntity.getMileage() + changeMile)
                        .build());
        pointLogService.save(PointLogEntity.builder()
                .action(Action.MOD.name())
                .type(Type.REVIEW.name())
                .reviewId(originReview.getUuid())
                .placeId(originReview.getPlaceId())
                .pointId(pointEntity.getUuid())
                .pointApply(changeMile)
                .build());

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
                .content(eventDto.getContent())
                .placeId(originReview.getPlaceId())
                .userId(originReview.getUserId())
                .build());

        return eventDto;
    }


    @Transactional
    public PointEntity deleteReview(EventDto eventDto) throws Exception {

        ReviewEntity reviewEntity = reviewRepository.findById(eventDto.getReviewId()).orElseThrow(() -> new RuntimeException("없는 리뷰입니다"));
        UserEntity userEntity = userService.searchByUserId(reviewEntity.getUserId()).orElseThrow(() -> new RuntimeException("없는 사용자입니다"));
        PointEntity pointEntity = pointService.searchByUuid(userEntity.getUuid());
        //로그 기록
        pointLogService.save(PointLogEntity.builder()
                .action(Action.DELETE.name())
                .type(Type.REVIEW.name())
                .reviewId(reviewEntity.getUuid())
                .placeId(reviewEntity.getPlaceId())
                .pointId(pointEntity.getUuid())
                .pointApply(-reviewEntity.getRewordScore())
                .build());
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

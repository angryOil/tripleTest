package com.example.tripletest.photo.service.impl;

import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.photo.repository.PhotoRepository;
import com.example.tripletest.photo.service.PhotoService;
import com.example.tripletest.review.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;


    public List<PhotoEntity> saveAllByEntity(ReviewEntity reviewEntity, List<PhotoDto> photoDtos) throws Exception {
        if (photoDtos == null || photoDtos.size() == 0) {
            return null;
        }
        try {
            List<PhotoEntity> photoEntities = photoDtos.stream().map(dto -> PhotoEntity.builder()
                    .name(dto.getPhotoName())
                    .reviewEntity(reviewEntity)
                    .build()
            ).collect(Collectors.toList());

            return photoRepository.saveAll(photoEntities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("저장실패");
        }
    }

    @Override
    public List<PhotoEntity> saveAllByEntity(List<PhotoEntity> photoEntities) throws Exception {
        return photoRepository.saveAll(photoEntities);
    }

    @Override
    public List<PhotoEntity> findAllByReviewEntity(ReviewEntity reviewEntity) {
        return photoRepository.findAllByReviewEntity(reviewEntity);
    }

    @Override
    public boolean deleteAll(ReviewEntity reviewEntity) throws Exception {
        try {
            photoRepository.deleteAllByReviewEntity(reviewEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("삭제에실패했습니다");
        }
    }
}

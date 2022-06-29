package com.example.tripletest.photo.service;


import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.photo.entity.PhotoEntity;
import com.example.tripletest.review.entity.ReviewEntity;

import java.util.List;

public interface PhotoService {
    List<PhotoEntity> saveAllByEntity(ReviewEntity reviewEntity, List<PhotoDto> photoDtos) throws Exception;
    List<PhotoEntity> saveAllByEntity(List<PhotoEntity> photoEntities) throws Exception;

    List<PhotoEntity> findAllByReviewEntity(ReviewEntity reviewEntity);
    boolean deleteAll(ReviewEntity reviewEntity) throws Exception;
}

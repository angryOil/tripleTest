package com.example.tripletest.review.dto;

import com.example.tripletest.photo.dto.PhotoDto;
import com.example.tripletest.review.entity.ReviewEntity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private UUID uuid;
    private UUID userId;
    private UUID placeId;
    private boolean deleteFlag;
    private String content;
    private List<PhotoDto> photoNames;

}

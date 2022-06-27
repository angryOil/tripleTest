package com.example.tripletest.review.dto;

import com.example.tripletest.photo.dto.PhotoDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private UUID uuid;
    private UUID userId;
    private UUID placeId;
    private boolean deleteFlag;
    private String content;
    private List<PhotoDto> photoDtos;

}

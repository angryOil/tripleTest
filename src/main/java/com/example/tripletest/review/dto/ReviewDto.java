package com.example.tripletest.review.dto;

import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.user.entity.UserEntity;
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
    private String content;
    private List<String> photoNames;
}

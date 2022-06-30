package com.example.tripletest.event.dto;

import com.example.tripletest.photo.dto.PhotoDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private UUID reviewId;
    private UUID userId;
    private UUID placeId;
    private boolean deleteFlag;
    private String content;
    private List<PhotoDto> photoDtos;

}

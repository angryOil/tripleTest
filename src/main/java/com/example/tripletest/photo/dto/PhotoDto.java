package com.example.tripletest.photo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PhotoDto {
    UUID photoIO;
    UUID userId;
    String photoName;
}

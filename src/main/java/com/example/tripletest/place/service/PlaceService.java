package com.example.tripletest.place.service;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaceService {

    PlaceEntity sava(PlaceDto placeDto);

    Optional<PlaceEntity> findById(UUID uuid);
    List<PlaceEntity> getPlaces();
}

package com.example.tripletest.place.service;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;

import java.util.List;

public interface PlaceService {

    PlaceEntity create(PlaceDto placeDto);
    List<PlaceEntity> getPlaces();
}

package com.example.tripletest.place.service;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;

public interface PlaceService {

    PlaceEntity create(PlaceDto placeDto);

}

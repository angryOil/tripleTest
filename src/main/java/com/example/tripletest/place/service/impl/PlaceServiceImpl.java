package com.example.tripletest.place.service.impl;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.repository.PlaceRepository;
import com.example.tripletest.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {


    private final PlaceRepository placeRepository;


    @Override
    public PlaceEntity create(PlaceDto placeDto) {
        return placeRepository.save(PlaceEntity
                .builder()
                .location(placeDto.getLocation())
                .name(placeDto.getName())
                .specialFlag(placeDto.isSpecialFlag())
                .build()
        );
    }

    @Override
    public List<PlaceEntity> getPlaces() {
        return placeRepository.findAll();
    }
}

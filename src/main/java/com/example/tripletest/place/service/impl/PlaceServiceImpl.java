package com.example.tripletest.place.service.impl;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.repository.PlaceRepository;
import com.example.tripletest.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {


    private final PlaceRepository placeRepository;



    @Override
    public PlaceEntity save(PlaceDto placeDto) throws Exception {
        try {
            return placeRepository.save(PlaceEntity
                    .builder()
                    .location(placeDto.getLocation())
                    .name(placeDto.getName())
                    .specialFlag(placeDto.isSpecialFlag())
                    .build()
            );
        } catch (Exception e) {
            throw new Exception("이미 존재하는 장소입니다.");
        }
    }

    @Override
    public Optional<PlaceEntity> findById(UUID uuid) {
        return placeRepository.findById(uuid);
    }


    @Override
    public List<PlaceEntity> getPlaces() {
        return placeRepository.findAll();
    }
}

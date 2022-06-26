package com.example.tripletest.place.controller;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/place/new")
    public PlaceEntity newPlace(@RequestBody PlaceDto placeDto) {
        return placeService.create(placeDto);
    }

    @GetMapping("/places")
    public List<PlaceEntity> getPlaces() {
        return placeService.getPlaces();
    }



}

package com.example.tripletest.place.controller;

import com.example.tripletest.place.dto.PlaceDto;
import com.example.tripletest.place.entity.PlaceEntity;
import com.example.tripletest.place.service.PlaceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "장소등록")
    @ApiParam("장소  + 장소이름")
    public ResponseEntity newPlace(@RequestBody PlaceDto placeDto) {
        try {
            return new ResponseEntity(placeService.save(placeDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("이미 존재하는 장소입니다", HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/places")
    @ApiOperation("모든 장소 보기")
    public List<PlaceEntity> getPlaces() {
        return placeService.getPlaces();
    }



}

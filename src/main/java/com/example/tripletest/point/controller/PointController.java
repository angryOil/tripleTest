package com.example.tripletest.point.controller;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.entity.PointLogEntity;
import com.example.tripletest.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/point/{userId}")
    public ResponseEntity findPoint(@PathVariable("userId") String userId) {
        try {
            return new ResponseEntity(pointService.searchByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

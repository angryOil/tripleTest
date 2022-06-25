package com.example.tripletest.point.controller;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/point/{userId}")
    public PointEntity findPoint(@PathVariable("userId") String userId) {
        return pointService.searchByUserId(userId);
    }

}

package com.example.tripletest.point.controller;

import com.example.tripletest.point.service.PointLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class PointLogController {
    private final PointLogService pointLogService;

    @GetMapping
    public ResponseEntity allLogs() {
        try {
            return new ResponseEntity(pointLogService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity getLogs(@PathVariable("userId")UUID uuid) {
        try {
            return new ResponseEntity(pointLogService.getByUserId(uuid), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

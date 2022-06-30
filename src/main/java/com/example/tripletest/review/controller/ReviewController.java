package com.example.tripletest.review.controller;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.event.dto.EventDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewEntity> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/review/{id}")
    public ResponseEntity getReview(@PathVariable("id") UUID id) {
        try {
            return new ResponseEntity(reviewService.getReview(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/review/new")
    public ResponseEntity addReview(@RequestBody EventDto eventDto) {
        try {
            ReviewEntity reviewEntity = reviewService.addReview(eventDto);
            return new ResponseEntity(reviewEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/review")
    public ResponseEntity modReview(@RequestBody EventDto eventDto) {
        try {
            return new ResponseEntity(reviewService.modifyReview(eventDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/review")
    public ResponseEntity deleteReview(@RequestBody EventDto eventDto) {
        try {
            PointEntity pointEntity = reviewService.deleteReview(eventDto);
            return new ResponseEntity<>(pointEntity, new HttpHeaders(HttpHeaders.EMPTY), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(HttpHeaders.EMPTY), HttpStatus.BAD_REQUEST);
        }
    }
}

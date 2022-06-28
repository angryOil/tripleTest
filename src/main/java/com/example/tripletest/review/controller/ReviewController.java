package com.example.tripletest.review.controller;

import com.example.tripletest.point.entity.PointEntity;
import com.example.tripletest.review.dto.ReviewDto;
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
    public ReviewDto getReview(@PathVariable("id") UUID id) {
        return reviewService.getReview(id);
    }

    @PostMapping("/review/new")
    public ResponseEntity addReview(@RequestBody ReviewDto reviewDto) throws Exception {
        try {
            ReviewEntity reviewEntity = reviewService.addReview(reviewDto);
            return new ResponseEntity(reviewEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/review")
    public ReviewDto modReview(@RequestBody ReviewDto reviewDto)throws Exception {
        return reviewService.modifyReview(reviewDto);
    }

    @DeleteMapping("/review")
    public ResponseEntity deleteReview(@RequestBody ReviewDto reviewDto) {
        try {
            PointEntity pointEntity = reviewService.deleteReview(reviewDto);
            return new ResponseEntity<>(pointEntity, new HttpHeaders(HttpHeaders.EMPTY), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(HttpHeaders.EMPTY), HttpStatus.BAD_REQUEST);
        }
    }
}

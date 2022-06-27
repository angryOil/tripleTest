package com.example.tripletest.review.controller;

import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public ReviewEntity addReview(@RequestBody ReviewDto reviewDto) throws Exception {
        return reviewService.addReview(reviewDto);
    }

    @PatchMapping("/review")
    public ReviewDto modReview(@RequestBody ReviewDto reviewDto)throws Exception {
        return reviewService.modifyReview(reviewDto);
    }
}

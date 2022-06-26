package com.example.tripletest.review.controller;

import com.example.tripletest.review.dto.ReviewDto;
import com.example.tripletest.review.entity.ReviewEntity;
import com.example.tripletest.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewEntity> getReviews() {
        return reviewService.getReviews();
    }

    @PostMapping("/review/new")
    public ReviewEntity addReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(reviewDto);
    }
}

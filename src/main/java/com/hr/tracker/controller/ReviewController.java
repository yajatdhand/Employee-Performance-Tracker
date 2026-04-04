package com.hr.tracker.controller;

import com.hr.tracker.dto.request.SubmitReviewRequest;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;
import com.hr.tracker.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewWithCycleResponse> submitReview(
            @RequestHeader(value = "Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody SubmitReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.submitReview(request, idempotencyKey));
    }
}

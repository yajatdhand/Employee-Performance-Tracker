package com.hr.tracker.service;

import com.hr.tracker.dto.request.SubmitReviewRequest;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;

public interface ReviewService {
    ReviewWithCycleResponse submitReview(SubmitReviewRequest request, String idempotencyKey);
}

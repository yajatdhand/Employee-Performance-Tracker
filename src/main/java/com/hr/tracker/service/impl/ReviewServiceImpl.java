package com.hr.tracker.service.impl;

import com.hr.tracker.dto.request.SubmitReviewRequest;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;
import com.hr.tracker.entity.Employee;
import com.hr.tracker.entity.PerformanceReview;
import com.hr.tracker.entity.ReviewCycle;
import com.hr.tracker.exception.DuplicateRequestException;
import com.hr.tracker.exception.ResourceNotFoundException;
import com.hr.tracker.repository.EmployeeRepository;
import com.hr.tracker.repository.PerformanceReviewRepository;
import com.hr.tracker.repository.ReviewCycleRepository;
import com.hr.tracker.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;
    private final ReviewCycleRepository cycleRepository;

    // Simple zero-setup in-memory lock for dev environments
    private final ConcurrentHashMap<String, Instant> idempotencyStore = new ConcurrentHashMap<>();

    @Override
    @Transactional
    @CacheEvict(value = {"cycleSummary", "employeeReviews", "employeesByDept"}, allEntries = true)
    public ReviewWithCycleResponse submitReview(SubmitReviewRequest request, String idempotencyKey) {

        // 1. Clean up expired keys (prevent memory leak)
        idempotencyStore.entrySet().removeIf(entry -> entry.getValue().isBefore(Instant.now()));

        // 2. Atomic Idempotency Check
        if (idempotencyStore.putIfAbsent(idempotencyKey, Instant.now().plus(Duration.ofMinutes(5))) != null) {
            throw new DuplicateRequestException("Review submission already in progress or completed for this key.");
        }

        try {
            // 3. Validate Entities
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.getEmployeeId()));

            ReviewCycle cycle = cycleRepository.findById(request.getReviewCycleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Review cycle not found: " + request.getReviewCycleId()));

            // 4. Save
            PerformanceReview review = PerformanceReview.builder()
                    .employee(employee)
                    .reviewCycle(cycle)
                    .rating(request.getRating())
                    .reviewerNotes(request.getReviewerNotes())
                    .build();

            PerformanceReview saved = reviewRepository.save(review);

            return ReviewWithCycleResponse.builder()
                    .reviewId(saved.getId())
                    .rating(saved.getRating())
                    .reviewerNotes(saved.getReviewerNotes())
                    .submittedAt(saved.getSubmittedAt())
                    .cycleId(cycle.getId())
                    .cycleName(cycle.getName())
                    .cycleStartDate(cycle.getStartDate())
                    .cycleEndDate(cycle.getEndDate())
                    .build();

        } catch (Exception e) {
            // 5. Release lock if transaction fails so user can retry safely
            idempotencyStore.remove(idempotencyKey);
            throw e;
        }
    }
}

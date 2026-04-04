package com.hr.tracker.service.impl;

import com.hr.tracker.dto.response.CycleSummaryResponse;
import com.hr.tracker.entity.Employee;
import com.hr.tracker.entity.ReviewCycle;
import com.hr.tracker.exception.ResourceNotFoundException;
import com.hr.tracker.repository.GoalRepository;
import com.hr.tracker.repository.PerformanceReviewRepository;
import com.hr.tracker.repository.ReviewCycleRepository;
import com.hr.tracker.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CycleServiceImpl implements CycleService {

    private final ReviewCycleRepository cycleRepository;
    private final PerformanceReviewRepository reviewRepository;
    private final GoalRepository goalRepository;

    @Override
    @Cacheable(value = "cycleSummary", key = "#cycleId")
    public CycleSummaryResponse getCycleSummary(Long cycleId) {
        ReviewCycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new ResourceNotFoundException("Review cycle not found: " + cycleId));

        Double avgRating = reviewRepository.findAverageRatingByCycleId(cycleId);
        Optional<Employee> topPerformer = reviewRepository.findTopPerformerByCycleId(cycleId);
        Long completed = goalRepository.countCompletedByCycleId(cycleId);
        Long missed = goalRepository.countMissedByCycleId(cycleId);

        return CycleSummaryResponse.builder()
                .cycleId(cycleId)
                .cycleName(cycle.getName())
                .averageRating(avgRating != null ? avgRating : 0.0)
                .topPerformerName(topPerformer.map(Employee::getName).orElse(null))
                .topPerformerId(topPerformer.map(Employee::getId).orElse(null))
                .completedGoals(completed)
                .missedGoals(missed)
                .build();
    }
}

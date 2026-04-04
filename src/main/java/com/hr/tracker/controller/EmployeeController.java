package com.hr.tracker.controller;

import com.hr.tracker.dto.request.CreateEmployeeRequest;
import com.hr.tracker.dto.response.EmployeeRatingResponse;
import com.hr.tracker.dto.response.EmployeeResponse;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;
import com.hr.tracker.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewWithCycleResponse>> getReviews(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getReviewsForEmployee(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeRatingResponse>> filterEmployees(
            @RequestParam String department,
            @RequestParam Double minRating,
            Pageable pageable) {
        return ResponseEntity.ok(employeeService.filterEmployees(department, minRating, pageable));
    }
}

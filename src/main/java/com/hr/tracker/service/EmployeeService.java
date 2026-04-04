package com.hr.tracker.service;

import com.hr.tracker.dto.request.CreateEmployeeRequest;
import com.hr.tracker.dto.response.EmployeeRatingResponse;
import com.hr.tracker.dto.response.EmployeeResponse;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(CreateEmployeeRequest request);
    List<ReviewWithCycleResponse> getReviewsForEmployee(Long employeeId);
    Page<EmployeeRatingResponse> filterEmployees(String department, Double minRating, Pageable pageable);
}

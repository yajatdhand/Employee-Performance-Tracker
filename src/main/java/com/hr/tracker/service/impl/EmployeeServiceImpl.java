package com.hr.tracker.service.impl;

import com.hr.tracker.dto.request.CreateEmployeeRequest;
import com.hr.tracker.dto.response.EmployeeRatingResponse;
import com.hr.tracker.dto.response.EmployeeResponse;
import com.hr.tracker.dto.response.ReviewWithCycleResponse;
import com.hr.tracker.entity.Employee;
import com.hr.tracker.exception.ResourceNotFoundException;
import com.hr.tracker.repository.EmployeeRepository;
import com.hr.tracker.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        Employee employee = Employee.builder()
                .name(request.getName())
                .department(request.getDepartment())
                .role(request.getRole())
                .joiningDate(request.getJoiningDate())
                .email(request.getEmail())
                .build();
        Employee saved = employeeRepository.save(employee);
        return toResponse(saved);
    }

    @Override
    @Cacheable(value = "employeeReviews", key = "#employeeId")
    public List<ReviewWithCycleResponse> getReviewsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findByIdWithReviews(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        return employee.getReviews().stream()
                .map(r -> ReviewWithCycleResponse.builder()
                        .reviewId(r.getId())
                        .rating(r.getRating())
                        .reviewerNotes(r.getReviewerNotes())
                        .submittedAt(r.getSubmittedAt())
                        .cycleId(r.getReviewCycle().getId())
                        .cycleName(r.getReviewCycle().getName())
                        .cycleStartDate(r.getReviewCycle().getStartDate())
                        .cycleEndDate(r.getReviewCycle().getEndDate())
                        .build())
                .toList();
    }

    @Override
    @Cacheable(value = "employeesByDept", key = "#department + '-' + #minRating + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<EmployeeRatingResponse> filterEmployees(String department, Double minRating, Pageable pageable) {
        return employeeRepository.findByDepartmentAndMinRating(department, minRating, pageable);
    }

    private EmployeeResponse toResponse(Employee e) {
        return EmployeeResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .department(e.getDepartment())
                .role(e.getRole())
                .joiningDate(e.getJoiningDate())
                .email(e.getEmail())
                .build();
    }
}

package com.hr.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRatingResponse {
    private Long id;
    private String name;
    private String department;
    private String role;
    private Double averageRating;
}

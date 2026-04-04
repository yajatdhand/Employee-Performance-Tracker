package com.hr.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewWithCycleResponse {
    private Long reviewId;
    private Integer rating;
    private String reviewerNotes;
    private LocalDateTime submittedAt;
    private Long cycleId;
    private String cycleName;
    private LocalDate cycleStartDate;
    private LocalDate cycleEndDate;
}

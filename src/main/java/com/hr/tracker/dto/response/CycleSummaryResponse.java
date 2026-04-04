package com.hr.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CycleSummaryResponse {
    private Long cycleId;
    private String cycleName;
    private Double averageRating;
    private String topPerformerName;
    private Long topPerformerId;
    private Long completedGoals;
    private Long missedGoals;
}

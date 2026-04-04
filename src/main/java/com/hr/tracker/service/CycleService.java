package com.hr.tracker.service;

import com.hr.tracker.dto.response.CycleSummaryResponse;

public interface CycleService {
    CycleSummaryResponse getCycleSummary(Long cycleId);
}

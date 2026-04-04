package com.hr.tracker.controller;

import com.hr.tracker.dto.response.CycleSummaryResponse;
import com.hr.tracker.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cycles")
@RequiredArgsConstructor
public class CycleController {
    private final CycleService cycleService;

    @GetMapping("/{id}/summary")
    public ResponseEntity<CycleSummaryResponse> getSummary(@PathVariable Long id) {
        return ResponseEntity.ok(cycleService.getCycleSummary(id));
    }
}

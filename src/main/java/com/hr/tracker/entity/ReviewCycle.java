package com.hr.tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review_cycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "reviewCycle", fetch = FetchType.LAZY)
    @Builder.Default
    private List<PerformanceReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "reviewCycle", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Goal> goals = new ArrayList<>();
}

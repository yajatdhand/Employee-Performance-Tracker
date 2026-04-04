package com.hr.tracker.repository;

import com.hr.tracker.entity.Employee;
import com.hr.tracker.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    @Query("SELECT AVG(r.rating) FROM PerformanceReview r WHERE r.reviewCycle.id = :cycleId")
    Double findAverageRatingByCycleId(@Param("cycleId") Long cycleId);

    @Query("SELECT r.employee FROM PerformanceReview r " +
           "WHERE r.reviewCycle.id = :cycleId " +
           "GROUP BY r.employee " +
           "ORDER BY AVG(r.rating) DESC " +
           "LIMIT 1")
    Optional<Employee> findTopPerformerByCycleId(@Param("cycleId") Long cycleId);
}

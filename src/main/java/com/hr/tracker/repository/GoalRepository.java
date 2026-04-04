package com.hr.tracker.repository;

import com.hr.tracker.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT COUNT(g) FROM Goal g WHERE g.reviewCycle.id = :cycleId AND g.status = 'completed'")
    Long countCompletedByCycleId(@Param("cycleId") Long cycleId);

    @Query("SELECT COUNT(g) FROM Goal g WHERE g.reviewCycle.id = :cycleId AND g.status = 'missed'")
    Long countMissedByCycleId(@Param("cycleId") Long cycleId);
}

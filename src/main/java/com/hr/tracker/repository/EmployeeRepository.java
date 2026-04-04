package com.hr.tracker.repository;

import com.hr.tracker.dto.response.EmployeeRatingResponse;
import com.hr.tracker.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e " +
           "LEFT JOIN FETCH e.reviews r " +
           "LEFT JOIN FETCH r.reviewCycle " +
           "WHERE e.id = :id")
    Optional<Employee> findByIdWithReviews(@Param("id") Long id);

    @Query("SELECT new com.hr.tracker.dto.response.EmployeeRatingResponse(" +
           "e.id, e.name, e.department, e.role, AVG(r.rating)) " +
           "FROM Employee e " +
           "JOIN e.reviews r " +
           "WHERE e.department = :department " +
           "GROUP BY e.id, e.name, e.department, e.role " +
           "HAVING AVG(r.rating) >= :minRating")
    Page<EmployeeRatingResponse> findByDepartmentAndMinRating(
        @Param("department") String department,
        @Param("minRating") Double minRating,
        Pageable pageable);
}

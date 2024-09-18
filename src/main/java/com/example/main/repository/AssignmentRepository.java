package com.example.main.repository;

import com.example.main.modals.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourse_UserUserID(Long userId);
    List<Assignment> findByCourse_UserUserIDAndDueDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}

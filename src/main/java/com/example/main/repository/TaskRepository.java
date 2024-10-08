package com.example.main.repository;

import com.example.main.modals.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserUserID(Long userId);
    List<Task> findByUserUserIDAndDueDateLessThanEqual(Long userId, LocalDate dueDate);


}
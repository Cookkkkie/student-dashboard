package com.example.main.repository;

import com.example.main.modals.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getCourseByCourseID(Long id);
}
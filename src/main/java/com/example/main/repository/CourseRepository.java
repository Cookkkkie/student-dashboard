package com.example.main.repository;

import com.example.main.modals.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getCourseByCourseID(Long id);

    @Query("SELECT c FROM Course c WHERE c.user.userID = :userID")
    List<Course> findByUserId(@Param("userID") Long userID);

    }
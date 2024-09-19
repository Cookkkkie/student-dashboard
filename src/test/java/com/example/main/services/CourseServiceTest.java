package com.example.main.services;

import com.example.main.dtos.ApiResponseDto;
import com.example.main.dtos.ApiResponseStatus;
import com.example.main.dtos.CreateCourseDto;
import com.example.main.modals.Course;
import com.example.main.modals.UserMod;
import com.example.main.repository.CourseRepository;
import com.example.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse() {
        // given
        CreateCourseDto createCourseDto = new CreateCourseDto("Test Course");

        UserMod user = new UserMod();
        user.setUserID(1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // when
        courseService.createCourse(createCourseDto, 1);

        // then
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testGetAllCourses() {
        // given
        UserMod user = new UserMod();
        user.setUserID(1);

        Course course1 = new Course();
        course1.setUser(user);

        Course course2 = new Course();
        course2.setUser(user);

        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findByUserId(1L)).thenReturn(courses);

        // when
        ResponseEntity<ApiResponseDto<?>> response = courseService.getAllCourses(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS.name(), response.getBody().getStatus());
        assertEquals(2, ((List<?>) response.getBody().getResponse()).size());
    }

    @Test
    void testGetAllCoursesWithException() {
        // given
        when(courseRepository.findByUserId(1L)).thenThrow(new RuntimeException("Database error"));

        // when
        ResponseEntity<ApiResponseDto<?>> response = courseService.getAllCourses(1L);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ApiResponseStatus.FAIL.name(), response.getBody().getStatus());
        assertEquals("Error retrieving courses", response.getBody().getResponse());
    }

    @Test
    void deleteById() {
        // given
        Long courseId = 1L;
        Course course = new Course();
        course.setCourseID(courseId);

        when(courseRepository.getCourseByCourseID(courseId)).thenReturn(course);

        // when
        courseService.deleteById(courseId);

        // then
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    void deleteById_NotFound() {
        // given
        Long courseId = 1L;

        when(courseRepository.getCourseByCourseID(courseId)).thenReturn(null);

        // when
        courseService.deleteById(courseId);

        // then
        verify(courseRepository, never()).deleteById(courseId);
    }
}

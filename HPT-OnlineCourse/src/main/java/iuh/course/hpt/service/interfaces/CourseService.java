package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    Page<Course> pageAllCourse(Pageable pageable);
    Course getCourseById(int id);
    void saveCourse(Course course);
}

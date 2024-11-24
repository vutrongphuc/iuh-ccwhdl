package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // find by youtube id
    Course findByYtId(String ytId);
    
    // check if course existed by course name
    boolean existsByCourseName(String courseName);
    
    List<Course> findByAuthorId(Long authorId);

    int countByAuthorId(Long authorId);
    
    // find by course name containing keyword
    List<Course> findByCourseNameContaining(String keyword);
}

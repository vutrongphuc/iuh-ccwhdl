package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // find by youtube id
    Course findByYtId(String ytId);
    Course findByCourseName(String courseName);

    List<Course> findByAuthorId(Long authorId);

    int countByAuthorId(Long authorId);
}

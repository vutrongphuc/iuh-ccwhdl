package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.entity.Course;

import java.util.List;

public interface CourseService {

    Course findYoutubeId(String youtubeId);

    List<Course> getAll();

    Course getById(Long id);

    Course save(Course course);

    void deleteById(Long id);

    boolean isCourseExisted(String courseName);

    String extractYoutubeId(String youtubeUrl);

    List<Course> findByAuthor(Long authorId);

    int countByAuthorId(Long authorId);
}

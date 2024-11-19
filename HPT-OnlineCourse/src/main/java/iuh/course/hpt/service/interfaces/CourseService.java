package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    String extractYoutubeId(String youtubeId);
    Course fetchYoutubeData(String ytId);
    void saveCourse(Course course);
}

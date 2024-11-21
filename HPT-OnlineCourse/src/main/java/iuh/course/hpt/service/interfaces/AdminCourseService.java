package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Course;

public interface AdminCourseService {
    String extractYoutubeId(String youtubeId);
    Course fetchYoutubeData(String ytId);
    void saveCourse(Course course);
    Course findCourseById(int id);
    void deleteCourse(int id);
}

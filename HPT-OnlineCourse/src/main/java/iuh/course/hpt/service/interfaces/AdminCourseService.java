package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Course;

import java.util.List;

public interface AdminCourseService {

    // Analyze youtube url
    String extractYoutubeId(String youtubeId);
    List<String> fetchYoutubeData(String ytId);

    // admin course service
    void addCourse(Course course);
    void updateCourse(int id, Course course);
    void deleteCourse(int id);
}

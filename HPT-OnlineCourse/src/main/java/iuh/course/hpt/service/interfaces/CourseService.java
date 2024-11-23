package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Course;

import java.util.List;

public interface CourseService {

    Course findYoutubeId(String youtubeId);

    public List<Course> getAll();

    public Course getById(Long id);

    public Course save(Course course);

    public void deleteById(Long id);

    boolean isCourseExisted(String courseName);

    String extractYoutubeId(String youtubeUrl);


}

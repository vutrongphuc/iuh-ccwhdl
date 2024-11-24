package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.repository.AuthorRepository;
import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.repository.EnrollmentRepository;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    // inject CourseRepository
    @Autowired
    private CourseRepository courseRepository;

    // inject EnrollmentRepository
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Course findYoutubeId(String youtubeId) {
        return courseRepository.findByYtId(youtubeId);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public boolean isCourseExisted(String courseName) {
        return courseRepository.findByCourseName(courseName) != null;
    }

    @Override
    public String extractYoutubeId(String youtubeUrl) {
        int startIndex = youtubeUrl.indexOf("v=") + 2;
        int endIndex = youtubeUrl.indexOf("&", startIndex);
        return (endIndex == -1) ? youtubeUrl.substring(startIndex) : youtubeUrl.substring(startIndex, endIndex);
    }

    @Override
    public List<Course> findByAuthor(Long authorId) {
        return courseRepository.findByAuthorId(authorId);
    }

    @Override
    public int countByAuthorId(Long authorId) {
        return courseRepository.countByAuthorId(authorId);
    }
}

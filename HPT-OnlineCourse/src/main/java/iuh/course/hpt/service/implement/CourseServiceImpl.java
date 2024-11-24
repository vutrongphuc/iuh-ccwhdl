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
        return courseRepository.existsByCourseName(courseName);
    }

    @Override
    public String extractYoutubeId(String youtubeUrl) {
        if (youtubeUrl.contains("?v=")) {
            return youtubeUrl.split("\\?v=")[1];
        }

        // define the pattern of youtube url
        String pattern = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        // replace the pattern with empty string
        String youtubeId = youtubeUrl.replaceAll(pattern, "");
        // if the youtubeId contains "&" then split it
        if (youtubeId.contains("&")) {
            youtubeId = youtubeId.split("&")[0];
        }
        if (youtubeId.contains("?")) {
            youtubeId = youtubeId.split("\\?")[0];
        }
        return youtubeId;
    }

    @Override
    public List<Course> findByAuthor(Long authorId) {
        return courseRepository.findByAuthorId(authorId);
    }

    @Override
    public int countByAuthorId(Long authorId) {
        return courseRepository.countByAuthorId(authorId);
    }

    @Override
    public List<Course> findByCourseName(String courseName) {
        return courseRepository.findByCourseNameContaining(courseName);
    }
}

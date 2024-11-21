package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.repository.AuthorRepository;
import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.repository.EnrollmentRepository;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Page<Course> pageAllCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
}

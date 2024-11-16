package iuh.course.hpt.service.implement;

import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.repository.EnrollmentRepository;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    // inject CourseRepository
    @Autowired
    private CourseRepository courseRepository;

    // inject EnrollmentRepository
    @Autowired
    private EnrollmentRepository enrollmentRepository;
}

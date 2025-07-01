package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.entity.Enrollment;
import iuh.course.hpt.entity.User;
import iuh.course.hpt.repository.EnrollmentRepository;
import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.repository.UserRepository;
import iuh.course.hpt.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public boolean isEnrolled(Long courseId, Long userId) {
        return enrollmentRepository.existsByCourseIdAndUserId(courseId, userId);
    }
    
    @Override
    public void enroll(Long courseId, Long userId) {
        // get course by id
        Course course = courseRepository.findById(courseId).orElse(null);
        
        // get user by id
        User user = userRepository.findById(userId).orElse(null);
        
        // create a new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollmentRepository.save(enrollment);
    }
    
}

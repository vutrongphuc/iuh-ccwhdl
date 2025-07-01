package iuh.course.hpt.service.interfaces;

public interface EnrollmentService {
    boolean isEnrolled(Long courseId, Long userId);

    void enroll(Long courseId, Long userId);
}

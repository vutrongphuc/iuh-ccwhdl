package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Enrollment;
import iuh.course.hpt.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
}
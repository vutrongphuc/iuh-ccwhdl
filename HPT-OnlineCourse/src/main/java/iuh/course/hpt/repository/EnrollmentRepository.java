package iuh.course.hpt.repository;

import iuh.course.hpt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<User, Long> {
}
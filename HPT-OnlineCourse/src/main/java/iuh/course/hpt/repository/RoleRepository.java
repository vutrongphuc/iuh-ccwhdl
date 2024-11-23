package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Role;
import iuh.course.hpt.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
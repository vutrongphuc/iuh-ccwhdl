package iuh.course.hpt.repository;

import iuh.course.hpt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    
    void deleteUserById(Long id);
}

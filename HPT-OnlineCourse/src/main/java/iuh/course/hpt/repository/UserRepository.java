package iuh.course.hpt.repository;

import iuh.course.hpt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    User findUserByUserNameAndPassword(String userName, String password);

    void deleteUserById(Long id);
}

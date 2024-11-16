package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.User;
import iuh.course.hpt.entity.enums.Role;
import iuh.course.hpt.repository.UserRepository;
import iuh.course.hpt.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    // inject UserRepository
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValidUser(User user) {
        User result = userRepository.findUserByUserNameAndPassword(user.getUserName(), user.getPassword());
        if (result != null) {
            user.setId(result.getId());
            user.setUserName(result.getUserName());
            user.setFullName(result.getPassword());
            user.setRole(result.getRole());
            user.setAdmin(result.getRole() == Role.ADMIN);
            
            return true;
        }
        return false;
    }

    public boolean isUserExisted(String userName) {
        User result = userRepository.findByUserName(userName);
        return result != null;
    }

    @Override
    public User save(User user) {
        user = userRepository.save(user);
        user.setAdmin(user.getRole() == Role.ADMIN);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteUserById(id);
    }
}

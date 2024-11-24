package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Role;
import iuh.course.hpt.entity.User;
import iuh.course.hpt.entity.enums.RoleEnum;
import iuh.course.hpt.repository.RoleRepository;
import iuh.course.hpt.repository.UserRepository;
import iuh.course.hpt.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    // inject UserRepository
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isUserExisted(String userName) {
        User result = userRepository.findByUsername(userName);
        return result != null;
    }

    @Override
    public User save(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // set default role for user
        if (user.getRole() == null) {
            Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
            if (optionalRole.isPresent()) {
                user.setRole(optionalRole.get());
            }
        }

        user = userRepository.save(user);

        return user;
    }

    // update user
    @Override
    public User update(User user) {
        // get user by username
        User userToUpdate = userRepository.findByUsername(user.getUsername());
        userToUpdate.setFullName(user.getFullName());

        if (user.getPassword() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user = userRepository.save(userToUpdate);

        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
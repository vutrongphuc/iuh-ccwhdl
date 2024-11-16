package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.User;

public interface UserService {

    boolean isValidUser(User user);

    boolean isUserExisted(String userName);

    User save(User user);

    void deleteById(Long id);
    
}

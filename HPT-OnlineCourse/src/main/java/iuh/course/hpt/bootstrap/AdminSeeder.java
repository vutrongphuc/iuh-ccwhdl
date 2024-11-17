package iuh.course.hpt.bootstrap;

import iuh.course.hpt.entity.Role;
import iuh.course.hpt.entity.User;
import iuh.course.hpt.entity.enums.RoleEnum;
import iuh.course.hpt.repository.RoleRepository;
import iuh.course.hpt.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        User adminUser = userRepository.findByUsername("admin");

        if (optionalRole.isEmpty() || adminUser != null) {
            return;
        }

        var user = new User();
        user.setUsername("admin");
        user.setFullName("Feliz Vu");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}

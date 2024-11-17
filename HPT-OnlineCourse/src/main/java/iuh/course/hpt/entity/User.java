package iuh.course.hpt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data // Generates getters, setters, ToString on all non-final fields
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all args
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    // create column role with enum type
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Transient
    private String repeatPassword;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime modifiedAt;

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

    // Getters and Setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());

        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}

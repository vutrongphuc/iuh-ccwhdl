package iuh.course.hpt.entity;

import iuh.course.hpt.entity.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data // Generates getters, setters, ToString on all non-final fields
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all args
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, nullable = false, length = 20)
    private RoleEnum name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime modifiedAt;
}

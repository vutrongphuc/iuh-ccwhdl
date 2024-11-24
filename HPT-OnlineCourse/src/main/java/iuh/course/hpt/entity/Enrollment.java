package iuh.course.hpt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data // Generates getters, setters, ToString on all non-final fields
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all args
@Entity
@IdClass(EnrollmentId.class)
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "FK_course_enrollment"))
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_user_enrollment"))
    private User user;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    // Getters and Setters
}

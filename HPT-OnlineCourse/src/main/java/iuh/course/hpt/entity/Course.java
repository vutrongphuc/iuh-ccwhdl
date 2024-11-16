package iuh.course.hpt.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data // Generates getters, setters, ToString on all non-final fields
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all args
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "yt_id", nullable = false, unique = true, length = 11)
    private String ytId;

    @Column(name = "course_name", nullable = false, length = 255)
    private String courseName;

    @Column(name = "course_desc", nullable = false, columnDefinition = "TEXT")
    private String courseDesc;

    @Column(name = "duration", nullable = false, length = 15)
    private String duration;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime modifiedAt;

    @Transient
    private boolean isEnrolled;

    // Getters and Setters
}

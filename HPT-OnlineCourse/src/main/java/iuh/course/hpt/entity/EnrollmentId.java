package iuh.course.hpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentId implements Serializable {
    private Long user;
    private Long course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(course, that.course) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, user);
    }
}

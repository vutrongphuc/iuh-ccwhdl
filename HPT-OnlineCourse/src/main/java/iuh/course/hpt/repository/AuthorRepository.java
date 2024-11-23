package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByAuthorName(String authorName);
}

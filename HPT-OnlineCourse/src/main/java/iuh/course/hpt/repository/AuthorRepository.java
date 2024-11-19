package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByAuthorName(String authorName);
}

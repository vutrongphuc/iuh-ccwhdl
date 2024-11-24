package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Author;

import java.util.List;

public interface AuthorService {

    // get all
    List<Author> getAll();

    // Find author by name
    Author findByAuthorName(String authorName);

    // Add new author
    void createAuthor(Author author);

    // Delete author
    void deleteAuthor(Long id);

    // Check author is existed
    boolean isAuthorExisted(String authorName);

    // check author email
    boolean isAuthorEmailExisted(String authorEmail);

    Author getById(Long id);

    // Update author
    Author updateAuthor(Author author);
}

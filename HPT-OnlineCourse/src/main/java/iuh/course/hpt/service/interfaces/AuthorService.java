package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Author;

public interface AuthorService {

    // Find author by name
    Author findByAuthorName(String authorName);

    // Add new author
    void createAuthor(Author author);
    
}

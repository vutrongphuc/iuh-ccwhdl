package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.repository.AuthorRepository;
import iuh.course.hpt.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author findByAuthorName(String authorName) {
        return authorRepository.findByAuthorName(authorName);
    }

    @Override
    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public boolean isAuthorExisted(String authorName) {
        Author result = authorRepository.findByAuthorName(authorName);
        return result != null;
    }

}

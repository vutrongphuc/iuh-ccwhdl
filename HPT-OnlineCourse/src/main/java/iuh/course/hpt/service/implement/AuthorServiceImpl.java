package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.repository.AuthorRepository;
import iuh.course.hpt.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author findByAuthorName(String authorName) {
        return authorRepository.findByAuthorName(authorName);
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Author getOrCreateAuthor(String authorName) {
        Author author = findByAuthorName(authorName);
        if (author == null) {
            author.setAuthorName(authorName);
            author.setAuthor_intro("Thông tin tác giả hiện chưa được cập nhật.");
            author.setAuthor_email(authorName.replace(" ", "") + "@gmail.com"); // Email
            saveAuthor(author);
        }
        return author;
    }

}

package by.modsen.library_app.service.book;

import by.modsen.library_app.model.book.Author;
import by.modsen.library_app.repository.book.AuthorRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findByLastnameAndFirstname(String lastname, String firstname) throws EntityNotFoundException {
        Optional<Author> author = authorRepository.findByLastnameAndFirstname(lastname, firstname);

        return author.orElseThrow(EntityNotFoundException.entityNotFoundException("Author '" + lastname + " " +
                firstname + "' not found"));
    }

    @Transactional
    public void save(Author author) {
        authorRepository.save(author);
    }
}

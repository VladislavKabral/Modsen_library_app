package by.modsen.library_app.service;

import by.modsen.library_app.model.Author;
import by.modsen.library_app.repository.AuthorRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() throws EntityNotFoundException {
        List<Author> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            throw new EntityNotFoundException("There aren't any authors in the library");
        }

        return authors;
    }

    public Author findById(int id) throws EntityNotFoundException {
        Optional<Author> author = authorRepository.findById(id);

        return author.orElseThrow(EntityNotFoundException.entityNotFoundException("Author with id: " + id + " not found"));
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

    @Transactional
    public void updateById(int id, Author author) throws EntityNotFoundException {
        Author authorFromDB = findById(id);

        authorFromDB.setLastname(author.getLastname());
        authorFromDB.setFirstname(author.getFirstname());

        authorRepository.save(authorFromDB);
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        Author author = findById(id);

        authorRepository.deleteById(author.getId());
    }
}

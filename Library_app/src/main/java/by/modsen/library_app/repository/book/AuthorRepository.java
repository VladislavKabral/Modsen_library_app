package by.modsen.library_app.repository.book;

import by.modsen.library_app.model.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findByLastnameAndFirstname(String lastname, String firstname);
}

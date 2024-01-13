package by.modsen.library_app.service.book;

import by.modsen.library_app.model.book.Genre;
import by.modsen.library_app.repository.book.GenreRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findByName(String name) throws EntityNotFoundException {
        Optional<Genre> genre = genreRepository.findByName(name);

        return genre.orElseThrow(EntityNotFoundException.entityNotFoundException("Genre with name '" + name + "' not found"));
    }
}

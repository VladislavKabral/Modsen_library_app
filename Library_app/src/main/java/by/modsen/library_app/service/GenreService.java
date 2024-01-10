package by.modsen.library_app.service;

import by.modsen.library_app.model.Genre;
import by.modsen.library_app.repository.GenreRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() throws EntityNotFoundException {
        List<Genre> genres = genreRepository.findAll();

        if (genres.isEmpty()) {
            throw new EntityNotFoundException("There aren't any genres in the library");
        }

        return genres;
    }

    public Genre findById(int id) throws EntityNotFoundException {
        Optional<Genre> genre = genreRepository.findById(id);

        return genre.orElseThrow(EntityNotFoundException.entityNotFoundException("Genre with id: " + id + " not found"));
    }

    public Genre findByName(String name) throws EntityNotFoundException {
        Optional<Genre> genre = genreRepository.findByName(name);

        return genre.orElseThrow(EntityNotFoundException.entityNotFoundException("Genre with name '" + name + "' not found"));
    }

    @Transactional
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Transactional
    public void updateById(int id, Genre genre) throws EntityNotFoundException {
        Genre genreFromDB = findById(id);

        genreFromDB.setName(genre.getName());
        genreFromDB.setDescription(genre.getDescription());

        genreRepository.save(genreFromDB);
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        Genre genre = findById(id);

        genreRepository.deleteById(genre.getId());
    }
}

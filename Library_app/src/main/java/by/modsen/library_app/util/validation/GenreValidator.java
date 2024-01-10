package by.modsen.library_app.util.validation;

import by.modsen.library_app.model.Genre;
import by.modsen.library_app.service.GenreService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GenreValidator implements Validator {

    private final GenreService genreService;

    @Autowired
    public GenreValidator(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Genre.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Genre genre = (Genre) target;

        try {
            Genre tempGenre = genreService.findByName(genre.getName());
            if ((tempGenre != null) && (tempGenre.getId() != genre.getId())) {
                errors.rejectValue("name", "", "Genre with name '" + genre.getName() +
                        "' already exists");
            }
        } catch (EntityNotFoundException e) {
            //TODO: add log
        }
    }
}

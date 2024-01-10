package by.modsen.library_app.util.validation;

import by.modsen.library_app.model.Author;
import by.modsen.library_app.service.AuthorService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorValidator implements Validator {

    private final AuthorService authorService;

    @Autowired
    public AuthorValidator(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        try {
            if (authorService.findByLastnameAndFirstname(author.getLastname(), author.getFirstname()) != null) {
                errors.rejectValue("lastname", "",  "Author '" + author.getLastname() + " " +
                        author.getFirstname() + "' already exists in the library");
            }
        } catch (EntityNotFoundException e) {
            //TODO add log
        }
    }
}

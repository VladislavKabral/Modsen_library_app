package by.modsen.library_app.util.validation;

import by.modsen.library_app.model.Book;
import by.modsen.library_app.service.BookService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        try {
            Book tempBook = bookService.findByISBN(book.getIsbn());
            if ((tempBook != null) && (tempBook.getId() != book.getId())) {
                errors.rejectValue("isbn", "", "Book with ISBN: '" + book.getIsbn() +
                        "' already exists");
            }
        } catch (EntityNotFoundException e) {
            //TODO: add log here
        }
    }
}

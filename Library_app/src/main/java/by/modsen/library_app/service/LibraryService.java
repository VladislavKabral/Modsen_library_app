package by.modsen.library_app.service;

import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryService {

    private final AvailableBookService availableBookService;

    @Autowired
    public LibraryService(AvailableBookService availableBookService) {
        this.availableBookService = availableBookService;
    }

    public void issueBook(AvailableBook availableBook) throws EntityNotFoundException {
        availableBookService.save(availableBook);
    }

    public void returnBook(int id) throws EntityNotFoundException {
        availableBookService.deleteById(id);
    }
}

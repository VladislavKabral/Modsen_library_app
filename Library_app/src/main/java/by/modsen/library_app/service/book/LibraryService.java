package by.modsen.library_app.service.book;

import by.modsen.library_app.model.book.AvailableBook;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryService {

    private final AvailableBookService availableBookService;

    private final BookService bookService;

    @Autowired
    public LibraryService(AvailableBookService availableBookService, BookService bookService) {
        this.availableBookService = availableBookService;
        this.bookService = bookService;
    }

    public List<AvailableBook> getAvailableBooks() throws EntityNotFoundException {
        return availableBookService.findAll()
                .stream()
                .filter(AvailableBook::isAvailable)
                .collect(Collectors.toList());
    }

    public void issueBook(AvailableBook availableBook) throws EntityNotFoundException {
        AvailableBook availableBookFromDB = availableBookService
                .findByBookId(bookService.findByISBN(availableBook.getBook().getIsbn()).getId());

        availableBookFromDB.setAvailable(false);
        availableBookFromDB.setStartDate(LocalDate.now());
        availableBookFromDB.setEndDate(availableBook.getEndDate());

        availableBookService.update(availableBookFromDB);
    }

    public void returnBook(AvailableBook availableBook) throws EntityNotFoundException {
        AvailableBook availableBookFromDB = availableBookService
                .findByBookId(bookService.findByISBN(availableBook.getBook().getIsbn()).getId());

        availableBookFromDB.setAvailable(true);
        availableBookFromDB.setStartDate(null);
        availableBookFromDB.setEndDate(null);

        availableBookService.update(availableBookFromDB);
    }
}

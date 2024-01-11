package by.modsen.library_app.service;

import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryService {

    private final AvailableBookService availableBookService;

    @Autowired
    public LibraryService(AvailableBookService availableBookService) {
        this.availableBookService = availableBookService;
    }

    public List<AvailableBook> getAvailableBooks() throws EntityNotFoundException {
        System.out.println(availableBookService.findAll()
                .stream()
                .filter(AvailableBook::isAvailable)
                .collect(Collectors.toList()));
        return availableBookService.findAll()
                .stream()
                .filter(AvailableBook::isAvailable)
                .collect(Collectors.toList());
    }

    public void issueBook(AvailableBook availableBook) throws EntityNotFoundException {
        AvailableBook availableBookFromDB = availableBookService.findByBookISBN(availableBook.getBook().getIsbn());

        availableBookFromDB.setAvailable(false);
        availableBookFromDB.setStartDate(LocalDate.now());
        availableBookFromDB.setEndDate(availableBook.getEndDate());

        availableBookService.update(availableBookFromDB);
    }

    public void returnBook(AvailableBook availableBook) throws EntityNotFoundException {
        AvailableBook availableBookFromDB = availableBookService.findByBookISBN(availableBook.getBook().getIsbn());

        availableBookFromDB.setAvailable(true);
        availableBookFromDB.setStartDate(null);
        availableBookFromDB.setEndDate(null);

        availableBookService.update(availableBookFromDB);
    }
}

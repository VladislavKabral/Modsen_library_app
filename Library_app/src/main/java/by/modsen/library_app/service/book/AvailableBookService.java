package by.modsen.library_app.service.book;

import by.modsen.library_app.model.book.AvailableBook;
import by.modsen.library_app.repository.book.AvailableBookRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AvailableBookService {

    private final AvailableBookRepository availableBookRepository;

    private static final Logger LOGGER = LogManager.getLogger(AvailableBookService.class);
    @Autowired
    public AvailableBookService(AvailableBookRepository availableBookRepository) {
        this.availableBookRepository = availableBookRepository;
    }

    public List<AvailableBook> findAll() throws EntityNotFoundException {
        List<AvailableBook> availableBooks = availableBookRepository.findAll();

        if (availableBooks.isEmpty()) {
            throw new EntityNotFoundException("There aren't any available books");
        }

        return availableBooks;
    }

    public AvailableBook findById(int id) throws EntityNotFoundException {
        Optional<AvailableBook> availableBook = availableBookRepository.findById(id);

        return availableBook.orElseThrow(EntityNotFoundException.entityNotFoundException("Available book with id: " + id
                + " not found"));
    }

    public AvailableBook findByBookId(int bookId) throws EntityNotFoundException {
        Optional<AvailableBook> availableBook = availableBookRepository.findByBook_Id(bookId);

        return availableBook.orElseThrow(EntityNotFoundException.entityNotFoundException("Available book with book's id: "
                + bookId + " not found"));
    }

    @Transactional
    public void save(AvailableBook availableBook) {
        availableBook.setBook(availableBook.getBook());
        availableBook.setAvailable(true);
        availableBook.setStartDate(null);
        availableBook.setEndDate(null);

        availableBookRepository.save(availableBook);
        LOGGER.info("New book with ISBN '" + availableBook.getBook().getIsbn() + "' has been saved as available book");
    }

    @Transactional
    public void update(AvailableBook availableBook) {
        availableBookRepository.save(availableBook);
        String bookStatus = availableBook.isAvailable() ? "Available" : "Not available";
        LOGGER.info("Book's status with ISBN '" + availableBook.getBook().getIsbn() + "' has been updated with status '" +
                bookStatus + "'");
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        AvailableBook availableBook = findById(id);

        availableBookRepository.deleteById(availableBook.getId());
        LOGGER.info("Book with id '" + availableBook.getBook().getId() + "' has been deleted from available book's list");
    }
}

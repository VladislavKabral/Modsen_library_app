package by.modsen.library_app.service;

import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.model.Book;
import by.modsen.library_app.repository.AvailableBookRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AvailableBookService {

    private final AvailableBookRepository availableBookRepository;

    private final BookService bookService;

    @Autowired
    public AvailableBookService(AvailableBookRepository availableBookRepository, BookService bookService) {
        this.availableBookRepository = availableBookRepository;
        this.bookService = bookService;
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

    @Transactional
    public void save(AvailableBook availableBook) throws EntityNotFoundException {
        Book book = bookService.findByName(availableBook.getBook().getName());

        availableBook.setBook(book);

        availableBookRepository.save(availableBook);
    }

    @Transactional
    public void updateById(int id, AvailableBook availableBook) throws EntityNotFoundException {
        AvailableBook availableBookFromDB = findById(id);

        availableBookFromDB.setBook(availableBook.getBook());
        availableBookFromDB.setStartDate(availableBook.getStartDate());
        availableBookFromDB.setEndDate(availableBook.getEndDate());

        availableBookRepository.save(availableBook);
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        AvailableBook availableBook = findById(id);

        availableBookRepository.deleteById(availableBook.getId());
    }
}

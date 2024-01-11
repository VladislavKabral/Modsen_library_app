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

    public AvailableBook findByBookId(int bookId) throws EntityNotFoundException {
        Optional<AvailableBook> availableBook = availableBookRepository.findByBook_Id(bookId);

        return availableBook.orElseThrow(EntityNotFoundException.entityNotFoundException("Available book with book's id: "
                + bookId + " not found"));
    }

    public AvailableBook findByBookISBN(String isbn) throws EntityNotFoundException {
        Book book = bookService.findByISBN(isbn);

        return findByBookId(book.getId());
    }

    @Transactional
    public void save(AvailableBook availableBook) throws EntityNotFoundException {
        Book book = bookService.findByISBN(availableBook.getBook().getIsbn());

        availableBook.setBook(book);
        availableBook.setAvailable(true);

        availableBookRepository.save(availableBook);
    }

    @Transactional
    public void update(AvailableBook availableBook) {
        availableBookRepository.save(availableBook);
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        AvailableBook availableBook = findById(id);

        availableBookRepository.deleteById(availableBook.getId());
    }
}

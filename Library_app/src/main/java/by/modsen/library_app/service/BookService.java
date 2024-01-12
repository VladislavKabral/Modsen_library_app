package by.modsen.library_app.service;

import by.modsen.library_app.model.Author;
import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.model.Book;
import by.modsen.library_app.model.Genre;
import by.modsen.library_app.repository.BookRepository;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final AvailableBookService availableBookService;
    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService, AvailableBookService availableBookService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.availableBookService = availableBookService;
    }

    public List<Book> findAll() throws EntityNotFoundException {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new EntityNotFoundException("There aren't any book in the library");
        }

        return books;
    }

    public Book findById(int id) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        return book.orElseThrow(EntityNotFoundException.entityNotFoundException("Book with id: " + id + " not found"));
    }

    public Book findByISBN(String isbn) throws EntityNotFoundException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);

        return book.orElseThrow(EntityNotFoundException.entityNotFoundException("Book with ISBN: '" + isbn + "' not found"));
    }

    public List<Book> findByName(String name) throws EntityNotFoundException {
        List<Book> books = bookRepository.findByName(name);

        if (books.isEmpty()) {
            throw new EntityNotFoundException("Books with name: '" + name + "' not found");
        }

        return books;
    }

    @Transactional
    public void save(Book book) throws EntityNotFoundException {
        Author author;
        try {
            author = authorService.findByLastnameAndFirstname(book.getAuthor().getLastname(),
                    book.getAuthor().getFirstname());
        } catch (EntityNotFoundException e) {
            author = new Author();
            author.setFirstname(book.getAuthor().getFirstname());
            author.setLastname(book.getAuthor().getLastname());
            authorService.save(author);
        }

        Genre genre = genreService.findByName(book.getGenre().getName());

        book.setAuthor(author);
        book.setGenre(genre);
        book.setName(String.valueOf(book.getName().charAt(0)).toUpperCase() + book.getName().substring(1));
        book.setDescription(String.valueOf(book.getDescription().charAt(0)).toUpperCase() +
                book.getDescription().substring(1));

        bookRepository.save(book);

        AvailableBook availableBook = new AvailableBook();
        availableBook.setBook(findByISBN(book.getIsbn()));

        availableBookService.save(availableBook);
    }

    @Transactional
    public void updateById(int id, Book book) throws EntityNotFoundException {
        Book bookFromDB = findById(id);

        Author author;
        try {
            author = authorService.findByLastnameAndFirstname(book.getAuthor().getLastname(),
                    book.getAuthor().getFirstname());
        } catch (EntityNotFoundException e) {
            author = new Author();
            author.setFirstname(book.getAuthor().getFirstname());
            author.setLastname(book.getAuthor().getLastname());
            authorService.save(author);
        }

        Genre genre = genreService.findByName(book.getGenre().getName());

        bookFromDB.setName(book.getName());
        bookFromDB.setDescription(book.getDescription());
        bookFromDB.setIsbn(book.getIsbn());
        bookFromDB.setAuthor(author);
        bookFromDB.setGenre(genre);

        bookRepository.save(bookFromDB);
    }

    @Transactional
    public void deleteById(int id) throws EntityNotFoundException {
        Book book = findById(id);

        availableBookService.deleteById(availableBookService.findByBookId(id).getId());
        bookRepository.deleteById(book.getId());
    }
}

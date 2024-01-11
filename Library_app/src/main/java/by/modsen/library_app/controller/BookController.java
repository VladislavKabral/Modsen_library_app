package by.modsen.library_app.controller;

import by.modsen.library_app.dto.BookDTO;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.model.Book;
import by.modsen.library_app.service.BookService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.validation.BookValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library/api/books")
public class BookController {

    private final BookService bookService;

    private final ModelMapper mapper;

    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, ModelMapper mapper, BookValidator bookValidator) {
        this.bookService = bookService;
        this.mapper = mapper;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() throws EntityNotFoundException {
        return new ResponseEntity<>(bookService.findAll()
                .stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"id"})
    public ResponseEntity<BookDTO> getBookById(@RequestParam("id") int id) throws EntityNotFoundException {
        return new ResponseEntity<>(convertToBookDTO(bookService.findById(id)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"isbn"})
    public ResponseEntity<BookDTO> getBookByISBN(@RequestParam("isbn") String isbn) throws EntityNotFoundException {
        return new ResponseEntity<>(convertToBookDTO(bookService.findByISBN(isbn)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"name"})
    public ResponseEntity<BookDTO> getBookByName(@RequestParam("name") String name) throws EntityNotFoundException {
        return new ResponseEntity<>(convertToBookDTO(bookService.findByName(name)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveBook(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult)
            throws EntityValidateException, EntityNotFoundException {

        Book book = convertToBook(bookDTO);

        bookValidator.validate(book, bindingResult);

        handleBindingResult(bindingResult);

        bookService.save(book);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") int id, @RequestBody @Valid BookDTO bookDTO,
                                 BindingResult bindingResult) throws EntityValidateException, EntityNotFoundException {

        Book book = convertToBook(bookDTO);
        book.setId(id);

        bookValidator.validate(book, bindingResult);

        handleBindingResult(bindingResult);

        bookService.updateById(id, book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) throws EntityNotFoundException {
        bookService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    private void handleBindingResult(BindingResult bindingResult) throws EntityValidateException {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new EntityValidateException(message.toString());
        }
    }

    private BookDTO convertToBookDTO(Book book) {
        return mapper.map(book, BookDTO.class);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return mapper.map(bookDTO, Book.class);
    }
}

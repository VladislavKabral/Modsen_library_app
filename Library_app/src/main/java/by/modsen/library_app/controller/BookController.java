package by.modsen.library_app.controller;

import by.modsen.library_app.dto.BookDTO;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.model.Book;
import by.modsen.library_app.service.BookService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.validation.BookValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "BookController", description = "Allows to works with books: get, create, update, delete")
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
    @Operation(
            summary = "Getting all books in the library",
            description = "Allows to get all books, which the library has"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<BookDTO>> getBooks() throws EntityNotFoundException {
        return new ResponseEntity<>(bookService.findAll()
                .stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"id"})
    @Operation(
            summary = "Getting book by id",
            description = "Allows to get book by book's id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<BookDTO> getBookById(@RequestParam("id") @Parameter(description = "Book's id", required = true)
                                                   int id) throws EntityNotFoundException {
        return new ResponseEntity<>(convertToBookDTO(bookService.findById(id)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"isbn"})
    @Operation(
            summary = "Getting book by ISBN",
            description = "Allows to get book by book's ISBN"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<BookDTO> getBookByISBN(@RequestParam("isbn") @Parameter(description = "Book's ISBN",
            required = true) String isbn) throws EntityNotFoundException {
        return new ResponseEntity<>(convertToBookDTO(bookService.findByISBN(isbn)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/book", params = {"name"})
    @Operation(
            summary = "Getting book by name",
            description = "Allows to get book by book's name"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<BookDTO>> getBookByName(@RequestParam("name") @Parameter(description = "Book's name",
            required = true) String name) throws EntityNotFoundException {
        return new ResponseEntity<>(bookService.findByName(name)
                .stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Saving book in the library",
            description = "Allows to save new book in the library storage"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> saveBook(@RequestBody @Valid @Parameter(description = "New book's data for saving",
                                                    required = true) BookDTO bookDTO,
                                               @Parameter(description = "Validation's errors") BindingResult bindingResult)
            throws EntityValidateException, EntityNotFoundException {

        Book book = convertToBook(bookDTO);

        bookValidator.validate(book, bindingResult);

        handleBindingResult(bindingResult);

        bookService.save(book);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Updating book in the library",
            description = "Allows to update book's data in the library by book's id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") @Parameter(description = "Book's id", required = true)
                                                     int id,
                                                 @RequestBody @Valid @Parameter(description = "Book's data for updating",
                                                     required = true) BookDTO bookDTO,
                                                 @Parameter(description = "Validation's errors")
                                                     BindingResult bindingResult)
            throws EntityValidateException, EntityNotFoundException {

        Book book = convertToBook(bookDTO);
        book.setId(id);

        bookValidator.validate(book, bindingResult);

        handleBindingResult(bindingResult);

        bookService.updateById(id, book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleting book from the library",
            description = "Allows to delete book from the library by book's id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") @Parameter(description = "Book's id", required = true)
                                                     int id)
            throws EntityNotFoundException {

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

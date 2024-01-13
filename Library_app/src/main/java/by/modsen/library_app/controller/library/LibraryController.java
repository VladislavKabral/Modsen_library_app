package by.modsen.library_app.controller.library;

import by.modsen.library_app.controller.Url;
import by.modsen.library_app.dto.book.AvailableBookDTO;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.model.book.AvailableBook;
import by.modsen.library_app.service.book.LibraryService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.exception.InvalidParamException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Url.Library.PATH)
@Tag(name = "LibraryController", description = "Allows to works with books: get available books, issue and return")
public class LibraryController {

    private static final String ACTION_PARAM_NAME = "action";

    private static final String ISSUE_ACTION_NAME = "issue";

    private static final String RETURN_ACTION_NAME = "return";

    private static final String SECURITY_REQUIREMENT_NAME = "JWT";

    private final LibraryService libraryService;

    private final ModelMapper mapper;

    @Autowired
    public LibraryController(LibraryService libraryService, ModelMapper mapper) {
        this.libraryService = libraryService;
        this.mapper = mapper;
    }

    @GetMapping(Url.Library.AVAILABLE_BOOKS)
    @Operation(
            summary = "Getting all available books",
            description = "Allows to get all available books in the library"
    )
    @SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
    public ResponseEntity<List<AvailableBookDTO>> getAvailableBooks() throws EntityNotFoundException {
        return new ResponseEntity<>(libraryService.getAvailableBooks()
                .stream()
                .map(this::convertToAvailableBookDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PatchMapping(value = Url.BOOK, params = {ACTION_PARAM_NAME})
    @Operation(
            summary = "Handling books",
            description = "Allows to handle a book: issue or return by book's ISBN"
    )
    @SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
    public ResponseEntity<HttpStatus> handleBook(@RequestBody @Valid @Parameter(description = "Data of handling book",
                                                         required = true) AvailableBookDTO availableBookDTO,
                                                 @RequestParam(ACTION_PARAM_NAME)
                                                 @Parameter(description = "Type of handle action",
                                                         required = true) String action)
            throws EntityValidateException, EntityNotFoundException, InvalidParamException {

        switch (action) {
            case ISSUE_ACTION_NAME -> {
                handleDate(availableBookDTO.getEndDate());
                libraryService.issueBook(convertToAvailableBook(availableBookDTO));
            }
            case RETURN_ACTION_NAME -> libraryService.returnBook(convertToAvailableBook(availableBookDTO));
            default -> throw new InvalidParamException("'" + action + "' is wrong param in request");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    private void handleDate(LocalDate endDate) throws EntityValidateException {
        if (LocalDate.now().isAfter(endDate)) {
            throw new EntityValidateException("End date must be after start date");
        }
    }

    private AvailableBookDTO convertToAvailableBookDTO(AvailableBook availableBook) {
        return mapper.map(availableBook, AvailableBookDTO.class);
    }

    private AvailableBook convertToAvailableBook(AvailableBookDTO availableBookDTO) {
        return mapper.map(availableBookDTO, AvailableBook.class);
    }

}

package by.modsen.library_app.controller;

import by.modsen.library_app.dto.AvailableBookDTO;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.service.LibraryService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.exception.InvalidParamException;
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
@RequestMapping("/library/api")
public class LibraryController {

    private final LibraryService libraryService;

    private final ModelMapper mapper;

    @Autowired
    public LibraryController(LibraryService libraryService, ModelMapper mapper) {
        this.libraryService = libraryService;
        this.mapper = mapper;
    }

    @GetMapping("/availableBooks")
    public ResponseEntity<List<AvailableBookDTO>> getAvailableBooks() throws EntityNotFoundException {
        return new ResponseEntity<>(libraryService.getAvailableBooks()
                .stream()
                .map(this::convertToAvailableBookDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PatchMapping(value = "/book", params = {"action"})
    public ResponseEntity<HttpStatus> issueBook(@RequestBody @Valid AvailableBookDTO availableBookDTO,
                                                @RequestParam("action") String action)
            throws EntityValidateException, EntityNotFoundException, InvalidParamException {

        switch (action) {
            case "issue" -> {
                handleDate(availableBookDTO.getEndDate());
                libraryService.issueBook(convertToAvailableBook(availableBookDTO));
            }
            case "return" -> libraryService.returnBook(convertToAvailableBook(availableBookDTO));
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

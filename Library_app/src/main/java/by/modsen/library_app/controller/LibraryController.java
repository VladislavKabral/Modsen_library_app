package by.modsen.library_app.controller;

import by.modsen.library_app.dto.AvailableBookDTO;
import by.modsen.library_app.dto.ErrorResponseDTO;
import by.modsen.library_app.model.AvailableBook;
import by.modsen.library_app.service.LibraryService;
import by.modsen.library_app.util.exception.EntityNotFoundException;
import by.modsen.library_app.util.exception.EntityValidateException;
import by.modsen.library_app.util.exception.InvalidParamException;
import by.modsen.library_app.util.validation.DateValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/library/api")
public class LibraryController {

    private final LibraryService libraryService;

    private final DateValidator dateValidator;

    private final SimpleDateFormat dateFormat;

    private final ModelMapper mapper;

    @Autowired
    public LibraryController(LibraryService libraryService, DateValidator dateValidator, ModelMapper mapper) {
        this.libraryService = libraryService;
        this.dateValidator = dateValidator;
        this.mapper = mapper;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostMapping(value = "/book", params = {"action"})
    public ResponseEntity<HttpStatus> issueBook(@RequestBody @Valid AvailableBookDTO availableBookDTO,
                                                @RequestParam("action") String action)
            throws EntityValidateException, EntityNotFoundException, InvalidParamException {

        if (action.equals("issue")) {
            handleDates(availableBookDTO.getStartDate().toString(), availableBookDTO.getEndDate().toString());

            libraryService.issueBook(convertToAvailableBook(availableBookDTO));
        } else {
            throw new InvalidParamException(action + " is wrong param in request");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(params = {"return"})
    public ResponseEntity<HttpStatus> returnBook() {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    private void handleDates(String startDate, String endDate) throws EntityValidateException {
        if (!dateValidator.validate(startDate)) {
            throw new EntityValidateException("Wrong start date format. Correct format is 'yyyy-MM-dd'");
        }

        if (!dateValidator.validate(endDate)) {
            throw new EntityValidateException("Wrong end date format. Correct format is 'yyyy-MM-dd'");
        }
    }

    private AvailableBookDTO convertToAvailableBookDTO(AvailableBook availableBook) {
        return mapper.map(availableBook, AvailableBookDTO.class);
    }

    private AvailableBook convertToAvailableBook(AvailableBookDTO availableBookDTO) {
        return mapper.map(availableBookDTO, AvailableBook.class);
    }

}

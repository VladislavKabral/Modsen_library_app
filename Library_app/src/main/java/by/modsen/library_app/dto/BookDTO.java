package by.modsen.library_app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @NotEmpty(message = "Book's ISBN must be not empty")
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "Wrong ISBN format")
    private String isbn;

    @NotEmpty(message = "Book's name must be not empty")
    @Size(min = 1, max = 50, message = "Book's name must be between 1 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Book's name must contain only letters")
    private String name;

    @NotNull(message = "Book must have an author")
    private AuthorDTO author;

    @NotEmpty(message = "Book's description must be not empty")
    @Size(min = 4, max = 255, message = "Book's description must be between 4 and 255 symbols")
    @Pattern(regexp = "^[a-zA-Z0-9-., ]+$", message = "Book's description must contain only letters and numbers")
    private String description;

    @NotNull(message = "Book must have a genre")
    private GenreDTO genre;
}

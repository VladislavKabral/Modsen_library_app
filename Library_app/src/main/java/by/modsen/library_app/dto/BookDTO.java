package by.modsen.library_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entity of a book")
public class BookDTO {

    @NotEmpty(message = "Book's ISBN must be not empty")
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "Wrong ISBN format")
    @Schema(description = "Book's ISBN", example = "978-5-699-12014-9")
    private String isbn;

    @NotEmpty(message = "Book's name must be not empty")
    @Size(min = 1, max = 50, message = "Book's name must be between 1 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Book's name must contain only letters")
    @Schema(description = "Book's name", example = "Pride and prejudice")
    private String name;

    @NotNull(message = "Book must have an author")
    @Schema(description = "Book's author")
    private AuthorDTO author;

    @NotEmpty(message = "Book's description must be not empty")
    @Size(min = 4, max = 255, message = "Book's description must be between 4 and 255 symbols")
    @Pattern(regexp = "^[a-zA-Z0-9-., ]+$", message = "Book's description must contain only letters and numbers")
    @Schema(description = "Book's description", example = "A gothic novel that follows the antihero, Heathcliff," +
            " as he gets revenge the people who kept him away from his love, Cathy Earnshaw")
    private String description;

    @NotNull(message = "Book must have a genre")
    @Schema(description = "Book's genre")
    private GenreDTO genre;
}
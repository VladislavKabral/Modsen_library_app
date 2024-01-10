package by.modsen.library_app.dto;

import by.modsen.library_app.model.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {

    @NotEmpty(message = "Genre's name must be not empty")
    @Size(min = 4, max = 50, message = "Genre's name must be between 4 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Genre's name must contain only letters")
    private String name;

    @NotEmpty(message = "Genre's description must be not empty")
    @Size(min = 4, max = 255, message = "Genre's description must be between 4 and 255 symbols")
    @Pattern(regexp = "^[a-zA-Z0-9-., ]+$", message = "Genre's description must contain only letters and numbers")
    private String description;

    private List<Book> books;
}

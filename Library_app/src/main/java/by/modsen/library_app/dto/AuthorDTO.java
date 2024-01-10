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
public class AuthorDTO {

    @NotEmpty(message = "Author's lastname must be not empty")
    @Size(min = 2, max = 50, message = "Author's lastname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's lastname must contain only letters")
    private String lastname;

    @NotEmpty(message = "Author's firstname must be not empty")
    @Size(min = 2, max = 50, message = "Author's firstname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's firstname must contain only letters")
    private String firstname;

    private List<Book> books;
}

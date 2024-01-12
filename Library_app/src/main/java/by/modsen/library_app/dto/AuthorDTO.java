package by.modsen.library_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity of an author")
public class AuthorDTO {

    @NotEmpty(message = "Author's lastname must be not empty")
    @Size(min = 2, max = 50, message = "Author's lastname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's lastname must contain only letters")
    @Schema(description = "Author's lastname", example = "Ivanov")
    private String lastname;

    @NotEmpty(message = "Author's firstname must be not empty")
    @Size(min = 2, max = 50, message = "Author's firstname must be between 2 and 50 symbols")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Author's firstname must contain only letters")
    @Schema(description = "Author's firstname", example = "Ivan")
    private String firstname;
}
